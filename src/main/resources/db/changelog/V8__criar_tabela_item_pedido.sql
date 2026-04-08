CREATE TABLE IF NOT EXISTS item_pedido (
    id BIGINT PRIMARY KEY AUTO_INCREMENT UNIQUE,
    pedido_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL,

    CONSTRAINT fk_pedido
        FOREIGN KEY (pedido_id)
        REFERENCES pedido(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_produto
        FOREIGN KEY (produto_id)
        REFERENCES produto(id)
        ON DELETE CASCADE,

    CONSTRAINT uk_pedido_produto UNIQUE (pedido_id, produto_id)
);