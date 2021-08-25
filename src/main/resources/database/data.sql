--
-- TOC entry 3155 (class 0 OID 16640)
-- Dependencies: 201
-- Data for Name: city; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.city
VALUES (1, 'Tirana', false, 0);
INSERT INTO public.city
VALUES (2, 'City', false, 0);
INSERT INTO public.city
VALUES (3, 'Test', false, 0);
INSERT INTO public.city
VALUES (4, 'Test565', false, 0);


--
-- TOC entry 3157 (class 0 OID 16645)
-- Dependencies: 203
-- Data for Name: ingredient; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.ingredient
VALUES (1, 'sausage', 30.5, false, 0);
INSERT INTO public.ingredient
VALUES (2, 'tomat', 30.5, false, 0);
INSERT INTO public.ingredient
VALUES (3, 'spinach', 30.5, false, 0);
INSERT INTO public.ingredient
VALUES (4, 'chocolate', 30.5, false, 0);
INSERT INTO public.ingredient
VALUES (5, 'ingredient 2', 99, false, 0);


--
-- TOC entry 3165 (class 0 OID 16668)
-- Dependencies: 211
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.roles
VALUES (1, 'seller', false, 0);
INSERT INTO public.roles
VALUES (2, 'user', false, 0);
INSERT INTO public.roles
VALUES (3, 'admin', false, 0);


--
-- TOC entry 3169 (class 0 OID 16681)
-- Dependencies: 215
-- Data for Name: user_table; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.user_table
VALUES (2, 'Joan Janku', '0695474025', 2, true, false, 1);
INSERT INTO public.user_table
VALUES (1, 'Joan Janku', '123456789', 1, true, false, 2);
INSERT INTO public.user_table
VALUES (3, 'FullName', '087312', 3, true, false, 0);
INSERT INTO public.user_table
VALUES (4, 'FullName', '087312', 1, false, false, 0);
INSERT INTO public.user_table
VALUES (5, 'FullName', '087312', 2, false, false, 0);
INSERT INTO public.user_table
VALUES (6, 'Joan Janku', '0695474025', 1, false, false, 0);
INSERT INTO public.user_table
VALUES (7, 'Joan Janku', '0695474025', 1, false, false, 0);


--
-- TOC entry 3159 (class 0 OID 16650)
-- Dependencies: 205
-- Data for Name: item; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.item
VALUES (2, 'Just a dish 322', 'description', 35288.3, 2, '1234.jpg', false, 0);
INSERT INTO public.item
VALUES (3, 'Just a dish 322', 'description', 35288.3, 2, '1234.jpg', false, 0);
INSERT INTO public.item
VALUES (4, 'Just a dish 322', 'description', 35288.3, 2, '1234.jpg', false, 0);
INSERT INTO public.item
VALUES (5, 'Just a dish', 'description', 35288.3, 1, '1234.jpg', false, 0);
INSERT INTO public.item
VALUES (6, 'Just a dish', 'description', 35288.3, 1, '1234.jpg', false, 0);
INSERT INTO public.item
VALUES (1, 'Hamburgeri', 'Fast Food', 25.2, 2, '1234.jpg', false, 7);


--
-- TOC entry 3161 (class 0 OID 16658)
-- Dependencies: 207
-- Data for Name: item_ingredient; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.item_ingredient
VALUES (1, 1);
INSERT INTO public.item_ingredient
VALUES (1, 2);
INSERT INTO public.item_ingredient
VALUES (1, 3);
INSERT INTO public.item_ingredient
VALUES (2, 1);
INSERT INTO public.item_ingredient
VALUES (2, 2);
INSERT INTO public.item_ingredient
VALUES (2, 3);
INSERT INTO public.item_ingredient
VALUES (3, 1);
INSERT INTO public.item_ingredient
VALUES (3, 2);
INSERT INTO public.item_ingredient
VALUES (3, 3);
INSERT INTO public.item_ingredient
VALUES (4, 1);
INSERT INTO public.item_ingredient
VALUES (4, 2);
INSERT INTO public.item_ingredient
VALUES (4, 3);
INSERT INTO public.item_ingredient
VALUES (5, 1);
INSERT INTO public.item_ingredient
VALUES (5, 2);
INSERT INTO public.item_ingredient
VALUES (5, 3);
INSERT INTO public.item_ingredient
VALUES (6, 1);
INSERT INTO public.item_ingredient
VALUES (6, 2);
INSERT INTO public.item_ingredient
VALUES (6, 3);


