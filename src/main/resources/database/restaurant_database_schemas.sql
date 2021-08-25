--
-- PostgreSQL database dump
--

-- Dumped from database version 13.3
-- Dumped by pg_dump version 13.3

-- Started on 2021-08-24 09:41:37

SET
statement_timeout = 0;
SET
lock_timeout = 0;
SET
idle_in_transaction_session_timeout = 0;
SET
client_encoding = 'UTF8';
SET
standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET
check_function_bodies = false;
SET
xmloption = content;
SET
client_min_messages = warning;
SET
row_security = off;

--
-- TOC entry 3160 (class 1262 OID 16518)
-- Name: restaurant; Type: DATABASE; Schema: -; Owner: -
--

CREATE
DATABASE restaurant WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'English_United States.1252';


\connect restaurant

SET statement_timeout = 0;
SET
lock_timeout = 0;
SET
idle_in_transaction_session_timeout = 0;
SET
client_encoding = 'UTF8';
SET
standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET
check_function_bodies = false;
SET
xmloption = content;
SET
client_min_messages = warning;
SET
row_security = off;

--
-- TOC entry 4 (class 2615 OID 16761)
-- Name: test; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA test;


SET
default_table_access_method = heap;

--
-- TOC entry 201 (class 1259 OID 16640)
-- Name: city; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.city
(
    id      integer NOT NULL,
    name    character varying(60),
    deleted boolean,
    version integer
);


--
-- TOC entry 202 (class 1259 OID 16643)
-- Name: city_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.city_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


--
-- TOC entry 3161 (class 0 OID 0)
-- Dependencies: 202
-- Name: city_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.city_id_seq OWNED BY public.city.id;


--
-- TOC entry 203 (class 1259 OID 16645)
-- Name: ingredient; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ingredient
(
    id       integer               NOT NULL,
    name     character varying(60) NOT NULL,
    calories double precision,
    deleted  boolean,
    version  integer
);


--
-- TOC entry 204 (class 1259 OID 16648)
-- Name: ingredient_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.ingredient_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


--
-- TOC entry 3162 (class 0 OID 0)
-- Dependencies: 204
-- Name: ingredient_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.ingredient_id_seq OWNED BY public.ingredient.id;


--
-- TOC entry 205 (class 1259 OID 16650)
-- Name: item; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.item
(
    id          integer                NOT NULL,
    name        character varying(60)  NOT NULL,
    description character varying(250) NOT NULL,
    price       double precision       NOT NULL,
    seller_id   integer,
    picture     character varying(255),
    deleted     boolean,
    version     integer
);


--
-- TOC entry 206 (class 1259 OID 16656)
-- Name: item_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.item_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


--
-- TOC entry 3163 (class 0 OID 0)
-- Dependencies: 206
-- Name: item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.item_id_seq OWNED BY public.item.id;


--
-- TOC entry 207 (class 1259 OID 16658)
-- Name: item_ingredient; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.item_ingredient
(
    id_item       integer NOT NULL,
    id_ingredient integer NOT NULL
);


--
-- TOC entry 208 (class 1259 OID 16661)
-- Name: item_ingredient_id_item_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.item_ingredient_id_item_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


--
-- TOC entry 3164 (class 0 OID 0)
-- Dependencies: 208
-- Name: item_ingredient_id_item_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.item_ingredient_id_item_seq OWNED BY public.item_ingredient.id_item;


--
-- TOC entry 209 (class 1259 OID 16663)
-- Name: orders; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.orders
(
    id        integer NOT NULL,
    date_time timestamp without time zone NOT NULL,
    feedback  character varying(250),
    delivered boolean NOT NULL,
    item_id   integer NOT NULL,
    user_id   integer NOT NULL,
    active    boolean,
    deleted   boolean,
    version   integer
);


--
-- TOC entry 210 (class 1259 OID 16666)
-- Name: orders_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.orders_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


--
-- TOC entry 3165 (class 0 OID 0)
-- Dependencies: 210
-- Name: orders_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.orders_id_seq OWNED BY public.orders.id;


--
-- TOC entry 211 (class 1259 OID 16668)
-- Name: roles; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.roles
(
    id      integer NOT NULL,
    name    character varying(60),
    deleted boolean,
    version integer
);


--
-- TOC entry 212 (class 1259 OID 16671)
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


--
-- TOC entry 3166 (class 0 OID 0)
-- Dependencies: 212
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


