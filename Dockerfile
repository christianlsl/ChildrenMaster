# 第一阶段：构建阶段
FROM maven:3.8.5-openjdk-17 AS build

# 设置工作目录
WORKDIR /app

# 将 pom.xml 和源代码复制到工作目录
COPY pom.xml .
COPY src ./src

# 构建应用程序
RUN mvn clean package -DskipTests

# 第二阶段：运行阶段
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 从构建阶段复制生成的 JAR 文件到运行阶段
COPY --from=build /app/target/*.jar ./your-app.jar

# 暴露应用程序的端口（假设你的应用程序运行在8080端口）
EXPOSE 8080

# 运行应用程序
CMD ["java", "-jar", "your-app.jar"]
