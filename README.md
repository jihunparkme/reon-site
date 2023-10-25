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


## System architecture

참고 구조. (업데이트 예정..)

![Result](https://raw.githubusercontent.com/jihunparkme/reon-site/dev/reference/system-architecture.png 'Result')


## Plan

- TDD 를 기본으로
- 단순 조회가 아닌 수정이 일어날 경우, DTO 로 리턴하기.

**전체 권한**

Main. ("/")
- [ ] 소개

Notice ("/notice")
- [ ] All permit
- [ ] 관리자만 작성

News Letter  ("/new-letter")
- [ ] All permit
- [ ] 관리자만 작성

Contact us ("/contact")
- [ ] All permit

Voice ("/voice")
- [ ] 고객의 소리(작성 시 admin 메일로 알림)

Sign In/Sign Up
- 이름, 이메일+인증, 비밀번호
- 가입 폼
  - 공통
    - 타입(개인/기업)
    - 구매자 이름
    - 이메일
    - 비밀번호
    - 전화번호
    - 상품코드
    - 제품 일련번호(S/N)
  - 기업
    - 상호명
    - 대표자 이름
    - 주소
- [ ] 가입 시 이메일 인증 -> mail SMTP 회사 계정으로
  - 이메일 인증해야 activated true 로 업데이트

.

**회원 권한**

Record  ("/record")
- [x] 로스팅 로그 조회
- [x] 로그는 차트로 [chart.js](https://www.chartjs.org/)
  - [cdnjs](https://cdnjs.com/libraries/Chart.js)
  - [documentation](https://www.chartjs.org/docs/latest/)
- [ ] 회원은 자신의 로그만 조회 가능, 관리자는 모든 로그 조회 가능
- [ ] 회원번호, 날짜 검색로 검색

```text
R200
-input
temp1
temp2
temp3
temp4

-output
heater1
fan1

---

R200 PRO
-input 
temp1
temp2
temp3
temp4
hum1
wei1

-output
heater1
fan1
fan2
tec1
```
.

**관리자 권한**

management ("/management")

Statistics ("/management/statistics")
- [ ] 통계 정보
- [ ] 가입자(개인/기업), 지역, 로스팅 횟수

Users  ("/management/users")
- [ ] 회원 정보 관리
- [ ] 조회, 생성, 수정-삭제

Product  ("/management/product")
- [ ] 기계 정보 관리
- [ ] 상태, 일련번호, SW버전

.

## TODO

- [ ] 레시피 공유(레시피 업로드, 다운로드)
- [ ] 레시피 명예의 전당

.

- [ ] 미사용 파일 제거
  - [ ] img
  - [ ] vendor
- [ ] 미서용 코드 제거
  - [ ] style.css
  - [ ] main.js