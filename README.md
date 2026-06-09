# Student Network Platform - Java Spring Boot

A RESTful backend for a student-focused social networking platform. Built with Spring Boot 3,
Spring Security + JWT, Spring Data JPA, and H2 (swappable for PostgreSQL).

---

## Tech Stack

| Layer        | Technology                            |
|--------------|---------------------------------------|
| Framework    | Spring Boot 3.2                       |
| Security     | Spring Security + JWT (jjwt 0.12)    |
| Database     | H2 In-Memory (dev) / PostgreSQL (prod)|
| ORM          | Spring Data JPA + Hibernate           |
| Validation   | Jakarta Validation (Bean Validation)  |
| Build        | Maven 3.8+                            |
| Java         | Java 17+                              |

---

## Quick Start

### Prerequisites
- Java 17+ (`java -version`)
- Maven 3.8+ (`mvn -version`)

### Run
```bash
mvn spring-boot:run
```

The app starts at **http://localhost:8080**
H2 console: **http://localhost:8080/h2-console** (JDBC URL: `jdbc:h2:mem:studentnetdb`)

### Seeded Test Accounts
```
alice@example.com / password123   (IIT Madras - CS, 3rd year)
bob@example.com   / password123   (NIT Trichy - ECE, 4th year)
carol@example.com / password123   (Anna University - IT, 2nd year)
```

---

## API Reference

All protected endpoints require:
```
Authorization: Bearer <token>
```

### AUTH

| Method | Endpoint           | Body                                          | Auth? |
|--------|--------------------|-----------------------------------------------|-------|
| POST   | /api/auth/register | fullName, email, password, university, dept   | No    |
| POST   | /api/auth/login    | email, password                               | No    |

**Register example:**
```json
POST /api/auth/register
{
  "fullName": "Ravi Kumar",
  "email": "ravi@example.com",
  "password": "securepass",
  "university": "PSG Tech",
  "department": "CSE",
  "yearOfStudy": "2nd"
}
```

**Response:**
```json
{
  "token": "eyJhbGci...",
  "type": "Bearer",
  "userId": 4,
  "fullName": "Ravi Kumar",
  "email": "ravi@example.com",
  "role": "STUDENT"
}
```

---

### USERS & PROFILES

| Method | Endpoint                                    | Description                      | Auth? |
|--------|---------------------------------------------|----------------------------------|-------|
| GET    | /api/users/me                               | Get your own profile             | Yes   |
| PUT    | /api/users/me                               | Update your profile              | Yes   |
| GET    | /api/users/{userId}                         | Get any user's profile           | Yes   |
| GET    | /api/users/search?q={query}                 | Search by name/university/skills | Yes   |
| GET    | /api/users/{userId}/connections             | Get user's connections           | Yes   |
| POST   | /api/users/{targetUserId}/connect           | Send connection request          | Yes   |
| GET    | /api/users/me/connection-requests           | View pending requests            | Yes   |
| PUT    | /api/users/me/connection-requests/{id}?accept=true | Accept/reject request    | Yes   |
| DELETE | /api/users/me/connections/{targetUserId}    | Remove a connection              | Yes   |

**Update profile example:**
```json
PUT /api/users/me
{
  "bio": "Final year CS student. Interested in distributed systems.",
  "skills": "Java, Spring Boot, Kafka, Docker, Kubernetes",
  "linkedinUrl": "https://linkedin.com/in/ravikumar",
  "githubUrl": "https://github.com/ravikumar"
}
```

---

### POSTS & FEED

| Method | Endpoint                         | Description                      | Auth? |
|--------|----------------------------------|----------------------------------|-------|
| POST   | /api/posts                       | Create a post                    | Yes   |
| GET    | /api/posts/feed?page=0&size=20   | Your personalized feed           | Yes   |
| GET    | /api/posts?page=0&size=20        | All posts (discovery)            | Yes   |
| GET    | /api/posts/type/{type}           | Filter: GENERAL/RESOURCE/ACHIEVE | Yes   |
| GET    | /api/posts/user/{userId}         | Posts by a specific user         | Yes   |
| GET    | /api/posts/search?q={query}      | Search posts                     | Yes   |
| POST   | /api/posts/{postId}/like         | Like a post                      | Yes   |
| DELETE | /api/posts/{postId}              | Delete your post                 | Yes   |
| POST   | /api/posts/{postId}/comments     | Add a comment                    | Yes   |
| GET    | /api/posts/{postId}/comments     | Get comments on a post           | Yes   |

