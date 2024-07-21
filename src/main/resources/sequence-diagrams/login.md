```mermaid
sequenceDiagram
    participant User
    participant Frontend
    participant Backend
    participant Security
    User->>Frontend: Enter username and password
    Frontend->>Backend: Send login request
    Backend->>Security: Validate credentials
    Security-->>Backend: Authentication result
    Backend-->>Frontend: Response (Success/Failure)
    Frontend-->>User: Display message
```