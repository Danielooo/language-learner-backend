package org.novi.languagelearner.dtos;

public class AnswerValidationResponseDTO {

    private boolean isCorrect;
    private String feedback;

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
