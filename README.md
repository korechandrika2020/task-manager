# Task Manager API

A simple REST API built with Java 17, Spring Boot, Spring Data JPA and MySQL.

## Endpoints
| Method | URL | Description |
|---|---|---|
| GET | /api/tasks | List all tasks |
| GET | /api/tasks/{id} | Get one task |
| POST | /api/tasks | Create a task |
| PUT | /api/tasks/{id} | Update a task |
| DELETE | /api/tasks/{id} | Delete a task |

## How to run
1. Create a MySQL database named `taskdb`.
2. Set the `DB_PASSWORD` environment variable to your MySQL password.
3. Run `TaskManagerApplication`.