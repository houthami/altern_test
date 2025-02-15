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
