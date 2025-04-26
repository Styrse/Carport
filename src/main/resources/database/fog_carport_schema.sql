-- Create users table
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    phone_number BIGINT,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255),
    is_customer BOOLEAN DEFAULT FALSE,
    is_staff BOOLEAN DEFAULT FALSE,
    is_sales_manager BOOLEAN DEFAULT FALSE
);

-- Create carports table
CREATE TABLE carports (
    carport_id SERIAL PRIMARY KEY
);

-- Create materials table
CREATE TABLE materials (
    material_id SERIAL PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    cost_price DOUBLE PRECISION NOT NULL,
    sales_price DOUBLE PRECISION NOT NULL,
    unit VARCHAR(50) NOT NULL
);

-- Create beams table (subtype of materials)
CREATE TABLE beams (
    beam_id SERIAL PRIMARY KEY,
    material_id INT REFERENCES materials(material_id) ON DELETE CASCADE,
    length_cm INT NOT NULL CHECK (length_cm > 0)
);

-- Create orders table
CREATE TABLE orders (
    order_id SERIAL PRIMARY KEY,
    order_date DATE DEFAULT CURRENT_DATE,
    carport_id INT REFERENCES carports(carport_id) ON DELETE SET NULL,
    customer_user_id INT REFERENCES users(user_id) ON DELETE SET NULL,
    staff_user_id INT REFERENCES users(user_id) ON DELETE SET NULL,
    order_status VARCHAR(50) DEFAULT 'Request received'
);

-- Create carport_materials linking table
CREATE TABLE carport_materials (
    id SERIAL PRIMARY KEY,
    carport_id INT REFERENCES carports(carport_id) ON DELETE CASCADE,
    material_id INT REFERENCES materials(material_id) ON DELETE CASCADE,
    quantity INT NOT NULL CHECK (quantity > 0)
);
