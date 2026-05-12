# Homework 6: Spring Data JPA and H2 Database

## Description

This homework updates the Task Board application to use Spring Data JPA and an H2 in-memory database. Tasks are now stored in a database instead of an in-memory list.

## New Features

- Task model converted into a JPA entity
- Spring Data JPA repository added
- H2 database configured
- H2 console enabled
- Filtering by completed, incomplete, and priority
- Search by keyword
- Pagination and sorting support

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/tasks` | Get all tasks |
| GET | `/api/tasks/{id}` | Get task by ID |
| POST | `/api/tasks` | Create a new task |
| PUT | `/api/tasks/{id}` | Update a task |
| DELETE | `/api/tasks/{id}` | Delete a task |
| GET | `/api/tasks/completed` | Get completed tasks |
| GET | `/api/tasks/incomplete` | Get incomplete tasks |
| GET | `/api/tasks/priority/{priority}` | Get tasks by priority |
| GET | `/api/tasks/search?keyword=homework` | Search tasks |
| GET | `/api/tasks/paginated?page=0&size=5&sortBy=title` | Get paginated tasks |

## H2 Console

URL:

`http://localhost:8080/h2-console`

Connection settings:

```txt
JDBC URL: jdbc:h2:mem:taskboarddb
Username: sa
Password: leave empty