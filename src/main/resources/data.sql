-- Catering Management System - Sample Data
-- Insert sample data for immediate testing and demonstration

-- Insert default admin user
INSERT INTO users (username, password, email, full_name, role) VALUES 
('admin', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'admin@catering.com', 'System Administrator', 'ADMIN');
-- Password is 'password' (BCrypt encoded)

-- Insert sample customers
INSERT INTO customers (name, contact_person, phone, email, address, customer_type, business_type, payment_terms, credit_limit) VALUES 
('Grand Hotel Palace', 'Mr. Rajesh Kumar', '+91-9876543210', 'rajesh@grandhotel.com', '123 MG Road, Mumbai, Maharashtra', 'PERMANENT', 'HOTEL', 'NET_30', 50000.00),
('Sharma Wedding Planners', 'Mrs. Priya Sharma', '+91-9876543211', 'priya@sharmaweddings.com', '456 Park Street, Delhi', 'PERMANENT', 'EVENT_MANAGER', 'NET_15', 30000.00),
('Corporate Events Ltd', 'Mr. Amit Singh', '+91-9876543212', 'amit@corporateevents.com', '789 Business District, Bangalore', 'PERMANENT', 'EVENT_MANAGER', 'ADVANCE_50', 75000.00),
('Ravi Birthday Party', 'Mr. Ravi Patel', '+91-9876543213', 'ravi.patel@gmail.com', '321 Residential Area, Pune', 'ONE_TIME', 'INDIVIDUAL', 'FULL_ADVANCE', 0.00),
('Sunita Anniversary', 'Mrs. Sunita Gupta', '+91-9876543214', 'sunita.gupta@yahoo.com', '654 Colony Road, Hyderabad', 'ONE_TIME', 'INDIVIDUAL', 'FULL_ADVANCE', 0.00);

-- Insert sample employees
INSERT INTO employees (employee_code, name, phone, email, address, employee_type, hire_date, salary_per_order, base_salary) VALUES 
('EMP001', 'Chef Ramesh Kumar', '+91-9876543220', 'ramesh@catering.com', '111 Chef Colony, Mumbai', 'COOK', '2023-01-15', 1500.00, 25000.00),
('EMP002', 'Sunita Bai', '+91-9876543221', 'sunita@catering.com', '222 Service Area, Mumbai', 'BAI', '2023-02-01', 800.00, 15000.00),
('EMP003', 'Waiter Suresh', '+91-9876543222', 'suresh@catering.com', '333 Staff Quarters, Mumbai', 'WAITER', '2023-02-15', 600.00, 12000.00),
('EMP004', 'Driver Mahesh', '+91-9876543223', 'mahesh@catering.com', '444 Transport Hub, Mumbai', 'DRIVER', '2023-03-01', 800.00, 18000.00),
('EMP005', 'Display Boy Kiran', '+91-9876543224', 'kiran@catering.com', '555 Setup Area, Mumbai', 'DISPLAY_TABLE_BOY', '2023-03-15', 500.00, 10000.00),
('EMP006', 'Service Boy Raj', '+91-9876543225', 'raj@catering.com', '666 Service Zone, Mumbai', 'SERVICE_BOY', '2023-04-01', 400.00, 8000.00),
('EMP007', 'Chef Assistant Pooja', '+91-9876543226', 'pooja@catering.com', '777 Kitchen Area, Mumbai', 'COOK', '2023-04-15', 1200.00, 20000.00),
('EMP008', 'Senior Waiter Vikram', '+91-9876543227', 'vikram@catering.com', '888 Service Block, Mumbai', 'WAITER', '2023-05-01', 700.00, 14000.00);

