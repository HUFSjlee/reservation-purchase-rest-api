# 📌reservation-purchase-rest-api
예약-구매 서비스 REST API를 개발하고, 고도화를 목표로 합니다 :)

## 🔎docker-compose 명령어
**f 옵션**

우선 기본적으로 docker compose 명령어는 docker-compose.yml 파일이 있는 위치에서 실행해야 한다. 만약 실행 위치에 docker-compose.yml 파일이 없거나, 이름이 다르다면 -f 옵션을 통해 파일 경로를 주어야 한다.

**1. docker compose build <service>**

docker-compose.yml 파일에서 필요한 이미지들을 찾아서 빌드한다.
따로 서비스를 지정하지 않으면 모든 이미지가 빌드된다.

**2. docker compose up <service>**

docker-compose.yml 파일에서 서비스에 필요한 모든 컨테이너를 생성하고 시작한다. 만약 컨테이너가 빌드되지 않았거나, 혹은 빌드 된 이후 도커파일의 변동으로 다시 빌드되야 하는 경우, 이미지를 빌드한 후 컨테이너를 생성, 시작한다.

만약 모든 이미지를 새로 빌드해서 컨테이너를 생성하고 싶다면, 아래 명령어를 사용한다.

docker compose up --build
일괄로 중단(stop)하고 싶다면 Ctrl + C !!

**3. docker compose down <service>**

해당 프로젝트의 컨테이너를 중단하고, 제거한다. network나 volume또한 제거된다. 따라서 단순히 잠시 중단한 뒤 나중에 다시 시작하고 싶다면 docker compose stop 혹은 docker compose 가 실행되고 있는 터미널에서 ctrl + C를 통해 중단해야 한다.

**4. docker compose stop <service>**

명시한 서비스, 혹은 명시한 서비스가 없다면 전체 컨테이너를 중단한다.
down과는 다르게 단순 중단이므로 start 명령어로 다시 시작할 수 있다.

**5. docker compose start <service>**

생성된 후에 멈췄던 컨테이너를 시작하는 명령어. 쉽게 말해 up을 통해 생성되고, 시작한 이후 docker compose stop 혹은 Ctrl + C를 통해 중단된 서비스를 다시 시작할 수 있다.

**6. docker copmose restart <service>**

docker compose stop + docker compose start
만약 build 후 restart를 한다면 직전 생성된 이미지를 사용한다. 따라서 돌아가고 있는 컨테이너의 이미지를 바꿔야 한다면,

docker compose build <service>
docker compose restart <service>
위와 같이 빌드 한 후 재시작하면 된다.

**7. docker compose ps**

현재 동작하는 컨테이너를 보여준다.

**8. docker compose logs <service>**

해당 서비스의 로그를 보여준다.

**9. docker exec <container_id/name> <command>**

현재 돌아가는 컨테이너에 명령어를 전달해준다.
만약 conatiner_name을 지정해주지 않았다면, docker ps를 통해 container id를 먼저 얻어야 한다.

이것을 활용해서 현재 컨테이너에 터미널을 접속할 수 있다.
-i는 interactive 옵션으로 터미널을 통한 입력이 가능하게 해주고, -t옵션은 컨테이너의 출력을 터미널로 전달해주므로 두 옵션을 함께 사용해 쉘을 열면 된다.

docker exec -it <container_id/name> /bin/bash
이미지에 따라, /bin/bash를 찾을 수 없다고 뜰 수 있다. 특히 alpine이미지에서 이렇게 뜨는 경우가 많은데, 이 때는 /bin/sh를 대신 써주면 된다.

**10. docker exec config <service>**

설정 값 등을 보여준다. 환경변수 등이 제대로 들어갔는지 확인하고 싶을 때 사용하면 유용하다.

