-- roles
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


-- verified students
CREATE TABLE IF NOT EXISTS verified_students
(
    id BIGINT NOT NULL,
    birth_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    email CHARACTER VARYING(255) NOT NULL,
    faculty CHARACTER VARYING(255) NOT NULL,
    first_name CHARACTER VARYING(255) NOT NULL,
    last_name CHARACTER VARYING(255) NOT NULL,
    university CHARACTER VARYING(255) NOT NULL,
    CONSTRAINT verified_students_pk PRIMARY KEY (id),
    CONSTRAINT verified_students_unique_email UNIQUE (email)
);

ALTER TABLE IF EXISTS verified_students OWNER to postgres;

INSERT INTO verified_students(id, birth_date, email, faculty, first_name, last_name, university)
VALUES (1, '2001-12-06 00:00:00', 'alex@ubb.com', 'FMI', 'Alexandru', 'Nedelcu', 'UBB');