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


### * 하나의 투자 상품에 여러명의 투자자가 몰리게 되고 투자 금액이 다모이게 되면 sold-out을 보여 주어야 함.
### 조회 하고 넣고 하다보면 투자 금액이 상이해 지는 효과가 나타날 수 있고 synchronized나 DB lock 을 걸면 성능 이슈가 있어 아래방법으로 진행. 
### 1. 투자 정보를 테이블(tb_product_invest)에 insert, 당시 투자 상태는 READY.
### 2. 등록된 투자 정보를 바탕으로 투자금액이 다모였는지 확인(상태가 SUCCESS인 것 중 투자금액에 대한 충족 여부 확인.)
### 3. 투자가 가능하다면 SUCCESS로 변경. 투자가 불가능 하다면 사유를 남기고 상태를 FAILED로 변경.
```
POST /api/invest
```
> Requst
 
```
{
  "investedAmount": <투자금액>,
  "memberId": <회원아이디>,
  "productId": <상품아이디>
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
            "failReason": ""
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
GET /api/invest/product , Header X-USER-ID <memberId>
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
## 추가 구현
> jwt 인증 구현 : 발급 후 헤더의 Authorization의 Bearer 에 선언 후 다른 기능 이용 가능.
````
POST http://localhost:8080/auth/authenticate
````
```
{
	"memberId" : "<memberID>",
	"password" : "<password>"

}
```

```
Response 예시
```
```
{
    "resultCode": "SUC-0000",
    "response": [
        {
            "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyMDE5MTIxOCIsImV4cCI6MTYxNTczNDAxOCwiaWF0IjoxNjE1NzMwNDE4fQ.4ubh9lrbcMdlCD0uw79qAkhBcbIrwVq0Gi2xQ7mf2hM"
        }
    ]
}
```

구현 상황
> 3개의 테이블 생성
> 1. tb_member : 회원관리 테이블. 관리자와 투자자를 회원 유형으로 구분.
> 2. tb_product : 투자 상품 관리 테이블. 상품은 관리자만 등록이 가능. 투자 개시 전 수정 및 삭제 가능. 이후 불가능.
> 3. tb_product_invest : 투자 상품과 투자자의 정보를 모은 테이블, 하나의 상품에 하나의 투자만 가능.

> 첫 시작시 회원 가입 및 로그인 필요(토큰발급). 회원 가입 및 로그인을 제외한 기능은 토큰으로 인증체크를 하여 통과를 해야 이용 가능.  

> jwt 인증을 통해 세션을 사용하지 않으므로 여러개의 서버들이 있어도 문제 없음. 


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

---
## Java package 구조
- web package : springboot에서 web관련 패키지
- common : 공통 패키지(json fomat, exception, util 등)
- api 실제 request를 받아 처리하는 패키지 크게 4개의 api package 구성
  > auth : 인증관련 (토큰 발급 및 토큰 체크)
  >  
  > member : 회원 관리 패키지
  >  
  > productmanagement : 상품관리 패키지
  >
  > investment : 투자 관련 기능이 모여있는 패키지


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