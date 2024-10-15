```mermaid
sequenceDiagram
    participant User
    participant SecurityContextHolder
    participant UserInputAnswerController
    participant UserInputAnswerMapper
    participant UserInputAnswerService
    participant UserService
    participant ExerciseService
    participant UserInputAnswerRepository
    
    
    User ->>+ UserInputAnswerController: POST /answer/user/submit
    UserInputAnswerController ->>+ SecurityContextHolder: getContext().getAuthentication()
    SecurityContextHolder -->>- UserInputAnswerController: Authentication
    UserInputAnswerController ->>+ UserInputAnswerService: submitAnswer(userInputAnswerRequestDTO)
    UserInputAnswerService ->>+ ExerciseService: getExerciseById(exerciseId)
    ExerciseService -->>- UserInputAnswerService: Exercise
    UserInputAnswerService ->>+ UserService: getUserByUserName(userName)
    UserService -->>- UserInputAnswerService: User
    UserInputAnswerService ->>+ UserInputAnswerRepository: save(userInputAnswer)
    UserInputAnswerRepository -->>- UserInputAnswerService: UserInputAnswer
    UserInputAnswerService ->>+ UserInputAnswerMapper: mapToAnswerSubmitResponseDTO(exerciseAnswer, correct, feedback)
    UserInputAnswerMapper -->>- UserInputAnswerService: UserInputAnswerFeedbackResponseDTO
    UserInputAnswerService -->>- UserInputAnswerController: UserInputAnswerFeedbackResponseDTO
    UserInputAnswerController -->>- User: ResponseEntity.ok(body)

```