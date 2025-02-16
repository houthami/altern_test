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