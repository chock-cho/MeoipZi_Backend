# MeoipZi

<br>

## 👩‍💻 팀원 소개

|[이윤진](https://github.com/yunjin-21)|[이규민](https://github.com/keke5149)|[신수정](https://github.com/chock-cho)|
| :-: | :-: | :-: |
|  <img src="https://avatars.githubusercontent.com/yunjin-21" width="150"> | <img src="https://avatars.githubusercontent.com/keke5149" width="150">|  <img src="https://avatars.githubusercontent.com/chock-cho" width="150"> |
|DB 설계 및 API 명세서 작성,<br>Outfit API 개발, Product API 개발,<br>Search API 개발, 댓글 API 개발, 배포 담당| |DB 설계 및 API 명세서 작성,<br>Profile API 개발,home API 개발,<br>Community API 개발,Shortform API 개발|


<br>


## 기술 스택
- Java
- Spring Boot
- MySQL
- AWS RDS, EC2

## ERD 설계
<img width="7000" alt="image" src="https://github.com/MeoipZi/MeoipZi-BE/assets/96698362/df552711-7a99-416a-9057-35c9520efa30">



## API 명세서
---
### 👤 회원가입 & 로그인:  `/api`
| Method | Description | URI |
| ------- | --- | --- |
| POST | 회원가입 |/api/signup |
| POST | 로그인 | /api/authenticate |

---
### 🪪 Profile Page:  `/profiles`

| Method | Description | URI |
| ------- | --- | --- |
| POST | 초기 회원가입 시 프로필 설정 |/profiles/settings |
| PATCH | 프로필 정보 수정 | /profiles/{profileId} |
| GET | 프로필 정보 조회 | /profiles/info |

---
### 🏠 Home Page: `/meoipzi`

#### ● Stakeholder 1: Administrator
| Method | Description | URI |
| ------- | --- | --- |
| POST | 빈티지 소식 업로드 |/meoipzi/news |
| DELETE | 빈티지 소식 삭제 | /meoipzi/news/{vintageNewsId}|
| POST | 제휴 기업 등록하기 | /meoipzi/partners |
| DELETE | 제휴 기업 삭제 | /meoipzi/partners/{partnersId}|

#### ● Stakeholder 2: User
| Method | Description | URI |
| ------- | --- | --- |
| GET | 메인 홈 페이지 조회 |/meoipzi |
| GET | 빈티지 소식 상세 조회 |/meoipzi/news/{vintageNewsId} |
| GET | 제휴 기업 상세 조회(링크 연결) | /meoipzi/partners/{partnersId} |

---
### 👕 Outfit Page: `/outfits`
| Method | Description | URI |
| ------- | --- | --- |
| GET | 코디 좋아요 순 조회 |/outfits/popular?page={pageNum}&size={size} |
| GET | 코디 최신 순 조회 |/outfits/latest?page={pageNum}&size={size} |
| GET | 코디 상세 조회(코디 1개+3개 상품) | /outfits/{outfitId} |
| POST | 코디 등록 | /outfits |
| PATCH | 코디 수정 | /outfits/{outfitId} |
| POST | 코디에 좋아요 등록/취소 | /outfits/{outfitId}/like |
| POST | 코디에 스크랩 등록/취소 | /outfits/{outfitId}/scrap |
| GET | 코디 1개 게시글에서 댓글 전체 조회 | /outfits/{outfitId}/comments |
| POST | 코디 댓글 등록 | /outfits/{outfitId}/comments |
| PUT | 코디 댓글 수정 | /outfits/{outfitId}/comments/{commentsId} |
| DELETE | 코디 댓글 삭제 | /outfits/{outfitId}/comments/{commentsId} |

---
### 🛍️ Product Page: `/products` 

#### ● Stakeholder 1: Administrator
| Method | Description | URI |
| ------- | --- | --- |
| POST | 상품 등록 |/products |
| DELETE | 상품 삭제 | /products/{productId} |

#### ● Stakeholder 2: User
| Method | Description | URI |
| ------- | --- | --- |
| POST | 상품 스크랩 등록/취소 |/products/{productId}/scrap |
| GET | 상품 1개 상세 조회 |/products/{productId} |


---
### 🔎 Search Page: `/search`
| Method | Description | URI |
| ------- | --- | --- |
| GET | 카테고리별 조회 |/products/search/category/latest?category={categoryName}&page={pageNum}&size={size} |
| GET | 브랜드별 조회 | /products/search/brand/latest?brand={categoryName}&page={pageNum}&size={size} |
| GET | 장르별 조회 | /outfits/search/genre/latest?genreId=1&page={pageNum}&size={size} |

---
### 💁 My Page: `/mypage`
| Method | Description | URI |
| ------- | --- | --- |
| GET | 내가 좋아요한 코디/커뮤니티 게시글 조회 |/mypage/likes |
| GET | 내가 좋아요한 코디 조회 | /mypage/likes/outfits |
| GET | 내가 좋아요한 숏폼 조회 | /mypage/likes/shortforms?page={pageNum} |
| GET | 내가 스크랩한 코디/상품 조회 |/mypage/posts/scraps |
| GET | 내가 스크랩한 코디들 조회 |/mypage/posts/outfits?page={pageNum} |
| GET | 내가 스크랩한 상품들 조회 |/mypage/posts/products?page={pageNum} |
| GET | 내가 작성한 게시글/숏폼 조회 |/mypage/posts/feeds |
| GET | 내가 작성한 커뮤니티 게시글 조회 |/mypage/posts/feeds/communities |

---
### 📝 Community Page: `/communities`
| Method | Description | URI |
| ------- | --- | --- |
| POST | 커뮤니티 글 등록 |/communities |
| DELETE | 커뮤니티 글 삭제 | /communities/{communityId} |
| PATCH | 커뮤니티 글 수정 | /communities/{communtiyId} |
| GET | 커뮤니티 글 상세 조회 |/communities/{communityId} |
| GET | 커뮤니티 카테고리별 최신순 조회 | /communities/latest?category={categoryName}&page={pageNum}&size={size} |
| GET | 커뮤니티 카테고리별 인기순 조회 | /communities/popular?category={categoryName}&page={pageNum}&size={size} |
| POST | 커뮤니티 글 좋아요 등록/삭제 |/communities/{communityId}/like |
| POST | 커뮤니티 글 댓글 등록 |/communities/{communityId}/comments |
| DELETE | 커뮤니티 글 댓글 삭제 |/communities/{communityId}/comments/{commentId} |
| POST | 커뮤니티 글 대댓글 등록 |/communities/{communityId}/replies |
| DELETE | 커뮤니티 글 대댓글 삭제 |/communities/{communityId}/replies/{commentId} |

---
### 📹 Shortform Page: `/shortforms`
| Method | Description | URI |
| ------- | --- | --- |
| POST | 숏폼 글 등록 |/shortforms |
| DELETE | 숏폼 글 삭제 | /shortforms/{shortformId} |
| PATCH | 숏폼 글 수정 | /shortforms/{shortformId} |
| GET | 숏폼 글 1개 상세 조회 |/shortforms/{shortformId} |
| GET | 숏폼 최신순 조회 | /shortforms/latest?page={pageNum}&size={size} |
| GET | 숏폼 인기순 조회 | /shortforms/popular?page={pageNum}&size={size} |
| POST | 숏폼 좋아요 등록/삭제 |/shortforms/{shortformId}/like |
| POST | 숏폼 댓글 등록 |/shortforms/{shortformId}/comments |
| PUT | 숏폼 댓글 수정 |/shortforms/{shortformId}/comments/{commentId} |
| DELETE | 숏폼 댓글 삭제 |/shortforms/{shortformId}/comments/{commentId} |
| GET | 숏폼 1개 게시글에서 댓글 전체 조회 |/shortforms/{shortformId}/comments |
