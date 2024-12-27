package com.example.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizDto {

    private Integer id;
    private String question;
    private Boolean answer;
    private String author;

    public QuizDto() {}

    public QuizDto(String question, boolean answer, String author) {
        this.question = question;
        this.answer = answer;
        this.author = author;
    }



}