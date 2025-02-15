import { Injectable, inject, signal } from "@angular/core";
import { Product } from "./product.model";
import { HttpClient, HttpParams } from "@angular/common/http";
import { catchError, delay, map, Observable, of, tap } from "rxjs";

@Injectable({
    providedIn: "root"
}) export class ProductsService {

    private readonly http = inject(HttpClient);
    private readonly path = "/api/products";
    
    private readonly _products = signal<Product[]>([]);

    public readonly products = this._products.asReadonly();

    private readonly _paginatedProducts = signal<Product[]>([]);
    public readonly paginatedProducts = this._paginatedProducts.asReadonly();

    public get(): Observable<Product[]> {
        return this.http.get<Product[]>(this.path).pipe(
            catchError((error) => {
                return this.http.get<Product[]>("assets/products.json");
            }),
            tap((products) => this._products.set(products)),
        );
    }

    public create(product: Product): Observable<boolean> {
        return this.http.post<boolean>(this.path, product).pipe(
            catchError(() => {
                return of(true);
            }),
            tap(() => this._products.update(products => [product, ...products])),
        );
    }

    public update(product: Product): Observable<boolean> {
        return this.http.patch<boolean>(`${this.path}/${product.id}`, product).pipe(
            catchError(() => {
                return of(true);
            }),
            tap(() => this._products.update(products => {
                return products.map(p => p.id === product.id ? product : p)
            })),
        );
    }

    public delete(productId: number): Observable<boolean> {
        return this.http.delete<boolean>(`${this.path}/${productId}`).pipe(
            catchError(() => {
                return of(true);
            }),
            tap(() => this._products.update(products => products.filter(product => product.id !== productId))),
        );
    }

    // Add paginated get method
    public getPaginated(page: number, pageSize: number, searchTerm?: string): Observable<Product[]> {
        const params = new HttpParams()
          .set('page', page.toString())
          .set('pageSize', pageSize.toString())
          .set('search', searchTerm || '');
      
        return this.http.get<Product[]>(this.path, { params }).pipe(
          tap((products) => this._paginatedProducts.set(products)),
          catchError((error) => {
            return this.http.get<Product[]>("assets/products.json").pipe(
                delay(250), 
                map((products) => {
                  if (searchTerm) {
                    const lowerCaseSearch = searchTerm.toLowerCase();
                    products = products.filter(product =>
                      product.name.toLowerCase().includes(lowerCaseSearch) ||
                      product.category.toLowerCase().includes(lowerCaseSearch) ||
                      product.description.toLowerCase().includes(lowerCaseSearch)
                    );
                  }
        
                  const startIndex = (page - 1) * pageSize;
                  return products.slice(startIndex, startIndex + pageSize);
                })
              );
          })
        );
      }
}