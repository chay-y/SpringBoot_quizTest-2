package com.example.quiz.service;

import com.example.quiz.dto.QuizDto;
import com.example.quiz.mapper.QuizMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QuizService {

    @Autowired
    private QuizMapper quizMapper;

    public int addQuiz(QuizDto dto){
        return quizMapper.addQuiz(dto);

    }

    public List<QuizDto> listAll(){
        return quizMapper.listAll();
    }

    public QuizDto findQuiz(int id){
        return quizMapper.findQuiz(id);
    }

    public int changeQuiz(QuizDto dto){
        return quizMapper.changeQuiz(dto);
    }

   public int removeQuiz(int id){
       return quizMapper.removeQuiz(id);
   }

   public QuizDto randomQuiz(){
        return quizMapper.randomQuiz();
   }

   public int removeALL(){
        return quizMapper.removeAll();
   }

   public Boolean playQuiz(Map map){
        return quizMapper.playQuiz(map);
   }
}
