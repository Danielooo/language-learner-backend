### Klassendiagram

```mermaid
classDiagram
    direction TB

    class Base {
        <<abstract>>
        +Long id
        +LocalDateTime createDate
        +LocalDateTime editDate
        +void onCreate()
        +void onEdit()
    }

    class User {
        +Long id
        +String userName
        +String firstName
        +String lastName
        +String password
        +List~Role~ roles
        +List~Group~ groups
        +boolean isExpired
        +boolean isLocked
        +boolean areCredentialsExpired
        +boolean isEnabled
        +Photo photo
        +List~UserInputAnswer~ userInputAnswers
    }

    class Group {
        +Long id
        +String groupName
        +User user
        +List~Exercise~ exercises
    }

    class Exercise {
        +Long id
        +String question
        +String answer
        +Group group
        +List~UserInputAnswer~ userInputAnswers
    }

    class Role {
        +Long id
        +String roleName
        +boolean active
        +String description
        +List~User~ users
    }

    class Photo {
        +Long id
        +String filename
        +String fileType
        +byte[] data
        +User user
    }

    class UserInputAnswer {
        +Long id
        +String userInput
        +User user
        +Exercise exercise
    }

    class SecurityConfig {
        +BCryptPasswordEncoder passwordEncoder()
        +SecurityFilterChain filterChain(HttpSecurity, JwtRequestFilter)
        +AuthenticationManager authManager(HttpSecurity, PasswordEncoder, UserDetailsService)
    }

    Base <|-- User
    Base <|-- Group
    Base <|-- Exercise
    Base <|-- Role
    Base <|-- Photo
    Base <|-- UserInputAnswer

    User  "0..*" <--> "1..*" Role
    User "1"  *--> "0-1" Photo 
    User "1" *-->   "0..*" Group
    Group "1" *--> "0..*" Exercise 
    User "1" *--> "0..*" UserInputAnswer 
    Exercise "1" *--> "0..*" UserInputAnswer 
```

## Toelichting
De applicatie is onderverdeeld in meerdere entiteiten en los de SecurityConfig. In de grafiek zie je de relaties en kardinaliteiten. Hieronder vind je een kleine toelichting op de onderdelen ter verduidelijking van de functie.

1. **SecurityConfig**:
    - Regelt de authenticatie en de authorisatie

2. **Base Entity**:
    - Een abstracte klasse die gemeenschappelijke velden en methoden biedt voor alle entiteiten, zoals `id`, `createDate` en `editDate`.

3. **User**:
   - In de User entity wordt informatie over de User opgeslagen
   - De User is ook gelinkt aan de oefendata en de antwoorden die zijn gegeven.. Group die weer Exercises bevat. Dit is gelinkt aan de User specifiek, zodat de data niet kan worden aangepast door andere Users.

4. **Group**:
    - Vertegenwoordigt een groep oefeningen (Exercises). 
    - De groep kan worden uitgebreid met meer Exercises

5. **Exercise**:
    - De oefening heeft een question en een answer.
    - De oefening moet zijn gelinkt aan een Group, als de Group verwijderd wordt, dan ook de Exercises.
    - Dit om een beter overzicht te houden en geen brei aan losse Exercises te krijgen

6. **Role**:
    - De User kan een Role hebben van User of Admin, of allebei
    - Als User kan je de Groups en Exercises maken
    - Als Admin heb je meer een beheerdersrol en kun je data verwijderen (die niet klopt, of om de database op te schonen)

7. **Photo**:
    - Een gebruiker (User en/of Admin) kan een enkele profielfoto uploaden
    - Als de gebruiker wordt verwijderd uit het systeem, wordt de Photo ook verwijderd.

8. **UserInputAnswer**:
    - Vertegenwoordigt een antwoord (input) die een User heeft gegeven op een Exercise
    - De UserInputAnswers worden als rauwe data opgeslagen, zodat de applicatie er zelf analyses op kan gaan uitvoeren