--
-- TOC entry 213 (class 1259 OID 16673)
-- Name: user_details; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.user_details
(
    id       integer                NOT NULL,
    address  character varying(250),
    email    character varying(120) NOT NULL,
    password character varying(255) NOT NULL,
    id_city  integer                NOT NULL,
    id_u     integer                NOT NULL,
    deleted  boolean,
    version  integer
);


--
-- TOC entry 214 (class 1259 OID 16679)
-- Name: user_details_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.user_details_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


--
-- TOC entry 3167 (class 0 OID 0)
-- Dependencies: 214
-- Name: user_details_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.user_details_id_seq OWNED BY public.user_details.id;


--
-- TOC entry 215 (class 1259 OID 16681)
-- Name: user_table; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.user_table
(
    id           integer NOT NULL,
    full_name    character varying(60),
    phone_number character varying(32),
    id_role      integer NOT NULL,
    enabled      boolean,
    deleted      boolean,
    version      integer
);


--
-- TOC entry 216 (class 1259 OID 16684)
-- Name: user_table_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.user_table_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


--
-- TOC entry 3168 (class 0 OID 0)
-- Dependencies: 216
-- Name: user_table_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.user_table_id_seq OWNED BY public.user_table.id;


--
-- TOC entry 217 (class 1259 OID 16686)
-- Name: verification_token; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.verification_token
(
    id          integer NOT NULL,
    token       character varying(255),
    id_user     integer,
    expiry_date timestamp without time zone,
    version     integer
);


--
-- TOC entry 218 (class 1259 OID 16689)
-- Name: verification_token_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.verification_token_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


--
-- TOC entry 3169 (class 0 OID 0)
-- Dependencies: 218
-- Name: verification_token_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.verification_token_id_seq OWNED BY public.verification_token.id;


--
-- TOC entry 219 (class 1259 OID 16762)
-- Name: city; Type: TABLE; Schema: test; Owner: -
--

CREATE TABLE test.city
(
    id      integer NOT NULL,
    name    character varying(60),
    deleted boolean,
    version integer
);


--
-- TOC entry 220 (class 1259 OID 16765)
-- Name: city_id_seq; Type: SEQUENCE; Schema: test; Owner: -
--

CREATE SEQUENCE test.city_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


--
-- TOC entry 3170 (class 0 OID 0)
-- Dependencies: 220
-- Name: city_id_seq; Type: SEQUENCE OWNED BY; Schema: test; Owner: -
--

ALTER SEQUENCE test.city_id_seq OWNED BY test.city.id;


--
-- TOC entry 221 (class 1259 OID 16767)
-- Name: ingredient; Type: TABLE; Schema: test; Owner: -
--

CREATE TABLE test.ingredient
(
    id       integer               NOT NULL,
    name     character varying(60) NOT NULL,
    calories double precision,
    deleted  boolean,
    version  integer
);


--
-- TOC entry 222 (class 1259 OID 16770)
-- Name: ingredient_id_seq; Type: SEQUENCE; Schema: test; Owner: -
--

CREATE SEQUENCE test.ingredient_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


--
-- TOC entry 3171 (class 0 OID 0)
-- Dependencies: 222
-- Name: ingredient_id_seq; Type: SEQUENCE OWNED BY; Schema: test; Owner: -
--

ALTER SEQUENCE test.ingredient_id_seq OWNED BY test.ingredient.id;


--
-- TOC entry 223 (class 1259 OID 16772)
-- Name: item; Type: TABLE; Schema: test; Owner: -
--

CREATE TABLE test.item
(
    id          integer                NOT NULL,
    name        character varying(60)  NOT NULL,
    description character varying(250) NOT NULL,
    price       double precision       NOT NULL,
    seller_id   integer,
    picture     character varying(255),
    deleted     boolean,
    version     integer
);


--
-- TOC entry 224 (class 1259 OID 16778)
-- Name: item_id_seq; Type: SEQUENCE; Schema: test; Owner: -
--

CREATE SEQUENCE test.item_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


--
-- TOC entry 3172 (class 0 OID 0)
-- Dependencies: 224
-- Name: item_id_seq; Type: SEQUENCE OWNED BY; Schema: test; Owner: -
--

ALTER SEQUENCE test.item_id_seq OWNED BY test.item.id;


--
-- TOC entry 225 (class 1259 OID 16780)
-- Name: item_ingredient; Type: TABLE; Schema: test; Owner: -
--

CREATE TABLE test.item_ingredient
(
    id_item       integer NOT NULL,
    id_ingredient integer NOT NULL
);


