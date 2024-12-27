package com.example.quiz.mapper;

import com.example.quiz.dto.QuizDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface QuizMapper {
    //리턴타입: sql문이 실행된 결과로 받을 타입
    //메서드 이름: mapper.xml에 있는 id와 동일하게 작성..
    //(매개변수): parameterType에 전달할 값을 작성..
    int addQuiz(QuizDto dto);

    List<QuizDto> listAll();

    QuizDto findQuiz(int id);

    int changeQuiz(QuizDto dto);

    int removeQuiz(int id);

    QuizDto randomQuiz();

    int removeAll();

    Boolean playQuiz(Map map);
}
