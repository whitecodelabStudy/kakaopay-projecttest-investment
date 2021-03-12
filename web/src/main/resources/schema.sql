SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;


-- ENUM
CREATE TYPE en_product_type AS ENUM (
    'REAL_ESTATE',
    'CREDIT'
);

CREATE TYPE en_product_status_type AS ENUM (
    'RECRUITING',
    'FINISHED',
    'SOLD_OUT'
);

CREATE TYPE en_invest_status_type AS ENUM (
    'READY',
    'SUCCESS',
    'FAIL'
);

-- Table: public.tb_admin
-- DROP TABLE public.tb_admin;
CREATE SEQUENCE public.seq_admin_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_admin_id OWNER TO kakaopay;

CREATE TABLE public.tb_admin
(
    admin_id bigint DEFAULT nextval('public.seq_admin_id'::regclass) NOT NULL,
    name text COLLATE pg_catalog."default" NOT NULL,
    created_time timestamp default now(),
    modified_time timestamp default now(),
    CONSTRAINT pk_admin_id PRIMARY KEY (admin_id)
)
TABLESPACE pg_default;

ALTER TABLE public.tb_admin OWNER to kakaopay;

COMMENT ON TABLE public.tb_admin IS '관리자 테이블';
COMMENT ON COLUMN public.tb_admin.admin_id IS '관리자 아이디';
COMMENT ON COLUMN public.tb_admin.name IS '관리자 이름';
COMMENT ON COLUMN public.tb_admin.created_time IS '관리자 생성일시';
COMMENT ON COLUMN public.tb_admin.modified_time IS '관리자 수정일시';


-- Table: public.tb_investor
-- DROP TABLE public.tb_investor;
CREATE SEQUENCE public.seq_investor_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_investor_id OWNER TO kakaopay;

CREATE TABLE public.tb_investor (
    investor_id bigint DEFAULT nextval('public.seq_investor_id'::regclass) NOT NULL,
    name text NOT NULL,
    created_time timestamp default now(),
    modified_time timestamp default now(),
    CONSTRAINT pk_investor_id PRIMARY KEY (investor_id)
);

COMMENT ON TABLE tb_investor IS '투자자 테이블';
COMMENT ON COLUMN tb_investor.investor_id IS '투자자 아이디';
COMMENT ON COLUMN tb_investor.name IS '투자자 이름';
COMMENT ON COLUMN tb_investor.created_time IS '투자자 생성일시';
COMMENT ON COLUMN tb_investor.modified_time IS '투자자 수정일시';


-- Table: public.tb_product
-- DROP TABLE public.tb_product;
CREATE SEQUENCE public.seq_product_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_product_id OWNER TO kakaopay;

CREATE TABLE public.tb_product (
   product_id bigint DEFAULT nextval('public.seq_product_id'::regclass) NOT NULL,
   title text NOT NULL,
   product_type en_product_type,
   total_investing_amount bigint,
   started_at timestamp NOT NULL,
   finished_at timestamp NOT NULL,
   created_time timestamp default now(),
   modified_time timestamp default now(),
   admin_id bigint NOT NULL,
   CONSTRAINT pk_product_id PRIMARY KEY (product_id),
   CONSTRAINT tb_product_admin_id_fkey FOREIGN KEY (admin_id) REFERENCES tb_admin(admin_id)
);

COMMENT ON TABLE tb_product IS '투자상품 테이블';
COMMENT ON COLUMN tb_product.product_id IS '상품 아이디';
COMMENT ON COLUMN tb_product.title IS '투자명';
COMMENT ON COLUMN tb_product.product_type IS '상품 유형';
COMMENT ON COLUMN tb_product.total_investing_amount IS '총 투자 모집 금액';
COMMENT ON COLUMN tb_product.started_at IS '투자시작일시';
COMMENT ON COLUMN tb_product.finished_at IS '투자종료일시';
COMMENT ON COLUMN tb_product.created_time IS '상품등록일시';
COMMENT ON COLUMN tb_product.modified_time IS '상품수정일시';


--
-- Name: tb_product_invest Schema: public; Owner: kakaopay
--
CREATE SEQUENCE public.seq_product_invest_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.seq_product_invest_id OWNER TO kakaopay;

