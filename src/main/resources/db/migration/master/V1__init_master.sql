-- Crear tabla tenants
CREATE TABLE tenants (
                         id VARCHAR(255) NOT NULL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL UNIQUE,
                         db_name VARCHAR(255) NOT NULL,
                         db_url VARCHAR(512) NOT NULL,
                         db_username VARCHAR(255) NOT NULL,
                         db_password VARCHAR(255) NOT NULL,
                         active BOOLEAN NOT NULL,
                         created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);


-- Crear tabla users
CREATE TABLE users (
                       id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       tenant_id VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       first_name VARCHAR(255),
                       last_name VARCHAR(255),
                       enabled BOOLEAN NOT NULL DEFAULT TRUE,
                       created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                       updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);