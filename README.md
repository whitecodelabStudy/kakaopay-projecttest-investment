## 카카오페이 서버개발 사전과제
> 상품 투자 서비스 개발

## 개발 환경

## 기능요구사항 
전체투자상품조회,투자하기,나의투자상품조회API구현합니다.
- 요청한사용자식별값은숫자형태이며"X-USER-ID"라는HTTPHeader로전달됩니다.
작성하신어플리케이션이다수의서버에서다수의인스턴스로동작하더라도기능에문제가없도
록설계되어야합니다.
각기능및제약사항에대한단위테스트를반드시작성합니다.

1.전체투자상품조회API
다음의요건을만족하는전체투자상품조회API를작성해주세요.
- 상품모집기간(started_at,finished_at)내의상품만응답합니다.
- 전체투자상품응답은다음내용을포함합니다.
- 상품ID,상품제목,총모집금액,현재모집금액,투자자수,투자모집상태(모집중,모집
완료),상품모집기간

2.투자하기API
다음의요건을만족하는투자하기API를작성해주세요.
- 사용자식별값,상품ID,투자금액을입력값으로받습니다.
- 총투자모집금액(total_investing_amount)을넘어서면sold-out상태를응답합니다

3.나의투자상품조회API
다음의요건을만족하는나의투자상품조회API를작성해주세요.
- 내가투자한모든상품을반환합니다.
- 나의투자상품응답은다음내용을포함합니다.
- 상품ID,상품제목,총모집금액,나의투자금액,투자일시

핵심문제해결전략및분석한내용을간단하게작성하여"readme.md"파일에첨부해주세요.
데이터베이스사용에는제약이없습니다.
API의HTTPMethod들(GET|POST|PUT|DEL)은자유롭게선택하세요.
에러응답,에러코드는자유롭게정의해주세요.


## Table ##




## 실행 ##