--
-- TOC entry 226 (class 1259 OID 16783)
-- Name: item_ingredient_id_item_seq; Type: SEQUENCE; Schema: test; Owner: -
--

CREATE SEQUENCE test.item_ingredient_id_item_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


--
-- TOC entry 3173 (class 0 OID 0)
-- Dependencies: 226
-- Name: item_ingredient_id_item_seq; Type: SEQUENCE OWNED BY; Schema: test; Owner: -
--

ALTER SEQUENCE test.item_ingredient_id_item_seq OWNED BY test.item_ingredient.id_item;


--
-- TOC entry 227 (class 1259 OID 16785)
-- Name: orders; Type: TABLE; Schema: test; Owner: -
--

CREATE TABLE test.orders
(
    id        integer NOT NULL,
    date_time timestamp without time zone NOT NULL,
    feedback  character varying(250),
    delivered boolean NOT NULL,
    item_id   integer NOT NULL,
    user_id   integer NOT NULL,
    active    boolean,
    deleted   boolean,
    version   integer
);


--
-- TOC entry 228 (class 1259 OID 16788)
-- Name: orders_id_seq; Type: SEQUENCE; Schema: test; Owner: -
--

CREATE SEQUENCE test.orders_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


--
-- TOC entry 3174 (class 0 OID 0)
-- Dependencies: 228
-- Name: orders_id_seq; Type: SEQUENCE OWNED BY; Schema: test; Owner: -
--

ALTER SEQUENCE test.orders_id_seq OWNED BY test.orders.id;


--
-- TOC entry 229 (class 1259 OID 16790)
-- Name: roles; Type: TABLE; Schema: test; Owner: -
--

CREATE TABLE test.roles
(
    id      integer NOT NULL,
    name    character varying(60),
    deleted boolean,
    version integer
);


--
-- TOC entry 230 (class 1259 OID 16793)
-- Name: roles_id_seq; Type: SEQUENCE; Schema: test; Owner: -
--

CREATE SEQUENCE test.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


--
-- TOC entry 3175 (class 0 OID 0)
-- Dependencies: 230
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: test; Owner: -
--

ALTER SEQUENCE test.roles_id_seq OWNED BY test.roles.id;


--
-- TOC entry 231 (class 1259 OID 16795)
-- Name: user_details; Type: TABLE; Schema: test; Owner: -
--

CREATE TABLE test.user_details
(
    id       integer                NOT NULL,
    address  character varying(250),
    email    character varying(120) NOT NULL,
    password character varying(255) NOT NULL,
    id_city  integer                NOT NULL,
    id_u     integer                NOT NULL,
    deleted  boolean,
    version  integer
);


--
-- TOC entry 232 (class 1259 OID 16801)
-- Name: user_details_id_seq; Type: SEQUENCE; Schema: test; Owner: -
--

CREATE SEQUENCE test.user_details_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


--
-- TOC entry 3176 (class 0 OID 0)
-- Dependencies: 232
-- Name: user_details_id_seq; Type: SEQUENCE OWNED BY; Schema: test; Owner: -
--

ALTER SEQUENCE test.user_details_id_seq OWNED BY test.user_details.id;


--
-- TOC entry 233 (class 1259 OID 16803)
-- Name: user_table; Type: TABLE; Schema: test; Owner: -
--

CREATE TABLE test.user_table
(
    id           integer NOT NULL,
    full_name    character varying(60),
    phone_number character varying(32),
    id_role      integer NOT NULL,
    enabled      boolean,
    deleted      boolean,
    version      integer
);


--
-- TOC entry 234 (class 1259 OID 16806)
-- Name: user_table_id_seq; Type: SEQUENCE; Schema: test; Owner: -
--

CREATE SEQUENCE test.user_table_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


--
-- TOC entry 3177 (class 0 OID 0)
-- Dependencies: 234
-- Name: user_table_id_seq; Type: SEQUENCE OWNED BY; Schema: test; Owner: -
--

ALTER SEQUENCE test.user_table_id_seq OWNED BY test.user_table.id;


--
-- TOC entry 235 (class 1259 OID 16808)
-- Name: verification_token; Type: TABLE; Schema: test; Owner: -
--

CREATE TABLE test.verification_token
(
    id          integer NOT NULL,
    token       character varying(255),
    id_user     integer,
    expiry_date timestamp without time zone,
    version     integer
);


