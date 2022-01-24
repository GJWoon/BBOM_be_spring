# Toy Project Fashion Flatform BBOM

<img width="263" alt="스크린샷 2021-11-16 오후 4 14 29" src="https://user-images.githubusercontent.com/72774518/150744519-bfd255ab-3cfe-4748-8c62-d7cf969e5cbe.png">


## 프로젝트 소개
- 해당프로젝트는 인스타그램을 크론코딩하여 만든 프로젝트이며 패션을 중심으로 사용자들이 게시글으 올릴 수 있는 SNS 입니다.

## 프로젝트 작업인원

- 프론트개발자 1명 
- 백엔드개발자 1명
- 앱 개발자 1명

## 백엔드 기술 스택

- SpringBoot 2.3.9 RELEASE
- Spring data JPA
- Spring Security
- FireBase
- JWT
- RetroFit 

## ERD
![bbom](https://user-images.githubusercontent.com/72774518/150747539-4cdc28f8-afb5-4913-b1b7-f19dcffaead8.png)

## 디렉터리 구조

<img width="341" alt="image" src="https://user-images.githubusercontent.com/72774518/150747716-eb36dd8e-e13c-4cf6-9d55-db6497487110.png">

### Domain 패키지
- 프로젝트 도메인이 나뉘어져 있는 패키지
- Domain패키지 파일구조 
- <img width="180" alt="image" src="https://user-images.githubusercontent.com/72774518/150748144-bcc61e1a-3393-4dda-abd7-6426f72c15b2.png">
-  api : 해당 도메인의 Controller가 집합되어 있는 패키지
-  application : 해다 도메인의 Service(비즈니스 로직)가 집합되어 있는 패키지 
-  domain : 해당 도메인의 Entity들이 집합되어 있는 패키지
-  dto: 해당 도메인의 dto(DataTranterObject)가 집합되어 있는 패키지
-  Repository: 해당 도메인의 Repository(DB와 Connetcion)가 집합되어 있는 패키지
 
