CREATE TABLE PRODUCT (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         code VARCHAR(255),
                         name VARCHAR(255),
                         description VARCHAR(255),
                         image VARCHAR(255),
                         category VARCHAR(255),
                         price DOUBLE,
                         quantity INT,
                         internal_reference VARCHAR(255),
                         shell_id BIGINT,
                         inventory_status VARCHAR(50),
                         rating INT,
                         created_at TIMESTAMP,
                         updated_at TIMESTAMP
);

CREATE TABLE MP_USER (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      username VARCHAR(255),
      firstname VARCHAR(255),
      email VARCHAR(255),
      role VARCHAR(255),
      password VARCHAR(255),
      created_at TIMESTAMP,
      updated_at TIMESTAMP
);

-- Nouvelle table CART
CREATE TABLE CART (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      user_id BIGINT UNIQUE,
                      FOREIGN KEY (user_id) REFERENCES MP_USER(id) ON DELETE CASCADE
);

-- Nouvelle table CART_ITEM
CREATE TABLE CART_ITEM (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           cart_id BIGINT,
                           product_id BIGINT,
                           quantity INT NOT NULL CHECK (quantity > 0),
                           FOREIGN KEY (cart_id) REFERENCES CART(id) ON DELETE CASCADE,
                           FOREIGN KEY (product_id) REFERENCES PRODUCT(id) ON DELETE CASCADE
);

-- Index pour optimiser les jointures
CREATE INDEX idx_cart_item_cart ON CART_ITEM(cart_id);
CREATE INDEX idx_cart_item_product ON CART_ITEM(product_id);