# Library Management System - Gateway

An API Gateway built with Spring Cloud Gateway that acts as a single entry point for routing requests to backend microservices.

## 🚀 Overview

The Gateway service provides:
- **Request Routing**: Routes API requests to appropriate backend services
- **CORS Handling**: Centralized CORS configuration
- **Load Balancing**: Can be extended for load balancing (future)
- **API Aggregation**: Single entry point for all API calls

## 📋 Architecture

```
Frontend (Port 3000/3001)
    ↓
Gateway (Port 8080)
    ↓
Backend API (Port 8081)
```

## 📋 Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- **Backend API** running on port 8081

## 🛠️ Installation & Setup

### 1. Clone the Repository

```bash
git clone <your-gateway-repo-url>
cd LibraryMS_gateway
```

### 2. Configure Backend URL

Edit `src/main/resources/application.properties`:

```properties
backend.service.url=http://localhost:8081
```

### 3. Build the Project

```bash
mvn clean install
```

### 4. Run the Gateway

**Option 1: Using Maven**
```bash
mvn spring-boot:run
```

**Option 2: Using IDE**
- Run `GatewayApplication.java` from your IDE

**Option 3: Using JAR**
```bash
mvn clean package
java -jar target/gateway-0.0.1-SNAPSHOT.jar
```

### 5. Verify it's Running

```bash
curl http://localhost:8080/api/gateway/health
```

Expected response: `Gateway is running on port 8080`

## 🔧 Configuration

### Port Configuration

Default port is `8080`. To change it, edit `application.properties`:

```properties
server.port=8080
```

### Route Configuration

The gateway routes requests from `/api/backend/**` to the backend service:

```properties
# Route all /api/backend/** requests to backend service
spring.cloud.gateway.routes[0].id=backend-route
spring.cloud.gateway.routes[0].uri=http://localhost:8081
spring.cloud.gateway.routes[0].predicates[0]=Path=/api/backend/**
spring.cloud.gateway.routes[0].filters[0]=StripPrefix=2
```

**Routing Logic:**
- Request: `http://localhost:8080/api/backend/api/books`
- Strips prefix: `/api/backend` → `/api/books`
- Forwards to: `http://localhost:8081/api/books`

### CORS Configuration

The gateway is configured to allow requests from:
- `http://localhost:3000`
- `http://localhost:3001`

To modify CORS settings, edit:
- `src/main/java/com/imashi/lms/gateway/config/CorsConfig.java`
- `src/main/resources/application.properties`

## 📁 Project Structure

```
LibraryMS_gateway/
├── src/main/java/com/imashi/lms/gateway/
│   ├── GatewayApplication.java          # Main application class
│   ├── config/
│   │   ├── GatewayConfig.java           # Route configuration
│   │   └── CorsConfig.java              # CORS configuration
│   └── controller/
│       └── GatewayHealthController.java # Health check endpoint
├── src/main/resources/
│   └── application.properties           # Gateway configuration
└── pom.xml                              # Maven dependencies

```

## 🔌 API Endpoints

### Health Check

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/gateway/health` | Gateway health status |

### Route Endpoints

All backend API endpoints are accessible through the gateway:

**Pattern**: `http://localhost:8080/api/backend/{backend-endpoint}`

**Examples**:
- `GET http://localhost:8080/api/backend/api/health` → Backend health check
- `GET http://localhost:8080/api/backend/api/books` → Get books
- `POST http://localhost:8080/api/backend/api/auth/login` → User login

## 🔄 Using the Gateway

### Option 1: Frontend via Gateway (Recommended for Production)

Update frontend `.env.local`:
```env
NEXT_PUBLIC_API_URL=http://localhost:8080/api/backend
```

Frontend calls: `http://localhost:8080/api/backend/api/books`
Gateway forwards to: `http://localhost:8081/api/books`

### Option 2: Direct Backend Connection (Current Setup)

Frontend connects directly to backend:
```env
NEXT_PUBLIC_API_URL=http://localhost:8081
```

## 📦 Dependencies

- **Spring Cloud Gateway** - API Gateway framework
- **Spring Boot 3.3.3** - Core framework
- **Lombok** - Code generation

## 🐛 Troubleshooting

### Gateway Not Starting

**Error**: Port 8080 already in use

**Solution**:
1. Change port in `application.properties`:
   ```properties
   server.port=8082
   ```
2. Or kill the process:
   ```bash
   # Windows
   netstat -ano | findstr :8080
   taskkill /PID <PID> /F
   ```

### Backend Connection Failed

**Error**: Connection refused to backend

**Solution**:
1. Ensure backend is running on port 8081
2. Check `backend.service.url` in `application.properties`
3. Verify backend health: `curl http://localhost:8081/api/health`

### CORS Errors

**Error**: CORS policy blocked

**Solution**:
1. Check `CorsConfig.java` includes your frontend port
2. Update `application.properties` CORS settings
3. Restart gateway after changes

### Route Not Working

**Error**: 404 Not Found

**Solution**:
1. Verify route pattern in `GatewayConfig.java`
2. Check prefix stripping configuration
3. Test backend endpoint directly first
4. Check gateway logs for routing details

## 🔒 Security Considerations

1. **Production**: Use HTTPS for all connections
2. **Authentication**: Implement API key or OAuth2
3. **Rate Limiting**: Add rate limiting for production
4. **Monitoring**: Add logging and monitoring
5. **Load Balancing**: Configure load balancing for multiple backend instances

## 🚀 Production Deployment

For production deployment:

1. Configure HTTPS
2. Set up service discovery (Eureka, Consul, etc.)
3. Add rate limiting
4. Configure load balancing
5. Set up monitoring and logging
6. Use environment variables for configuration

## 📝 When to Use Gateway

### Use Gateway When:
- Multiple backend services
- Need centralized CORS/security
- Load balancing required
- API versioning needed
- Request/response transformation needed

### Direct Connection When:
- Single backend service
- Simple architecture
- Development/testing
- No routing complexity needed

## 🔄 Route Examples

### Current Setup

| Frontend Request | Gateway Routes To |
|-----------------|-------------------|
| `/api/backend/api/books` | `http://localhost:8081/api/books` |
| `/api/backend/api/auth/login` | `http://localhost:8081/api/auth/login` |
| `/api/backend/api/health` | `http://localhost:8081/api/health` |

## 📄 License

This project is part of an internship assignment.

## 👤 Author

Developed as part of Library Management System internship project.

## 📞 Support

For issues or questions, please check the backend documentation or open an issue on GitHub.

