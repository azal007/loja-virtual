CREATE TABLE IF NOT EXISTS pedido (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    data_emissao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('CRIADO') NOT NULL,
    total DECIMAL(10, 2) ,
    CONSTRAINT fk_usurario
        FOREIGN KEY (user_id)
        REFERENCES usuario(id)
        ON DELETE CASCADE
);