-- =========================================
-- Description: Create the game news table
-- Author: Halcyon
-- Date: 2024-07-22
-- Version: V1.0.0
-- =========================================

CREATE TABLE IF NOT EXISTS game_news(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    title VARCHAR(1000) NOT NULL,
    content VARCHAR(20000) NOT NULL,
    source VARCHAR(300) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    tags JSON
)