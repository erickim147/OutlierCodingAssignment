## Shop API Server



### 서버 설명

> **Shop API Server는 JWT  토큰 검증을 통해 사용자 권한에 따라** 
>
> **회원가입 및 조회, 물품등록 및 검색, 장바구니 기능, 구매 등의 기능을 하는 API 서버 입니다.**
>
> **구현되어 있는 기능으로는 아래와 같습니다.**



------

### 구현 기능

- **로그인**

- **회원**
  - 회원가입
  - 본인 정보 조회
  - ADMIN 계정을 통한 전체 회원 조회
- **판매자**
  - 일반 유저 -> 판매자 유저로의 등록
  - 상품 등록
- **상품검색**
  - 1차, 2차, 3차 카테고리 별 검색
  - 상품명을 통한 검색
- **장바구니**
  - 상품 추가
  - 장바구니 전체 조회
  - 단일 상품 삭제
  - 전체 상품 삭제
- **상품 구매**
  - 장바구니 상품 구매
  - 구매한 상품 리스트 조회
- **카테고리**
  - 전체 카테고리 계층형식의 조회
- **Refresh Token **
  - JWT Access Token의 만료 시 Refresh Token을 활용한 Access Token 재발급

------

## Dockerizing Application 실행 순서

- **Docker Network 생성**

  ```bash
  hm@hm-VirtualBox:~$ docker network create api_network
  ```

- **MySql Image 다운로드 및 실행**

  ```bash
  hm@hm-VirtualBox:~$ docker run -d --name mysql-db -e MYSQL_ROOT_PASSWORD=hm041400 -p 3306:3306 --network api_network mysql
  ```

- **db dump 파일 docker container로 복사**

  - ```bash
    # dump파일은 git repository /dbdump안에 있음.
    docker cp /tmp/api_tump.sql mysql-db:/tmp
    ```

- **DB 사용자 및 데이터베이스 생성**

  - mysql-db Container 연결

    ```bash
    hm@hm-VirtualBox:~$ docker exec -it mysql-db bash
    ```

  - DB 접속

    ```bash
    bash-4.4# mysql -u root -p
    Enter password: 
    Welcome to the MySQL monitor.  Commands end with ; or \g.
    Your MySQL connection id is 8
    Server version: 8.0.30 MySQL Community Server - GPL
    
    Copyright (c) 2000, 2022, Oracle and/or its affiliates.
    
    Oracle is a registered trademark of Oracle Corporation and/or its
    affiliates. Other names may be trademarks of their respective
    owners.
    
    Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.
    
    mysql> 
    ```

  - 데이터베이스 생성

    ```bash
    mysql> create database shop_schema;
    Query OK, 1 row affected (0.01 sec)
    ```

  - 사용자 생성

    ```bash
    mysql> create user 'api'@'%' identified by 'hm041400';
    Query OK, 0 rows affected (0.01 sec)
    
    mysql> flush privileges;
    Query OK, 0 rows affected (0.01 sec)
    ```

  - 사용자 권한 부여

    ```bash
    mysql> grant all privileges on shop_schema.* to 'api'@'%';
    Query OK, 0 rows affected (0.00 sec)
    
    mysql> flush privileges;
    Query OK, 0 rows affected (0.01 sec)
    ```

  - shop_schema DB에 dump파일 import

    ```bash
    bash-4.4# mysql -u root -p shop_schema < /tmp/api_dump.sql 
    Enter password: 
    ```

  - Table 생성 확인

    ```bash
    mysql> show tables;
    +-----------------------+
    | Tables_in_shop_schema |
    +-----------------------+
    | tb_cart               |
    | tb_categories         |
    | tb_member             |
    | tb_order              |
    | tb_product            |
    | tb_role               |
    | tb_seller             |
    | tb_token              |
    +-----------------------+
    8 rows in set (0.00 sec)
    ```

- **Application 실행**

  ```bash
  docker run -it -d --name shopApp --network api_network -p 8080:8080 gudals147/shop_application:0.4 bash
  ```

