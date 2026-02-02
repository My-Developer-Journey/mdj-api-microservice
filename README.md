# BlogApplication Microservice

Demo of a microservices ecosystem, built by migrating and extending the existing **BlogApplication** project using common **microservice design patterns**, Docker, and modern Git workflows.

---

## 1. Project Overview

This project demonstrates how a traditional blog application can be transformed into a **microservice-based architecture**.

### Key goals

- Clear separation of concerns (Auth, Blog, Media, Notification, etc.)
- Independent deployment and scalability per service
- Production-like Git workflow (branching, PR, commit rules)
- Containerized development using Docker

---

## 2. Architecture Overview

Typical services in this ecosystem:

- **auth-service**: authentication, JWT, refresh token
- **user-service**: user profile and user-related data
- **blog-service**: blog posts, comments, likes
- **media-service**: file upload, image storage (S3-compatible)
- **notification-service**: websocket / async notifications

Each service:

- Is an independent Spring Boot application
- Has its own database (where applicable)
- Communicates via REST or async messaging

---

## 3. How to Run the Project (Local)

### 3.1 Prerequisites

Make sure you have:

- Java 17+
- Maven 3.9+
- Docker & Docker Compose
- Git

---

### 3.2 Run services locally (without Docker)

From a service directory:

```bash
./mvnw spring-boot:run
```

Or using Maven:

```bash
mvn spring-boot:run
```

> ⚠️ You must configure environment variables or `application.properties` for DB connections.

---

### 3.3 Run with Docker Compose (recommended)

From the root project directory:

```bash
docker compose up -d
```

To stop:

```bash
docker compose down
```

To rebuild images:

```bash
docker compose up -d --build
```

---

## 4. Git Workflow Rules (IMPORTANT)

This repository follows a **production-like Git workflow**, even for solo development.

### 4.1 Protected Branch

#### `main`

- Represents **stable, deployable code**
- ❌ Direct push is NOT allowed
- ✅ All changes must go through **Pull Requests**

---

## 5. Branch Naming Convention

Keep all the branches after merge (no deletion).

### 5.1 Feature branches

Used for new features or modules.

```text
feature/<short-description>
```

Examples:

```text
feature/media-service
feature/auth-refresh-token
feature/notification-websocket
```

---

### 5.2 Fix branches

Used for bug fixes.

```text
fix/<short-description>
```

Examples:

```text
fix/db-connection-env
fix/jpa-entity-scan
```

---

### 5.3 Chore / Refactor / Infra branches

Used for non-functional changes or infrastructure work.

```text
chore/<description>
refactor/<description>
infra/<description>
```

Examples:

```text
chore/update-readme
refactor/auth-package-structure
infra/docker-compose
infra/k8s-helm-chart
```

---

## 6. Working Flow (Step by Step)

1. Create a new branch from `main`

```bash
git checkout -b feature/media-service
```

2. Make changes and commit
3. Push branch to remote

```bash
git push origin feature/media-service
```

4. Create a Pull Request into `main`
5. Squash & merge
6. Delete the branch after merge

---

## 7. Commit Message Convention

This project follows **Conventional Commit** style.

### Format

```text
<type>: <short description>
```

### Common types

- `feat`: new feature
- `fix`: bug fix
- `chore`: config, build, docs
- `refactor`: code refactoring (no behavior change)
- `infra`: Docker, CI/CD, Kubernetes

### Examples

```text
feat: add media service module
fix: resolve database env variable issue
chore: update README documentation
infra: add docker compose configuration
```

---

## 8. Pull Request Rules

- PR must target `main`
- PR description should clearly explain:
  - What was changed
  - Why the change is needed

- Squash merge is recommended to keep history clean

---

## 9. General Notes

- Do NOT commit `.env` or secret files
- Keep services loosely coupled
- Prefer configuration via environment variables
- Treat this repository as a **production-grade project**, not a playground

---

## 10. Future Improvements

- CI pipeline (GitHub Actions)
- Automated test execution on PR
- Helm charts for Kubernetes deployment
- Centralized logging & monitoring

---

Happy coding 🚀
