CREATE TABLE products (
                          id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                          title TEXT NOT NULL,
                          description TEXT,
                          price NUMERIC,
                          vendor TEXT,
                          featured BOOLEAN DEFAULT false
);

CREATE TABLE variants (
                          id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                          product_id INT REFERENCES products(id) ON DELETE CASCADE,
                          title TEXT,
                          sku TEXT,
                          price NUMERIC
);
