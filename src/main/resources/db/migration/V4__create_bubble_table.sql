CREATE TABLE bubbles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    radius DOUBLE PRECISION NOT NULL, -- em metros
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);

CREATE TABLE bubble_members (
    id BIGSERIAL PRIMARY KEY,
    bubble_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    joined_at TIMESTAMP NOT NULL,
    left_at TIMESTAMP,
    role VARCHAR(50) DEFAULT 'MEMBER',
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    CONSTRAINT fk_bubble_member_bubble FOREIGN KEY (bubble_id) REFERENCES bubbles(id) ON DELETE CASCADE,
    CONSTRAINT fk_bubble_member_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT uq_bubble_member UNIQUE (bubble_id, user_id)
);
