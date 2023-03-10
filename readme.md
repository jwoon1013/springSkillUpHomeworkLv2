# springSkillUpHomework LV2
- 스프링 숙련 주차 숙제(22/12/21 제출)  
- 입문주차에 만든 과제 프로젝트에 회원가입과 로그인 기능 추가하고 기존 요구사항의 일부를 변경.
- 회원가입&로그인 구현 / 인증&인가 이해 / JWT를 활용한 게시글 **+ 댓글** 처리 / JPA 연관관계 이해 및 회원과 게시글 **+ 댓글** 구현
- LV1의 코드를 리펙토링 + 댓글기능 구현

# 1. 과제 작성시 주의사항
- GitHub 링크, API 명세서, ERD 총 세가지 제출하기. 
- Postman을 이용해 HTTP 메서드 요청해보기.

# 2. 과제 요구사항
1. 전체 게시글 목록 조회 API
   - 제목, 작성자명(username), 작성 내용, 작성 날짜를 조회하기
   - 작성 날짜 기준 내림차순으로 정렬하기  
   
2. 게시글 작성 API
    - 토큰을 검사하여, 유효한 토큰일 경우에만 게시글 작성 가능
    - 제목, 작성자명(username), 작성 내용을 저장하고
    - 저장된 게시글을 Client 로 반환하기  
     
3. 선택한 게시글 조회 API
    - 선택한 게시글의 제목, 작성자명(username), 작성 날짜, 작성 내용을 조회하기
      (검색 기능이 아닙니다. 간단한 게시글 조회만 구현해주세요.)
   
4. 선택한 게시글 수정 API   
    - 토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 수정 가능
    - 제목, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기
   
5. 선택한 게시글 삭제 API    
    - 토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 삭제 가능
    - 선택한 게시글을 삭제하고 Client 로 성공했다는 메시지, 상태코드 반환하기
   
6. 회원 가입 API 요건
    - username, password를 Client에서 전달받기
    - username은  `최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)`로 구성되어야 한다.
    - password는  `최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), **특수문자**`로 구성되어야 한다.
    - DB에 중복된 username이 없다면 회원을 저장하고 Client 로 성공했다는 메시지, 상태코드 반환하기
    - 회원 권한 부여하기 (ADMIN, USER) - ADMIN 회원은 모든 게시글, 댓글 수정 / 삭제 가능
7. 로그인 API 요건
    - username, password를 Client에서 전달받기
    - DB에서 username을 사용하여 저장된 회원의 유무를 확인하고 있다면 password 비교하기
    - 로그인 성공 시, 로그인에 성공한 유저의 정보와 JWT를 활용하여 토큰을 발급하고,
      발급한 토큰을 Header에 추가하고 성공했다는 메시지, 상태코드 와 함께 Client에 반환하기  

8. 댓글 작성 API **( 여기서부터 추가된 요구사항 )**
   - 토큰을 검사하여, 유효한 토큰일 경우에만 댓글 작성 가능
   - 선택한 게시글의 DB 저장 유무를 확인하기
   - 선택한 게시글이 있다면 댓글을 등록하고 등록된 댓글 반환하기
9. 댓글 수정 API
   - 토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 댓글만 수정 가능
   - 선택한 댓글의 DB 저장 유무를 확인하기
   - 선택한 댓글이 있다면 댓글 수정하고 수정된 댓글 반환하기
10. 댓글 삭제 API
   - 토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 댓글만 삭제 가능
   - 선택한 댓글의 DB 저장 유무를 확인하기
   - 선택한 댓글이 있다면 댓글 삭제하고 Client 로 성공했다는 메시지, 상태코드 반환하기
11. 예외 처리
   - 토큰이 필요한 API 요청에서 토큰을 전달하지 않았거나 정상 토큰이 아닐 때는 "토큰이 유효하지 않습니다." 라는 에러메시지와 statusCode: 400을 Client에 반환하기
   - 토큰이 있고, 유효한 토큰이지만 해당 사용자가 작성한 게시글/댓글이 아닌 경우에는 “작성자만 삭제/수정할 수 있습니다.”라는 에러메시지와 statusCode: 400을 Client에 반환하기
   - DB에 이미 존재하는 username으로 회원가입을 요청한 경우 "중복된 username 입니다." 라는 에러메시지와 statusCode: 400을 Client에 반환하기
   - 로그인 시, 전달된 username과 password 중 맞지 않는 정보가 있다면 "회원을 찾을 수 없습니다."라는 에러메시지와 statusCode: 400을 Client에 반환하기

