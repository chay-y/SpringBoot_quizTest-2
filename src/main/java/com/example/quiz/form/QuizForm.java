package com.example.quiz.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuizForm {

    private Integer id;

    @NotBlank
    private String question;

    @NotNull
    private Boolean answer;

    @NotBlank
    private String author;

    private Boolean newQuiz;


}