--
-- TOC entry 3163 (class 0 OID 16663)
-- Dependencies: 209
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: postgres
--


--
-- TOC entry 3167 (class 0 OID 16673)
-- Dependencies: 213
-- Data for Name: user_details; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.user_details
VALUES (1, 'Rruga Gramoz Pashko', 'joan.janku@fti.edu.al',
        '$2a$10$jK1WZ6oFA2b7V30pPyWu.u3XigQotDyzxlXMDfjKRfsslvq257zCC', 1, 1, false, 1);
INSERT INTO public.user_details
VALUES (2, 'bsabsd', 'janku.joan4@gmail.com', '$2a$10$rpOjxPFoDsRkS8vUs5V4yOU5AqHrqsW3jnec6he2Od.i.DbDPgaiS', 1, 2,
        false, 0);
INSERT INTO public.user_details
VALUES (3, 'Address', 'joanjanku7@gmail.com', '$2a$10$Nn6mGw/QZv/ekAkDErtEneo91ETfpdwAXe4cXiTAbf8J.QFkYmtk6', 1, 3,
        false, 0);
INSERT INTO public.user_details
VALUES (4, 'Address', 'joan2647@gmail.com', '$2a$10$Tc7ADUga8lmnLXcHDBn.XeJU0XjUCWa0DUY4/eRP8bO3Mf5cIABEO', 1, 4, false,
        0);
INSERT INTO public.user_details
VALUES (5, 'Address', 'joanjankudiploma2021@gmail.com', '$2a$10$6Sy7jS79ELR/lBhN1bGUtu.bK6OV8o1c2E4iaMqH42RpZi5LvAZ42',
        1, 5, false, 0);
INSERT INTO public.user_details
VALUES (6, 'bsabsd', 'joanjanku@hotmail.com', '$2a$10$6GCIjba1UM42cNQkj/W2a.ckON7vKrJbpp8sjt3EtN/mYJgCriUz6', 1, 6,
        false, 0);
INSERT INTO public.user_details
VALUES (7, 'bsabsd', 'joan.janku@hotmail.com', '$2a$10$AWAfjeF0wbPL/rAaiWSBwOnEUjMeKcCWyiySglHhWodqwY3rBIlkq', 1, 7,
        false, 0);


--
-- TOC entry 3171 (class 0 OID 16686)
-- Dependencies: 217
-- Data for Name: verification_token; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.verification_token
VALUES (1, '5c820517-1c68-4700-a08b-6486c369c8861', 1, NULL, 0);
INSERT INTO public.verification_token
VALUES (2, '16c76712-b597-49c0-93fc-6196cb6a0f5e2', 2, NULL, 0);
INSERT INTO public.verification_token
VALUES (3, 'e2e9207f-9db0-44e9-a445-ddd94a552f1b3', 3, NULL, 0);
INSERT INTO public.verification_token
VALUES (4, '0ac795ed-0637-435e-b1cc-fcc290b2bdd34', 4, NULL, 0);
INSERT INTO public.verification_token
VALUES (5, '0c550758-15df-4d59-bc04-50eac88104e25', 5, NULL, 0);
INSERT INTO public.verification_token
VALUES (6, 'e10147a6-bfb6-407e-8daf-3ae8f15215926', 6, NULL, 0);
INSERT INTO public.verification_token
VALUES (7, '20d6ec0e-5610-4829-a787-6a7f15ae810a7', 7, NULL, 0);


--
-- TOC entry 3173 (class 0 OID 16762)
-- Dependencies: 219
-- Data for Name: city; Type: TABLE DATA; Schema: test; Owner: postgres
--

INSERT INTO test.city
VALUES (2, 'City', true, 1);
INSERT INTO test.city
VALUES (3, 'City', true, 1);
INSERT INTO test.city
VALUES (4, 'City', true, 1);
INSERT INTO test.city
VALUES (5, 'City', true, 1);
INSERT INTO test.city
VALUES (6, 'City', true, 1);
INSERT INTO test.city
VALUES (7, 'City', true, 1);
INSERT INTO test.city
VALUES (8, 'City', true, 1);
INSERT INTO test.city
VALUES (9, 'City', true, 1);
INSERT INTO test.city
VALUES (10, 'City', true, 1);
INSERT INTO test.city
VALUES (11, 'City', true, 1);
INSERT INTO test.city
VALUES (12, 'City', true, 1);
INSERT INTO test.city
VALUES (13, 'City', true, 1);
INSERT INTO test.city
VALUES (14, 'City', true, 1);
INSERT INTO test.city
VALUES (1, 'Permet', false, 1);
INSERT INTO test.city
VALUES (15, 'City', false, 0);
INSERT INTO test.city
VALUES (16, 'City2', false, 0);
INSERT INTO test.city
VALUES (17, 'City23', false, 0);


