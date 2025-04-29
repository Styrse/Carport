-- Drop tables if they exist
DROP TABLE IF EXISTS price_history CASCADE;
DROP TABLE IF EXISTS material_colors CASCADE;
DROP TABLE IF EXISTS predefined_lengths CASCADE;
DROP TABLE IF EXISTS carports CASCADE;
DROP TABLE IF EXISTS building_materials CASCADE;
DROP TABLE IF EXISTS regular_products CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS colors CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- USERS table
CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       firstname VARCHAR(100) NOT NULL,
                       lastname VARCHAR(100) NOT NULL,
                       phone_number VARCHAR(20),
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       is_staff BOOLEAN DEFAULT FALSE,
                       is_staff_manager BOOLEAN DEFAULT FALSE
);

-- PRODUCTS table
CREATE TABLE products (
                          product_id SERIAL PRIMARY KEY,
                          product_type VARCHAR(50) NOT NULL
);

-- COLORS table
CREATE TABLE colors (
                        color_id SERIAL PRIMARY KEY,
                        color_name VARCHAR(50) UNIQUE NOT NULL,
                        hex_code VARCHAR(7) NOT NULL
);

-- BUILDING_MATERIALS table
CREATE TABLE building_materials (
                                    sub_product_id SERIAL PRIMARY KEY,
                                    product_id INT REFERENCES products(product_id) ON DELETE CASCADE,
                                    name VARCHAR(100) NOT NULL,
                                    description TEXT,
                                    width INT,
                                    height INT,
                                    cost_price NUMERIC(10, 2),
                                    sales_price NUMERIC(10, 2),
                                    material_type VARCHAR(50),
                                    treated_for_ground_contact BOOLEAN,
                                    max_length_between_posts INT,
                                    supports_gutters BOOLEAN
);

-- REGULAR_PRODUCTS table
CREATE TABLE regular_products (
                                  sub_product_id SERIAL PRIMARY KEY,
                                  product_id INT REFERENCES products(product_id) ON DELETE CASCADE,
                                  name VARCHAR(100) NOT NULL,
                                  description TEXT,
                                  cost_price NUMERIC(10, 2),
                                  sales_price NUMERIC(10, 2)
);

-- CARPORTS table
CREATE TABLE carports (
                          sub_product_id SERIAL PRIMARY KEY,
                          product_id INT REFERENCES products(product_id) ON DELETE CASCADE,
                          width INT NOT NULL,
                          length INT NOT NULL,
                          height INT,
                          roof_type VARCHAR(100),
                          roof_angle INT,

                          post_sub_product_id INT REFERENCES building_materials(sub_product_id),
                          post_color_id INT REFERENCES colors(color_id),

                          beam_sub_product_id INT REFERENCES building_materials(sub_product_id),
                          beam_color_id INT REFERENCES colors(color_id),

                          rafter_sub_product_id INT REFERENCES building_materials(sub_product_id),
                          rafter_color_id INT REFERENCES colors(color_id),

                          fascia_sub_product_id INT REFERENCES building_materials(sub_product_id),
                          fascia_color_id INT REFERENCES colors(color_id),

                          roof_cover_sub_product_id INT REFERENCES building_materials(sub_product_id),
                          roof_cover_color_id INT REFERENCES colors(color_id)
);

-- MATERIAL_COLORS table
CREATE TABLE material_colors (
                                 id SERIAL PRIMARY KEY,
                                 sub_product_id INT REFERENCES building_materials(sub_product_id) ON DELETE CASCADE,
                                 color_id INT REFERENCES colors(color_id) ON DELETE CASCADE
);

-- PREDEFINED_LENGTHS table
CREATE TABLE predefined_lengths (
                                    id SERIAL PRIMARY KEY,
                                    sub_product_id INT REFERENCES building_materials(sub_product_id) ON DELETE CASCADE,
                                    length INT NOT NULL
);

-- PRICE_HISTORY table (with composite key)
CREATE TABLE price_history (
                               price_history_id SERIAL PRIMARY KEY,
                               product_id INT NOT NULL REFERENCES products(product_id),
                               sub_product_id INT NOT NULL,
                               price_type VARCHAR(10) NOT NULL,
                               price NUMERIC(10,2) NOT NULL,
                               valid_from DATE NOT NULL DEFAULT CURRENT_DATE,
                               valid_to DATE,
                               CONSTRAINT unique_price_per_period UNIQUE (product_id, sub_product_id, price_type, valid_from)
);

-- ORDERS table
CREATE TABLE orders (
                        order_id SERIAL PRIMARY KEY,
                        user_id INT REFERENCES users(user_id) ON DELETE CASCADE,
                        product_id INT REFERENCES products(product_id) ON DELETE CASCADE,
                        sub_product_id INT NOT NULL,
                        order_date DATE DEFAULT CURRENT_DATE,
                        payment_date DATE,
                        order_status VARCHAR(50) DEFAULT 'pending',
                        payment_status VARCHAR(50) DEFAULT 'unpaid',
                        total_price NUMERIC(10, 2)
);
