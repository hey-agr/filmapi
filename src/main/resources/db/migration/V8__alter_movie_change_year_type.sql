ALTER TABLE main.movie
    RENAME year TO year_tmp;

ALTER TABLE main.movie
    ADD COLUMN year text;

UPDATE main.movie
SET year=year_tmp;

ALTER TABLE main.movie DROP COLUMN year_tmp;