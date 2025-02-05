FROM openjdk:17-jdk-slim

WORKDIR /app

# Gradle 실행을 위한 환경 변수 설정 (옵션)
ENV GRADLE_USER_HOME=/app/.gradle

# 소스 코드 복사
COPY . .

# Gradle 실행 권한 부여
RUN chmod +x ./gradlew

# Gradle 빌드 수행 (테스트 제외)
RUN ./gradlew build -x test

# 빌드된 JAR 파일을 실행
CMD ["java", "-jar", "build/libs/web-0.0.1-SNAPSHOT.jar"]
