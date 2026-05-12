# Campus Task Board API

This project is a Spring Boot REST API for managing tasks. It supports creating, reading, updating, and deleting tasks, along with input validation.

## Technologies Used
- Java 21
- Spring Boot
- Maven
- Lombok
- Validation
- H2 Database
- Postman

## How to Run
1. Open the project in IntelliJ IDEA
2. Run `CampusTaskboardApplication.java`
3. The server starts at `http://localhost:8080`

## API Endpoints

### GET all tasks
GET `/api/tasks`

### GET task by ID
GET `/api/tasks/{id}`

### POST create task
POST `/api/tasks`

Example JSON:
```json
{
  "title": "Complete Homework 5",
  "description": "Finish Spring Boot API assignment",
  "completed": false,
  "priority": "HIGH"
}