- **Postman을 통한 초기 데이터로 들어있던 userTest 로그인 테스트**

  ![image-20220811142957013](https://raw.githubusercontent.com/erickim147/OutlierCodingAssignment/typora_upload/README_IMG/image-20220811142957013.png)

------

### API 명세서 

#### 전체 URL

|  NO  | URL                      | Method | Description             | Authority |
| :--: | ------------------------ | ------ | ----------------------- | --------- |
|  1   | /api/v1/user             | POST   | 회원가입                | PermitAll |
|  2   | /api/v1/user/{memberId}  | GET    | 회원조회 (본인 정보)    | USER      |
|  3   | /api/v1/users            | GET    | 전체회원 조회           | ADMIN     |
|  4   | /api/vi/login            | POST   | 로그인                  | PermitAll |
|  5   | /api/v1/refresh          | POST   | Access Token 재발급     | ANONYMOUS |
|  6   | /api/v2/seller           | POST   | 판매자 등록             | USER      |
|  7   | /api/v3/product          | POST   | 상품 등록               | SELLER    |
|  8   | /api/v4/search           | GET    | 상품 검색               | PermitAll |
|  9   | /api/v5/cart             | POST   | 장바구니 상품 추가      | USER      |
|  10  | /api/v5/cart             | GET    | 장바구니 상품 전체 조회 | USER      |
|  11  | /api/v5/cart/{productId} | DELETE | 장바구니 상품 단건 삭제 | USER      |
|  12  | /api/v5/cart             | DELETE | 장바구니 상품 전체 삭제 | USER      |
|  13  | /api/v6/order            | POST   | 물품 구매               | USER      |
|  14  | /api/v6/order            | GET    | 구매 물품 리스트 조회   | USER      |

#### URL 별 상세 명세서

- **회원 가입**

  - Header

    ```tex
    POST /api/v1/user HTTP/1.1
    Host: localhost
    Content-type: application/json;
    ```

  - Request Parameter

    |      Name      |  Type  |   Description   | Required |
    | :------------: | :----: | :-------------: | :------: |
    |    memberId    | String |   유저 아이디   |    O     |
    |    memberPw    | String |  유저 패스워드  |    O     |
    |    memberNm    | String |    유저 이름    |    O     |
    | memberPhoneNum | String | 유저 휴대폰번호 |    O     |
    |  memberEmail   | String |   유저 이메일   |    O     |
    |   memberAddr   | String |    유저 주소    |    O     |
    |  memberBirth   | String |  유저 생년월일  |    O     |

  - Response Parameter

    |    Name    |  Type  |   Description    | Required |
    | :--------: | :----: | :--------------: | :------: |
    | timestamp  | String |    응답 시간     |    O     |
    |    code    | String | 커스텀 응답 코드 |    O     |
    | httpStatus | String |    HttpStatus    |    O     |
    |  message   | String |   응답 메세지    |    O     |

  - Sample

    ```json
    #Request Sample
    {
    	"memberId" : "resultTest",
    	"memberPw" : "Test1234!@",
    	"memberNm" : "김형민",
    	"memberPhoneNum" : "01012345678",
    	"memberEmail" : "result@naver.com",
    	"memberAddr" : "경기도 구리시",
    	"memberBirth" : "2022-01-01"
    }
    
    #Response Sample
    {
        "timestamp": "2022-08-12T02:26:06.980747",
        "code": "UC000",
        "httpStatus": "CREATED",
        "message": "회원 가입 성공"
    }
    ```
    

- **로그인**

  - Header

    ```tex
    POST /api/v1/login HTTP/1.1
    Host: localhost
    ```

  - Request Parameter

    |   Name   |  Type  |  Description  | Required |
    | :------: | :----: | :-----------: | :------: |
    | memberId | String |  유저 아이디  |    O     |
    | memberPw | String | 유저 비밀번호 |    O     |

  - Response Parameter

    |     Name     |  Type  |   Description    | Required |
    | :----------: | :----: | :--------------: | :------: |
    |  timestamp   | String |    응답 시간     |    O     |
    |     code     | String | 커스텀 응답 코드 |    O     |
    |  httpStatus  | String |    HttpStatus    |    O     |
    |   message    | String |   응답 메세지    |    O     |
    |    count     |  int   |  오브젝트 개수   |    O     |
    |     data     |   {}   |  응답 오브젝트   |    O     |
    | accessToken  | String |   accessToken    |    O     |
    |   memberId   | String |   유저 아이디    |    O     |
    | refreshToken | String |   refreshToken   |    O     |

  - Sample

    ```json
    #Request Sample
    http://localhost:8080/api/v1/login?memberId=resultTest&memberPw=Test1234!@
    
    #Response Sample
    {
        "timestamp": "2022-08-12T02:27:35.422276",
        "code": "L0000",
        "httpStatus": "OK",
        "message": "로그인 성공",
        "count": 3,
        "data": {
            "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyZXN1bHRUZXN0IiwiaWF0IjoxNjYwMjcxMjU1LCJleHAiOjE2NjAyNzQ4NTV9.RZ_Fzjx9xZmJKxy5s3lF7SyowtCoJInujep7vB3ZTL0",
            "memberId": "resultTest",
            "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyZXN1bHRUZXN0IiwiaWF0IjoxNjYwMjcxMjU1LCJleHAiOjE2NjAzMDcyNTV9.BGzz8ibew7wrxEIX6TCHxqaUfPMaRwZ7PZN0pDQWjQI"
        }
    }
    ```

- **회원 조회**

  - Header

    ```tex
    GET /api/v1/user/{memberId} HTTP/1.1
    Host: localhost
    Authorization: ${ACCESS_TOKEN}
    ```

  - Request Parameter

    |   Name   |  Type  | Description | Required |
    | :------: | :----: | :---------: | :------: |
    | memberId | String | 유저 아이디 |    O     |

  - Response Parameter

    |        Name         |  Type  |   Description    | Required |
    | :-----------------: | :----: | :--------------: | :------: |
    |      timestamp      | String |    응답 시간     |    O     |
    |        code         | String | 커스텀 응답 코드 |    O     |
    |     httpStatus      | String |    HttpStatus    |    O     |
    |       message       | String |   응답 메세지    |    O     |
    |        count        |  int   |  오브젝트 개수   |    O     |
    |        data         |   {}   | 회원정보 Object  |    O     |
    | data.memberPhoneNum | String |  유저 전화번호   |    O     |
    |    data.memberNm    | String |    유저 이름     |    O     |
    |  data.memberBirth   | String |  유저 생년월일   |    O     |
    |   data.memberAddr   | String |    유저 주소     |    O     |
    |    data.memberId    | String |   유저 아이디    |    O     |
    |  data.memberEmail   | String |   유저 이메일    |    O     |

  - Sample

    ```json
    #Request Sample
    http://localhost:8080/api/v1/user?memberId=resultTest
    
    #Response Sample
    {
        "timestamp": "2022-08-12T02:28:58.402128",
        "code": "200",
        "httpStatus": "OK",
        "message": "회원 조회 성공",
        "count": 1,
        "data": {
            "memberPhoneNum": "01012345678",
            "memberNm": "김형민",
            "memberBirth": "2022-01-01",
            "memberAddr": "경기도 구리시",
            "memberId": "resultTest",
            "memberEmail": "result@naver.com"
        }
    }
    ```

- **회원 전체 조회**

  - Header

    ```tex
    GET /api/v1/uses HTTP/1.1
    Host: localhost
    Authorization: ${ACCESS_TOKEN}
    ```

  - Request Parameter (Access Token 조회 후 권한에 따라 ADMIN 권한의 사용자만이 조회 가능) 

    | Name | Type | Description | Required |
    | :--: | :--: | :---------: | :------: |
    |      |      |             |          |

  - Response Parameter

    |        Name         |  Type  |     Description      | Required |
    | :-----------------: | :----: | :------------------: | :------: |
    |      timestamp      | String |      응답 시간       |    O     |
    |        code         | String |   커스텀 응답 코드   |    O     |
    |     httpStatus      | String |      HttpStatus      |    O     |
    |       message       | String |     응답 메세지      |    O     |
    |        count        |  int   | 회원정보 리스트 개수 |    O     |
    |        data         |   []   |    회원정보 List     |    O     |
    | data.memberPhoneNum | String |    유저 전화번호     |    O     |
    |    data.memberNm    | String |      유저 이름       |    O     |
    |  data.memberBirth   | String |    유저 생년월일     |    O     |
    |   data.memberAddr   | String |      유저 주소       |    O     |
    |    data.memberId    | String |     유저 아이디      |    O     |
    |  data.memberEmail   | String |     유저 이메일      |    O     |

  - Sample

    ```json
    #Response Sample
    {
        "timestamp": "2022-08-12T02:30:41.89768",
        "code": "200",
        "httpStatus": "OK",
        "message": "회원 전체 조회 성공",
        "count": 4,
        "data": [
            {
                "memberPhoneNum": "01089568304",
                "memberNm": "김형민",
                "memberBirth": "2022-01-01",
                "memberAddr": "경기도 구리시",
                "memberId": "userTest",
                "memberEmail": "test@naver.com"
            },
            {
                "memberPhoneNum": "01089568304",
                "memberNm": "관리자",
                "memberBirth": "2022-01-01",
                "memberAddr": "서울시 가산동",
                "memberId": "admin",
                "memberEmail": "admin@naver.com"
            },
            {
                "memberPhoneNum": "01089568304",
                "memberNm": "김형민2",
                "memberBirth": "2022-01-01",
                "memberAddr": "경기도 구리시",
                "memberId": "userTest2",
                "memberEmail": "test2@naver.com"
            },
            {
                "memberPhoneNum": "01012345678",
                "memberNm": "김형민",
                "memberBirth": "2022-01-01",
                "memberAddr": "경기도 구리시",
                "memberId": "resultTest",
                "memberEmail": "result@naver.com"
            }
        ]
    }
    ```

- **Access Token 재발급**

  - Header

    ```tex
    POST /api/v1/refresh HTTP/1.1
    Host: localhost
    Content-type: application/json;
    ```
  
  - Request Parameter
  
    |     Name     |  Type  | Description  | Required |
    | :----------: | :----: | :----------: | :------: |
    | refreshToken | String | refreshToken |    O     |

  - Response Parameter

    |       Name       |  Type  |   Description    | Required |
    | :--------------: | :----: | :--------------: | :------: |
    |    timestamp     | String |    응답 시간     |    O     |
    |       code       | String | 커스텀 응답 코드 |    O     |
    |    httpStatus    | String |    HttpStatus    |    O     |
    |     message      | String |   응답 메세지    |    O     |
    |      count       |  int   |  오브젝트 개수   |    O     |
    |       data       |   {}   |  응답 오브젝트   |    O     |
    | data.accessToken | String |   accessToken    |    O     |
  
  - Sample

    ```json
    #Request Sample
    {
        "refreshToken" : "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyZXN1bHRUZXN0IiwiaWF0IjoxNjYwMjcxNDc2LCJleHAiOjE2NjAzMDc0NzZ9.04r49Jc1nvhsphGAhHx00NaGkSSvGSAH2yDEYGHMGJQ"
    }
    
    #Response Sample
    {
        "timestamp": "2022-08-12T02:31:32.85237",
        "code": "TC000",
        "httpStatus": "OK",
        "message": "REFRESH TOKEN으로 신규 ACCESS TOKEN이 생성 되었습니다.",
        "count": 1,
        "data": {
            "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyZXN1bHRUZXN0IiwiaWF0IjoxNjYwMjcxNDkyLCJleHAiOjE2NjAyNzUwOTJ9.UBw3j0In-7pIBuo_89OJDWtMQybkpV1n8XBtgRgp_bw"
        }
    }
    ```
  
- **판매자 등록**

  - Header

    ```tex
    POST /api/v2/seller HTTP/1.1
    Host: localhost
    Content-type: application/json;
    Authorization: ${ACCESS_TOKEN}
    ```

  - Request Parameter

    |     Name     |  Type  |  Description  | Required |
    | :----------: | :----: | :-----------: | :------: |
    |   shopName   | String |   매장 이름   |    O     |
    |   shopAddr   | String |   매장 주소   |    O     |
    |  shopTelNum  | String | 매장 전화번호 |    O     |

  - Response Parameter

    |    Name    |  Type  |   Description    | Required |
    | :--------: | :----: | :--------------: | :------: |
    | timestamp  | String |    응답 시간     |    O     |
    |    code    | String | 커스텀 응답 코드 |    O     |
    | httpStatus | String |    HttpStatus    |    O     |
    |  message   | String |   응답 메세지    |    O     |

  - Sample

    ```json
    # Request Sample
    {
    	"shopName" : "result매장",
    	"shopAddr" : "경기도 구리시",
    	"shopTelNum" : "031-123-1234"
    }
    
    # Response Sample
    {
        "timestamp": "2022-08-12T02:32:07.718805",
        "code": "SC000",
        "httpStatus": "OK",
        "message": "판매자 등록 완료"
    }
    ```

- **상품등록**

  - Header

    ```tex
    POST /api/v3/product HTTP/1.1
    Host: localhost
    Content-type: application/json;
    Authorization: ${ACCESS_TOKEN}
    ```

  - Request Parameter

    |       Name       |  Type   |    Description     | Required |
    | :--------------: | :-----: | :----------------: | :------: |
    |    pdLargeCat    | String  |    1차 카테고리    |    O     |
    |   pdMiddleCat    | String  |    2차 카테고리    |    O     |
    |     pdSubCat     | String  |    3차 카테고리    |    O     |
    |      pdName      | String  |     제품 이름      |    O     |
    |     pdPrice      |   int   |     제품 가격      |    O     |
    |    pdDiscount    |   int   |     할인 금액      |    O     |
    |  setSalePeriod   | boolean | 판매기간 설정 여부 |    O     |
    |   pdSalePeriod   | String  |      판매기간      |          |
    |     pdStock      |   int   |     재고 수량      |    O     |
    |      pdDesc      | String  |     제품 설명      |    O     |
    | pdDeliveryCharge |   int   |       배송비       |    O     |

  - Response Parameter

    |    Name    |  Type  |   Description    | Required |
    | :--------: | :----: | :--------------: | :------: |
    | timestamp  | String |    응답 시간     |    O     |
    |    code    | String | 커스텀 응답 코드 |    O     |
    | httpStatus | String |    HttpStatus    |    O     |
    |  message   | String |   응답 메세지    |    O     |

  - Sample

    ```json
    # Request Sample
    {
        "pdLargeCat" : "A100000",
        "pdMiddleCat" : "B100000",
        "pdSubCat" : "C100001",
        "pdName" : "삼성 갤럭시 패드",
        "pdPrice" : 1200000,
        "pdDiscount" : 0,
        "setSalePeriod" : false,
        "pdSalePeriod" : "2099-01-01",
        "pdStock" : 100,
        "pdDesc" : "삼성 갤럭시 패드 설명",
        "pdDeliveryCharge" : 2500
    }
    
    # Response Sample
    {
        "timestamp": "2022-08-12T02:34:05.793189",
        "code": "PC000",
        "httpStatus": "OK",
        "message": "상품 등록 완료"
    }
    ```

- **상품 검색**

  - Header

    ```tex
    GET /api/v4/search HTTP/1.1
    Host: localhost
    Content-type: application/json;
    ```

  - Request Parameter

    |      Name       |  Type  |    Description     | Required |
    | :-------------: | :----: | :----------------: | :------: |
    |   searchTitle   | String |   제품 이름 검색   |          |
    |   isCatSearch   | String | 카테고리 검색 여부 |    O     |
    | searchLargeCat  | String |  1차 카테고리 ID   |          |
    | searchMiddleCat | String |  2차 카테고리 ID   |          |
    |  searchSubCat   |  int   |  3차 카테고리 ID   |          |

  - Response Parameter

    |    Name    |  Type  |     Description     | Required |
    | :--------: | :----: | :-----------------: | :------: |
    | timestamp  | String |      응답 시간      |    O     |
    |    code    | String |  커스텀 응답 코드   |    O     |
    | httpStatus | String |     HttpStatus      |    O     |
    |  message   | String |     응답 메세지     |    O     |
    |   count    |  int   |  검색 된 제품 개수  |    O     |
    |    data    |   []   | 검색 된 제품 리스트 |    O     |

  - Sample

    ```json
    # Request Sample
    
    # 제목으로 검색
    {
        "searchTitle" : "갤럭시",
        "isCatSearch" : false,
        "searchLargeCat" : "",
        "searchMiddleCat" : "",
        "searchSubCat" : ""
    }
    # 카테고리 검색
    {
        "searchTitle" : "",
        "isCatSearch" : true,
        "searchLargeCat" : "A100000",
        "searchMiddleCat" : "",
        "searchSubCat" : ""
    }
    
    # Response Sample
    
    # 제목으로 검색
    {
        "timestamp": "2022-08-12T02:35:05.283387",
        "code": "SEARCH_000",
        "httpStatus": "OK",
        "message": "상품 검색 완료",
        "count": 1,
        "data": [
            {
                "pdIdx": 8,
                "pdSellerId": "resultTest",
                "pdLargeCat": "A100000",
                "pdMiddleCat": "B100000",
                "pdSubCat": "C100001",
                "pdName": "삼성 갤럭시 패드",
                "pdPrice": 1200000,
                "pdDiscount": 0,
                "pdSalePeriod": "2099-01-01",
                "pdStock": 100,
                "pdDesc": "삼성 갤럭시 패드 설명",
                "pdDeliveryCharge": 2500,
                "setSalePeriod": false
            }
        ]
    }
    
    #카테고리 검색
    {
        "timestamp": "2022-08-12T02:36:32.794778",
        "code": "SEARCH_000",
        "httpStatus": "OK",
        "message": "상품 검색 완료",
        "count": 8,
        "data": [
            {
                "pdIdx": 1,
                "pdSellerId": "userTest",
                "pdLargeCat": "A100000",
                "pdMiddleCat": "B100000",
                "pdSubCat": "C100001",
                "pdName": "안드로이드 태블릿",
                "pdPrice": 100000,
                "pdDiscount": 0,
                "pdSalePeriod": "2099-01-01",
                "pdStock": 96,
                "pdDesc": "안드로이드 태블릿 입니다.",
                "pdDeliveryCharge": 2500,
                "setSalePeriod": false
            },
            {
                "pdIdx": 2,
                "pdSellerId": "userTest",
                "pdLargeCat": "A100000",
                "pdMiddleCat": "B100000",
                "pdSubCat": "C100002",
                "pdName": "아이패드 프로",
                "pdPrice": 1000000,
                "pdDiscount": 0,
                "pdSalePeriod": "2099-01-01",
                "pdStock": 100,
                "pdDesc": "아이패드 프로 입니다.",
                "pdDeliveryCharge": 2500,
                "setSalePeriod": false
            },
            .
            .
            .
        ]
    }
    ```

- **장바구니 상품 추가**

  - Header

    ```tex
    POST /api/v5/cart HTTP/1.1
    Host: localhost
    Content-type: application/json;
    Authorization: ${ACCESS_TOKEN}
    ```

  - Request Parameter

    |   Name    | Type | Description | Required |
    | :-------: | :--: | :---------: | :------: |
    | productId | int  |   제품 ID   |    O     |
    |  amount   | int  |  제품 수량  |    O     |

  - Response Parameter

    |    Name    |  Type  |   Description    | Required |
    | :--------: | :----: | :--------------: | :------: |
    | timestamp  | String |    응답 시간     |    O     |
    |    code    | String | 커스텀 응답 코드 |    O     |
    | httpStatus | String |    HttpStatus    |    O     |
    |  message   | String |   응답 메세지    |    O     |

  - Sample

    ```json
    # Request Sample
    {
        "productId" : 8, // 위에서 등록한 갤럭시 패드 ProductId (pdIdx)
        "amount" : 5
    }
    
    # Response Sample
    {
        "timestamp": "2022-08-12T02:39:15.464098",
        "code": "CART_000",
        "httpStatus": "OK",
        "message": "장바구니 등록 완료"
    }
    ```

- **장바구니 상품 조회 **

  - Header

    ```tex
    GET /api/v5/cart HTTP/1.1
    Host: localhost
    Authorization: ${ACCESS_TOKEN}
    ```

  - Request Parameter

    | Name | Type | Description | Required |
    | :--: | :--: | :---------: | :------: |
    |      |      |             |          |

  - Response Parameter

    |      Name      |  Type  |         Description         | Required |
    | :------------: | :----: | :-------------------------: | :------: |
    |   timestamp    | String |          응답 시간          |    O     |
    |      code      | String |      커스텀 응답 코드       |    O     |
    |   httpStatus   | String |         HttpStatus          |    O     |
    |    message     | String |         응답 메세지         |    O     |
    |     count      |  int   |        오브젝수 개수        |    O     |
    |      data      |   []   |   조회된 장바구니 리스트    |    O     |
    |     cartId     |  int   |         장바구니 ID         |    O     |
    |    memberId    | String |       조회한 유저 ID        |    O     |
    |   productId    |  int   |           제품 ID           |    O     |
    |     amount     |  int   |          주문 수량          |    O     |
    |     price      |  int   | 주문 가격 (수량 * 제품가격) |    O     |
    | cartCreateTime | String |     장바구니 추가 시간      |    O     |
    |  productInfo   |   {}   |          제품 정보          |    O     |

  - Sample

    ```json
    {
        "timestamp": "2022-08-12T02:41:02.386262",
        "code": "CART_001",
        "httpStatus": "OK",
        "message": "장바구니 전체 조회 완료",
        "count": 2,
        "data": [
            {
                "cartId": 5,
                "memberId": "resultTest",
                "productId": 7,
                "amount": 10,
                "price": 100000,
                "cartCreateTime": "2022-08-12 02:40:49",
                "productInfo": {
                    "pdIdx": 7,
                    "pdSellerId": "userTest",
                    "pdLargeCat": "A100000",
                    "pdMiddleCat": "B100000",
                    "pdSubCat": "C100007",
                    "pdName": "아이패드 프로 액정보호필름",
                    "pdPrice": 10000,
                    "pdDiscount": 0,
                    "pdSalePeriod": "2099-01-01",
                    "pdStock": 100,
                    "pdDesc": "아이패드 프로 액정보호필름 입니다",
                    "pdDeliveryCharge": 2500,
                    "setSalePeriod": false
                }
            },
            {
                "cartId": 4,
                "memberId": "resultTest",
                "productId": 8,
                "amount": 5,
                "price": 6000000,
                "cartCreateTime": "2022-08-12 02:39:15",
                "productInfo": {
                    "pdIdx": 8,
                    "pdSellerId": "resultTest",
                    "pdLargeCat": "A100000",
                    "pdMiddleCat": "B100000",
                    "pdSubCat": "C100001",
                    "pdName": "삼성 갤럭시 패드",
                    "pdPrice": 1200000,
                    "pdDiscount": 0,
                    "pdSalePeriod": "2099-01-01",
                    "pdStock": 100,
                    "pdDesc": "삼성 갤럭시 패드 설명",
                    "pdDeliveryCharge": 2500,
                    "setSalePeriod": false
                }
            }
        ]
    }
    ```

- **장바구니 단일 상품 삭제**

  - Header

    ```tex
    DELETE /api/v5/cart/{productId} HTTP/1.1
    Host: localhost
    Authorization: ${ACCESS_TOKEN}
    ```

  - Request Parameter

    |   Name    |  Type  | Description | Required |
    | :-------: | :----: | :---------: | :------: |
    | productId | String |   제품 ID   |    O     |

  - Response Parameter

    |    Name    |  Type  |   Description    | Required |
    | :--------: | :----: | :--------------: | :------: |
    | timestamp  | String |    응답 시간     |    O     |
    |    code    | String | 커스텀 응답 코드 |    O     |
    | httpStatus | String |    HttpStatus    |    O     |
    |  message   | String |   응답 메세지    |    O     |

  - Sample

    ```json
    #Request Sample
    http://localhost:8080/api/v5/cart?productId=7
    
    #Response Sample
    {
        "timestamp": "2022-08-12T02:41:48.325381",
        "code": "CART_002",
        "httpStatus": "OK",
        "message": "장바구니 상품 삭제 완료"
    }
    ```

- **장바구니 전체 상품 삭제**

  - Header

    ```tex
    DELETE /api/v5/cart/del HTTP/1.1
    Host: localhost
    Authorization: ${ACCESS_TOKEN}
    ```

  - Request Parameter

    | Name | Type | Description | Required |
    | :--: | :--: | :---------: | :------: |
    |      |      |             |          |

  - Response Parameter

    |    Name    |  Type  |   Description    | Required |
    | :--------: | :----: | :--------------: | :------: |
    | timestamp  | String |    응답 시간     |    O     |
    |    code    | String | 커스텀 응답 코드 |    O     |
    | httpStatus | String |    HttpStatus    |    O     |
    |  message   | String |   응답 메세지    |    O     |

  - Sample

    ```json
    #Response Sample
    {
        "timestamp": "2022-08-12T02:42:24.346103",
        "code": "CART_003",
        "httpStatus": "OK",
        "message": "장바구니 상품 전체 삭제 완료"
    }
    ```

- **상품 구매**

  - Header

    ```tex
    GET /api/v6/order HTTP/1.1
    Host: localhost
    Content-type: application/json;
    Authorization: ${ACCESS_TOKEN}
    ```

  - Request Parameter

    | Name | Type | Description | Required |
    | :--: | :--: | :---------: | :------: |
    |      |      |             |          |

  - Response Parameter

    |    Name    |  Type  |   Description    | Required |
    | :--------: | :----: | :--------------: | :------: |
    | timestamp  | String |    응답 시간     |    O     |
    |    code    | String | 커스텀 응답 코드 |    O     |
    | httpStatus | String |    HttpStatus    |    O     |
    |  message   | String |   응답 메세지    |    O     |

  - Sample

    ```json
    # Request Sample
    {
        "cartId" : 6
    }
    
    #Response Sample
    {
        "timestamp": "2022-08-12T02:43:09.327859",
        "code": "ORDER_000",
        "httpStatus": "OK",
        "message": "구매 완료"
    }
    ```

- **구매 상품 리스트**

  - Header

    ```tex
    GET /api/v6/order HTTP/1.1
    Host: localhost
    Authorization: ${ACCESS_TOKEN}
    ```

  - Request Parameter

    | Name | Type | Description | Required |
    | :--: | :--: | :---------: | :------: |
    |      |      |             |          |

  - Response Parameter

    |      Name       |  Type  |    Description     | Required |
    | :-------------: | :----: | :----------------: | :------: |
    |    timestamp    | String |     응답 시간      |    O     |
    |      code       | String |  커스텀 응답 코드  |    O     |
    |   httpStatus    | String |     HttpStatus     |    O     |
    |     message     | String |    응답 메세지     |    O     |
    |      count      |  int   |   오브젝수 개수    |    O     |
    |      data       |   []   | 조회된 구매 리스트 |    O     |
    |     orderId     |  int   |      구매 ID       |    O     |
    |    productId    |  int   |      제품 ID       |    O     |
    |    memberId     | String |     구매자 ID      |    O     |
    |     cartId      |  int   |    장바구니 ID     |    O     |
    |   totalPrice    |  int   |    총 구매 금액    |    O     |
    |   totalAmount   |  int   |    총 구매 수량    |    O     |
    | orderCreateTime | String |     구매 시간      |    O     |
    |   productInfo   | Object |   구매 제품 정보   |    O     |

  - Sample

    ```json
    {
        "timestamp": "2022-08-12T02:43:35.446704",
        "code": "ORDER_001",
        "httpStatus": "OK",
        "message": "구매 상품 조회완료",
        "count": 2,
        "data": [
            {
                "orderId": 3,
                "productId": 7,
                "memberId": "resultTest",
                "cartId": 7,
                "totalPrice": 50000,
                "totalAmount": 5,
                "orderCreateTime": "2022-08-12 02:43:26",
                "productInfo": {
                    "pdIdx": 7,
                    "pdSellerId": "userTest",
                    "pdLargeCat": "A100000",
                    "pdMiddleCat": "B100000",
                    "pdSubCat": "C100007",
                    "pdName": "아이패드 프로 액정보호필름",
                    "pdPrice": 10000,
                    "pdDiscount": 0,
                    "pdSalePeriod": "2099-01-01",
                    "pdStock": 95,
                    "pdDesc": "아이패드 프로 액정보호필름 입니다",
                    "pdDeliveryCharge": 2500,
                    "setSalePeriod": false
                }
            },
            {
                "orderId": 2,
                "productId": 8,
                "memberId": "resultTest",
                "cartId": 6,
                "totalPrice": 6000000,
                "totalAmount": 5,
                "orderCreateTime": "2022-08-12 02:43:09",
                "productInfo": {
                    "pdIdx": 8,
                    "pdSellerId": "resultTest",
                    "pdLargeCat": "A100000",
                    "pdMiddleCat": "B100000",
                    "pdSubCat": "C100001",
                    "pdName": "삼성 갤럭시 패드",
                    "pdPrice": 1200000,
                    "pdDiscount": 0,
                    "pdSalePeriod": "2099-01-01",
                    "pdStock": 95,
                    "pdDesc": "삼성 갤럭시 패드 설명",
                    "pdDeliveryCharge": 2500,
                    "setSalePeriod": false
                }
            }
        ]
    }
    ```

- **카테고리 조회**

  - Header

    ```tex
    GET /category HTTP/1.1
    Host: localhost
    ```

  - Request Parameter

    | Name | Type | Description | Required |
    | :--: | :--: | :---------: | :------: |
    |      |      |             |          |

  - Response Parameter

    |    Name    |  Type  |        Description        | Required |
    | :--------: | :----: | :-----------------------: | :------: |
    | timestamp  | String |         응답 시간         |    O     |
    |    code    | String |     커스텀 응답 코드      |    O     |
    | httpStatus | String |        HttpStatus         |    O     |
    |  message   | String |        응답 메세지        |    O     |
    |   count    |  int   |     1차 카테고리 개수     |    O     |
    |    data    |   []   | 카테고리 계층 구조 데이터 |    O     |
    |   catId    |  int   |        카테고리 ID        |    O     |
    |  catCode   | String |       카테고리 코드       |    O     |
    |  catName   | String |       카테고리 이름       |    O     |
    |   upCode   | String |    상위 카테고리 코드     |          |
    |  children  |   []   |   하위 카테고리 리스트    |          |

  - Sample

    ```json
    {
        "timestamp": "2022-08-12T01:41:43.0834",
        "code": "S0000",
        "httpStatus": "OK",
        "message": "조회 완료",
        "count": 2,
        "data": [
            {	// 1차 카테고리
                "catId": 1,
                "catCode": "A100000",
                "catName": "디지털",
                "upCode": null,
                "children": [
                    {	// 2차 카테고리
                        "catId": 3,
                        "catCode": "B100000",
                        "catName": "태블릿PC",
                        "upCode": "A100000",
                        "children": [
                            {	// 3차 카테고리
                                "catId": 10,
                                "catCode": "C100007",
                                "catName": "액세서리",
                                "upCode": "B100000",
                                "children": null
                            },
                            {
                                "catId": 9,
                                "catCode": "C100006",
                                "catName": "전자책",
                                "upCode": "B100000",
                                "children": null
                            },
                           	.
                            .
                            .
                        ]
                    }
                ]
            },
            {
                "catId": 2,
                "catCode": "A100001",
                "catName": "생활가전",
                "upCode": null,
                "children": []
            }
        ]
    }
    ```

------

## Custom Code

- **공통**

  |  No  | CustomCode |                   Message                   | HttpCode |      HttpStatus       |
  | :--: | :--------: | :-----------------------------------------: | :------: | :-------------------: |
  |  1   |   AE100    |            접근 권한이 없습니다.            |   403    |       FORBIDDEN       |
  |  2   |   AE101    |    요청 파라미터 형식이 잘못 되었습니다.    |   400    |      BAD_REQUEST      |
  |  3   |   AE102    |      요청 파라미터가 누락 되었습니다.       |   400    |      BAD_REQUEST      |
  |  4   |   E0003    | 서버 내부 Error로 관리자에게 문의 바랍니다. |   500    | INTERNAL_SERVER_ERROR |

- **로그인**

  |  No  | CustomCode |            Message             | HttpCode | HttpStatus  |
  | :--: | :--------: | :----------------------------: | :------: | :---------: |
  |  1   |   L0000    |          로그인 성공           |   200    |     OK      |
  |  2   |   LE100    |   아이디를 찾을 수 없습니다.   |   400    | BAD_REQUEST |
  |  3   |   LE101    | 비밀번호가 일치 하지 않습니다. |   400    | BAD_REQUEST |

- **회원가입**

  |  No  | CustomCode |                           Message                            | HttpCode | HttpStatus  |
  | :--: | :--------: | :----------------------------------------------------------: | :------: | :---------: |
  |  1   |   UC000    |                        회원 가입 성공                        |   200    |     OK      |
  |  2   |   UE101    |                    이미 가입된 ID 입니다.                    |   400    | BAD_REQUEST |
  |  3   |   UE102    |                     ID를 입력 해주세요.                      |   400    | BAD_REQUEST |
  |  4   |   UE103    |                  비밀번호를 입력 해주세요.                   |   400    | BAD_REQUEST |
  |  5   |   UE104    | 비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다. |   400    | BAD_REQUEST |
  |  6   |   UE105    |                    이름을 입력 해주세요.                     |   400    | BAD_REQUEST |
  |  7   |   UE106    |                 휴대폰 번호를 입력 해주세요.                 |   400    | BAD_REQUEST |
  |  8   |   UE107    |                   이메일을 입력 해주세요.                    |   400    | BAD_REQUEST |
  |  9   |   UE108    |                 이메일 형식에 맞지 않습니다.                 |   400    | BAD_REQUEST |
  |  10  |   UE109    |                    주소를 입력 해주세요.                     |   400    | BAD_REQUEST |
  |  11  |   UE110    |                  생년월일을 입력 해주세요.                   |   400    | BAD_REQUEST |

- **회원 조회**

  |  No  | CustomCode |       Message       | HttpCode | HttpStatus |
  | :--: | :--------: | :-----------------: | :------: | :--------: |
  |  1   |   UR000    |   회원 조회 성공    |   200    |     OK     |
  |  2   |   UR001    | 회원 전체 조회 성공 |   200    |     OK     |

- **JWT Token**

  |  No  | CustomCode |                         Message                          | HttpCode |  HttpStatus  |
  | :--: | :--------: | :------------------------------------------------------: | :------: | :----------: |
  |  1   |   TC000    |  REFRESH TOKEN으로 신규 ACCESS TOKEN이 생성 되었습니다.  |   200    |      OK      |
  |  2   |   TE101    |            유효하지 않은 ACCESS TOKEN 입니다.            |   401    | UNAUTHORIZED |
  |  3   |   TE102    |               만료된 ACCESS TOKEN 입니다.                |   401    | UNAUTHORIZED |
  |  4   |   TE103    |            잘못된 서명의 ACCESS TOKEN 입니다.            |   401    | UNAUTHORIZED |
  |  5   |   TE105    |             ACCESS TOKEN을 찾을 수 없습니다.             |   401    | UNAUTHORIZED |
  |  6   |   TE106    | REFRESH TOKEN이 유효하지 않습니다. 다시 로그인 해주세요. |   401    | UNAUTHORIZED |

- **상품 등록**

  |  No  | CustomCode |                  Message                  | HttpCode | HttpStatus  |
  | :--: | :--------: | :---------------------------------------: | :------: | :---------: |
  |  1   |   PC000    |              상품 등록 완료               |   200    |     OK      |
  |  2   |   PC001    |              상품 조회 완료               |   200    |     OK      |
  |  3   |   PCE100   |        1차 카테고리 값이 없습니다.        |   400    | BAD_REQUEST |
  |  4   |   PCE101   |        2차 카테고리 값이 없습니다.        |   400    | BAD_REQUEST |
  |  5   |   PCE102   |        3차 카테고리 값이 없습니다.        |   400    | BAD_REQUEST |
  |  6   |   PCE103   |         상품 제목 값이 없습니다.          |   400    | BAD_REQUEST |
  |  7   |   PCE104   |         상품 가격 값이 없습니다.          |   400    | BAD_REQUEST |
  |  8   |   PCE105   |        상품 할인가 값이 없습니다.         |   400    | BAD_REQUEST |
  |  9   |   PCE106   |  상품 판매기간 설정 여부 값이 없습니다.   |   400    | BAD_REQUEST |
  |  10  |   PCE107   |       상품 판매기간 값이 없습니다.        |   400    | BAD_REQUEST |
  |  11  |   PCE108   |         상품 재고 값이 없습니다.          |   400    | BAD_REQUEST |
  |  12  |   PCE109   |         상품 설명 값이 없습니다.          |   400    | BAD_REQUEST |
  |  13  |   PCE110   |        상품 배송비 값이 없습니다.         |   400    | BAD_REQUEST |
  |  14  |   PCE111   | 1차 카테고리 값에 일치하는 값이 없습니다. |   400    | BAD_REQUEST |
  |  15  |   PCE112   | 2차 카테고리 값에 일치하는 값이 없습니다. |   400    | BAD_REQUEST |
  |  16  |   PCE113   | 3차 카테고리 값에 일치하는 값이 없습니다. |   400    | BAD_REQUEST |

- **상품 검색**

  |  No  |  CustomCode  |                  Message                  | HttpCode | HttpStatus  |
  | :--: | :----------: | :---------------------------------------: | :------: | :---------: |
  |  1   |  SEARCH_000  |              상품 검색 완료               |   200    |     OK      |
  |  2   | SEARCH_E1000 | 카테고리 검색의 요청 파라미터가 없습니다. |   400    | BAD_REQUEST |

- **장바구니**

  |  No  | CustomCode |                Message                | HttpCode | HttpStatus  |
  | :--: | :--------: | :-----------------------------------: | :------: | :---------: |
  |  1   |  CART_000  |          장바구니 등록 완료           |   200    |     OK      |
  |  2   |  CART_001  |        장바구니 전체 조회 완료        |   200    |     OK      |
  |  3   |  CART_002  |        장바구니 상품 삭제 완료        |   200    |     OK      |
  |  4   |  CART_003  |     장바구니 상품 전체 삭제 완료      |   200    |     OK      |
  |  5   | CART_E100  |         상품 ID값이 없습니다.         |   400    | BAD_REQUEST |
  |  6   | CART_E101  |       상품 수량 값이 없습니다.        |   400    | BAD_REQUEST |
  |  7   | CART_E102  |       상품 수량 값이 없습니다.        |   400    | BAD_REQUEST |
  |  8   | CART_E103  |       상품을 찾을 수 없습니다.        |   400    | BAD_REQUEST |
  |  9   | CART_E104  |        장바구니가 비었습니다.         |   400    | BAD_REQUEST |
  |  10  | CART_E105  | 해당 상품이 이미 장바구니에 있습니다. |   400    | BAD_REQUEST |

- **상품 구매**

  |  No  | CustomCode |              Message              | HttpCode | HttpStatus  |
  | :--: | :--------: | :-------------------------------: | :------: | :---------: |
  |  1   | ORDER_000  |            구매 완료.             |   200    |     OK      |
  |  2   | ORDER_001  |        구매 상품 조회완료.        |   200    |     OK      |
  |  3   | ORDER_002  | 장바구니 정보를 찾을 수 없습니다. |   400    | BAD_REQUEST |
  |  4   | ORDER_003  |       구매 이력이 없습니다.       |   400    | BAD_REQUEST |

