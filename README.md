# re-on-site

```text
> SpringBoot: 3.1.4
> Java: jdk 17
> Gradle
```

```text
> 개발: Java, Spring
> Version Control: Github
> Server: Amazon EC2(t2.micro)
  - Docker
  - CI/CD: Jenkins
  - nginx
  - Redis ???
> RDB Server
  - Amazon RDS for MariaDB(db.t2.micro)
```


## System architecture

![Result](https://raw.githubusercontent.com/jihunparkme/blog/main/img/aws-ec2/system-architecture.png 'Result')

## Plan

- TDD 를 기본으로.
- 단순 조회가 아닌 수정이 일어날 경우, DTO 로 리턴하기.

**전체 권한 페이지**

Main. ("/")
- [ ] 소개

.

Notice ("/notice")
- [ ] All permit
- [ ] 관리자만 작성 가능

.

News Letter  ("/new-letter")
- [ ] All permit
- [ ] 관리자만 작성 가능

.

Contact us ("/contact")
- [ ] All permit
- [ ] 관리자 메일로 내용 발송

.

Voice ("/voice")
- [ ] 고객의 소리
- [ ] 로그인 사용자만 작성 가능
- [ ] 작성 완료 시 관리자 메일로 알림

.

Sign In/Sign Up 🏃🏻‍🏃🏻
- [x] 가입
  - [x] first name, last name, email, password
  - [ ] 이메일 인증 기능. 관리자

.

Member ("/member") 🏃🏻‍🏃🏻
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

.

Record ("/record") 🏃🏻‍🏃🏻
- [x] 로스팅 로그 조회
- [x] 로그 그래프 [chart.js](https://www.chartjs.org/) 활용
  - [cdnjs](https://cdnjs.com/libraries/Chart.js)
  - [documentation](https://www.chartjs.org/docs/latest/)
- [ ] 회원은 자신의 로그만 조회 가능, 관리자는 모든 로그 조회 가능
- [ ] 회원번호/S.N/날짜로 검색 가능하도록

.

**관리자 권한**

management ("/management")

Statistics ("/management/statistics")
- [ ] 가입자(개인/기업), 지역, 로스팅 횟수 등 통계 정보

Users  ("/management/members")
- [ ] 회원 정보 관리
- [ ] 조회, 생성, 수정-삭제

Product  ("/management/product")
- [ ] 상태, 상품코드, S/N, 버전 관리

.

## Test.

- JaCoCo > 테코드 커버리지

## Monitoring

- [ ] Prometheus
- [ ] Grafana
- [ ] 로그 파일 생성 규칙

.

## TODO

- [ ] 레시피 공유(레시피 업로드, 다운로드)
- [ ] 레시피 명예의 전당

## Refactor

- [ ] 미사용 파일 제거
  - [ ] img
  - [ ] vendor
- [ ] 미서용 코드 제거
  - [ ] style.css
  - [ ] main.js