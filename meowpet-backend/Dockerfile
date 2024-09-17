FROM eclipse-temurin:21-jdk

#WORKDIR /app: Thư mục làm việc (working directory) của container
WORKDIR /app

#COPY: Copy file từ máy host (pc, laptop) vào container
#COPY .mvn/ .mvn: Copy thư mục .mvn từ máy host vào thư mục .mvn của container
COPY .mvn/ .mvn
#COPY mvnw: Copy file mvnw từ máy host vào thư mục gốc của container
COPY mvnw pom.xml ./
# Ensure the mvnw script has execute permissions
RUN chmod +x mvnw

# Install tar utility
RUN apt-get update && apt-get install -y tar

#RUN: Chạy lệnh trong container
#./mvnw: Chạy file mvnw
#dependency:go-offline: Tải các dependencies cần thiết cho project
RUN ./mvnw dependency:go-offline
#COPY src ./src: Copy thư mục src từ máy host vào thư mục src của container
COPY src ./src

#CMD: Chạy lệnh khi container được khởi động
#./mvnw spring-boot:run: Chạy ứng dụng Spring Boot
CMD ["./mvnw", "spring-boot:run"]