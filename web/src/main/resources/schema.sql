SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET row_security = off;

-- ENUM
CREATE TYPE en_product_type AS ENUM (
    'REAL_ESTATE',
    'CREDIT'
    );

CREATE TYPE en_member_type AS ENUM (
    'ADMIN',
    'INVESTOR'
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

-- Table: public.tb_member
-- DROP TABLE public.tb_member;
CREATE TABLE public.tb_member
(
    member_id     bigint         NOT NULL,
    name          text           NOT NULL,
    password      text           NOT NULL,
    member_type   en_member_type NOT NULL,
    created_time  timestamp default now(),
    modified_time timestamp default now(),
    CONSTRAINT pk_member_id PRIMARY KEY (member_id)
)
    TABLESPACE pg_default;

ALTER TABLE public.tb_member
    OWNER to kakaopay;

COMMENT ON TABLE public.tb_member IS '회원 테이블';
COMMENT ON COLUMN public.tb_member.member_id IS '회원 아이디';
COMMENT ON COLUMN public.tb_member.name IS '회원 이름';
COMMENT ON COLUMN public.tb_member.password IS '회원 비밀번호';
COMMENT ON COLUMN public.tb_member.member_type IS '회원 유형 : ADMIN(관리자)/INVESTOR(투자자)';
COMMENT ON COLUMN public.tb_member.created_time IS '회원 생성일시';
COMMENT ON COLUMN public.tb_member.modified_time IS '회원 수정일시';

-- Table: public.tb_product
-- DROP TABLE public.tb_product;
CREATE SEQUENCE public.seq_product_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_product_id
    OWNER TO kakaopay;

CREATE TABLE public.tb_product
(
    product_id             bigint    DEFAULT nextval('public.seq_product_id'::regclass) NOT NULL,
    title                  text                                                         NOT NULL,
    product_type           en_product_type,
    total_investing_amount bigint,
    started_at             timestamp                                                    NOT NULL,
    finished_at            timestamp                                                    NOT NULL,
    created_time           timestamp DEFAULT now(),
    modified_time          timestamp DEFAULT now(),
    member_id              int                                                          NOT NULL,
    CONSTRAINT pk_product_id PRIMARY KEY (product_id),
    CONSTRAINT tb_product_member_id_fkey FOREIGN KEY (member_id) REFERENCES tb_member (member_id)
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

ALTER TABLE public.seq_product_invest_id
    OWNER TO kakaopay;

CREATE TABLE public.tb_product_invest
(
    invest_id       bigint                DEFAULT nextval('public.seq_product_invest_id'::regclass) NOT NULL,
    product_id      bigint    NOT NULL,
    member_id       bigint    NOT NULL,
    invested_amount bigint    NOT null,
    invest_status   en_invest_status_type default 'READY',
    fail_reason     en_product_status_type,
    created_time    timestamp NOT null    default now(),
    modified_time   timestamp NOT null    default now(),
    CONSTRAINT pk_invest_id PRIMARY KEY (invest_id),
    CONSTRAINT tb_product_product_id_fkey FOREIGN KEY (product_id) REFERENCES tb_product (product_id),
    CONSTRAINT tb_invest_member_id_fkey FOREIGN KEY (member_id) REFERENCES tb_member (member_id)
);

COMMENT ON TABLE tb_product_invest IS '상품투자 테이블';
COMMENT ON COLUMN tb_product_invest.invest_id IS '투자아이디';
COMMENT ON COLUMN tb_product_invest.product_id IS '투자상품아이디';
COMMENT ON COLUMN tb_product_invest.member_id IS '투자자 아이디';
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
CREATE FUNCTION sp_set_product_invest(i_product_id bigint, i_member_id bigint, i_invested_amount bigint)
    RETURNS TABLE
            (
                invest_status en_invest_status_type,
                fail_reason   en_product_status_type
            )
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_invest_count    bigint := (SELECT count(invest_id)
                                 FROM tb_product_invest tpp
                                 WHERE tpp.product_id = i_product_id
                                   AND tpp.member_id = i_member_id
                                   AND tpp.invest_status = 'SUCCESS');
    v_invest_id       bigint;
    v_is_not_finished boolean;
    v_is_sold_out     boolean;
    v_invest_status   en_invest_status_type;
    v_fail_reason     en_product_status_type;
BEGIN

    IF v_invest_count > 0 THEN
        RAISE EXCEPTION 'duplicate invest' USING HINT = 'ERR-0004';
    END IF;

    INSERT INTO tb_product_invest(product_id, member_id, invested_amount, created_time, modified_time)
    values (i_product_id, i_member_id, i_invested_amount, now(), now())
    returning tb_product_invest.invest_id INTO v_invest_id;

    SELECT now() BETWEEN started_at AND finished_at                            as is_not_finished
         , (now_investing_amount + i_invested_amount) > total_investing_amount as is_sold_out
    into v_is_not_finished, v_is_sold_out
    FROM (
             SELECT tp.product_id
                  , tp.total_investing_amount
                  , tp.started_at
                  , tp.finished_at
                  , COALESCE(sum(tpi.invested_amount), 0) as now_investing_amount
             FROM tb_product tp
                      LEFT OUTER JOIN tb_product_invest tpi
                                      ON tp.product_id = tpi.product_id AND tpi.invest_status = 'SUCCESS'
                      LEFT OUTER JOIN tb_member ti ON ti.member_id = tpi.member_id
             WHERE tp.product_id = i_product_id
             group by tp.product_id
                    , tp.total_investing_amount
                    , tp.started_at
                    , tp.finished_at
         ) AS b;

    IF v_is_not_finished = false THEN
        UPDATE tb_product_invest
        SET invest_status = 'FAIL'
          , fail_reason   = 'FINISHED'
        WHERE tb_product_invest.invest_id = v_invest_id
        returning tb_product_invest.invest_status, tb_product_invest.fail_reason INTO v_invest_status, v_fail_reason;
    ELSEIF v_is_sold_out = true THEN
        UPDATE tb_product_invest
        SET invest_status = 'FAIL'
          , fail_reason   = 'SOLD_OUT'
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

--
-- 테스트 데이터 입력.
--
-- 관리자 테스트 데이터 (비밀번호가 암호화가 안되어 수정테스트 이후 가능)
INSERT INTO public.tb_member (member_id, "password", "name", member_type, created_time, modified_time)
VALUES(20171036, '1q2w3e4r','상품관리자', 'ADMIN', now(), now());
INSERT INTO public.tb_member (member_id, "password","name", member_type, created_time, modified_time)
VALUES(20191218, '1q2w3e4r','승후', 'INVESTOR', now(), now());
INSERT INTO public.tb_member (member_id, "password","name", member_type, created_time, modified_time)
VALUES(10111218, '1q2w3e4r','sangsub.lee', 'INVESTOR', now(), now());
INSERT INTO public.tb_member (member_id, "password","name", member_type, created_time, modified_time)
VALUES(76664, '1q2w3e4r','mirae', 'INVESTOR', now(), now());
INSERT INTO public.tb_member (member_id, "password","name", member_type, created_time, modified_time)
VALUES(97553, '1q2w3e4r','호비', 'INVESTOR', now(), now());
INSERT INTO public.tb_member (member_id, "password","name", member_type, created_time, modified_time)
VALUES(3325812, '1q2w3e4r','베니', 'INVESTOR', now(), now());

-- 상품 테스트 데이터 (만료된것, 진행중, 진행중(sold_out), 시작전
INSERT INTO tb_product (title, "product_type", total_investing_amount, started_at, finished_at, created_time, modified_time, member_id)
VALUES('부동산 포트폴리오', 'REAL_ESTATE', 350000000, '2021-03-01', '2021-03-10', NOW(), NOW(), 20171036);
INSERT INTO tb_product (title, "product_type", total_investing_amount, started_at, finished_at, created_time, modified_time, member_id)
VALUES('개인신용 포트폴리오', 'CREDIT', 10000000, '2021-03-01', '2022-03-20', NOW(), NOW(), 20171036);
INSERT INTO tb_product (title, "product_type", total_investing_amount, started_at, finished_at, created_time, modified_time, member_id)
VALUES('헤외주식', 'CREDIT', 10000, '2021-03-01', '2021-03-20', NOW(), NOW(), 20171036);
INSERT INTO tb_product (title, "product_type", total_investing_amount, started_at, finished_at, created_time, modified_time, member_id)
VALUES('국내주식', 'CREDIT', 10000, '2021-03-01', '2021-04-10', NOW(), NOW(), 20171036);
INSERT INTO tb_product (title, "product_type", total_investing_amount, started_at, finished_at, created_time, modified_time, member_id)
VALUES('3기신도시 투기지역', 'REAL_ESTATE', 1000000, '2021-04-01', '2021-05-10', NOW(), NOW(), 20171036);

-- 투자 정보


/* 테이블 초기화 하기 */
/*
-- drop table
drop table tb_product_invest;
drop table tb_product;
drop table tb_member;

-- drop sequence
drop sequence seq_product_id;
drop sequence seq_product_invest_id;

-- drop function
drop function sp_set_product_invest(bigint,bigint,bigint);

-- drop enum type
drop type en_invest_status_type;
drop type en_member_type;
drop type en_product_status_type;
drop type en_product_type;
*/