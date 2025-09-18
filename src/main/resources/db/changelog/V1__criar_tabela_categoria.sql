CREATE TABLE Categoria (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    id_categoria_pai BIGINT UNSIGNED,
    CONSTRAINT fk_categoria_pai FOREIGN KEY (id_categoria_pai) REFERENCES Categoria(id),
    ativo BOOLEAN DEFAULT TRUE
);