CREATE SEQUENCE main.seq_genre_id;

CREATE TABLE main.genre
(
    id bigint NOT NULL DEFAULT nextval('main.seq_genre_id'::regclass),
    name character varying,
    CONSTRAINT genre_pkey PRIMARY KEY (id)
);

create table main.movie_genre
(
    movie_id bigint
        constraint movie_id_fk
            references main.movie
            on update restrict on delete cascade,
    genre_id bigint
        constraint genre_id_fk
            references main.genre
            on update restrict on delete cascade
);