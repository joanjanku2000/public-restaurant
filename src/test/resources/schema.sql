CREATE TABLE if not exists city
(
    id
    integer
    NOT
    NULL,
    name
    character
    varying
(
    60
),
    deleted boolean,
    version integer
    );



CREATE TABLE if not exists ingredient
(
    id
    integer
    NOT
    NULL,
    name
    character
    varying
(
    60
) NOT NULL,
    calories double precision,
    deleted boolean,
    version integer
    );

CREATE TABLE if not exists public.item
(
    id
    integer
    NOT
    NULL,
    name
    character
    varying
(
    60
) NOT NULL,
    description character varying
(
    250
) NOT NULL,
    price double precision NOT NULL,
    seller_id integer,
    picture character varying
(
    255
),
    deleted boolean,
    version integer
    );


CREATE TABLE if not exists item_ingredient
(
    id_item
    integer
    NOT
    NULL,
    id_ingredient
    integer
    NOT
    NULL
);


CREATE TABLE if not exists orders
(
    id
    integer
    NOT
    NULL,
    date_time
    timestamp
    without
    time
    zone
    NOT
    NULL,
    feedback
    character
    varying
(
    250
),
    delivered boolean NOT NULL,
    item_id integer NOT NULL,
    user_id integer NOT NULL,
    active boolean,
    deleted boolean,
    version integer
    );


CREATE TABLE if not exists roles
(
    id
    integer
    NOT
    NULL,
    name
    character
    varying
(
    60
),
    deleted boolean,
    version integer
    );



CREATE TABLE if not exists user_details
(
    id
    integer
    NOT
    NULL,
    address
    character
    varying
(
    250
),
    email character varying
(
    120
) NOT NULL,
    password character varying
(
    255
) NOT NULL,
    id_city integer NOT NULL,
    id_u integer NOT NULL,
    deleted boolean,
    version integer
    );


CREATE TABLE if not exists user_table
(
    id
    integer
    NOT
    NULL,
    full_name
    character
    varying
(
    60
),
    phone_number character varying
(
    32
),
    id_role integer NOT NULL,
    enabled boolean,
    deleted boolean,
    version integer
    );



CREATE TABLE if not exists verification_token
(
    id
    integer
    NOT
    NULL,
    token
    character
    varying
(
    255
),
    id_user integer,
    expiry_date timestamp without time zone,
    version integer
    );



ALTER TABLE city
    ADD CONSTRAINT city_pkey PRIMARY KEY (id);


--
-- Name: ingredient ingredient_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE public.ingredient
    ADD CONSTRAINT ingredient_pkey PRIMARY KEY (id);


--
-- Name: item item_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE public.item
    ADD CONSTRAINT item_pkey PRIMARY KEY (id);


--
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: user_details user_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE public.user_details
    ADD CONSTRAINT user_details_pkey PRIMARY KEY (id);


--
-- Name: user_table user_table_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE public.user_table
    ADD CONSTRAINT user_table_pkey PRIMARY KEY (id);


--
-- Name: verification_token verification_token_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE public.verification_token
    ADD CONSTRAINT verification_token_pkey PRIMARY KEY (id);


ALTER TABLE public.item_ingredient
    ADD CONSTRAINT item_ingredient_id_ingredient_fkey FOREIGN KEY (id_ingredient) REFERENCES public.ingredient (id);


--
-- Name: item_ingredient item_ingredient_id_item_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE public.item_ingredient
    ADD CONSTRAINT item_ingredient_id_item_fkey FOREIGN KEY (id_item) REFERENCES public.item (id);


--
-- Name: item item_seller_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE public.item
    ADD CONSTRAINT item_seller_id_fkey FOREIGN KEY (seller_id) REFERENCES public.user_table (id);


--
-- Name: orders orders_item_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE public.orders
    ADD CONSTRAINT orders_item_id_fkey FOREIGN KEY (item_id) REFERENCES public.item (id);


--
-- Name: orders orders_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE public.orders
    ADD CONSTRAINT orders_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_table (id);


--
-- Name: user_details user_details_id_city_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE public.user_details
    ADD CONSTRAINT user_details_id_city_fkey FOREIGN KEY (id_city) REFERENCES public.city (id);



ALTER TABLE public.user_details
    ADD CONSTRAINT user_details_id_u_fkey FOREIGN KEY (id_u) REFERENCES public.user_table (id);



ALTER TABLE public.verification_token
    ADD CONSTRAINT user_fkey FOREIGN KEY (id) REFERENCES public.user_table (id);



ALTER TABLE public.user_table
    ADD CONSTRAINT user_table_id_role_fkey FOREIGN KEY (id_role) REFERENCES public.roles (id);


