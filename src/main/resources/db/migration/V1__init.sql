CREATE SCHEMA IF NOT EXISTS public;

SET default_tablespace = '';

SET default_table_access_method = heap;

CREATE TABLE public.attachment (
    created_at timestamp(6) with time zone NOT NULL,
    updated_at timestamp(6) with time zone NOT NULL,
    created_by uuid,
    id uuid NOT NULL,
    product_id uuid,
    updated_by uuid,
    img_url character varying(255) NOT NULL
);

CREATE TABLE public.category (
    created_at timestamp(6) with time zone NOT NULL,
    updated_at timestamp(6) with time zone NOT NULL,
    created_by uuid,
    id uuid NOT NULL,
    updated_by uuid,
    name character varying(255) NOT NULL
);

CREATE TABLE public.product (
    leftover integer DEFAULT 0 CHECK (leftover >= 0),
    price numeric(19,2),
    version integer,
    created_at timestamp(6) with time zone NOT NULL,
    updated_at timestamp(6) with time zone NOT NULL,
    category_id uuid NOT NULL,
    created_by uuid,
    id uuid NOT NULL,
    updated_by uuid,
    description text,
    name character varying(255)
);

CREATE TABLE public.product_income (
    quantity integer NOT NULL,
    created_at timestamp(6) with time zone NOT NULL,
    date_time timestamp(6) without time zone,
    updated_at timestamp(6) with time zone NOT NULL,
    created_by uuid,
    id uuid NOT NULL,
    product_id uuid,
    updated_by uuid
);

CREATE TABLE public.product_outcome (
    quantity integer,
    created_at timestamp(6) with time zone NOT NULL,
    date_time timestamp(6) without time zone,
    updated_at timestamp(6) with time zone NOT NULL,
    created_by uuid,
    id uuid NOT NULL,
    product_id uuid,
    updated_by uuid
);

CREATE TABLE public.refresh_token (
    revoked boolean NOT NULL,
    created_at timestamp(6) with time zone NOT NULL,
    expires_at timestamp(6) with time zone NOT NULL,
    id uuid NOT NULL,
    user_id uuid NOT NULL,
    token character varying(255) NOT NULL
);

CREATE TABLE public.roles (
    created_at timestamp(6) with time zone NOT NULL,
    updated_at timestamp(6) with time zone NOT NULL,
    created_by uuid,
    id uuid NOT NULL,
    updated_by uuid,
    role character varying(255) NOT NULL,
    CONSTRAINT roles_role_check CHECK (((role)::text = ANY ((ARRAY['ADMIN'::character varying, 'USER'::character varying])::text[])))
);

CREATE TABLE public.users (
    created_at timestamp(6) with time zone NOT NULL,
    updated_at timestamp(6) with time zone NOT NULL,
    created_by uuid,
    id uuid NOT NULL,
    updated_by uuid,
    email character varying(255) NOT NULL,
    first_name character varying(255),
    last_name character varying(255),
    password character varying(255) NOT NULL
);

CREATE TABLE public.users_roles (
    roles_id uuid NOT NULL,
    user_id uuid NOT NULL
);



ALTER TABLE ONLY public.attachment
    ADD CONSTRAINT attachment_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.product_income
    ADD CONSTRAINT product_income_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.product_outcome
    ADD CONSTRAINT product_outcome_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.refresh_token
    ADD CONSTRAINT refresh_token_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.refresh_token
    ADD CONSTRAINT refresh_token_token_key UNIQUE (token);

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT uk_roles_role UNIQUE (role);

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.product
    ADD CONSTRAINT fk1mtsbur82frn64de7balymq9s FOREIGN KEY (category_id) REFERENCES public.category(id);

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT fk2o0jvgh89lemvvo17cbqvdxaa FOREIGN KEY (user_id) REFERENCES public.users(id);

ALTER TABLE ONLY public.product_income
    ADD CONSTRAINT fk69s8cmbq8ar4kf4e6p00ar358 FOREIGN KEY (created_by) REFERENCES public.users(id);

ALTER TABLE ONLY public.product_outcome
    ADD CONSTRAINT fk7rsqy1kb1obwb4pq85xel0l9n FOREIGN KEY (created_by) REFERENCES public.users(id);

ALTER TABLE ONLY public.product_outcome
    ADD CONSTRAINT fk8c9ga8cwfrh4ml4s8hvmij0s8 FOREIGN KEY (product_id) REFERENCES public.product(id);

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT fka62j07k5mhgifpp955h37ponj FOREIGN KEY (roles_id) REFERENCES public.roles(id);

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fkci7xr690rvyv3bnfappbyh8x0 FOREIGN KEY (updated_by) REFERENCES public.users(id);

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT fkf0p4aw14esgr0ukams27qfl3m FOREIGN KEY (updated_by) REFERENCES public.users(id);

ALTER TABLE ONLY public.category
    ADD CONSTRAINT fkho9xxhac4fwi30iuak1pah7l4 FOREIGN KEY (updated_by) REFERENCES public.users(id);

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fkibk1e3kaxy5sfyeekp8hbhnim FOREIGN KEY (created_by) REFERENCES public.users(id);

ALTER TABLE ONLY public.product
    ADD CONSTRAINT fkjlafeta1wfhorrg6f7v8c60jc FOREIGN KEY (updated_by) REFERENCES public.users(id);

ALTER TABLE ONLY public.refresh_token
    ADD CONSTRAINT fkjtx87i0jvq2svedphegvdwcuy FOREIGN KEY (user_id) REFERENCES public.users(id);

ALTER TABLE ONLY public.product_income
    ADD CONSTRAINT fkm9g1v339a0ye4di5241r14a1c FOREIGN KEY (updated_by) REFERENCES public.users(id);

ALTER TABLE ONLY public.product_income
    ADD CONSTRAINT fkmseanvuw1qf2c6jkf2u8pkk31 FOREIGN KEY (product_id) REFERENCES public.product(id);

ALTER TABLE ONLY public.category
    ADD CONSTRAINT fknbi9umnlfmtbpd3kcs8o37ta3 FOREIGN KEY (created_by) REFERENCES public.users(id);

ALTER TABLE ONLY public.attachment
    ADD CONSTRAINT fkprb2uffjge7su6iid86xpekh1 FOREIGN KEY (updated_by) REFERENCES public.users(id);

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT fkq6ium4se7bjk3mfbj3qm1gvy FOREIGN KEY (created_by) REFERENCES public.users(id);

ALTER TABLE ONLY public.attachment
    ADD CONSTRAINT fkqjbv1unele187shvpiwx5xit7 FOREIGN KEY (product_id) REFERENCES public.product(id);

ALTER TABLE ONLY public.product_outcome
    ADD CONSTRAINT fkskj4uc6o0oke15bx7hfg3aw1s FOREIGN KEY (updated_by) REFERENCES public.users(id);

ALTER TABLE ONLY public.product
    ADD CONSTRAINT fkstb290bdq1jf21dnnc91ap27p FOREIGN KEY (created_by) REFERENCES public.users(id);

ALTER TABLE ONLY public.attachment
    ADD CONSTRAINT fksthq9nduup0a4j1n0lmfcc1j9 FOREIGN KEY (created_by) REFERENCES public.users(id);

CREATE EXTENSION IF NOT EXISTS pgcrypto;

INSERT INTO roles (id, role, created_at, updated_at)
VALUES
    (gen_random_uuid(), 'ADMIN', now(), now()),
    (gen_random_uuid(), 'USER', now(), now())
ON CONFLICT (role) DO NOTHING;