-- Insert sample inventory items
INSERT INTO inventory (item_code, item_name, category, description, unit, current_stock, minimum_stock, unit_cost, supplier, location) VALUES 
-- Utensils
('UTN001', 'Stainless Steel Plates', 'UTENSILS', 'Round dinner plates, 10 inch diameter', 'PIECES', 500, 100, 25.00, 'Kitchen Supplies Co.', 'Warehouse A'),
('UTN002', 'Serving Spoons', 'UTENSILS', 'Large serving spoons for buffet', 'PIECES', 200, 50, 15.00, 'Kitchen Supplies Co.', 'Warehouse A'),
('UTN003', 'Water Glasses', 'UTENSILS', 'Transparent water glasses, 250ml', 'PIECES', 300, 75, 12.00, 'Glassware Ltd.', 'Warehouse A'),
('UTN004', 'Serving Bowls', 'UTENSILS', 'Large ceramic serving bowls', 'PIECES', 150, 30, 45.00, 'Kitchen Supplies Co.', 'Warehouse A'),

-- Display Tables
('TBL001', 'Folding Display Table', 'DISPLAY_TABLES', '6ft folding table for food display', 'PIECES', 25, 5, 1200.00, 'Furniture Mart', 'Storage Room B'),
('TBL002', 'Round Dining Table', 'DISPLAY_TABLES', '4-seater round dining table', 'PIECES', 15, 3, 2500.00, 'Furniture Mart', 'Storage Room B'),
('TBL003', 'Buffet Counter', 'DISPLAY_TABLES', 'Stainless steel buffet counter', 'PIECES', 8, 2, 5000.00, 'Commercial Kitchen Co.', 'Storage Room B'),

-- Water Cans
('WTR001', '20L Water Can', 'WATER_CANS', 'Purified drinking water, 20 liters', 'PIECES', 100, 20, 45.00, 'Pure Water Co.', 'Storage Room C'),
('WTR002', 'Water Dispenser', 'WATER_CANS', 'Electric water dispenser', 'PIECES', 10, 2, 3500.00, 'Appliance Store', 'Storage Room C'),

-- Vegetables
('VEG001', 'Onions', 'VEGETABLES', 'Fresh red onions', 'KG', 50, 10, 30.00, 'Fresh Mart', 'Cold Storage'),
('VEG002', 'Tomatoes', 'VEGETABLES', 'Fresh tomatoes', 'KG', 40, 10, 40.00, 'Fresh Mart', 'Cold Storage'),
('VEG003', 'Potatoes', 'VEGETABLES', 'Fresh potatoes', 'KG', 60, 15, 25.00, 'Fresh Mart', 'Cold Storage'),
('VEG004', 'Green Vegetables Mix', 'VEGETABLES', 'Mixed green vegetables', 'KG', 30, 8, 60.00, 'Fresh Mart', 'Cold Storage'),

-- Grocery
('GRC001', 'Basmati Rice', 'GROCERY', 'Premium basmati rice', 'KG', 100, 25, 80.00, 'Grain Suppliers', 'Dry Storage'),
('GRC002', 'Cooking Oil', 'GROCERY', 'Refined sunflower oil', 'LITERS', 50, 10, 120.00, 'Oil Mills', 'Dry Storage'),
('GRC003', 'Spices Mix', 'GROCERY', 'Indian spices mixture', 'KG', 20, 5, 200.00, 'Spice House', 'Dry Storage'),
('GRC004', 'Dal (Lentils)', 'GROCERY', 'Mixed lentils', 'KG', 40, 10, 90.00, 'Grain Suppliers', 'Dry Storage'),

-- Equipment
('EQP001', 'Gas Stove (4 Burner)', 'EQUIPMENT', 'Commercial 4-burner gas stove', 'PIECES', 5, 1, 8000.00, 'Kitchen Equipment Co.', 'Equipment Room'),
('EQP002', 'Large Cooking Pot', 'EQUIPMENT', '20L stainless steel cooking pot', 'PIECES', 12, 3, 1500.00, 'Kitchen Equipment Co.', 'Equipment Room'),
('EQP003', 'Serving Trolley', 'EQUIPMENT', 'Mobile serving trolley', 'PIECES', 8, 2, 2500.00, 'Kitchen Equipment Co.', 'Equipment Room');

