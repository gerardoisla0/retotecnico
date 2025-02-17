CREATE TABLE customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    document_id VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    birth_day DATE NOT NULL
);

INSERT INTO seek.customer (name, last_name, document_id, age, birth_day)
VALUES
('Juan', 'Perez', '47154481', 28, '1996-01-15'),
('Maria', 'Gomez', '47154482', 35, '1989-07-22'),
('Julio', 'Isla', '47154491', 33, '1991-08-22'),
('Carlos', 'Lopez', '47154483', 41, '1983-10-10'),
('Ana', 'Diaz', '47154484', 27, '1996-05-11'),
('Pedro', 'Martinez', '47154485', 38, '1986-02-28'),
('Laura', 'Sanchez', '47154486', 30, '1993-03-25'),
('Jose', 'Ramirez', '47154487', 45, '1978-04-05'),
('Lucia', 'Hernandez', '47154488', 25, '1997-12-18'),
('Felipe', 'Cruz', '47154489', 50, '1974-11-30');