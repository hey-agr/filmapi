CREATE TABLE main."user"
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 ),
    username text NOT NULL,
    password text NOT NULL,
    date_created timestamp NOT NULL DEFAULT now(),
    blocked boolean NOT NULL DEFAULT false,
    CONSTRAINT user_id_pk PRIMARY KEY (id),
    CONSTRAINT user_username_unique UNIQUE (username)
);

CREATE TABLE main.role
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 ),
    name text NOT NULL,
    description text,
    CONSTRAINT role_id_pk PRIMARY KEY (id),
    CONSTRAINT role_name_unique UNIQUE (name)
);

CREATE TABLE main.user_role
(
    user_id bigint,
    role_id bigint,
    CONSTRAINT user_role_role_id_fk FOREIGN KEY (role_id)
        REFERENCES main.role (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT user_role_user_id_fk FOREIGN KEY (user_id)
        REFERENCES main.user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

CREATE TABLE main.role_permission
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 ),
    authority text,
    role_id bigint,
    CONSTRAINT role_permission_unique UNIQUE (authority, role_id),
    CONSTRAINT role_permission_role_id_fk FOREIGN KEY (role_id)
        REFERENCES main.role (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

INSERT INTO main.role(
     name, description)
VALUES ('ADMIN', 'Администратор')
ON CONFLICT DO NOTHING;

INSERT INTO main.role(
    name, description)
VALUES ('USER', 'Пользователь')
ON CONFLICT DO NOTHING;

INSERT INTO main.role_permission(authority, role_id)
SELECT 'ADD_EDIT_MOVIE', id FROM main.role;

INSERT INTO main.role_permission(authority, role_id)
SELECT 'DELETE_MOVIE', id FROM main.role r WHERE r.name = 'ADMIN';

INSERT INTO main.role_permission(authority, role_id)
SELECT 'USER_ADMIN', id FROM main.role r WHERE r.name = 'ADMIN';

INSERT INTO main."user"(
    username, password, date_created, blocked)
VALUES ('admin', '{bcrypt}$2a$10$VgKY4MV0xFk0tppiQxX53eNrxFTNh68cuxKjR7jCsr3/aZ.U4v8eO', '2020-11-22', false);