-- Update total_value for inventory items
UPDATE inventory SET total_value = current_stock * unit_cost;

-- Insert sample orders
INSERT INTO orders (order_number, customer_id, order_type, event_name, event_date, event_time, venue_address, guest_count, menu_details, order_status, total_amount, advance_amount, remaining_amount, payment_status, created_by) VALUES 
('ORD001', 1, 'FULL_CATERING', 'Corporate Annual Meeting', '2024-01-15', '12:00:00', 'Grand Hotel Palace, Conference Hall', 150, 'North Indian Buffet with Desserts', 'COMPLETED', 75000.00, 37500.00, 37500.00, 'ADVANCE_PAID', 1),
('ORD002', 2, 'FULL_CATERING', 'Sharma-Gupta Wedding', '2024-01-20', '19:00:00', 'Wedding Garden, Sector 15, Delhi', 300, 'Traditional Wedding Menu with Live Counters', 'IN_PROGRESS', 150000.00, 75000.00, 75000.00, 'ADVANCE_PAID', 1),
('ORD003', 4, 'HALF_CATERING', 'Ravi 30th Birthday Party', '2024-01-25', '18:00:00', '321 Residential Area, Pune', 50, 'Birthday Special Menu with Cake', 'PENDING', 25000.00, 25000.00, 0.00, 'FULLY_PAID', 1),
('ORD004', 3, 'FULL_CATERING', 'Product Launch Event', '2024-02-01', '11:00:00', 'Tech Park Convention Center, Bangalore', 200, 'Continental Breakfast and Lunch', 'PENDING', 100000.00, 50000.00, 50000.00, 'ADVANCE_PAID', 1),
('ORD005', 5, 'HALF_CATERING', 'Sunita 25th Anniversary', '2024-02-05', '20:00:00', '654 Colony Road, Hyderabad', 80, 'Anniversary Special Dinner', 'PENDING', 40000.00, 20000.00, 20000.00, 'ADVANCE_PAID', 1);

-- Insert order-employee assignments
INSERT INTO order_employees (order_id, employee_id, role_in_order, payment_amount, payment_status) VALUES 
-- Order 1 assignments
(1, 1, 'HEAD_COOK', 1500.00, 'PAID'),
(1, 3, 'WAITER', 600.00, 'PAID'),
(1, 4, 'DRIVER', 800.00, 'PAID'),
(1, 6, 'SERVICE_BOY', 400.00, 'PAID'),

-- Order 2 assignments (in progress)
(2, 1, 'HEAD_COOK', 1500.00, 'PENDING'),
(2, 7, 'ASSISTANT_COOK', 1200.00, 'PENDING'),
(2, 3, 'WAITER', 600.00, 'PENDING'),
(2, 8, 'SENIOR_WAITER', 700.00, 'PENDING'),
(2, 4, 'DRIVER', 800.00, 'PENDING'),
(2, 5, 'DISPLAY_TABLE_BOY', 500.00, 'PENDING'),
(2, 6, 'SERVICE_BOY', 400.00, 'PENDING'),

-- Order 3 assignments
(3, 7, 'HEAD_COOK', 1200.00, 'PENDING'),
(3, 8, 'WAITER', 700.00, 'PENDING'),
(3, 4, 'DRIVER', 800.00, 'PENDING');

-- Insert sample inventory usage for orders
INSERT INTO order_inventory (order_id, inventory_id, quantity_used, unit_cost, total_cost) VALUES 
-- Order 1 inventory usage
(1, 1, 150, 25.00, 3750.00), -- Plates
(1, 3, 150, 12.00, 1800.00), -- Water glasses
(1, 9, 10, 45.00, 450.00),   -- Water cans
(1, 13, 20, 80.00, 1600.00), -- Rice
(1, 10, 15, 30.00, 450.00),  -- Onions

