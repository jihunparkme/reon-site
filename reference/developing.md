# developing

ing.
- 서버 배포하기
- 회원가입/로그인 폼 만들기
- 시큐리티 테스트 코드 작성
- 대표 메일로 SMTP 등록
- 회원가입 시 이메일 인증 추가
  - 인증하기 버튼 클릭 시 인증번호를 메일로 전송
  - 인증번호를 파라미터로 가지고 있다가 입력한 값하고 비교해서 동일하면 가입 완료







## Docker.
- [추석특집 도커 (Docker) 기초 강의 몰아보기](https://www.youtube.com/watch?v=IqnAiM1A0d8)
- [가장 쉽게 배우는 도커](https://www.youtube.com/watch?v=hWPv9LMlme8)
- [쿠버네티스 설치부터 서비스 배포까지](https://d-life93.tistory.com/449)
- [Container](https://tech.ktcloud.com/category/%EC%83%81%ED%92%88%2C%EC%84%9C%EB%B9%84%EC%8A%A4/Container?page=3)
- [[따배쿠] 쿠버네티스 시리즈](https://www.youtube.com/playlist?list=PLApuRlvrZKohaBHvXAOhUD-RxD0uQ3z0c)
    - https://wikidocs.net/186107

### Mariadb

`docker run --name mariadb -d -p 3306:3306 -e MARIADB_ROOT_PASSWORD=root mariadb:latest`

```bash
# run container
docker container start mariadb

# execute mysql
docker exec -it mariadb bash

# root login
mariadb -uroot -proot

# checker process
docker ps

# stop conatiner
docker stop mariadb
```

## H2

```text
데이터베이스 파일 생성
jdbc:h2:~/databaseName (jsessionid 포함 - 파일 모드)

데이터베이스 접속
jdbc:h2:tcp://localhost/~/databaseName (네트워크 모드)
```

## AWS.

Github - EC2 - Jenkins - nginx - MariaDB

- [생활코딩: 아마존 웹서비스와 클라우드](https://opentutorials.org/course/2717/11268)
- [AWS EC2 & RDS 구축하기](https://jojoldu.tistory.com/259)
- [Github, Jenkins, EC2](https://woodcock.tistory.com/20)
- [AWS EC2 Jenkins 구축](https://woodcock.tistory.com/20)
- [EC2에 배포하기](https://jojoldu.tistory.com/263)
- [Nginx를 활용한 무중단 배포 구축하기](https://jojoldu.tistory.com/267)
- [도메인구매, HTTPS 연결, 타임존 수정](https://jojoldu.tistory.com/270)

Gitlab + Jenkins + Nginx + Docker + AWS EC2 - 무중단 배포
- https://gksdudrb922.tistory.com/236#EC-%--SSH%--%EC%--%B-%EA%B-%B-

Jenkins EC2 연동.
- https://velog.io/@sa1341/Jenkins%EC%97%90%EC%84%9C-EC2%EB%A1%9C-%EB%B0%B0%ED%8F%AC%ED%95%98%EA%B8%B0
- https://thalals.tistory.com/430

## Test.
- JaCoCo > 테코드 커버리지

## Monitoring
- Prometheus 
- Grafana