--
-- TOC entry 236 (class 1259 OID 16811)
-- Name: verification_token_id_seq; Type: SEQUENCE; Schema: test; Owner: -
--

CREATE SEQUENCE test.verification_token_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


--
-- TOC entry 3178 (class 0 OID 0)
-- Dependencies: 236
-- Name: verification_token_id_seq; Type: SEQUENCE OWNED BY; Schema: test; Owner: -
--

ALTER SEQUENCE test.verification_token_id_seq OWNED BY test.verification_token.id;


--
-- TOC entry 2957 (class 2604 OID 16691)
-- Name: city id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.city ALTER COLUMN id SET DEFAULT nextval('public.city_id_seq'::regclass);


--
-- TOC entry 2958 (class 2604 OID 16692)
-- Name: ingredient id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ingredient ALTER COLUMN id SET DEFAULT nextval('public.ingredient_id_seq'::regclass);


--
-- TOC entry 2959 (class 2604 OID 16693)
-- Name: item id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.item ALTER COLUMN id SET DEFAULT nextval('public.item_id_seq'::regclass);


--
-- TOC entry 2960 (class 2604 OID 16694)
-- Name: item_ingredient id_item; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.item_ingredient ALTER COLUMN id_item SET DEFAULT nextval('public.item_ingredient_id_item_seq'::regclass);


--
-- TOC entry 2961 (class 2604 OID 16695)
-- Name: orders id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders ALTER COLUMN id SET DEFAULT nextval('public.orders_id_seq'::regclass);


--
-- TOC entry 2962 (class 2604 OID 16696)
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


--
-- TOC entry 2963 (class 2604 OID 16697)
-- Name: user_details id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_details ALTER COLUMN id SET DEFAULT nextval('public.user_details_id_seq'::regclass);


--
-- TOC entry 2964 (class 2604 OID 16698)
-- Name: user_table id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_table ALTER COLUMN id SET DEFAULT nextval('public.user_table_id_seq'::regclass);


--
-- TOC entry 2965 (class 2604 OID 16699)
-- Name: verification_token id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.verification_token ALTER COLUMN id SET DEFAULT nextval('public.verification_token_id_seq'::regclass);


--
-- TOC entry 2966 (class 2604 OID 16813)
-- Name: city id; Type: DEFAULT; Schema: test; Owner: -
--

ALTER TABLE ONLY test.city ALTER COLUMN id SET DEFAULT nextval('test.city_id_seq'::regclass);


--
-- TOC entry 2967 (class 2604 OID 16814)
-- Name: ingredient id; Type: DEFAULT; Schema: test; Owner: -
--

ALTER TABLE ONLY test.ingredient ALTER COLUMN id SET DEFAULT nextval('test.ingredient_id_seq'::regclass);


--
-- TOC entry 2968 (class 2604 OID 16815)
-- Name: item id; Type: DEFAULT; Schema: test; Owner: -
--

ALTER TABLE ONLY test.item ALTER COLUMN id SET DEFAULT nextval('test.item_id_seq'::regclass);


--
-- TOC entry 2969 (class 2604 OID 16816)
-- Name: item_ingredient id_item; Type: DEFAULT; Schema: test; Owner: -
--

ALTER TABLE ONLY test.item_ingredient ALTER COLUMN id_item SET DEFAULT nextval('test.item_ingredient_id_item_seq'::regclass);


--
-- TOC entry 2970 (class 2604 OID 16817)
-- Name: orders id; Type: DEFAULT; Schema: test; Owner: -
--

ALTER TABLE ONLY test.orders ALTER COLUMN id SET DEFAULT nextval('test.orders_id_seq'::regclass);


--
-- TOC entry 2971 (class 2604 OID 16818)
-- Name: roles id; Type: DEFAULT; Schema: test; Owner: -
--

ALTER TABLE ONLY test.roles ALTER COLUMN id SET DEFAULT nextval('test.roles_id_seq'::regclass);


--
-- TOC entry 2972 (class 2604 OID 16819)
-- Name: user_details id; Type: DEFAULT; Schema: test; Owner: -
--

ALTER TABLE ONLY test.user_details ALTER COLUMN id SET DEFAULT nextval('test.user_details_id_seq'::regclass);


--
-- TOC entry 2973 (class 2604 OID 16820)
-- Name: user_table id; Type: DEFAULT; Schema: test; Owner: -
--

ALTER TABLE ONLY test.user_table ALTER COLUMN id SET DEFAULT nextval('test.user_table_id_seq'::regclass);


