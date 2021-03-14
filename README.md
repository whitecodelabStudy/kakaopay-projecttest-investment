## 카카오페이 서버개발 사전과제
> 상품 투자 서비스 개발

## 개발 환경
- Java11
- Spring Boot 2.4.3
- Mybatis
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

```
GET /api/invest/product
```
```
Response 예시
```
```
{
    "resultCode": "SUC-0000",
    "response": [
        {
            "productId": 4,
            "title": "국내주식",
            "productType": "CREDIT",
            "totalInvestingAmount": 10000,
            "startedAt": "2021-03-01 00:00:00",
            "finishedAt": "2021-04-10 00:00:00",
            "createdTime": "2021-03-14 22:06:53.814295",
            "modifiedTime": "2021-03-14 22:06:53.814295",
            "memberId": 20171036,
            "investorCount": 0,
            "nowInvestingAmount": 0,
            "investmentRecruitmentStatus": "RECRUITING"
        },
        {
            "productId": 2,
            "title": "개인신용 포트폴리오",
            "productType": "CREDIT",
            "totalInvestingAmount": 10000000,
            "startedAt": "2021-03-01 00:00:00",
            "finishedAt": "2022-03-20 00:00:00",
            "createdTime": "2021-03-14 22:06:53.814295",
            "modifiedTime": "2021-03-14 22:06:53.814295",
            "memberId": 20171036,
            "investorCount": 0,
            "nowInvestingAmount": 0,
            "investmentRecruitmentStatus": "RECRUITING"
        },
        {
            "productId": 3,
            "title": "헤외주식",
            "productType": "CREDIT",
            "totalInvestingAmount": 10000,
            "startedAt": "2021-03-01 00:00:00",
            "finishedAt": "2021-03-20 00:00:00",
            "createdTime": "2021-03-14 22:06:53.814295",
            "modifiedTime": "2021-03-14 22:06:53.814295",
            "memberId": 20171036,
            "investorCount": 0,
            "nowInvestingAmount": 0,
            "investmentRecruitmentStatus": "RECRUITING"
        }
    ]
}
```
 
> 투자하기
- 사용자식별값,상품ID,투자금액을입력값으로받습니다.
- 총투자모집금액(total_investing_amount)을넘어서면sold-out상태를응답합니다
```
POST /api/invest
```
> Requst
 
```
{
  "investedAmount": 30000,
  "memberId": 20191218,
  "productId": 1
}
```
> Response 예시
```
투자에 성공했을 때 
{
    "resultCode": "SUC-0000",
    "response": [
        {
            "investStatus": "SUCCESS",
            "failReason": null
        }
    ]
}


```
```
기한이 지난 상품에 투자하려 할때. FINISHED
{
    "resultCode": "SUC-0000",
    "response": [
        {
            "investStatus": "FAIL",
            "failReason": "FINISHED"
        }
    ]
}
```
```
투자가 진행 중이나 토자모금액이 내가 투자할 금액과 더해서 초과 되었을 때.
{
    "resultCode": "SUC-0000",
    "response": [
        {
            "investStatus": "FAIL",
            "failReason": "SOLD_OUT"
        }
    ]
}
```
> 나의 투자상품 조회 API구현
- 내가투자한모든상품을반환합니다.
- 나의투자상품응답은다음내용을포함합니다.
- 상품ID,상품제목,총모집금액,나의투자금액,투자일시

```
GET /api/invest/product
```
```
Response 예시
```
```
{
    "resultCode": "SUC-0000",
    "response": [
        {
            "productId": 2,
            "title": "개인신용 포트폴리오",
            "totalInvestingAmount": 10000000,
            "myInvestedAmount": 30000,
            "modifiedTime": "2021-03-14 23:08:33.278032",
            "totalInvestedAmount": 30000
        }
    ]
}
```
----
> Error Code

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

## 4. postgres 접속
> docker exec -it -u postgres kakaopay-postgres psql -U kakaopay

## 5. 테이블 내역서를 보고 그대로 테이블 및 테스트 데이터 생성.

-----
# 테이블 명세서
![테이블 ERD](https://github.com/kakaopaycoding-server/202103-namhanjunsul-naver.com/blob/main/web/src/main/resources/erd.PNG)

[테이블 상세 내역서](https://github.com/kakaopaycoding-server/202103-namhanjunsul-naver.com/blob/main/web/src/main/resources/schema.sql)