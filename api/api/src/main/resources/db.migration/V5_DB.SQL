-- V3__alter_paymente_column_type.sql
ALTER TABLE videos
    MODIFY COLUMN paymente INT NOT NULL;

SELECT * FROM videos WHERE paymente = 3;
