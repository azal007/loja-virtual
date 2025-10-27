CREATE TABLE IF NOT EXISTS Categorias (
    id BIGINT PRIMARY KEY AUTO_INCREMENT UNIQUE,
    nome VARCHAR(100) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    id_categoria_pai BIGINT,
    CONSTRAINT fk_categoria_pai
        FOREIGN KEY (id_categoria_pai)
        REFERENCES Categorias(id)
        ON DELETE CASCADE
);