--
-- TOC entry 2974 (class 2604 OID 16821)
-- Name: verification_token id; Type: DEFAULT; Schema: test; Owner: -
--

ALTER TABLE ONLY test.verification_token ALTER COLUMN id SET DEFAULT nextval('test.verification_token_id_seq'::regclass);


--
-- TOC entry 2976 (class 2606 OID 16701)
-- Name: city city_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.city
    ADD CONSTRAINT city_pkey PRIMARY KEY (id);


--
-- TOC entry 2978 (class 2606 OID 16703)
-- Name: ingredient ingredient_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ingredient
    ADD CONSTRAINT ingredient_pkey PRIMARY KEY (id);


--
-- TOC entry 2980 (class 2606 OID 16705)
-- Name: item item_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.item
    ADD CONSTRAINT item_pkey PRIMARY KEY (id);


--
-- TOC entry 2982 (class 2606 OID 16707)
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- TOC entry 2984 (class 2606 OID 16709)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- TOC entry 2986 (class 2606 OID 16711)
-- Name: user_details user_details_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_details
    ADD CONSTRAINT user_details_pkey PRIMARY KEY (id);


--
-- TOC entry 2988 (class 2606 OID 16713)
-- Name: user_table user_table_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_table
    ADD CONSTRAINT user_table_pkey PRIMARY KEY (id);


--
-- TOC entry 2990 (class 2606 OID 16715)
-- Name: verification_token verification_token_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.verification_token
    ADD CONSTRAINT verification_token_pkey PRIMARY KEY (id);


--
-- TOC entry 2992 (class 2606 OID 16823)
-- Name: city city_pkey; Type: CONSTRAINT; Schema: test; Owner: -
--

ALTER TABLE ONLY test.city
    ADD CONSTRAINT city_pkey PRIMARY KEY (id);


--
-- TOC entry 2994 (class 2606 OID 16825)
-- Name: ingredient ingredient_pkey; Type: CONSTRAINT; Schema: test; Owner: -
--

ALTER TABLE ONLY test.ingredient
    ADD CONSTRAINT ingredient_pkey PRIMARY KEY (id);


--
-- TOC entry 2996 (class 2606 OID 16827)
-- Name: item item_pkey; Type: CONSTRAINT; Schema: test; Owner: -
--

ALTER TABLE ONLY test.item
    ADD CONSTRAINT item_pkey PRIMARY KEY (id);


--
-- TOC entry 2998 (class 2606 OID 16829)
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: test; Owner: -
--

ALTER TABLE ONLY test.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- TOC entry 3000 (class 2606 OID 16831)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: test; Owner: -
--

ALTER TABLE ONLY test.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- TOC entry 3002 (class 2606 OID 16833)
-- Name: user_details user_details_pkey; Type: CONSTRAINT; Schema: test; Owner: -
--

ALTER TABLE ONLY test.user_details
    ADD CONSTRAINT user_details_pkey PRIMARY KEY (id);


--
-- TOC entry 3004 (class 2606 OID 16835)
-- Name: user_table user_table_pkey; Type: CONSTRAINT; Schema: test; Owner: -
--

ALTER TABLE ONLY test.user_table
    ADD CONSTRAINT user_table_pkey PRIMARY KEY (id);


--
-- TOC entry 3006 (class 2606 OID 16837)
-- Name: verification_token verification_token_pkey; Type: CONSTRAINT; Schema: test; Owner: -
--

ALTER TABLE ONLY test.verification_token
    ADD CONSTRAINT verification_token_pkey PRIMARY KEY (id);


--
-- TOC entry 3008 (class 2606 OID 16716)
-- Name: item_ingredient item_ingredient_id_ingredient_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.item_ingredient
    ADD CONSTRAINT item_ingredient_id_ingredient_fkey FOREIGN KEY (id_ingredient) REFERENCES public.ingredient(id);


--
-- TOC entry 3009 (class 2606 OID 16721)
-- Name: item_ingredient item_ingredient_id_item_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.item_ingredient
    ADD CONSTRAINT item_ingredient_id_item_fkey FOREIGN KEY (id_item) REFERENCES public.item(id);


--
-- TOC entry 3007 (class 2606 OID 16726)
-- Name: item item_seller_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.item
    ADD CONSTRAINT item_seller_id_fkey FOREIGN KEY (seller_id) REFERENCES public.user_table(id);


--
-- TOC entry 3010 (class 2606 OID 16731)
-- Name: orders orders_item_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_item_id_fkey FOREIGN KEY (item_id) REFERENCES public.item(id);


