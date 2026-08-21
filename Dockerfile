# Chỉ cần JRE môi trường chạy
FROM eclipse-temurin:11-jre-alpine
WORKDIR /app

# Phân quyền User
RUN addgroup -S vdtgroup && adduser -S vdtuser -G vdtgroup
USER vdtuser:vdtgroup

# Copy thẳng file .jar đã được GitHub Actions build xong ở bên ngoài vào
COPY target/vdt-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]