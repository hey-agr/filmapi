CREATE SEQUENCE main.seq_movie_id;

CREATE TABLE main.movie
(
    id bigint NOT NULL DEFAULT nextval('main.seq_movie_id'::regclass),
    title character varying,
    year integer,
    imdb_id character varying,
    type character varying,
    poster character varying,
    CONSTRAINT movie_pkey PRIMARY KEY (id)
);

