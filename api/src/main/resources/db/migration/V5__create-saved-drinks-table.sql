CREATE TABLE saved_drinks (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    drink_id VARCHAR(36) NOT NULL,
    note VARCHAR(500),
    CONSTRAINT fk_saved_drinks_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_saved_drinks_drink FOREIGN KEY (drink_id) REFERENCES drinks (id),
    CONSTRAINT uk_saved_drinks_user_drink UNIQUE (user_id, drink_id)
);
