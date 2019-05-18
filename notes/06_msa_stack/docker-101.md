## Docker

#### 2018년에 CLI 네임스페이스 도입

Before|After
---|---
docker ps -a|docker container ls -a
docker rm {}|docker container rm {}
docker images -a|docker image ls -a
docker rmi {}|docker image rm {}
docker rm $(docker ps -a -q)|docker container rm $(docker container ls -aq)

#### 몰랐던 도커 커맨드

```bash
# 이미지 상세 정보
$ docker image inspect {}

# Dangling 이미지, 컨테이너, 네트워크, 볼륨 등 전체 삭제
$ docker system prune

# 무조건 삭제
$ docker system prune -a

$ ifconfig docker0
```

#### 몰랐던 리눅스 커맨드

```bash
# [Linux only] 전체 호스트 조회
$ hostname -I
# 172.17.0.3

# Piping args. {} 는 Optional
$ docker image ls -aq | xargs echo {}

# 프로세스 트리
$ ps auxf

# 주기적으로 명령어 실행
$ watch -n 1 {}
```

