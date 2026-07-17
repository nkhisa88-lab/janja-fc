CREATE TABLE IF NOT EXISTS users (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    full_name VARCHAR(255) NOT NULL,

    phone_number VARCHAR(20) NOT NULL UNIQUE,

    role VARCHAR(30) NOT NULL,

    activation_code VARCHAR(255),

    password VARCHAR(255),

    created_at TIMESTAMP NOT NULL

);

CREATE TABLE IF NOT EXISTS matches (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    opponent VARCHAR(255) NOT NULL,

    venue VARCHAR(255) NOT NULL,

    status VARCHAR(30) NOT NULL,

    match_date DATE NOT NULL,

    kickoff_time TIME NOT NULL,

    created_at TIMESTAMP NOT NULL,

    created_by BIGINT NOT NULL,

    CONSTRAINT fk_match_creator
        FOREIGN KEY (created_by)
        REFERENCES users(id)

);

CREATE TABLE IF NOT EXISTS responses (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    user_id BIGINT NOT NULL,

    match_id BIGINT NOT NULL,

    status VARCHAR(30) NOT NULL,

    responded_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_response_user
        FOREIGN KEY(user_id)
        REFERENCES users(id),

    CONSTRAINT fk_response_match
        FOREIGN KEY(match_id)
        REFERENCES matches(id),

    CONSTRAINT unique_response
        UNIQUE(user_id, match_id)

);