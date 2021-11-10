## Back-end


### Fluentd-out-docker
 - 어린이집 로컬 서버에 돌아가는 Fluentd
 - 로컬 서버에서 작성되는 로그들을 포트24224 TCP통신으로 메인서버에 있는 Fluentd로 로그를 보낸다.
 - 로그 형식은 JSON
 - 로그파일의 위치는 ~/person.log
 - 도커 실행시 person.log가 들어있는 폴더를 볼륨공유 해야한다.
 - 명령어
 ``` bash
 docker run -d -v 로그위치:/home/ubuntu --name 별명 이미지이름
 ```
---
### Fluentd-in-docker 
 - 메인서버 안에서 돌아가는 Fluentd
 - 기본적인 Fluentd에 ElasticSearch 플러그인만 설치 하였다.
---
### test.java
 - 5초마다 10명의 아이들의 로그를 100개 출력한다.
 - 형식
 ``` 
 {"name":이름,"age":나이,"sex":성별,"action":행동,"x":X좌표,"y":y좌표}
 ```

 - 실행방법
  ``` bash 
  javac -cp ".:/home/ubuntu/back-end/json-simple-1.1.1.jar" /home/ubuntu/back-end/test.java # 컴파일  java -cp ".:/home/ubuntu/java/json-simple-1.1.1.jar" test # 실행
  ```
---
### yaml
 - k8s 오브젝트들을 담고있다.
 - elasticsearch.yml
    - StatefulSet
        - init컨터이너를 사용하여 초기설정, 7.13.4버전 사용, 포트는 9200, 멀티노드 , 레프리카 3, volumeClaimTemplates를 사용하여 각 노드마다 EBS 생성
    - Service
        - ClusterIP, 포트는 9200
 - fluentd.yml
    - Deployment
        - init컨터이너를 사용하여 configMap에 작성된 fluent.conf를 만든다, 포트는 데이터를 받는 24224 헬스체크용 8888
    - Service
        - LoadBalancer, 포트는 데이터를 받는 24224 헬스체크용 8888
    - ConfigMap
        - Fluentd 설정파일을 작성
 - kibana.yml
    - Deployment
        - 7.13.4버전 사용, 포트는 5601, 레프리카 1, 6.6버전 이후에 환경변수가 ELASTICSEARCH_URL -> ELASTICSEARCH_HOSTS 변경
    - Service
        - LoadBalancer, 포트는 5601

  