CREATE TABLE public.tb_product_invest (
  invest_id bigint DEFAULT nextval('public.seq_product_invest_id'::regclass) NOT NULL,
  product_id bigint NOT NULL,
  investor_id bigint  NOT NULL,
  invested_amount bigint NOT null,
  invest_status en_invest_status_type default 'READY',
  fail_reason en_product_status_type,
  created_time timestamp NOT null default now(),
  modified_time timestamp NOT null default now(),
  CONSTRAINT pk_invest_id PRIMARY KEY (invest_id),
  CONSTRAINT tb_product_product_id_fkey FOREIGN KEY (product_id) REFERENCES tb_product(product_id),
  CONSTRAINT tb_investor_investor_id_fkey FOREIGN KEY (investor_id) REFERENCES tb_investor(investor_id)
);

COMMENT ON TABLE tb_product_invest IS '상품투자 테이블';
COMMENT ON COLUMN tb_product_invest.invest_id IS '투자아이디';
COMMENT ON COLUMN tb_product_invest.product_id IS '투자상품아이디';
COMMENT ON COLUMN tb_product_invest.investor_id IS '투자자 아이디';
COMMENT ON COLUMN tb_product_invest.invested_amount IS '투자 금액';
COMMENT ON COLUMN tb_product_invest.invest_status IS '투자상태';
COMMENT ON COLUMN tb_product_invest.fail_reason IS '투자실패이유';
COMMENT ON COLUMN tb_product_invest.created_time IS '투자일시';
COMMENT ON COLUMN tb_product_invest.modified_time IS '투자수정일시';

SET default_tablespace = '';
SET default_with_oids = false;



--
-- Name: sp_set_product_invest procedure: public; Owner: kakaopay
--
CREATE FUNCTION sp_set_product_invest (i_product_id bigint, i_investor_id bigint, i_invested_amount bigint)
    RETURNS TABLE(invest_status en_invest_status_type, fail_reason en_product_status_type)
    LANGUAGE plpgsql
AS $$
DECLARE
    v_invest_id bigint;
    v_is_finished boolean;
    v_is_sold_out boolean;
    v_invest_status en_invest_status_type;
    v_fail_reason en_product_status_type;
BEGIN

    INSERT INTO tb_product_invest(product_id, investor_id, invested_amount, created_time, modified_time) values (i_product_id, i_investor_id, i_invested_amount, now(), now())
    returning tb_product_invest.invest_id INTO v_invest_id;

    SELECT
        now() BETWEEN started_at AND finished_at as is_finished
         , (now_investing_amount + i_invested_amount) > total_investing_amount as is_sold_out into v_is_finished, v_is_sold_out
    FROM (
             SELECT
                 tp.product_id
                  , tp.total_investing_amount
                  , tp.started_at
                  , tp.finished_at
                  , COALESCE(sum(tpi.invested_amount), 0) as now_investing_amount
             FROM tb_product tp
                      LEFT OUTER JOIN tb_product_invest tpi ON tp.product_id = tpi.product_id
                      LEFT OUTER JOIN tb_investor ti ON ti.investor_id = tpi.investor_id
             WHERE tp.product_id = i_product_id
             group by
                 tp.product_id
                    , tp.total_investing_amount
                    , tp.started_at
                    , tp.finished_at
         ) AS b;

    IF v_is_finished THEN
        UPDATE tb_product_invest
        SET invest_status = 'FAIL'
          , fail_reason = 'FINISHED'
        WHERE tb_product_invest.invest_id = v_invest_id
        returning tb_product_invest.invest_status, tb_product_invest.fail_reason INTO v_invest_status, v_fail_reason;
    ELSEIF v_is_sold_out THEN
        UPDATE tb_product_invest
        SET invest_status = 'FAIL'
          , fail_reason = 'SOLD_OUT'
        WHERE invest_id = v_invest_id
        returning tb_product_invest.invest_status, tb_product_invest.fail_reason INTO v_invest_status, v_fail_reason;
    ELSE
        UPDATE tb_product_invest
        SET invest_status = 'SUCCESS'
        WHERE invest_id = v_invest_id
        returning tb_product_invest.invest_status, tb_product_invest.fail_reason INTO v_invest_status, v_fail_reason;
    END IF;

    RETURN QUERY SELECT v_invest_status, v_fail_reason;
END;
$$;
