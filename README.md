# habit management app
##### 나쁜 습관을 교정하고 좋은 습관을 유지하기 위해서 해당 어플리케이션을 제작했습니다
![image](https://user-images.githubusercontent.com/60885635/225555714-ff3e1070-fba0-4eca-b2d1-01b1fcfc2cd0.png)
![image](https://user-images.githubusercontent.com/60885635/225555872-cca6ad9f-8d6d-496c-a29f-f016fee40cea.png)

## Description

 Spring boot, Spring security, Redis를 사용하였습니다.
 RESTful architecture를 위하여 JWT 토큰을 사용했고, 이를 Redis에 저장하여 인증을 합니다. 또한 회원의 권한과 그룹의 권한이 따로 설정되어있어, 한 그룹의 관리자이더라도 다른 그룹의 관리자가 아니면 접근 권한을 얻지 못합니다.
 또한 습관에 대한 전체 통계를 통하여 습관을 더 효율적으로 달성 할 수 있도록 했습니다. 현재는 AWS에 배포되지 않은 상태이며 향후 Test code와 부재된 채팅 기능을 추가 할 예정입니다. 첨부된 링크는 프로젝트 기능명세를 한 Notion입니다. https://quilt-tarragon-566.notion.site/637a331c8d7f40d9a6dc58935b91b69d?v=7efa3aa813544e7aa2757efdcdf127cc
## Installation
### Redis(Mac brew)

```
brew install redis
```

## Usage
```
redis-cli -h host -p port -a password
```


