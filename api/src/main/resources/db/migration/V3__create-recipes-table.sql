CREATE TABLE recipes (
    id VARCHAR(36) PRIMARY KEY,
    drink_id VARCHAR(36) NOT NULL,
    title VARCHAR(255) NOT NULL,
    ingredients VARCHAR(4000) NOT NULL,
    steps VARCHAR(4000) NOT NULL,
    CONSTRAINT fk_recipes_drink FOREIGN KEY (drink_id) REFERENCES drinks (id)
);
