CREATE SEQUENCE main.seq_movie_id;

ALTER SEQUENCE main.seq_movie_id
    OWNER TO postgres;

CREATE TABLE main.movie
(
    id bigint NOT NULL DEFAULT nextval('main.seq_movie_id'::regclass),
    title character varying,
    year integer,
    "imdbID" integer,
    type character varying,
    poster character varying,
    CONSTRAINT movie_pkey PRIMARY KEY (id)
);

ALTER TABLE main.movie
    OWNER to postgres;

