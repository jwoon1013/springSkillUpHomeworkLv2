# springSkillUpHomework
- 스프링 숙련 주차 숙제(22/12/17 제출)  
- 입문주차에 만든 과제 프로젝트에 회원가입과 로그인 기능 추가하고 기존 요구사항의 일부를 변경.
- 회원가입&로그인 구현 / 인증&인가 이해 / JWT를 활용한 게시글 처리 / JPA 연관관계 이해 및 회원과 게시글 구현


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
    - password는  `최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)`로 구성되어야 한다.
    - DB에 중복된 username이 없다면 회원을 저장하고 Client 로 성공했다는 메시지, 상태코드 반환하기
7. 로그인 API 요건
    - username, password를 Client에서 전달받기
    - DB에서 username을 사용하여 저장된 회원의 유무를 확인하고 있다면 password 비교하기
    - 로그인 성공 시, 로그인에 성공한 유저의 정보와 JWT를 활용하여 토큰을 발급하고,
      발급한 토큰을 Header에 추가하고 성공했다는 메시지, 상태코드 와 함께 Client에 반환하기

# 3. API 명세서
| 기능         | Method | URL          | Request                                    | Response                                                                |
|------------|--------|--------------|--------------------------------------------|-------------------------------------------------------------------------|
| 회원가입 (추가)  | POST   | /auth/signup | username, password                         | msg(회원가입 성공)                                                            |
| 로그인 (추가)   | POST   | /auth/login  | username, password                         | msg(로그인 성공), header(Authorization:Bearer)                               |
| 게시글 작성     | POST   | /posts       | header(Authorization:Bearer), title, content | postId, createdAt, modifiedAt, ttitle, content                              |  
| 전체게시글 목록조회 | GET    | /posts       | 없음                                         | totalPostList[ {postId, createdAt, modifiedAt, ttitle, username, content} ] |  
| 선택한 게시글 조회 | GET    | /posts/{postId}  | 없음                                         | createdAt, modifiedAt, postId, ttitle, username, content                    |  
| 선택한 게시글 수정 | PUT    | /posts/{postId}  | header(Authorization:Bearer), title, content | postId, createdAt, modifiedAt, ttitle, username, content                |  
| 선택한 게시글 삭제 | DELETE | /posts/{postId}  | header(Authorization:Bearer), postId   | msg(게시글 삭제 성공)                                                          |    

#  4. ERD


# 5. 느낀 점
