CREATE TABLE public.mypictures
(
    id bigint NOT NULL,
    path varchar NOT NULL,
    source NULL,
    info json NOT NULL,
    CONSTRAINT mypictures_pkey PRIMARY KEY (id, path, source)
)

TABLESPACE pg_default;

ALTER TABLE public.mypictures
    OWNER to postgres;