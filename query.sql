-- Create the database
CREATE DATABASE IF NOT EXISTS perfume_management;
USE perfume_management;

-- Stores table (contains all perfume shops)
CREATE TABLE IF NOT EXISTS stores (
    store_id INT AUTO_INCREMENT PRIMARY KEY,
    store_name VARCHAR(100) NOT NULL,
    location VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Perfume table (contains all perfume products)
CREATE TABLE IF NOT EXISTS perfume (
    perfume_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    brand VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Stock table (connects perfumes to stores with quantity)
CREATE TABLE IF NOT EXISTS stock (
    stock_id INT AUTO_INCREMENT PRIMARY KEY,
    perfume_id INT NOT NULL,
    store_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (perfume_id) REFERENCES perfume(perfume_id) ON DELETE CASCADE,
    FOREIGN KEY (store_id) REFERENCES stores(store_id) ON DELETE CASCADE,
    UNIQUE KEY (perfume_id, store_id) -- Prevent duplicate entries
);

-- Users table (for authentication)
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL, -- Store hashed passwords
    role ENUM('employee', 'manager', 'admin') NOT NULL,
    store_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (store_id) REFERENCES stores(store_id) ON DELETE SET NULL
);

-- Insert sample stores
INSERT INTO stores (store_name, location) VALUES
('Parfumerie Centrale', 'București, Strada Unirii 15'),
('Parfumerie Nord', 'Cluj-Napoca, Piața Mihai Viteazu 3'),
('Parfumerie Sud', 'Timișoara, Bulevardul Revoluției 10');

-- Insert sample perfumes
INSERT INTO perfume (name, brand, price, description) VALUES
('Chanel No 5', 'Chanel', 120.99, 'Parfum iconic floral-aldehidic'),
('Sauvage', 'Dior', 95.50, 'Parfum masculin fresh spicy'),
('Black Opium', 'Yves Saint Laurent', 88.75, 'Parfum feminin oriental floral'),
('Acqua di Giò', 'Armani', 76.20, 'Parfum masculin aquatic');

-- Insert sample stock data
INSERT INTO stock (perfume_id, store_id, quantity) VALUES
(1, 1, 15), -- Chanel No 5 in Parfumerie Centrale
(1, 2, 8),  -- Chanel No 5 in Parfumerie Nord
(2, 1, 22), -- Sauvage in Parfumerie Centrale
(3, 3, 5),  -- Black Opium in Parfumerie Sud
(4, 2, 12); -- Acqua di Giò in Parfumerie Nord

-- Insert users with easy passwords
INSERT INTO users (username, password, role, store_id) VALUES
('admin', 'admin', 'admin', NULL),       -- password: admin
('manager1', 'manager', 'manager', 1),   -- password: manager
('employee1', '12345', 'employee', 1),   -- password: 12345
('employee2', 'parfum', 'employee', 2);  -- password: parfum

-- Create indexes for better performance
CREATE INDEX idx_perfume_brand ON perfume(brand);
CREATE INDEX idx_stock_store ON stock(store_id);
CREATE INDEX idx_stock_perfume ON stock(perfume_id);