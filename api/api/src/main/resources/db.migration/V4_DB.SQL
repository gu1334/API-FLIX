-- V2__add_paymente_to_videos.sql
ALTER TABLE videos
    ADD COLUMN paymente VARCHAR(255) NOT NULL;