**Create resource-sharing post:**
```json
POST /api/posts
{
  "content": "Best free resource to learn System Design from scratch.",
  "type": "RESOURCE",
  "resourceUrl": "https://github.com/donnemartin/system-design-primer",
  "resourceTitle": "System Design Primer",
  "tags": "System Design, Backend, Interviews"
}
```

---

### OPPORTUNITIES

| Method | Endpoint                              | Description                               | Auth? |
|--------|---------------------------------------|-------------------------------------------|-------|
| POST   | /api/opportunities                    | Post an opportunity                       | Yes   |
| GET    | /api/opportunities?page=0&size=20     | Browse active opportunities               | Yes   |
| GET    | /api/opportunities/type/{type}        | Filter by type                            | Yes   |
| GET    | /api/opportunities/search?q={query}   | Search by skill/company/title             | Yes   |
| PUT    | /api/opportunities/{id}/close         | Close your opportunity                    | Yes   |

**Opportunity types:** `INTERNSHIP`, `PROJECT`, `RESEARCH`, `JOB`, `HACKATHON`, `SCHOLARSHIP`

**Post an internship:**
```json
POST /api/opportunities
{
  "title": "Android Developer Intern",
  "description": "Build features for our 100K+ user app. Real impact, real ownership.",
  "type": "INTERNSHIP",
  "company": "Zoho Corporation",
  "location": "Chennai",
  "isPaid": true,
  "stipend": "₹25,000/month",
  "requiredSkills": "Kotlin, Android SDK, REST APIs",
  "applyUrl": "https://careers.zoho.com",
  "deadline": "2025-08-01"
}
```

---

## Project Structure

```
src/main/java/com/studentnet/
├── StudentNetworkApplication.java    # Entry point
├── model/
│   ├── User.java                     # User entity with role, profile fields
│   ├── Post.java                     # Posts with type (GENERAL/RESOURCE/ACHIEVEMENT)
│   ├── Comment.java                  # Post comments
│   ├── ConnectionRequest.java        # PENDING/ACCEPTED/REJECTED requests
│   └── Opportunity.java              # Internships, research, hackathons, etc.
├── repository/
│   ├── UserRepository.java           # Search queries, findBySkill, etc.
│   ├── PostRepository.java           # Feed query, type filter, search
│   ├── CommentRepository.java
│   ├── ConnectionRequestRepository.java
│   └── OpportunityRepository.java
├── service/
│   ├── AuthService.java              # Register + login
│   ├── UserService.java              # Profile, connections, search
│   ├── PostService.java              # CRUD, feed, like, comment
│   └── OpportunityService.java       # Post, browse, search opportunities
├── controller/
│   ├── AuthController.java
│   ├── UserController.java
│   ├── PostController.java
│   └── OpportunityController.java
├── dto/
│   ├── AuthDto.java                  # Request/response for auth
│   ├── UserDto.java                  # Profile + summary DTOs
│   ├── PostDto.java                  # Post + comment DTOs
│   └── OpportunityDto.java
├── security/
│   ├── JwtUtils.java                 # Token generation + validation
│   ├── JwtAuthFilter.java            # Per-request token check
│   └── UserDetailsServiceImpl.java   # Spring Security user loading
└── config/
    ├── SecurityConfig.java           # Route-level auth rules
    ├── GlobalExceptionHandler.java   # Clean JSON error responses
    └── DataInitializer.java          # Seeds 3 users + posts + opportunities
```

---

## Switching to PostgreSQL (Production)

1. Add dependency to `pom.xml`:
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

2. Replace in `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/studentnetdb
spring.datasource.username=your_user
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```

---

## What's NOT in this version (deliberate scope decisions)

| Feature                  | Why excluded                                           |
|--------------------------|--------------------------------------------------------|
| Real-time messaging      | Needs WebSocket/STOMP — separate concern               |
| File/image uploads       | Needs S3 or Cloudinary integration                     |
| Email notifications      | Needs SMTP config — add Spring Mail later              |
| Groups/communities       | Phase 2 feature — data model is ready to extend        |
| OAuth (Google login)     | Add Spring Security OAuth2 client when needed          |
| Unlike / undo actions    | Simple to add — intentionally kept minimal             |

---

## Next Steps (in priority order)

1. **Add PostgreSQL** and deploy to Railway / Render (free tier)
2. **Add Swagger/OpenAPI** (`springdoc-openapi-ui`) for interactive docs
3. **Add messaging** with WebSocket + STOMP
4. **Add email notifications** with Spring Mail
5. **Build a frontend** with React or Angular consuming these APIs
