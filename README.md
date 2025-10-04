# Financial App Starter (Hexagonal + SOLID + Spring Boot)

A recruiter‑friendly starter showcasing clean architecture (Hexagonal), SOLID, and common enterprise practices for a financial domain.

## Tech
- Java 21, Spring Boot 3.x, Web, Validation, Security (JWT-ready), Data JPA
- PostgreSQL + Flyway migrations
- OpenAPI (Swagger UI)
- GitHub Actions CI
- Dockerfile + docker-compose for local dev

## Domain (initial scope)
- Accounts: create, deposit, withdraw
- Transactions: append-only log

## Architecture
- `domain`: entities & business rules (pure Java)
- `application`: use cases (ports in) depend on domain; no Spring
- `infrastructure`: adapters (web, persistence, security, config)
- `shared`: cross-cutting concerns

## Quick start

### 1) Run with Docker Compose (recommended)
```bash
docker compose up --build
```
- App: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui/index.html

### 2) Run locally (requires Postgres)
```bash
export DB_URL="jdbc:postgresql://localhost:5432/finapp"
export DB_USERNAME="finapp"
export DB_PASSWORD="finapp"
mvn spring-boot:run
```

### Sample API calls
```bash
# Create account
curl -X POST localhost:8080/api/v1/accounts -H "Content-Type: application/json" -d '{"ownerId":"user-123"}' -i

# Deposit
curl -X POST localhost:8080/api/v1/accounts/{id}/deposit -H "Content-Type: application/json" -d '{"amount": "100.00"}' -i

# Withdraw
curl -X POST localhost:8080/api/v1/accounts/{id}/withdraw -H "Content-Type: application/json" -d '{"amount": "50.00"}' -i
```

## Deploy ideas

### Render
- Create a new Web Service from your GitHub repo using the Dockerfile.
- Set env vars: `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` (use your hosted DB).
- For Render PostgreSQL: use Render's internal connection string.
- For MongoDB Atlas (optional future): add a new persistence adapter using Spring Data Mongo.

### GitHub Actions
Provided workflow runs `mvn verify` + tests on PRs and pushes.

## Security
- Stateless Spring Security with space for JWT.
- Lock down mutating endpoints; expose `/actuator/health` and Swagger publicly.
- Add rate limiting, auditing, input validation, and secrets via env vars in future iterations.

## Next steps
- Add JWT authentication & roles (ADMIN/USER).
- Add query endpoints (get balance, list transactions).
- Introduce DTO mappers, pagination, and HATEOAS if needed.
- Add domain-level invariants and richer value objects (Money, AccountId).
- Add integration tests with Testcontainers.
- Add monitoring (Micrometer/Prometheus) and tracing (OpenTelemetry).
