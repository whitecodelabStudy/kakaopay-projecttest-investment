## 카카오페이 서버개발 사전과제
> 상품 투자 서비스 개발

## 개발 환경
- Java11
- Spring Boot 2.4.3
- Postgresql
- Gradle
- Junit5 
- Lombok
- ETC. pmd, checkstyle

## 기능요구사항 
> 전체투자상품조회
- 상품모집기간(started_at,finished_at)내의상품만응답합니다.
- 전체투자상품응답은다음내용을포함합니다.
- 상품ID,상품제목,총모집금액,현재모집금액,투자자수,투자모집상태(모집중,모집완료), 상품 모집기간.
  
> 투자하기
- 사용자식별값,상품ID,투자금액을입력값으로받습니다.
- 총투자모집금액(total_investing_amount)을넘어서면sold-out상태를응답합니다
> 나의 투자상품 조회 API구현
- 내가투자한모든상품을반환합니다.
- 나의투자상품응답은다음내용을포함합니다.
- 상품ID,상품제목,총모집금액,나의투자금액,투자일시

----
> Error

| 에러 코드       | 설명                                                         |
| :-----  | ------------------------------------------------------------ |
| SUC-0000  | 성공     |
| ERR-9999 | 알수업는 오류 |
| ERR-0001 |  SOLD OUT |
| ERR-0002 | 회원을 찾을 수 없음 |
| ERR-0003 | 비밀번호 틀림 |
| ERR-0004 | 중복 투자. |
| ERR-0005 | 회원 생성 실패 |
| ERR-0006 | 회원 수정 실패 |
| ERR-0007 | 상품 수정 실패 |
| ERR-0008 | 상품 삭제 실패 |
| ERR-0009 | 상품 생성 실패 |


-------
## 실행 ##
## POSTGRES docker 설치 및 세팅.
## 1. postgres image 가져오기.
> docker pull postgres:12.6

## 2. docker images 명령어로 postgres image 확인
> docker images

REPOSITORY                   TAG       IMAGE ID       CREATED        SIZE

postgres                     latest    1f0815c1cb6e   3 weeks ago    314MB

## 3. postgres 컨테이너 실행 및 세팅.
> docker run -p 12505:5432 -d --name kakaopay-postgres -e POSTGRES_PASSWORD=1q2w3e4r -e POSTGRES_USER=kakaopay -e POSTGRES_INITDB_WALDIR=/var/lib/postgresql/log -e PGDATA=/var/lib/postgresql/data/pgdata -v <postgres 데이터 폴더 입력>:/var/lib/postgresql/data -v <postgres 로그 폴더 입력>:/var/lib/postgresql/log postgres:12.6

# postgres 접속
> docker exec -it -u postgres kakaopay-postgres psql -U kakaopay

## 테이블 명세서
![테이블 ERD](https://github.com/kakaopaycoding-server/202103-namhanjunsul-naver.com/blob/main/web/src/main/resources/erd.PNG)

[테이블 상세 내역서](https://github.com/kakaopaycoding-server/202103-namhanjunsul-naver.com/blob/main/web/src/main/resources/schema.sql)