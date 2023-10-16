# re-on-site

```text
SpringBoot: 3.1.4
Java: jdk 17
Gradle
```

```text
> 개발: Java, Spring
> Version Control: Github
> Server: Amazon EC2(t2.micro)
  - CI/CD: Jenkins
  - nginx
  - Docker ???
    - Redis ???
> RDB Server
  - Amazon RDS for MariaDB(db.t2.micro)
```

참고 구조. (업데이트 예정..)

![Result](https://raw.githubusercontent.com/jihunparkme/re-on-site/dev/reference/system-architecture.png 'Result')

## Plan

공통 권한.

- 메인페이지에 회사 소개
- Notice - 관리자 작성
  ```sql
  ```
- News Letter - 관리자 작성
  ```sql
  ```
- Contact us - 고객 작성
  ```sql
  ```

회원가입.

- 개인/기업
- 개인: 구매자 이름, 이메일, 전화번호 / 기업: 상호명, 대표자 이름, 주소, 이메일, 전화번호
- 제품코드
- 일련번호(S/N)
- 참고: OAuth 로그인대신 SNS 로그인 사용, 일반 Session 보다는 JDBC Session
- 가입 시 이메일 인증

가입 계정.

- 고객의 소리(관리자에게 알림으로)

관리자 권한.

- 통계 정보(가입자(개인/기업), 지역, 로스팅 횟수)
- 로스팅 로그 확인 메뉴(회원번호, 날짜 검색)
- 회원 정보 관리 메뉴(조회, 생성, 수정-삭제)
- 기계 정보(상태, 일련번호, SW버전)
- 고객의 소리 대응

TODO.

- 레시피 공유(레시피 업로드, 다운로드)
- 명예의 전당