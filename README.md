# Bài Thực Hành Số 2 - JWT Authentication API

## Mô tả
Dự án thực hiện xây dựng RESTful API với JWT authentication theo yêu cầu Bài thực hành số 2.

## Yêu cầu đã đáp ứng

### ✅ Cài đặt thư viện JWT
- Sử dụng `io.jsonwebtoken:jjwt-api:0.11.5`
- Có JwtUtil và JwtService để xử lý JWT

### ✅ Dịch vụ đăng nhập
- **URL**: `POST localhost:8080/login`
- **Tham số**: 
  ```json
  {
    "username": "lap",
    "password": "123"
  }
  ```
- **Response**: JWT token

### ✅ API xác thực token
- **URL**: `POST localhost:8080/auth`
- **Header**: `Authorization: Bearer <token>`
- **Response**: Thông báo token hợp lệ hoặc lỗi

### ✅ Middleware xác thực JWT
- JwtFilter được áp dụng cho các endpoint `/hello` và `/`
- Kiểm tra token trong header Authorization

### ✅ API Hello World được bảo vệ
- **URL**: `GET localhost:8080/hello` hoặc `GET localhost:8080/`
- **Yêu cầu**: Token JWT hợp lệ trong header
- **Response**: "Hello World from Spring Boot!"

### ✅ Bảng User
- **Cấu trúc**:
  - `idUser` (INT, PRIMARY KEY)
  - `userName` (VARCHAR(255))
  - `password` (VARCHAR(255))
  - `token` (VARCHAR(255))

## Cách sử dụng

### 1. Đăng nhập để lấy token
```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"username": "lap", "password": "123"}'
```

### 2. Xác thực token
```bash
curl -X POST http://localhost:8080/auth \
  -H "Authorization: Bearer <your-token>"
```

### 3. Truy cập API Hello World (cần token)
```bash
curl -X GET http://localhost:8080/hello \
  -H "Authorization: Bearer <your-token>"
```

## Cấu hình Database
- **Database**: MySQL
- **URL**: `jdbc:mysql://localhost:3306/SOA`
- **Username**: `root`
- **Password**: `Lapmo843@`

## Chạy ứng dụng
```bash
./gradlew bootRun
```

Ứng dụng sẽ chạy trên `http://localhost:8080`
