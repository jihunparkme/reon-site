# REON

로스팅 프로파일 분석 및 공유 사이트입니다.

평소 관심있고, 프로젝트에 적용해 보고 싶었던 기술을 적용해 보았습니다.

- `AWS EC2/RDS`, `Docker` 기반 인프라 구축, `Spring Security`, `OAuth 2.0`, `JPA`, `Redis` 등..

## Development Stack

```bash
├── Backend
│   ├── Java JDK 17
│   ├── Spring Boot 3.2.0
│   ├── Spring Web MVC
│   ├── Spring Data JPA/REDIS
│   ├── Query DSL
│   ├── Spring Security
│   ├── Spring OAuth2
│   ├── Spring WebFlux
│   └── Gradle 8.3
├── DataBase
│   ├── MariaDB
│   └── Redis
├── DevOps
│   ├── AWS RDS (db.t3.micro)
│   ├── AWS EC2 (t2.micro)
│   ├── AWS Route 53
│   ├── Docker
│   ├── Jenkins
│   └── Nginx
├── Front
│   ├── thymeleaf
│   ├── JavaScript(ES6)
│   └── CSS
├── SSL
│   ├──Certbot
│   └──Let’s encrypt
├── TEST
│   ├──Junit 5
│   ├──mockito
│   ├──REST Assured
│   └──Coverage(JaCoCo)
```

## System architecture

![Result](./reference/system-architecture.png 'Result')

## Key Features

### Main

- Contact

### Login

**Login/Logout**
- Social(KAKAO, GOOGLE, APPLE) account Login
- Email Login
  - [ ] Find Password(Authenticate mail)
  - [ ] Find Email

**Login Session Management**
- `spring-session-data-redis`

**Sign Up**
- Sign up with email
  - Send and verify authentication number
  - Authentication number and status are `managed by Redis`
- Sign up with social account(KAKAO, GOOGLE, APPLE)
  - Social `Oauth2 Login API`

### MyPage

**account info**
- View my information
- Update my information
  - [ ] change password

**Account withdrawal**
- Email account
  - Delete Account information
