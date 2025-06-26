# 🚀 Steam_Lite 백엔드
본 문서는 [Steam_Lite] 백엔드 개발을 위한 가이드라인과 정보를 제공합니다.

📌 1. 프로젝트 개요
본 프로젝트는 사용자가 게임을 구매, 다운로드하고, 자신의 라이브러리를 관리하며, 다른 사용자와 소셜 상호작용(친구, 리뷰)을 할 수 있는 웹 서비스의 백엔드를 구축하는 것을 목표로 합니다. 관리자 기능을 통해 게임 콘텐츠 및 사용자 활동을 효율적으로 관리할 수 있습니다.

1.1. 주요 기능
회원 기능: 회원가입, 로그인/로그아웃 (세션 기반), 내 정보 조회/변경

게임 스토어: 게임 목록/상세 조회, 게임 다운로드, 게임 검색

라이브러리: 보유 게임 목록 조회, 게임 라이브러리 추가

리뷰: 리뷰 작성, 조회, 삭제

친구 기능: 친구 목록 조회, 친구 요청, 받은 요청 관리, 친구의 라이브러리 조회

관리자 기능: 게임 등록/삭제/수정, 유저/리뷰 관리

1.2. 기술 스택 (Backend)
언어: Java 21

프레임워크: Spring Boot 3.x

빌드 툴: Gradle

데이터베이스: MySQL

ORM: Spring Data JPA (Hibernate)

인증/권한: Spring Security (세션 기반)

유틸리티: Lombok

📌 2. 개발 환경 설정
2.1. 필수 설치 도구
JDK 21

Gradle 8.x 이상

MySQL 8.0 이상

선호하는 IDE (IntelliJ IDEA Ultimate 권장 - Spring Boot 지원 용이)

2.2. 프로젝트 설정
프로젝트 클론:

bash
git clone [https://github.com/slave-factory/steam-lite-be](https://github.com/slave-factory/steam-lite-be)


application.yml 설정:
src/main/resources/application.yml 파일을 열고, 데이터베이스 연결 정보를 본인 환경에 맞게 수정합니다.

📌 3. Git & 협업 가이드라인
3.1. 브랜치 전략
main 브랜치는 항상 배포 가능한 상태를 유지합니다.

새로운 기능 개발 또는 버그 수정을 위해 main에서 브랜치를 생성합니다.

브랜치명 규칙:

feat/\<기능-요약\>: 새로운 기능 개발 (예: feat/user-signup, feat/game-download)

fix/\<버그-요약\>: 버그 수정 (예: fix/login-bug, fix/review-delete-error)

refactor/\<리팩토링-요약\>: 코드 구조 변경 및 리팩토링 (예: refactor/exception-handling)

docs/\<문서-요약\>: 문서 변경 (예: docs/update-readme)

3.2. 커밋 메시지 규칙
커밋 메시지는 다음 형식에 따라 작성하여 히스토리 관리를 용이하게 합니다.

<타입>: <설명>

<body> (선택 사항)


\<타입\>: feat, fix, refactor, docs, test, build, ci, chore 중 하나를 사용합니다.

\<설명\>: 50자 이내로 간결하게 작성하고, 명령형 어조를 사용합니다 (예: "로그인 기능 추가", "회원가입 버그 수정").

\<body\>: 선택 사항이며, 커밋의 상세 내용이나 배경, 해결책 등을 설명합니다.

예시:

feat: 사용자 회원가입 API 구현

이메일, 비밀번호, 닉네임을 이용한 회원가입 기능을 추가했습니다.
- User 엔티티 정의
- UserRepository, UserService, UserController 구현
- 비밀번호 암호화 적용
- 이메일, 닉네임 중복 검사 로직 포함

3.3. Pull Request (PR) 가이드라인
새로운 기능 개발 또는 버그 수정 완료 후, 본인의 브랜치에서 main 브랜치로 PR을 생성합니다.

PR 생성 시, 변경 사항에 대한 충분한 설명을 포함해야 합니다.

모든 PR은 Squash and Merge 방식으로 main 브랜치에 병합됩니다.

코드 리뷰를 요청하고, 리뷰어의 승인 후에만 병합이 가능합니다.