-- Order 2 inventory usage (partial, as it's in progress)
(2, 1, 300, 25.00, 7500.00), -- Plates
(2, 2, 50, 15.00, 750.00),   -- Serving spoons
(2, 3, 300, 12.00, 3600.00), -- Water glasses
(2, 6, 5, 2500.00, 12500.00), -- Round tables
(2, 9, 20, 45.00, 900.00);   -- Water cans

-- Insert sample tasks
INSERT INTO tasks (order_id, task_title, task_description, task_status, priority, assigned_to, due_date, created_by) VALUES 
-- Tasks for Order 1 (completed)
(1, 'Menu Planning', 'Finalize menu items and quantities for 150 guests', 'DONE', 'HIGH', 1, '2024-01-10', 1),
(1, 'Ingredient Procurement', 'Purchase all required ingredients and materials', 'DONE', 'HIGH', 1, '2024-01-12', 1),
(1, 'Setup Tables and Display', 'Arrange tables and food display area', 'DONE', 'MEDIUM', 5, '2024-01-15', 1),
(1, 'Food Preparation', 'Prepare all food items as per menu', 'DONE', 'HIGH', 1, '2024-01-15', 1),

-- Tasks for Order 2 (in progress)
(2, 'Wedding Menu Finalization', 'Confirm final menu with client including live counters', 'DONE', 'HIGH', 1, '2024-01-15', 1),
(2, 'Venue Setup Planning', 'Plan the layout for 300 guests wedding setup', 'IN_PROGRESS', 'HIGH', 5, '2024-01-18', 1),
(2, 'Staff Coordination', 'Coordinate with all assigned staff members', 'IN_PROGRESS', 'MEDIUM', 8, '2024-01-19', 1),
(2, 'Transportation Arrangement', 'Arrange transportation for equipment and food', 'TODO', 'MEDIUM', 4, '2024-01-20', 1),
(2, 'Final Food Preparation', 'Prepare all wedding food items', 'TODO', 'HIGH', 1, '2024-01-20', 1),

-- Tasks for Order 3
(3, 'Birthday Cake Arrangement', 'Order and arrange special birthday cake', 'TODO', 'HIGH', 7, '2024-01-23', 1),
(3, 'Party Decoration Setup', 'Setup birthday party decorations', 'TODO', 'MEDIUM', 5, '2024-01-25', 1),
(3, 'Food Preparation for 50 guests', 'Prepare birthday special menu', 'TODO', 'HIGH', 7, '2024-01-25', 1),

-- Tasks for Order 4
(4, 'Corporate Menu Planning', 'Plan continental breakfast and lunch menu', 'TODO', 'HIGH', 1, '2024-01-28', 1),
(4, 'Equipment Transportation', 'Arrange transport for Bangalore event', 'TODO', 'MEDIUM', 4, '2024-01-31', 1),

-- Tasks for Order 5
(5, 'Anniversary Special Menu', 'Design special anniversary dinner menu', 'TODO', 'HIGH', 7, '2024-02-02', 1),
(5, 'Romantic Setup Planning', 'Plan romantic dinner setup for anniversary', 'TODO', 'MEDIUM', 5, '2024-02-04', 1);

-- Update employee statistics
UPDATE employees SET 
    total_orders_served = (
        SELECT COUNT(*) FROM order_employees oe 
        JOIN orders o ON oe.order_id = o.id 
        WHERE oe.employee_id = employees.id AND o.order_status = 'COMPLETED'
    ),
    total_earnings = (
        SELECT COALESCE(SUM(oe.payment_amount), 0) FROM order_employees oe 
        WHERE oe.employee_id = employees.id AND oe.payment_status = 'PAID'
    );

-- Update customer statistics
UPDATE customers SET 
    total_orders = (
        SELECT COUNT(*) FROM orders WHERE customer_id = customers.id
    ),
    total_amount = (
        SELECT COALESCE(SUM(total_amount), 0) FROM orders WHERE customer_id = customers.id
    ),
    outstanding_amount = (
        SELECT COALESCE(SUM(remaining_amount), 0) FROM orders 
        WHERE customer_id = customers.id AND payment_status != 'FULLY_PAID'
    );

