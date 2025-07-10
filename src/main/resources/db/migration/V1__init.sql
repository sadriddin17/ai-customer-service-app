CREATE TABLE products (
                          id SERIAL PRIMARY KEY,
                          title TEXT NOT NULL,
                          description TEXT,
                          price NUMERIC,
                          vendor TEXT
);

CREATE TABLE variants (
                          id SERIAL PRIMARY KEY,
                          product_id INT REFERENCES products(id) ON DELETE CASCADE,
                          title TEXT,
                          sku TEXT,
                          price NUMERIC
);
