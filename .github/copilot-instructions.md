# Time Management Backend - Copilot Instructions

## Project Overview
This is a Spring Boot backend API for a time management application with MongoDB Atlas as the database. The project provides REST endpoints for user authentication, task management, category organization, and time tracking.

## Architecture
- **Framework**: Spring Boot 3.2.0
- **Database**: MongoDB Atlas
- **Authentication**: JWT (JSON Web Tokens)
- **Security**: Spring Security
- **Documentation**: OpenAPI/Swagger
- **Build Tool**: Maven

## Key Components

### Models
- **User**: User accounts with email, password, name, and preferences
- **Task**: Tasks with title, description, priority, status, due date, and category
- **Category**: Task categories with name, color, and icon
- **TimeEntry**: Time tracking entries with start/end time and duration

### Controllers
- **AuthController**: Registration and login endpoints
- **TaskController**: CRUD operations for tasks
- **CategoryController**: CRUD operations for categories  
- **TimeEntryController**: Time tracking and analytics

### Services
- **UserService**: User management and authentication
- **TaskService**: Task business logic
- **CategoryService**: Category management
- **TimeEntryService**: Time tracking logic

### Security
- **JwtTokenUtil**: JWT token generation and validation
- **JwtAuthenticationFilter**: Request authentication filter
- **SecurityConfig**: Spring Security configuration

## API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login

### Tasks
- `GET /api/tasks` - List user tasks
- `POST /api/tasks` - Create new task
- `PUT /api/tasks/{id}` - Update task
- `DELETE /api/tasks/{id}` - Delete task
- `PUT /api/tasks/{id}/complete` - Mark task as complete
- `GET /api/tasks/overdue` - Get overdue tasks
- `GET /api/tasks/today` - Get tasks due today

### Categories
- `GET /api/categories` - List user categories
- `POST /api/categories` - Create category
- `PUT /api/categories/{id}` - Update category
- `DELETE /api/categories/{id}` - Delete category

### Time Entries
- `GET /api/time-entries` - List time entries
- `POST /api/time-entries` - Create time entry
- `PUT /api/time-entries/{id}` - Update time entry
- `DELETE /api/time-entries/{id}` - Delete time entry
- `POST /api/time-entries/start` - Start timer
- `PUT /api/time-entries/{id}/stop` - Stop timer
- `GET /api/time-entries/analytics` - Get time analytics

## Development Guidelines

### Code Style
- Follow Java naming conventions (camelCase for variables/methods, PascalCase for classes)
- Use meaningful variable and method names
- Add proper JavaDoc comments for public methods
- Keep methods focused and small (single responsibility)

### Security
- All endpoints except `/api/auth/**` require JWT authentication
- User isolation: Users can only access their own data
- Input validation using Bean Validation annotations
- Password hashing with BCrypt

### Error Handling
- Use global exception handler for consistent error responses
- Return appropriate HTTP status codes
- Provide meaningful error messages
- Log errors for debugging

### Database
- Use Spring Data MongoDB repositories
- Implement proper indexing for performance
- Use @CreatedDate and @LastModifiedDate for auditing
- Follow MongoDB naming conventions

### Testing
- Write unit tests for services
- Integration tests for controllers
- Use embedded MongoDB for testing
- Test security configurations

## Configuration

### Environment Variables
```
MONGODB_URI=mongodb+srv://username:password@cluster.mongodb.net/timemanagement
MONGODB_DATABASE=timemanagement
JWT_SECRET=your-jwt-secret-key
JWT_EXPIRATION=86400000
SERVER_PORT=8080
```

### Build Commands
```bash
mvn clean compile    # Compile project
mvn test            # Run tests
mvn spring-boot:run # Run application
mvn clean package   # Build JAR
```

## Integration with Flutter App
- The Flutter app consumes these REST APIs
- CORS is enabled for cross-origin requests
- JWT tokens are passed in Authorization headers
- Response format is JSON
- Error responses follow standard format

## Monitoring and Documentation
- Swagger UI available at `/swagger-ui.html`
- Actuator endpoints for health monitoring
- Comprehensive logging for debugging
- API documentation auto-generated with OpenAPI

When working on this project:
1. Maintain consistency with existing patterns
2. Ensure proper authentication and authorization
3. Write tests for new functionality
4. Update documentation when adding new endpoints
5. Consider performance implications for database queries
6. Follow REST API best practices
