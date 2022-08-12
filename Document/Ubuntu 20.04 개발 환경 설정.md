## Ubuntu 20.04 개발 환경 설정

------

##### vim 설치

```bash
sudo apt-get install vim
```

##### 패키지 update && upgrade

```bash
sudo apt-get update && upgrade
```

##### 설치 가능한 패키지  검색

```bash
sudo apt-cache search
```

##### JAVA11 설치

```bash
sudo apt-get install openjdk-11-jdk
```

##### JAVA _HOME 설정

```bash
#bashrc 열기
vim ~/.bashrc
```

```bash
# java 위치 등록
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
export PATH="$PATH:$JAVA_HOME/bin”
```

```bash
# 적용
source ~/.bashrc	
```

```bash
# 확인 (/usr/lib/jvm/java-8-openjdk-amd64 자바 설치 경로가 출력되면 정상)
echo $JAVA_HOME
```

##### mysql 설치 및 설정

```bash
sudo apt-get update
sudo apt-get install mysql-server

# 외부 접속 기능 설정
sudo ufw allow mysql
# Mysql 실행
sudo systemctl start mysql
# 서버 시작 시 Mysql 자동 실행
sudo systemctl enable mysql
```

##### mysql 사용자 등록, 데이터베이스 생성, 권한 생성

```bash
# 데이터베이스 생성
mysql> CREATE DATABASE shop_schema;
# 데이터베이스 사용자 생성
mysql> CREATE USER 'api'@'%' IDENTIFIED BY 'hm041400';
mysql> FLUSH PRIVILEGES;
# 권한 부여
mysql> GRANT ALL PRIVILEGES ON shop_schema.* TO'api'@'%';
mysql> FLUSH PRIVILEGES;
```

##### Docker 설치

```bash
 sudo apt-get update
 sudo apt-get install \
   ca-certificates \
   curl \
   gnupg \
   lsb-release
   
 curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
 sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu (lsb_release -cs) stable"
 apt-get update
 sudo apt-get install docker-ce docker-ce-cli containerd.io
```

- 참고 사이트
  - https://docs.docker.com/engine/install/ubuntu/
  - https://dongle94.github.io/docker/docker-ubuntu-install/
  - https://boying-blog.tistory.com/82

##### **번외**

- virtualBox 디스크 재할당

  - 참고 사이트
    - https://velog.io/@wlgns410/VM-VirtualBox-%EC%9A%A9%EB%9F%89-%EB%8A%98%EB%A6%AC%EA%B8%B0

- Intellij 설치

  - 참고 사이트

    - https://www.bddungsblog.com/2021/02/ubuntu-ubuntu-2004-intellij-idea.html

  - bash 등록

    - ```bash
      sudo vim ~/.bashrc
      
      #하단에 alias 설정
      alias intellij=/opt/idea-IC-222.3345.118/bin/idea.sh
      ```



