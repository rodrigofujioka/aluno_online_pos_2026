CREATE TABLE IF NOT EXISTS blocos_aula (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome_bloco VARCHAR(120) NOT NULL,
    latitude DECIMAL(10, 8) NOT NULL,
    longitude DECIMAL(11, 8) NOT NULL,
    contato_responsavel VARCHAR(150) NOT NULL
);