--
-- TOC entry 3175 (class 0 OID 16767)
-- Dependencies: 221
-- Data for Name: ingredient; Type: TABLE DATA; Schema: test; Owner: postgres
--

INSERT INTO test.ingredient
VALUES (3, 'Ingredient', 20.1, true, 0);
INSERT INTO test.ingredient
VALUES (4, 'Ingredient', 20.1, true, 1);
INSERT INTO test.ingredient
VALUES (2, 'Ingredient2', 40.1, false, 0);
INSERT INTO test.ingredient
VALUES (1, 'Ingredient1', 20.1, false, 0);
INSERT INTO test.ingredient
VALUES (5, 'Ingredient', 20.1, false, 0);
INSERT INTO test.ingredient
VALUES (6, 'Ingredient222', 20.1, false, 0);
INSERT INTO test.ingredient
VALUES (7, 'Ingredient22', 20.1, false, 0);
INSERT INTO test.ingredient
VALUES (8, 'Ingredient223', 20.1, false, 0);


--
-- TOC entry 3183 (class 0 OID 16790)
-- Dependencies: 229
-- Data for Name: roles; Type: TABLE DATA; Schema: test; Owner: postgres
--

INSERT INTO test.roles
VALUES (3, 'user', true, 1);
INSERT INTO test.roles
VALUES (4, 'user', true, 1);
INSERT INTO test.roles
VALUES (5, 'user', true, 0);
INSERT INTO test.roles
VALUES (6, 'user', true, 1);
INSERT INTO test.roles
VALUES (7, 'user', true, 1);
INSERT INTO test.roles
VALUES (8, 'user', true, 1);
INSERT INTO test.roles
VALUES (9, 'user', true, 1);
INSERT INTO test.roles
VALUES (10, 'user', false, 0);
INSERT INTO test.roles
VALUES (1, 'admin', false, 2);
INSERT INTO test.roles
VALUES (2, 'user', false, 0);
INSERT INTO test.roles
VALUES (11, 'user', false, 0);
INSERT INTO test.roles
VALUES (12, 'user', false, 0);
INSERT INTO test.roles
VALUES (13, 'user', false, 0);
INSERT INTO test.roles
VALUES (14, 'user', false, 0);
INSERT INTO test.roles
VALUES (15, 'role', false, 0);
INSERT INTO test.roles
VALUES (16, 'roles', false, 0);


--
-- TOC entry 3187 (class 0 OID 16803)
-- Dependencies: 233
-- Data for Name: user_table; Type: TABLE DATA; Schema: test; Owner: postgres
--

INSERT INTO test.user_table
VALUES (1, 'Joan Janku', '0695474025', 1, true, false, 1);
INSERT INTO test.user_table
VALUES (2, 'Joan Janku', '0695474025', 2, true, false, 1);


--
-- TOC entry 3177 (class 0 OID 16772)
-- Dependencies: 223
-- Data for Name: item; Type: TABLE DATA; Schema: test; Owner: postgres
--

INSERT INTO test.item
VALUES (1, 'Item', 'Description', 10, 1, '1234.jpg', false, 0);
INSERT INTO test.item
VALUES (3, 'Item', 'Description', 10, 1, '1234.jpg', false, 0);
INSERT INTO test.item
VALUES (4, 'Item', 'Description', 10, 1, '1234.jpg', false, 0);
INSERT INTO test.item
VALUES (5, 'Item', 'Description', 10, 1, '1234.jpg', false, 0);
INSERT INTO test.item
VALUES (6, 'Item', 'Description', 10, 1, '1234.jpg', false, 0);
INSERT INTO test.item
VALUES (7, 'Item', 'Description', 10, 1, '1234.jpg', false, 0);
INSERT INTO test.item
VALUES (8, 'Item', 'Description', 10, 1, '1234.jpg', false, 0);
INSERT INTO test.item
VALUES (9, 'Item', 'Description', 10, 1, '1234.jpg', false, 0);
INSERT INTO test.item
VALUES (10, 'Item', 'Description', 10, 1, '1234.jpg', false, 0);
INSERT INTO test.item
VALUES (2, 'Update item ', 'Updated Description', 1, 1, '1234.jpg', false, 19);


