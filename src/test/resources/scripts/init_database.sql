CREATE DATABASE fyrm WITH OWNER = postgres;
ALTER USER postgres WITH SUPERUSER;

CREATE TABLE IF NOT EXISTS roles
(
    id BIGINT NOT NULL,
    name CHARACTER VARYING(255),
    CONSTRAINT roles_pkey PRIMARY KEY (id),
    CHECK(name IN ('ROLE_USER','ROLE_ADMIN'))
);

ALTER TABLE IF EXISTS roles OWNER to postgres;

INSERT INTO roles(id, name)
VALUES (1, 'ROLE_USER'), (2, 'ROLE_ADMIN');
