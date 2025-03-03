CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at DATETIME DEFAULT NOW()
);

CREATE TABLE task (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at DATETIME DEFAULT NOW()
);

INSERT INTO seek.user (name, email, password)
VALUES
('Juan', 'Perez@gmail.com', '12345678'),
('Maria', 'Gomez@gmail.com', '12345678'),
('Julio', 'Isla@gmail.com', '12345678'),
('Carlos', 'Lopez@gmail.com', '12345678'),
('Ana', 'Diaz@gmail.com', '12345678'),
('Pedro', 'Martinez@gmail.com', '12345678'),
('Laura', 'Sanchez@gmail.com', '12345678'),
('Jose', 'Ramirez@gmail.com', '12345678'),
('Lucia', 'Hernandez@gmail.com', '12345678'),
('Felipe', 'Cruz@gmail.com', '12345678');