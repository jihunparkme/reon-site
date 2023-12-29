## AWS

- [생활코딩: 아마존 웹서비스와 클라우드](https://opentutorials.org/course/2717/11268)

## Docker.
- [추석특집 도커 (Docker) 기초 강의 몰아보기](https://www.youtube.com/watch?v=IqnAiM1A0d8)
- [가장 쉽게 배우는 도커](https://www.youtube.com/watch?v=hWPv9LMlme8)
- [쿠버네티스 설치부터 서비스 배포까지](https://d-life93.tistory.com/449)
- [Container](https://tech.ktcloud.com/category/%EC%83%81%ED%92%88%2C%EC%84%9C%EB%B9%84%EC%8A%A4/Container?page=3)
- [[따배쿠] 쿠버네티스 시리즈](https://www.youtube.com/playlist?list=PLApuRlvrZKohaBHvXAOhUD-RxD0uQ3z0c)
    - https://wikidocs.net/186107

## Mariadb

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

## ETC

**Spring MVC**
- https://justforchangesake.wordpress.com/2014/05/07/spring-mvc-request-life-cycle/

**Front**
- https://bootstrapmade.com/sailor-free-bootstrap-theme/
