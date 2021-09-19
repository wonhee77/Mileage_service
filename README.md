# Mileage_service
리뷰 작성이 이뤄질때마다 발생하는 리뷰 작성 이벤트를 통하여 포인트를 적립하는 REST API Server입니다.  
포인트 적립과 조회 2개의 API를 구현하였습니다.


### 사용 기술 및 환경
- Java 8, Spring Boot, Spring Data JPA, Gradle, Lombok
- Test: Junit5, Mockito
- DB: MySql 5.7
- Collaboration & Tools: IntelliJ, Git, Sourcetree

### 실행 방법
Spring boot framework를 사용하여 Intellij IDE 환경에서 작성되었으며 Build tool로 Gradle을 사용하였습니다.
Spring Data JPA, Lombok에 의존성을 가지고 있습니다.

- Git clone 혹은 file을 다운 받으신 후 JDK 1.8이 설치된 환경에서 실행할 수 있습니다.
- 프로젝트 내에 있는 sql.ddl.sql 파일의 sql DDL을 통해 Database를 통해 DB table을 만들 수 있습니다.
- EC2 IP:13.124.236.225 에도 실행 중으로 확인 가능합니다.(MySql RDS 연동)

### 프로젝트 설계 시 가정한 상황
- Review 저장이 완료된 상황에서 event가 전달됩니다.
- 각 유저의 점수에 대한 부분은 매번 계산하는 것보다 User Entity가 가지고 있는 것이 낫다는 판단하에 User Entity에 
  userPoint를 추가 하였으며 그 외에 다른 Entity에 대한 의존성은 최대한 배제하려고 하였습니다.
- Action이 ADD인 event가 발생하기 전 MOD, DELETE인 event가 먼저 발생하는 경우는 없다고 가정하였습니다.   
  (event 발생 순서 : ADD -> MOD -> MOD -> DELETE)
  
### 프로젝트 기능 설명
1. 포인트 적립 API  
   - 아래의 API로 JSON 형식을 통해 Review Event를 전달하여 Point 적립을 할 수 있습니다.   
   - 현재 예시에 해당하는 reviewId, userId, placeId만 RDS에 저장이 되어 있기 때문에 추가로 User, Review, Place에 
     대한 값을 입력하지 않은 상황에서는 하기 id로만 API 요청이 가능합니다.
>Post , http://13.124.236.225/events,    
{  
    "type" : "REVIEW",  
    "action" : "ADD",   
    "reviewId" : "240a0658-dc5f-4878-9381-ebb7b2667772",   
    "content" : "좋아요!",   
    "attachedPhotoIds" : ["3b234b55-b1g4-gdfg-134g-21fnm4d4m12f", "8fhjdlk2-b1g4-gdfg-134g-21fnm4d4m12f"],   
    "userId" : "2346kb55-b1g4-gdfg-134g-21fnm4d4m12f",   
    "placeId" : "jj424b55-b1g4-gdfg-134g-21fnm4d4m12f"   
}
2. 포인트 조회 API   
    - 아래의 API로 Path Pamrameter를 통해 해당 userId의 점수를 조회할 수 있습니다.
>Get, http://13.124.236.225/users/2346kb55-b1g4-gdfg-134g-21fnm4d4m12f/point   
> response   
> {   
    "userPoint": 0   
}

### DB ERD


### DIAGRAM
