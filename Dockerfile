# ==========================================
# STAGE 1: BUILD (Giai đoạn biên dịch)
# ==========================================
# Sử dụng Maven và JDK 11 (Bản Alpine cho nhẹ)
FROM maven:3.8.7-eclipse-temurin-11-alpine AS builder
WORKDIR /build

# Tối ưu Cache 1: Copy pom.xml vào trước để tải thư viện
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Tối ưu Cache 2: Copy source code vào sau và tiến hành đóng gói
COPY src ./src
RUN mvn clean package -DskipTests

# ==========================================
# STAGE 2: RUNTIME (Giai đoạn chạy thực tế)
# ==========================================
# Chỉ sử dụng JRE 11 (Java Runtime) Alpine để tối giản kích thước
FROM eclipse-temurin:11-jre-alpine
WORKDIR /app

# Bảo mật: Tạo user không có đặc quyền root
RUN addgroup -S vdtgroup && adduser -S vdtuser -G vdtgroup
USER vdtuser:vdtgroup

# Copy DUY NHẤT file .jar từ Stage 1 sang
COPY --from=builder /build/target/vdt-0.0.1-SNAPSHOT.jar app.jar

# Khai báo port ứng dụng đang sử dụng (đọc từ application.yml)
EXPOSE 8081

# Khởi chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]