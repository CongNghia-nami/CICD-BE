# ---------- STAGE 1: Build app ----------
FROM gradle:8.5-jdk17 AS builder
WORKDIR /app

# Copy toàn bộ project vào container
COPY . .

# Build project và tạo file .jar
RUN gradle build -x test

# ---------- STAGE 2: Runtime ----------
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy file JAR từ stage build sang
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose port ứng dụng (anh dùng 8085)
EXPOSE 8085

# Lệnh chạy ứng dụng
CMD ["java", "-jar", "app.jar"]