--
-- TOC entry 3179 (class 0 OID 16780)
-- Dependencies: 225
-- Data for Name: item_ingredient; Type: TABLE DATA; Schema: test; Owner: postgres
--

INSERT INTO test.item_ingredient
VALUES (1, 1);
INSERT INTO test.item_ingredient
VALUES (1, 2);
INSERT INTO test.item_ingredient
VALUES (3, 1);
INSERT INTO test.item_ingredient
VALUES (3, 2);
INSERT INTO test.item_ingredient
VALUES (4, 1);
INSERT INTO test.item_ingredient
VALUES (4, 2);
INSERT INTO test.item_ingredient
VALUES (5, 1);
INSERT INTO test.item_ingredient
VALUES (5, 2);
INSERT INTO test.item_ingredient
VALUES (6, 1);
INSERT INTO test.item_ingredient
VALUES (6, 2);
INSERT INTO test.item_ingredient
VALUES (7, 1);
INSERT INTO test.item_ingredient
VALUES (7, 2);
INSERT INTO test.item_ingredient
VALUES (8, 1);
INSERT INTO test.item_ingredient
VALUES (8, 2);
INSERT INTO test.item_ingredient
VALUES (9, 1);
INSERT INTO test.item_ingredient
VALUES (9, 2);
INSERT INTO test.item_ingredient
VALUES (10, 1);
INSERT INTO test.item_ingredient
VALUES (10, 2);
INSERT INTO test.item_ingredient
VALUES (2, 1);
INSERT INTO test.item_ingredient
VALUES (2, 2);


--
-- TOC entry 3181 (class 0 OID 16785)
-- Dependencies: 227
-- Data for Name: orders; Type: TABLE DATA; Schema: test; Owner: postgres
--

INSERT INTO test.orders
VALUES (1, '2021-08-19 14:47:18.147462', 'Description', true, 2, 1, false, false, 2);
INSERT INTO test.orders
VALUES (2, '2021-08-19 14:57:30.574455', 'Description', true, 2, 1, false, false, 2);
INSERT INTO test.orders
VALUES (3, '2021-08-19 14:57:59.044744', 'Description', true, 2, 1, false, false, 2);
INSERT INTO test.orders
VALUES (7, '2021-08-23 09:08:38.931724', '', false, 2, 1, true, false, 0);
INSERT INTO test.orders
VALUES (8, '2021-08-23 15:23:18.589246', '', false, 2, 1, true, false, 0);
INSERT INTO test.orders
VALUES (9, '2021-08-23 15:24:41.324373', '', false, 2, 1, true, false, 0);
INSERT INTO test.orders
VALUES (4, '2021-08-19 14:58:35.937534', 'Description', true, 3, 1, false, false, 2);
INSERT INTO test.orders
VALUES (10, '2021-08-23 15:48:18.713249', '', false, 2, 1, true, false, 0);
INSERT INTO test.orders
VALUES (11, '2021-08-23 15:49:13.159029', '', false, 2, 1, true, false, 0);
INSERT INTO test.orders
VALUES (5, '2021-08-19 14:59:37.725959', 'Description', true, 2, 1, false, false, 2);
INSERT INTO test.orders
VALUES (12, '2021-08-23 17:02:26.198092', '', false, 2, 1, true, false, 0);
INSERT INTO test.orders
VALUES (13, '2021-08-23 17:03:09.629268', '', false, 2, 1, true, false, 0);
INSERT INTO test.orders
VALUES (6, '2021-08-19 15:03:07.313551', 'Description', true, 2, 1, false, false, 2);


--
-- TOC entry 3185 (class 0 OID 16795)
-- Dependencies: 231
-- Data for Name: user_details; Type: TABLE DATA; Schema: test; Owner: postgres
--

INSERT INTO test.user_details
VALUES (1, 'bsabsd', 'joan.janku@fti.edu.al', '$2a$10$GG..DTjJ82GJLd4tx5vx.ufGk9t14/0x2BBNf7jpt0Nsw3GHAC0k2', 1, 1,
        false, 0);
