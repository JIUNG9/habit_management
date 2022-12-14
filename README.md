# habit management app
##### 나쁜 습관을 교정하고 좋은 습관을 유지하기 위해서 해당 어플리케이션을 제작했습니다
## Description

 Spring boot, Spring security, Redis를 사용하였습니다.
 RESTful architecture를 위하여 JWT 토큰을 사용했고, 이를 Redis에 저장하여 인증을 합니다. 또한 회원의 권한과 그룹의 권한이 따로 설정되어있어, 한 그룹의 관리자이더라도 다른 그룹의 관리자가 아니면 접근 권한을 얻지 못합니다.
 또한 습관에 대한 전체 통계를 통하여 습관을 더 효율적으로 달성 할 수 있도록 했습니다. 현재는 AWS에 배포되지 않은 상태이며 향후 Test code와 부재된 채팅기능을 Kafka를 통하여 구현할 예정입니다. 해당 메인 브랜치는 백엔드를 올려놓은 브랜치입니다.
첨부된 링크는 프로젝트 Notion입니다. https://plucky-jumbo-d38.notion.site/1a6b3a5fc1dc49b6b8b8ff6be54981bd?v=33266c465c064f0ebc549c47eb34ff8e
## Installation
### Redis(Mac brew)

```
brew install redis
```

## Usage
```
redis-cli -h host -p port -a password
```


