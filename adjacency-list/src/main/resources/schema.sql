CREATE TABLE category
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    parent_id INT,
    name      VARCHAR(100) NOT NULL,
    FOREIGN KEY (parent_id) REFERENCES category (id)
);