INSERT INTO test.user_details
VALUES (2, 'bsabsd', 'janku.joan4@gmail.com', '$2a$10$rpOjxPFoDsRkS8vUs5V4yOU5AqHrqsW3jnec6he2Od.i.DbDPgaiS', 1, 2,
        false, 0);


--
-- TOC entry 3189 (class 0 OID 16808)
-- Dependencies: 235
-- Data for Name: verification_token; Type: TABLE DATA; Schema: test; Owner: postgres
--

INSERT INTO test.verification_token
VALUES (1, 'fdae9b7f-f310-4b2f-9011-6ac58427066d1', 1, NULL, 0);


--
-- TOC entry 3196 (class 0 OID 0)
-- Dependencies: 202
-- Name: city_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.city_id_seq', 4, true);


--
-- TOC entry 3197 (class 0 OID 0)
-- Dependencies: 204
-- Name: ingredient_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.ingredient_id_seq', 5, true);


--
-- TOC entry 3198 (class 0 OID 0)
-- Dependencies: 206
-- Name: item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.item_id_seq', 6, true);


--
-- TOC entry 3199 (class 0 OID 0)
-- Dependencies: 208
-- Name: item_ingredient_id_item_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.item_ingredient_id_item_seq', 1, false);


--
-- TOC entry 3200 (class 0 OID 0)
-- Dependencies: 210
-- Name: orders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.orders_id_seq', 1, false);


--
-- TOC entry 3201 (class 0 OID 0)
-- Dependencies: 212
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_id_seq', 3, true);


--
-- TOC entry 3202 (class 0 OID 0)
-- Dependencies: 214
-- Name: user_details_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_details_id_seq', 7, true);


--
-- TOC entry 3203 (class 0 OID 0)
-- Dependencies: 216
-- Name: user_table_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_table_id_seq', 7, true);


--
-- TOC entry 3204 (class 0 OID 0)
-- Dependencies: 218
-- Name: verification_token_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.verification_token_id_seq', 7, true);


--
-- TOC entry 3205 (class 0 OID 0)
-- Dependencies: 220
-- Name: city_id_seq; Type: SEQUENCE SET; Schema: test; Owner: postgres
--

SELECT pg_catalog.setval('test.city_id_seq', 17, true);


--
-- TOC entry 3206 (class 0 OID 0)
-- Dependencies: 222
-- Name: ingredient_id_seq; Type: SEQUENCE SET; Schema: test; Owner: postgres
--

SELECT pg_catalog.setval('test.ingredient_id_seq', 8, true);


--
-- TOC entry 3207 (class 0 OID 0)
-- Dependencies: 224
-- Name: item_id_seq; Type: SEQUENCE SET; Schema: test; Owner: postgres
--

SELECT pg_catalog.setval('test.item_id_seq', 10, true);


--
-- TOC entry 3208 (class 0 OID 0)
-- Dependencies: 226
-- Name: item_ingredient_id_item_seq; Type: SEQUENCE SET; Schema: test; Owner: postgres
--

SELECT pg_catalog.setval('test.item_ingredient_id_item_seq', 1, false);


--
-- TOC entry 3209 (class 0 OID 0)
-- Dependencies: 228
-- Name: orders_id_seq; Type: SEQUENCE SET; Schema: test; Owner: postgres
--

SELECT pg_catalog.setval('test.orders_id_seq', 13, true);


--
-- TOC entry 3210 (class 0 OID 0)
-- Dependencies: 230
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: test; Owner: postgres
--

SELECT pg_catalog.setval('test.roles_id_seq', 16, true);


--
-- TOC entry 3211 (class 0 OID 0)
-- Dependencies: 232
-- Name: user_details_id_seq; Type: SEQUENCE SET; Schema: test; Owner: postgres
--

SELECT pg_catalog.setval('test.user_details_id_seq', 1, true);


--
-- TOC entry 3212 (class 0 OID 0)
-- Dependencies: 234
-- Name: user_table_id_seq; Type: SEQUENCE SET; Schema: test; Owner: postgres
--

SELECT pg_catalog.setval('test.user_table_id_seq', 1, true);


--
-- TOC entry 3213 (class 0 OID 0)
-- Dependencies: 236
-- Name: verification_token_id_seq; Type: SEQUENCE SET; Schema: test; Owner: postgres
--

SELECT pg_catalog.setval('test.verification_token_id_seq', 1, true);


-- Completed on 2021-08-24 09:42:48

--
-- PostgreSQL database dump complete
--