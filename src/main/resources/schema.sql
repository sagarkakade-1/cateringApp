-- Catering Management System Database Schema
-- PostgreSQL Database Schema for all modules

-- Drop tables if they exist (for clean setup)
DROP TABLE IF EXISTS task_assignments CASCADE;
DROP TABLE IF EXISTS order_inventory CASCADE;
DROP TABLE IF EXISTS order_employees CASCADE;
DROP TABLE IF EXISTS tasks CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS inventory CASCADE;
DROP TABLE IF EXISTS employees CASCADE;
DROP TABLE IF EXISTS customers CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- Users table for authentication
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    full_name VARCHAR(100),
    role VARCHAR(20) DEFAULT 'ADMIN',
    active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Customers table (one-time customers and permanent clients)
CREATE TABLE customers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    contact_person VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(100),
    address TEXT,
    customer_type VARCHAR(20) DEFAULT 'ONE_TIME', -- ONE_TIME, PERMANENT
    business_type VARCHAR(50), -- HOTEL, EVENT_MANAGER, CATERING_SERVICE, INDIVIDUAL
    payment_terms VARCHAR(50),
    credit_limit DECIMAL(10,2) DEFAULT 0,
    total_orders INTEGER DEFAULT 0,
    total_amount DECIMAL(12,2) DEFAULT 0,
    outstanding_amount DECIMAL(12,2) DEFAULT 0,
    active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Employees table
CREATE TABLE employees (
    id BIGSERIAL PRIMARY KEY,
    employee_code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    address TEXT,
    employee_type VARCHAR(30) NOT NULL, -- COOK, BAI, WAITER, DRIVER, DISPLAY_TABLE_BOY, SERVICE_BOY
    hire_date DATE,
    salary_per_order DECIMAL(8,2) DEFAULT 0,
    base_salary DECIMAL(10,2) DEFAULT 0,
    total_orders_served INTEGER DEFAULT 0,
    total_earnings DECIMAL(12,2) DEFAULT 0,
    active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Inventory table
CREATE TABLE inventory (
    id BIGSERIAL PRIMARY KEY,
    item_code VARCHAR(30) UNIQUE NOT NULL,
    item_name VARCHAR(100) NOT NULL,
    category VARCHAR(50), -- UTENSILS, DISPLAY_TABLES, WATER_CANS, VEGETABLES, GROCERY, EQUIPMENT
    description TEXT,
    unit VARCHAR(20), -- PIECES, KG, LITERS, BOXES
    current_stock INTEGER DEFAULT 0,
    minimum_stock INTEGER DEFAULT 0,
    unit_cost DECIMAL(8,2) DEFAULT 0,
    total_value DECIMAL(10,2) DEFAULT 0,
    supplier VARCHAR(100),
    location VARCHAR(100),
    active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Orders table
CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    order_number VARCHAR(30) UNIQUE NOT NULL,
    customer_id BIGINT REFERENCES customers(id),
    order_type VARCHAR(20) NOT NULL, -- FULL_CATERING, HALF_CATERING
    event_name VARCHAR(200),
    event_date DATE NOT NULL,
    event_time TIME,
    venue_address TEXT,
    guest_count INTEGER,
    menu_details TEXT,
    special_requirements TEXT,
    order_status VARCHAR(20) DEFAULT 'PENDING', -- PENDING, IN_PROGRESS, COMPLETED, CANCELLED
    total_amount DECIMAL(12,2) DEFAULT 0,
    advance_amount DECIMAL(12,2) DEFAULT 0,
    remaining_amount DECIMAL(12,2) DEFAULT 0,
    payment_status VARCHAR(20) DEFAULT 'PENDING', -- PENDING, ADVANCE_PAID, FULLY_PAID
    created_by BIGINT REFERENCES users(id),
    assigned_cook BIGINT REFERENCES employees(id),
    assigned_driver BIGINT REFERENCES employees(id),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Order-Employee assignments (many-to-many)
CREATE TABLE order_employees (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT REFERENCES orders(id) ON DELETE CASCADE,
    employee_id BIGINT REFERENCES employees(id),
    role_in_order VARCHAR(50), -- COOK, WAITER, SERVICE_BOY, DRIVER, etc.
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_amount DECIMAL(8,2) DEFAULT 0,
    payment_status VARCHAR(20) DEFAULT 'PENDING' -- PENDING, PAID
);

-- Order-Inventory assignments (many-to-many)
CREATE TABLE order_inventory (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT REFERENCES orders(id) ON DELETE CASCADE,
    inventory_id BIGINT REFERENCES inventory(id),
    quantity_used INTEGER NOT NULL,
    unit_cost DECIMAL(8,2) DEFAULT 0,
    total_cost DECIMAL(10,2) DEFAULT 0,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tasks table
CREATE TABLE tasks (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT REFERENCES orders(id) ON DELETE CASCADE,
    task_title VARCHAR(200) NOT NULL,
    task_description TEXT,
    task_status VARCHAR(20) DEFAULT 'TODO', -- TODO, IN_PROGRESS, DONE, DELETED
    priority VARCHAR(10) DEFAULT 'MEDIUM', -- LOW, MEDIUM, HIGH
    assigned_to BIGINT REFERENCES employees(id),
    due_date DATE,
    completed_at TIMESTAMP,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Task assignments (if multiple employees work on same task)
CREATE TABLE task_assignments (
    id BIGSERIAL PRIMARY KEY,
    task_id BIGINT REFERENCES tasks(id) ON DELETE CASCADE,
    employee_id BIGINT REFERENCES employees(id),
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    notes TEXT
);

-- Create indexes for better performance
CREATE INDEX idx_orders_customer_id ON orders(customer_id);
CREATE INDEX idx_orders_event_date ON orders(event_date);
CREATE INDEX idx_orders_status ON orders(order_status);
CREATE INDEX idx_employees_type ON employees(employee_type);
CREATE INDEX idx_inventory_category ON inventory(category);
CREATE INDEX idx_tasks_order_id ON tasks(order_id);
CREATE INDEX idx_tasks_status ON tasks(task_status);
CREATE INDEX idx_customers_type ON customers(customer_type);

-- Create triggers for updated_at timestamps
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_users_updated_at BEFORE UPDATE ON users FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_customers_updated_at BEFORE UPDATE ON customers FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_employees_updated_at BEFORE UPDATE ON employees FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_inventory_updated_at BEFORE UPDATE ON inventory FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_orders_updated_at BEFORE UPDATE ON orders FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_tasks_updated_at BEFORE UPDATE ON tasks FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

