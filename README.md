# re-on-site

Version.

```text
> SpringBoot: 3.2.0
> Java: jdk 17
> Gradle
```

```text
> Backend
  - Java
  - Spring Boot
  - Spring Data JPA, REDIS
  - Spring Security
  - Spring OAuth2
> DataBase
  - MariaDB
  - Redis
> DevOps
  - AWS RDS (db.t2.micro)
  - AWS EC2 (t2.micro)
  - Docker
  - Jenkins
  - Nginx
> Front
  - thymeleaf
> Test
  - Junit
  - mockito
```

# System architecture

![Result](./reference/system-architecture.png 'Result')

---

# developing

ing.
- Sign In/Sign Up
- Record
- Member -> 회원 정보 수정
- 5xx, 4xx 에러 페이지 만들기
- 로그 관리 -> 날짜별로 덮어쓰기
- 도메인 등록
  - [Google Search Console 등록](https://search.google.com/search-console/about)

# Global

- TDD, DDD 적용
- 단순 조회가 아닌 수정이 일어날 경우, DTO 로 리턴하기.

# Domain

## Main

**Main.** (`/`)
- [ ] 소개

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

## Sign In/Sign Up

**Sign In/Sign Up**

![Result](./reference/login.png 'Result')

- 소셜(카카오, 구글, 애플) 및 이메일 로그인
- Redis 로 Spring Session 관리
- 로그인, 로그아웃, 탈퇴

- [ ] 소셜 로그인
  - [x] 카카오 로그인
  - [x] 구글 로그인 (앱 게시)
  - [ ] 애플 로그인
  - [ ] 소셜 가입 탈퇴 요청 시 소셜 연결 끊기 API 연동 후 세션 제거
  
- [ ] 가입하기
  - [x] email, first name, last name, 비밀번호
  - 비밀번호 재확인
  - [ ] 가입 시 이메일 인증 기능.
    - [ ] 대표 메일 SMTP 등록
    - [ ] 인증하기 버튼 클릭 시 인증번호를 메일로 전송
    - [ ] 인증번호를 가지고 있다가 입력한 값하고 비교
- [ ] 비밀번호 찾기 (메일로 비밀번호 변경 링크 전달)
- [ ] 이메일 찾기

## Member

**Member** (`/member`)

![Result](./reference/mypage.png 'Result')

- [x] 회원 정보 조회 및 수정
  - [x] 소셜 로그인이면 소설 마크 노출
  - [x] 이메일 가입자만 First name 변경 가능
  - [x] 글로벌 에러 처리 
- [ ] 비밀번호 변경은 별도 페이지에서 관리
- [ ] 회원탈퇴

## Record

**Record** (`/record`)

**Record List**

![Result](./reference/record-list.png 'Result')

**Record View**

![Result](./reference/record-view.png 'Result')

- [x] 로스팅 로그 조회
- [x] [amcharts](https://www.amcharts.com/) 적용
  - [Highlighting Line Chart](https://www.amcharts.com/demos/highlighting-line-chart-series-on-legend-hover/)
- [ ] 로스팅 로그 추출 기능(csv?) 
- [ ] 회원은 자신의 로그만 조회 가능, 관리자는 모든 로그 조회 가능
- [ ] 회원번호, S/N, 날짜로 검색 기능

.

## Admin

**management** (`/management`)

**Statistics** (`/management/statistics`)
- [ ] 가입자(개인/기업), 지역, 로스팅 횟수 등 통계 정보

**Users**  (`/management/members`)
- [ ] 회원 정보 관리
- [ ] 조회, 생성, 수정-삭제

**Product**  (`/management/product`)
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
- [ ] amcharts.com 결제

## Refactor

- [ ] 미사용 파일 제거
  - [ ] img
  - [ ] vendor
- [ ] 미서용 코드 제거
  - [ ] style.css
  - [ ] main.js