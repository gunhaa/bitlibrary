## 커밋 메시지 컨벤션 
- [Feat]: 새로운 기능 추가
- [Fix]: 버그 수정
- [Chore]: 빌드 테스트 업데이트, 프로젝트 설정 변경
- [Design]: CSS 등 사용자 UI 디자인 변경
- [Refactoring]: 코드 리팩토링
- [Test]: 테스트 추가
- [docs]: 문서 추가
- [Rename]: 파일, 폴더명을 수정하거나 옮기는 작업 수행
- [Remove]: 파일 삭제 작업 수행

## 브랜치 전략
- feature - 각 기능 또는 작업을 개발할 때 사용하는 브랜치
- main - 기능 개발 완료 및 테스트 검증 후 병합되는 브랜치
- prod - Jenkins Webhook이 연결된 배포 브랜치

## run
- Jenkins에서 application.yml을 생성해야 실행할 수 있음
```shell
docker-compose up --build -d
```

## Build
- prod build