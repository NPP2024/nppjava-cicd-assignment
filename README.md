# Task API — CI/CD Assignment

A small Spring Boot REST API for managing a to-do list. The application is
already written and tested. **Your job is to build the CI/CD pipeline around
it** using GitHub Actions.

Read [`ASSIGNMENT.md`](ASSIGNMENT.md) for the tasks and how you'll be graded.

## What's in here

```
src/main/java/...        The application (REST controller, service, model)
src/test/java/...         JUnit 5 tests (unit + web-layer)
pom.xml                   Maven build: Spring Boot, JaCoCo, Checkstyle
Dockerfile                Multi-stage build for a runnable container image
checkstyle.xml            The lint ruleset
.github/workflows/ci.yml  ← THE FILE YOU EDIT. Starts minimal; you grow it.
```

## Run it locally

Requires JDK 17 and Maven (or use the bundled `./mvnw` if your instructor
added one).

```bash
mvn verify              # compile, run tests, produce coverage report
mvn spring-boot:run     # start the API on http://localhost:8080
```

Coverage report after `mvn verify`: open `target/site/jacoco/index.html`.

## Try the API

```bash
curl -X POST localhost:8080/api/tasks \
     -H 'Content-Type: application/json' \
     -d '{"title":"learn ci/cd"}'

curl localhost:8080/api/tasks
curl -X POST localhost:8080/api/tasks/1/done
```

## The endpoints

| Method | Path                  | Purpose                  |
|--------|-----------------------|--------------------------|
| GET    | `/api/tasks`          | List all tasks           |
| GET    | `/api/tasks/{id}`     | Get one task (404 if missing) |
| POST   | `/api/tasks`          | Create a task (title required) |
| POST   | `/api/tasks/{id}/done`| Mark a task done         |
| DELETE | `/api/tasks/{id}`     | Delete a task            |
