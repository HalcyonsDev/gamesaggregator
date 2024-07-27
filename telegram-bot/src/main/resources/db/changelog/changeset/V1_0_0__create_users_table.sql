-- =========================================
-- Description: Create the users table
-- Author: Halcyon
-- Date: 2024-07-23
-- Version: V1.0.0
-- =========================================

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    username VARCHAR(200) NOT NULL,
    chat_id BIGINT NOT NULL
)