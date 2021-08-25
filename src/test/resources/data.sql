INSERT INTO public.city (id, name, deleted, version)
VALUES (1, 'Tirana', false, 0);


--
-- TOC entry 3157 (class 0 OID 16645)
-- Dependencies: 203
-- Data for Name: ingredient; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.ingredient (id, name, calories, deleted, version)
VALUES (1, 'sausage', 30.5, false, 0);
INSERT INTO public.ingredient (id, name, calories, deleted, version)
VALUES (2, 'tomat', 30.5, false, 0);
INSERT INTO public.ingredient (id, name, calories, deleted, version)
VALUES (3, 'spinach', 30.5, false, 0);
INSERT INTO public.ingredient (id, name, calories, deleted, version)
VALUES (4, 'chocolate', 30.5, false, 0);


--
-- TOC entry 3165 (class 0 OID 16668)
-- Dependencies: 211
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.roles (id, name, deleted, version)
VALUES (1, 'seller', false, 0);
INSERT INTO public.roles (id, name, deleted, version)
VALUES (2, 'user', false, 0);


--
-- TOC entry 3169 (class 0 OID 16681)
-- Dependencies: 215
-- Data for Name: user_table; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.user_table (id, full_name, phone_number, id_role, enabled, deleted, version)
VALUES (1, 'Joan Janku', '0695474025', 1, true, false, 1);
INSERT INTO public.user_table (id, full_name, phone_number, id_role, enabled, deleted, version)
VALUES (2, 'Joan Janku', '0695474025', 2, true, false, 1);


--
-- TOC entry 3159 (class 0 OID 16650)
-- Dependencies: 205
-- Data for Name: item; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.item (id, name, description, price, seller_id, picture, deleted, version)
VALUES (1, 'Just a dish 322', 'description', 35288.3, 2, '1234.jpg', false, 0);
INSERT INTO public.item (id, name, description, price, seller_id, picture, deleted, version)
VALUES (2, 'Just a dish 322', 'description', 35288.3, 2, '1234.jpg', false, 0);
INSERT INTO public.item (id, name, description, price, seller_id, picture, deleted, version)
VALUES (3, 'Just a dish 322', 'description', 35288.3, 2, '1234.jpg', false, 0);
INSERT INTO public.item (id, name, description, price, seller_id, picture, deleted, version)
VALUES (4, 'Just a dish 322', 'description', 35288.3, 2, '1234.jpg', false, 0);
INSERT INTO public.item (id, name, description, price, seller_id, picture, deleted, version)
VALUES (5, 'Just a dish', 'description', 35288.3, 1, '1234.jpg', false, 0);
INSERT INTO public.item (id, name, description, price, seller_id, picture, deleted, version)
VALUES (6, 'Just a dish', 'description', 35288.3, 1, '1234.jpg', false, 0);


--
-- TOC entry 3161 (class 0 OID 16658)
-- Dependencies: 207
-- Data for Name: item_ingredient; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.item_ingredient (id_item, id_ingredient)
VALUES (1, 1);
INSERT INTO public.item_ingredient (id_item, id_ingredient)
VALUES (1, 2);
INSERT INTO public.item_ingredient (id_item, id_ingredient)
VALUES (1, 3);
INSERT INTO public.item_ingredient (id_item, id_ingredient)
VALUES (2, 1);
INSERT INTO public.item_ingredient (id_item, id_ingredient)
VALUES (2, 2);
INSERT INTO public.item_ingredient (id_item, id_ingredient)
VALUES (2, 3);
INSERT INTO public.item_ingredient (id_item, id_ingredient)
VALUES (3, 1);
INSERT INTO public.item_ingredient (id_item, id_ingredient)
VALUES (3, 2);
INSERT INTO public.item_ingredient (id_item, id_ingredient)
VALUES (3, 3);
INSERT INTO public.item_ingredient (id_item, id_ingredient)
VALUES (4, 1);
INSERT INTO public.item_ingredient (id_item, id_ingredient)
VALUES (4, 2);
INSERT INTO public.item_ingredient (id_item, id_ingredient)
VALUES (4, 3);
INSERT INTO public.item_ingredient (id_item, id_ingredient)
VALUES (5, 1);
INSERT INTO public.item_ingredient (id_item, id_ingredient)
VALUES (5, 2);
INSERT INTO public.item_ingredient (id_item, id_ingredient)
VALUES (5, 3);
INSERT INTO public.item_ingredient (id_item, id_ingredient)
VALUES (6, 1);
INSERT INTO public.item_ingredient (id_item, id_ingredient)
VALUES (6, 2);
INSERT INTO public.item_ingredient (id_item, id_ingredient)
VALUES (6, 3);


--
-- TOC entry 3163 (class 0 OID 16663)
-- Dependencies: 209
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: -
--


--
-- TOC entry 3167 (class 0 OID 16673)
-- Dependencies: 213
-- Data for Name: user_details; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.user_details (id, address, email, password, id_city, id_u, deleted, version)
VALUES (1, 'Rruga Gramoz Pashko', 'joan.janku@fti.edu.al',
        '$2a$10$jK1WZ6oFA2b7V30pPyWu.u3XigQotDyzxlXMDfjKRfsslvq257zCC', 1, 1, false, 1);
INSERT INTO public.user_details (id, address, email, password, id_city, id_u, deleted, version)
VALUES (2, 'bsabsd', 'janku.joan4@gmail.com', '$2a$10$rpOjxPFoDsRkS8vUs5V4yOU5AqHrqsW3jnec6he2Od.i.DbDPgaiS', 1, 2,
        false, 0);


--
-- TOC entry 3171 (class 0 OID 16686)
-- Dependencies: 217
-- Data for Name: verification_token; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.verification_token (id, token, id_user, expiry_date, version)
VALUES (1, '5c820517-1c68-4700-a08b-6486c369c8861', 1, NULL, 0);
INSERT INTO public.verification_token (id, token, id_user, expiry_date, version)
VALUES (2, '16c76712-b597-49c0-93fc-6196cb6a0f5e2', 2, NULL, 0);
