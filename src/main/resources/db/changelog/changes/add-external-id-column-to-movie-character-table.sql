--liquibase formatted sql
--changeset <sniklz>:<add-external-id-column-to-movie-character-table>

ALTER TABLE public.movie_character ADD external_id bigint;


--rollback ALTER TABLE public.movie_character DROP COLUMN external_id;