# 3. API 명세서 ( LV2에 맞춰 수정 )
| 기능         | Method | URL                         | Request                                      | Response                                                                                 |
|------------|--------|-----------------------------|----------------------------------------------|------------------------------------------------------------------------------------------|
| 회원가입 (추가)  | POST   | /auth/signup                | username, password                           | msg(회원가입 성공), statusCode                                                                 |
| 로그인 (추가)   | POST   | /auth/login                 | username, password                           | msg(로그인 성공), statusCode,  header(Authorization:Bearer)                                   |
| 게시글 작성     | POST   | /posts                      | header(Authorization:Bearer), title, content | postId, createdAt, modifiedAt, ttitle, content                                           |  
| 전체게시글 목록조회 | GET    | /posts                      | 없음                                           | totalPostList[ {postId, createdAt, modifiedAt, ttitle, username, content, commentList} ] |  
| 선택한 게시글 조회 | GET    | /posts/{postId}             | 없음                                           | createdAt, modifiedAt, postId, ttitle, username, content, comment List                   |  
| 선택한 게시글 수정 | PUT    | /posts/{postId}             | header(Authorization:Bearer), title, content | postId, createdAt, modifiedAt, ttitle, username, content                                 |  
| 선택한 게시글 삭제 | DELETE | /posts/{postId}             | header(Authorization:Bearer)                 | msg(게시글 삭제 성공), statusCode                                                               |
| 댓글 작성      | POST   | /posts/{postId}             | header(Authorization:Bearer), commentContent | postId, ommentId, CreatedAt, modifiedAt, commentContent                                  |
| 댓글 수정      | PUT    | /posts/{postId}/{commentId} | header(Authorization:Bearer), commentContent | postId, commentId, CreatedAt, modifiedAt, commentContent                                 |
| 댓글 삭제      | DELETE | /posts/{postId}/{commentId} | header(Authorization:Bearer)                 |                                                                                          |


#  4. ERD
- 숙련과제 LV1 때 작성한 ERD
![ERD](/ERD.png)
- 숙련과제 LV2 때 작성한 ERD

# 5. 느낀 점
- 코드 리펙토링을 하니 확실히 가독성이 좋아진걸 느꼈다. 메소드 하나가 하나의 역할만 하도록 하는 것. 개념은 알겠는데, 실제로 코드로 구현하기는 좀 어렵지만, 구현하니까 좋다는건 확실히 알겠다.
- comment와 post사이의 연결관계를 만드는게 어려워서 많이 해맸다. JPA에 대해 더 공부해야할 것 같다.
- 이번 LV2 과제 느낀점 : 나는 내가 꽤 이해했다고 생각했는데, 과제 해보니.. 나는 모른다는 것을 알았다. 이래서 직접 코딩해보는게 백날 자료랑 강의만 보는거보다 낫다고 하는거같다...

# 6. 현재 진행도 
(완료시 취소선)  
<s>
- checkToken메소드생성 
- 코드리펙토링 - 포스트 작성
- 코드리펙토링 - 포스트 수정
- 코드리펙토링 - 포스트 삭제


- 코드리펙토링 - 로그인(RESPONSE SERVICE로 안넘어가게...) +PASSWORD체크는 USER가 알아서 하게...  
- 회원가입시 ADMIN코드 확인후 관리자 생성  
- ADMIN 권한 구현 : 게시글 댓글 모두 삭제가능  


- 비밀번호 조건변경 (특수문자추가)


- 댓글 작성기능 구현  </s>
- Post에 댓글 리스트추가 연관관계구현 :: 현재 오류 발생중...!  
  <s>
 - 댓글 수정 기능 구현  
- 댓글 삭제 기능 구현  </s>

- 오류시 statusCode와 특정 메세지를 뱉어내게 수정하기
- 현재 post < - > comment 간 연관관계 구현에서 에러 발생 중.
  1.  post 생성 됨. comment 생성됨. 개별 코멘트, 개별 post 조회가능.
  2. comment가 달린 post 로딩시 HttpMessageNotWritableException: Could not write JSON: Infinite recursion (StackOverflowError) 발생중.
  3. 전체 comment 로드시 작성한 모든 comment 보임. but 연관관계 관련된거 로드시 에러발생. DB POST에도 commentList없음(post 생성시 빈 리스트는 나옴). 연관관계 문제로 추정중.  
