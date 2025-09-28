CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY,
    username VARCHAR(50),
    name VARCHAR(100),
    email VARCHAR(100)
);

INSERT INTO users (id, username, name, email) VALUES 
(1, 'admin', 'Administrator', 'admin@example.com'),
(2, 'user1', 'John Doe', 'john@example.com'),
(3, 'user2', 'Jane Smith', 'jane@example.com');