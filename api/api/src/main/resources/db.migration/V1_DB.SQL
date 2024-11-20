CREATE TABLE videos (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                          titulo VARCHAR(255) NOT NULL,       -- Campo para o título (máximo 255 caracteres)
                          descricao TEXT,                     -- Campo para a descrição (tipo TEXT, sem limite específico)
                          url VARCHAR(255) NOT NULL           -- Campo para a URL (máximo 255 caracteres)
);