- Social account
  - Delete Account information
  - unlink social account
    - [kakaologin unlink API](https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#unlink)
    - Google revoke token
    - [ ] Apple revoke token

### Record

View
- View private roasting profile
  - Roasting Record Graph with [amcharts](https://www.amcharts.com/)
  - Roasting Profile Recipe with [AG Grid](https://www.ag-grid.com/)
    - [AG Grid javascript Doc.](https://www.ag-grid.com/javascript-data-grid/getting-started/)
- Share to workshop article
- Register pilot record
- Update record memo

List
- Search roasting profile(title, date ..)
- Compare roasting profile

### Workshop

View
- View workshop article and roasting profile
- Subscribe roasting profile
  - Subscribed records cannot be shared

List
- Search workshop article(title, date ..)

### Admin

**Members**
- Search all Member
- Management member
  - View/Update/Delete 

**Products**
- Search all product
- Management product
  - View/Update/Delete
- Create S/N
- Register S/N

**Records**
- Search all roasting profile

**Statistics**
- [ ] Member(PRIVATE/COMPANY), Region, Roasting statistics

---

## Page

### Login

**Login**

![login-mypage](https://github.com/user-attachments/assets/c4e8898e-3854-465c-996c-01994ea2ebc2)

**Create Email Account**

![create-account](https://github.com/user-attachments/assets/f3fe184e-10d0-455f-9dc3-240a32b59e35)

### Mypage

![mypage](https://github.com/user-attachments/assets/89cffd9c-cefe-4ace-bc65-7173a339e276)

### Record

**Record List**

![record-list](https://github.com/user-attachments/assets/9a36de3c-e6b9-4b94-909a-9c33d63d9c30)


**Record View**

![record-view](https://github.com/user-attachments/assets/3d860602-8985-4f1d-bbf2-00e67cbb0ef4)

**Record Compare**

![compare-record](https://github.com/user-attachments/assets/02f9da74-b7cc-4a40-9a27-be26499c9e23)


### Admin

**Members**

![admin-members](https://github.com/user-attachments/assets/578a7e07-1069-4f2f-aace-b2d21f512917)

**Products**

![admin-products](https://github.com/user-attachments/assets/950b98b2-bbf9-4bdd-b13f-da114fa38e24)

**Records**

![admin-records](https://github.com/user-attachments/assets/445669ec-0fe8-44db-a90b-904d6352edc8)

### Workshop

**share to workshop**

![workshop-save](https://github.com/user-attachments/assets/8bb0da00-a9b7-4eab-b182-2fcaaab0847d)

**subscribe record**

![workshop-subscribe](https://github.com/user-attachments/assets/9db25b70-2f36-486d-8e50-600fc3284ea4)

# TODD

## Notice

**Notice.** (`/notice`)
- [ ] All permit
- [ ] 공지사항 기본 기능
  - 목록 / 조회 / 수정 / 삭제(삭제 flag)
- [ ] 관리자만 작성/수정/삭제 버튼 노출 및 권한 예외 처리
- [ ] 기본 회원은 조회만 가능

## News Letter

**News Letter** (`/new-letter`)
- [ ] 뉴스레터 기본 기능
  - 목록 / 조회 / 수정 / 삭제(삭제 flag)
- [ ] 관리자만 작성/수정/삭제 버튼 노출 및 권한 예외 처리
- [ ] 기본 회원은 조회만 가능

## Contact us

**Contact us** (`/contact`)
- [ ] 관리자 메일로 문의 내용 발송

## Voice

**Voice** (`/voice`)
- [ ] 고객의 소리 기본 기능
  - 목록 / 조회 / 수정 / 삭제(삭제 flag)
- [ ] 로그인 사용자만 작성 가능
- [ ] 수정/삭제 시 작성자와 요청자 검증 필요
- [ ] 작성/수정 완료 시 관리자 메일로 알림

.

## Monitoring

- [ ] Prometheus
- [ ] Grafana
- [ ] 로그 파일 생성 규칙

## TOBE

- [ ] 레시피 명예의 전당
- [ ] amcharts.com 결제
- 로그 관리 -> 날짜별로 덮어쓰기

## Refactor

- [ ] 미사용 파일 제거
  - [ ] img
  - [ ] vendor
- [ ] 미서용 코드 제거
  - [ ] style.css
  - [ ] main.js

# Domain

**Records**

![Result](./reference/image/domain/records.png 'Result')

**Members**

![Result](./reference/image/domain/members.png 'Result')

**Products**

![Result](./reference/image/domain/products.png 'Result')


---

## Reference

Guide.

```text
JAVA
- final keyword
- record class
- Assert in constructor
- rest-assured api test
- var type
  
REST API 
- 동사 보다는 복수 명사 사용
  - GET /dogs, POST /dogs/{puppy}/owner/{terry}
- GET /dogs (목록 조회)
- GET /dogs/1 (1번 개체 조회)
- POST /dogs (개체 생성)
- PUT /dogs/1 (1번 개체 수정)
- DELETE /dogs/1 (1번 개체 삭제)

DDD
- 도메인에서 비즈니스 로직 처리 + VO 활용하기
- 한 Aggregate에서 다른 Aggregate의 참조는 식별자(id)를 통해서만 참조
- 하나의 Transaction에서 여러 개의 Aggregate이 갱신되어야 하는 경우, 다른 Aggregate 갱신은 비동기 통신을 활용해서 결과적 일관성 맞추기
- Domain Event: 비지니스 도메인에서 일어난 이벤트를 설명하는 메시지(`'과거형'`으로 명명)
- 공통 에러 코드 관리

JPA
- @JoinColumn
- @Index
- @UniqueConstraint

common
- @RestControllerAdvice, @ExceptionHandler 활용 예외 공통 처리
```
- [주문 API 개발로 알아보는 TDD](https://github.com/jihunparkme/Study-project-spring-java/tree/main/product-order-service)
- [Microservice 내부 아키텍처와 EventStorming 설계 - DDD](https://jihunparkme.gitbook.io/docs/lecture/msa/ddd)