--
-- TOC entry 3011 (class 2606 OID 16736)
-- Name: orders orders_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_table(id);


--
-- TOC entry 3012 (class 2606 OID 16741)
-- Name: user_details user_details_id_city_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_details
    ADD CONSTRAINT user_details_id_city_fkey FOREIGN KEY (id_city) REFERENCES public.city(id);


--
-- TOC entry 3013 (class 2606 OID 16746)
-- Name: user_details user_details_id_u_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_details
    ADD CONSTRAINT user_details_id_u_fkey FOREIGN KEY (id_u) REFERENCES public.user_table(id);


--
-- TOC entry 3015 (class 2606 OID 16751)
-- Name: verification_token user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.verification_token
    ADD CONSTRAINT user_fkey FOREIGN KEY (id) REFERENCES public.user_table(id);


--
-- TOC entry 3014 (class 2606 OID 16756)
-- Name: user_table user_table_id_role_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_table
    ADD CONSTRAINT user_table_id_role_fkey FOREIGN KEY (id_role) REFERENCES public.roles(id);


--
-- TOC entry 3017 (class 2606 OID 16838)
-- Name: item_ingredient item_ingredient_id_ingredient_fkey; Type: FK CONSTRAINT; Schema: test; Owner: -
--

ALTER TABLE ONLY test.item_ingredient
    ADD CONSTRAINT item_ingredient_id_ingredient_fkey FOREIGN KEY (id_ingredient) REFERENCES test.ingredient(id);


--
-- TOC entry 3018 (class 2606 OID 16843)
-- Name: item_ingredient item_ingredient_id_item_fkey; Type: FK CONSTRAINT; Schema: test; Owner: -
--

ALTER TABLE ONLY test.item_ingredient
    ADD CONSTRAINT item_ingredient_id_item_fkey FOREIGN KEY (id_item) REFERENCES test.item(id);


--
-- TOC entry 3016 (class 2606 OID 16848)
-- Name: item item_seller_id_fkey; Type: FK CONSTRAINT; Schema: test; Owner: -
--

ALTER TABLE ONLY test.item
    ADD CONSTRAINT item_seller_id_fkey FOREIGN KEY (seller_id) REFERENCES test.user_table(id);


--
-- TOC entry 3019 (class 2606 OID 16853)
-- Name: orders orders_item_id_fkey; Type: FK CONSTRAINT; Schema: test; Owner: -
--

ALTER TABLE ONLY test.orders
    ADD CONSTRAINT orders_item_id_fkey FOREIGN KEY (item_id) REFERENCES test.item(id);


--
-- TOC entry 3020 (class 2606 OID 16858)
-- Name: orders orders_user_id_fkey; Type: FK CONSTRAINT; Schema: test; Owner: -
--

ALTER TABLE ONLY test.orders
    ADD CONSTRAINT orders_user_id_fkey FOREIGN KEY (user_id) REFERENCES test.user_table(id);


--
-- TOC entry 3021 (class 2606 OID 16863)
-- Name: user_details user_details_id_city_fkey; Type: FK CONSTRAINT; Schema: test; Owner: -
--

ALTER TABLE ONLY test.user_details
    ADD CONSTRAINT user_details_id_city_fkey FOREIGN KEY (id_city) REFERENCES test.city(id);


--
-- TOC entry 3022 (class 2606 OID 16868)
-- Name: user_details user_details_id_u_fkey; Type: FK CONSTRAINT; Schema: test; Owner: -
--

ALTER TABLE ONLY test.user_details
    ADD CONSTRAINT user_details_id_u_fkey FOREIGN KEY (id_u) REFERENCES test.user_table(id);


--
-- TOC entry 3024 (class 2606 OID 16873)
-- Name: verification_token user_fkey; Type: FK CONSTRAINT; Schema: test; Owner: -
--

ALTER TABLE ONLY test.verification_token
    ADD CONSTRAINT user_fkey FOREIGN KEY (id) REFERENCES test.user_table(id);


--
-- TOC entry 3023 (class 2606 OID 16878)
-- Name: user_table user_table_id_role_fkey; Type: FK CONSTRAINT; Schema: test; Owner: -
--

ALTER TABLE ONLY test.user_table
    ADD CONSTRAINT user_table_id_role_fkey FOREIGN KEY (id_role) REFERENCES test.roles(id);


-- Completed on 2021-08-24 09:41:37

--
-- PostgreSQL database dump complete
--

