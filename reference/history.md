# AWS EC2 & RDS Free Tier

## AWS EC2 & RDS 구축

AWS EC2 & RDS 구축 방법은 [향로님의 블로그](https://jojoldu.tistory.com/259) 에도 상세하게 설명이 되어 있고, (2023년 10월 기준 UI 약간 다르긴 하지만 기본적인 설정은 동일)

많은 블로그에서 설명을 해주고 있으니 생략하는 것으로..

.

다만, 구축하는 과정에서 필요한 참고 내용들을 추가로 정리하고 넘어가보자.

.

### AWS 프리티어 무료 사용량

`EC2`(Amazon Elastic Compute Cloud) : 클라우드에서 제공되는 크기 조정 가능한 컴퓨팅 파워
- 월별 750시간 무료 (EC2 인스턴스 하나를 풀로 돌려도 남는 시간)
  - EC2 하나당 750시간 가동이 기준으로, 만일 EC2 2개를 돌린다면 375시간만(15일) 무료. 3개일 경우 250시간(10일) 무료.
  - 프리티어에서 다수의 인스턴스를 풀로 돌릴경우 월별 무료 사용량이 금방 제한되어 과금이 되니 학습이 끝나면 항상 인스턴스를 종료 혹은 중지 해주자.
- 리전에 따라 Linux/Windows 운영체제의 t2.micro 또는 t3.micro 인스턴스 타입만 무료


`RDS`(Amazon Relational Database Service) : MySQL, PostgreSQL, MariaDB 또는 SQL Server를 위한 관리형 관계형 데이터베이스 서비스
- RDS 인스턴스 1개 무료 사용 가능
- 월별 750시간까지 무료(단, db.t2.micro 타입만 사용 가능)
- 범용(SSD) 데이터베이스 스토리지 20GB 제한
  - 10GB를 사용하는 RDS 인스턴스 3개를 생성하면 과금 발생(30GB)
- 데이터베이스 백업 및 DB 스냅샷용 스토리지 20GB
- 과금 방지 옵션 설정
  - 자동 백업 활성화 옵션 OFF
  - 스토리지 자동 조정 활성화 옵션 OFF
  - 마이너 버전 자동 업그레이드 사용 옵션 OFF
  - Multi-AZ와 고성능 I/O인 Provisioned IOPS Storate를 사용하지 않도록 설정

> [AWS 프리티어 요금 폭탄 방지 무료 사용량 정리](https://inpa.tistory.com/entry/AWS-%F0%9F%92%B0-%ED%94%84%EB%A6%AC%ED%8B%B0%EC%96%B4-%EC%9A%94%EA%B8%88-%ED%8F%AD%ED%83%84-%EB%B0%A9%EC%A7%80-%F0%9F%92%B8-%EB%AC%B4%EB%A3%8C-%EC%82%AC%EC%9A%A9%EB%9F%89-%EC%A0%95%EB%A6%AC)
>
> [AWS Free Tier](https://aws.amazon.com/ko/free/?all-free-tier.sort-by=item.additionalFields.SortRank&all-free-tier.sort-order=asc&awsf.Free%20Tier%20Types=*all&awsf.Free%20Tier%20Categories=*all)

.

### Amazon Linux 2023 MySQL 설치

AMI(Amazon Machine Image)로 EC2 Amazon Linux AMI 2023 를 선택했었는데,

커맨드 라인 사용을 위해 mysql 을 설치하는 과정에서 `sudo yum install mysql` 명령어를 사용해도 설치가 제대로 되지 않고

`mysql: command not found` 라는 말만 할 뿐이었다..

여러 서칭을 하면서 *Amazon Linux 2023의 경우 EL9 버전의 레파지토리와 mysql-community-sever를 설치해야 한다.* 는 글을 보게 되었고,

아래 명령어로 MySQL 설치 문제를 해결할 수 있었다.

```shell
$ sudo dnf install https://dev.mysql.com/get/mysql80-community-release-el9-1.noarch.rpm
$ sudo dnf install mysql-community-server
```

> [EC2 mysql 패키지 설치 오류](https://hyunki99.tistory.com/102)
>
> [EC2 mysql: command not found](https://velog.io/@miracle-21/EC2mysql-command-not-found)

.

## EC2에 배포하기

EC2 배포 방법도 [향로님의 블로그](https://jojoldu.tistory.com/263)에 상세하게 설명이 되어 있다.

향로님의 블로그를 참고하며 추가로 필요한 내용들을 확인해 보자.

.

### Install Java 17

```shell
# Install java
$ yum list java*  # 설치 가능한 java 조회
$ sudo yum install java-17-amazon-corretto
$ java -version

# java 설치 위치 확인
$ echo $JAVA_HOME # 현재 JAVA_HOME 확인
$ which java # java 설치 위치 확인
$ ls -l /usr/bin/java # which java 경로
$ readlink -f /usr/bin/java # 심볼릭 링크의 java 원본 위치

# 환경변수 설정
$ sudo vi /etc/profile

export JAVA_HOME=/usr/lib/jvm/java-17-amazon-corretto.x86_64

$ source /etc/profile

# 설정 확인
$ echo $JAVA_HOME
$ $JAVA_HOME -version
```

- `/etc/profile` : 모든 사용자에 적용
- `~/.bashrc` : 해당 사용자에게만 적용

> [EC2 Java 설치 및 JAVA_HOME 설정](https://happy-jjang-a.tistory.com/57)

.

### Install Git And Clone

```shell
# Install git
$ sudo yum install git
$ git --version

# git project directory
$ mkdir app
$ mkdir app/git
$ cd ~/app/git

# clone git repository
$ git clone https://github.com/프로젝트주소.git
$ git branch -a # 현재 브랜치 확인
$ ./gradlew test # gradle 테스트 수행
```

.

### Personal access tokens 로 로그인

git clone 명령을 수행했다면 github 로그인이 필요한데 비밀번호로는 인증이 실패할 것이다.

[Git password authentication is shutting down](https://github.blog/changelog/2021-08-12-git-password-authentication-is-shutting-down/)

```
2021년 8월 13일부터 Git 작업을 인증할 때 비밀번호를 입력하는 방식은 만료되었고, 토큰 기반 인증(ex. personal access, OAuth, SSH Key, or GitHub App installation token)이 필요하게 되었다.
Personal Access Token 을 활용하여 로그인해보자.
```

> [Github Token 방식으로 로그인하기](https://velog.io/@shin6949/Github-Token-%EB%B0%A9%EC%8B%9D%EC%9C%BC%EB%A1%9C-%EB%A1%9C%EA%B7%B8%EC%9D%B8%ED%95%98%EA%B8%B0-ch3ra7vc)

.

### Git 자동 로그인

git 자동 로그인 설정을 해두지 않으면 매번 git 작업 때마다 로그인이 필요할 것이다.

```shell
$ git config credential.helper store
$ git push https://github.com/jihunparkme/jihunparkme.github.io.git # 자동 로그인이 필요한 저장소 주소
```

> [Github 자동 로그인 설정](https://daechu.tistory.com/33)

.

### EC2 프리티어 메모리 부족현상 해결

`./gradlew test` 명령을 수행했다면 EC2 가 먹통이 되는 현상을 마주하였을 것이다..

t2.micro RAM 이 1GB 뿐이라서 그렇다..

다행히도 AWS 에서는 HDD 의 일정 공간을 마치 RAM 처럼 사용할 수 있도록 SWAP 메모리를 지정할 수 있게 해준다.

[How do I allocate memory to work as swap space in an Amazon EC2 instance by using a swap file?](https://repost.aws/ko/knowledge-center/ec2-memory-swap-file)

```shell
# dd 명령어로 swap 메모리 할당 (128M 씩 16개의 공간, 약 2GB)
$ sudo dd if=/dev/zero of=/swapfile bs=128M count=16

# swap 파일에 대한 읽기 및 쓰기 권한 업데이트
$ sudo chmod 600 /swapfile

# Linux 스왑 영역 설정
$ sudo mkswap /swapfile

# swap 공간에 swap 파일을 추가하여 swap 파일을 즉시 사용할 수 있도록 설정
$ sudo swapon /swapfile

# 절차가 성공했는지 확인
$ sudo swapon -s

# /etc/fstab 파일을 수정하여 부팅 시 swap 파일 활성화
$ sudo vi /etc/fstab

/swapfile swap swap defaults 0 0 # 파일 끝에 추가 후 저장

# 메모리 상태 확인
$ free -h
```

> [AWS EC2 프리티어에서 메모리 부족현상 해결방법](https://sundries-in-myidea.tistory.com/102)

.

### 배포 스크립트

```shell
$ cd ~/app/git/
$ vi deploy.sh
```

**deploy.sh**

```shell
#!/bin/bash

REPOSITORY=/home/ec2-user/app/git

cd $REPOSITORY/my-webservice-site/

echo "> Git Pull"
git pull

echo "> start Build Project"
./gradlew build

echo "> copy build jar File"
cp ./build/libs/*.jar $REPOSITORY/deploy/

echo "> Check the currently running application PID"
CURRENT_PID=$(pgrep -f my-webservice)

echo "$CURRENT_PID"

if [ -z $CURRENT_PID ]; then
    echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> kill -15 $CURRENT_PID"
    kill -15 $CURRENT_PID
    sleep 5
fi

echo "> Deploy a new application"
JAR_NAME=$(ls $REPOSITORY/deploy/ | grep 'my-webservice' | tail -n 1)

echo "> JAR Name: $JAR_NAME"
nohup java -jar $REPOSITORY/deploy/$JAR_NAME > $REPOSITORY/deploy/deploy.log 2>&1 &
```

**스크립트 실행**

```shell
# 스크립트 실행 권한 추가
$ chmod 755 ./deploy.sh

# 스크립트 실행
$ ./deploy.sh

# 실행중인 프로세스 확인
$ ps -ef | grep my-webservice
```
.

### 외부에서 서비스 접속

EC2 에 배포된 서비스의 포트 번호가 외부에서 접근 가능하도록 설정이 필요

AWS EC2 인스턴스 페이지 -> 보안그룹 -> 현재 프로젝트의 인스턴스 -> 인바운드 탭
- 인바운드 편집 버튼을 클릭해서 사용자지정(TCP), 8080 포트를 추가
- `퍼블릭 DNS(IPv4):8080` 주소로 접속

> [EC2에 배포하기](https://jojoldu.tistory.com/263)

.

## Docker & Jenkins 배포 자동화 구축

### Install Docker

```shell
# Install
$ sudo yum update -y # 인스턴스에 있는 패키지 업데이트
$ sudo yum install -y docker # docker 설치
$ docker -v # 버전 확인

# Setting
$ sudo systemctl enable docker.service # 재부팅 시 docker 자동 실행 설정

# Start
$ sudo systemctl start docker.service # docker 서비스 실행
$ systemctl status docker.service # docker 서비스 상태 확인
```

.

### Install Jenkins

**docker search, pull 권한 에러 발생 시 권한 설정**

`permission denied while trying to connect to the Docker daemon socket at ...`

[Got permission denied while trying to connect to the Docker daemon socket](https://technote.kr/369)

```shell
# 접근을 위해 root:docker 권한 필요
$ ls -al /var/run/docker.sock 

# 현재 로그인한 사용자를 docker group 에 포함
$ sudo usermod -a -G docker $USER 

# EC2 인스턴스 재구동 후 해당 ID 에 docker group 권한 부여 확인
$ id

# 다시 docker pull 시도
$ docker pull jenkins/jenkins:lts
```

.

Install jenkins image in docker

```shell
$ docker search jenkins # search image
$ docker pull jenkins/jenkins:lts # docker image 가져오기
$ docker images # 설치된 jenkins image 확인 
```

.

Create jenkins Container

```shell
$ docker run -itd -p 8000:8080 -v /home/ec2-user/app/git:/var/app/git --name jenkins -u root jenkins/jenkins:lts

$ docker ps # 실행중인 docker 확인
$ docker exec -it --user root 'Container ID' /bin/bash # jenkins container 진입

# etc docker command
$ docker stop 'Container ID' # Stop container
$ docker container restart 'Container ID' # Restart container
```





























-p 8000:8080
- 8080 포트는 webservice 에 이미 사용중이므로 8000 포트 사용 
- 컨테이너 외부와 통신할 8000 포트와 내부적으로 사용할 8080 포트 설정

8000 포트 번호도 외부에서 접근 가능하도록 설정 필요
- AWS EC2 인스턴스 페이지 -> 보안그룹 -> 현재 프로젝트의 인스턴스 -> 인바운드 탭
- 인바운드 편집 버튼을 클릭해서 사용자지정(TCP), 8000 포트를 추가

exit Shell
- exit : `Ctrl + d`
- 백그라운드 종료 : `ctrl + p + q`

> [docker run 커맨드 사용법](https://www.daleseo.com/docker-run/)



$ docker run -itd -p 8000:8080 --name jenkins jenkins/jenkins:lts


### Init Jenkins

Jenkins 초기 패스워드 확인

```shell
cd /var/jenkins_home/secrets/
cat initialAdminPassword
```

`퍼블릭 DNS(IPv4):8000` 주소로 접속하여 Jenkins 설정 시작
- 초기 화면에서 initialAdminPassword 에 저장된 초기 패스워드 입력
- Install Suggested plugins 선택
- 설치 진행..
- Create First Admin User 설정

### Jenkins 자동 배포 설정

(1) GitHub Repository
- Settings -> Webhooks -> Add webhook
- Payload URL : http://퍼블릭 DNS(IPv4):8000/github-webhook/

(3) Jenkins 설정
- Jenkins 관리 -> Plugins -> Available plugins
  - Publish Over SSH 검색 후 Install
- Jenkins 관리 -> System -> GitHub -> GitHub Servers 



젠킨스 지우도 다시 처음부터................


> [Docker + Jenkins 자동 배포](https://velog.io/@wijoonwu/AWS-Jenkins-%EC%9E%90%EB%8F%99-%EB%B0%B0%ED%8F%AC)





















































---


Jenkins 로 배포 자동화 구축 (수동 Test & Build를 자동화???)

- [https://velog.io/@wijoonwu/AWS-Jenkins-자동-배포](https://velog.io/@wijoonwu/AWS-Jenkins-%EC%9E%90%EB%8F%99-%EB%B0%B0%ED%8F%AC)
- https://woodcock.tistory.com/20
- [https://velog.io/@sa1341/Jenkins에서-EC2로-배포하기](https://velog.io/@sa1341/Jenkins%EC%97%90%EC%84%9C-EC2%EB%A1%9C-%EB%B0%B0%ED%8F%AC%ED%95%98%EA%B8%B0)
- https://thalals.tistory.com/430

Nginx를 활용한 무중단 배포 구축

- https://jojoldu.tistory.com/267?category=635883

Gitlab + Jenkins + Nginx + Docker + AWS EC2 - 무중단 배포

- https://gksdudrb922.tistory.com/236

운영 환경 설정

- https://jojoldu.tistory.com/269?category=635883

도메인구매, HTTPS 연결, 타임존 수정

- https://jojoldu.tistory.com/270?category=635883