# Dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy toàn bộ source code vào image
COPY . .

# Build app bằng Gradle Wrapper
RUN ./gradlew build -x test

# Lấy file jar build ra từ thư mục build/libs
RUN cp build/libs/*.jar app.jar

# Expose port nếu app chạy trên 8085
EXPOSE 8085

# Chạy file jar
CMD ["java", "-jar", "app.jar"]
