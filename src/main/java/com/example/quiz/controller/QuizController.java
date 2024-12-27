package com.example.quiz.controller;

import com.example.quiz.dto.QuizDto;
import com.example.quiz.form.QuizForm;
import com.example.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    //폼 클래스 초기화
    //클라이언트가 전달하는 값을 객체로 맵핑해주는 역할
    @ModelAttribute
    public QuizForm setUpForm(){
        QuizForm form = new QuizForm();
        form.setAnswer(true);
        return form;
    }

    @GetMapping
    public String showList(QuizForm quizForm, Model model){

        //신규 등록 설정
        quizForm.setNewQuiz(true);

        //퀴즈 목록 가져오기
        List<QuizDto> list = quizService.listAll();

        //모델에 값을 저장
        model.addAttribute("list", list);
        model.addAttribute("title","등록 폼");

        //화면으로 리턴
        return "crud";
    }

    //@Validated :  검증할 객체에 붙이는 어노테이션
    //BindingResult : 검증한 결과의 오류를 보관하는 객체
    //RedirectAttributes : 특정 속성을 리다이렉트되는 뷰에서 사용할 수 있도록 전달
    //addAttribute : 브라우저의 주소창에 보이게 url에 추가하여 정보를 전달
    //addFlashAttribute :
    @PostMapping("/insert")
    public String insert(@Validated QuizForm quizForm, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes){

        //Form클래스의 값을 Dto에 설정
        QuizDto quiz = new QuizDto();
        quiz.setQuestion(quizForm.getQuestion());
        quiz.setAnswer(quizForm.getAnswer());
        quiz.setAuthor(quizForm.getAuthor());

        //오류확인
        if(!bindingResult.hasErrors()){
            quizService.addQuiz(quiz);
            redirectAttributes.addFlashAttribute("complete","등록이 완료되었습니다.");
            return "redirect:/quiz";
        }else {
            return showList(quizForm, model);
        }



    }

    //id에 해당하는 퀴즈의 값을 폼 안에 표시
    @GetMapping("/{id}")
    public String showUpdate(QuizForm quizForm,
                             @PathVariable Integer id, //url에 저장된 값을 받아서 변수에 저장
                             Model model){

        //Quiz를 검색하여 dto에 저장
        QuizDto quiz = quizService.findQuiz(id);

        //quiz에 내용이 비지 않았으면
        //db에서 읽어온 내용(dto)를 form형태로 반환
        if(!(quiz == null)){
            quizForm = makeQuizForm(quiz);
        }

        makeUpdateModel(quizForm, model);
        return "crud";
    }

    @PostMapping("/update")
    public String update(@Validated QuizForm quizForm,
                         BindingResult BindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes){

        //form에서 받아온 내용을 db에 저장하기 위해서 dto로 변환
        QuizDto quiz = makeQuiz(quizForm);

        //만약 에러가 없으면...
        if(!BindingResult.hasErrors()){
            quizService.changeQuiz(quiz);
            redirectAttributes.addFlashAttribute("complete","변경이 완료되었습니다.");
            return "redirect:/quiz/" + quiz.getId();
        }else {
            makeUpdateModel(quizForm, model);
            return "crud";
        }
    }

    @PostMapping("/delete")
    public String delete(String id, RedirectAttributes redirectAttributes){
        //removeQuiz 서비스를 호출
        quizService.removeQuiz(Integer.parseInt(id));

        //delcomplete 라는 이름으로 메세지 전달(삭제가 완료되었습니다.)
        redirectAttributes.addFlashAttribute("delcomplete","삭제를 완료하였습니다.");

        //메인페이지로 이동
        return "redirect:/quiz";
    }

    @GetMapping("/play")
    public String showQuiz(QuizForm quizForm, Model model){
        QuizDto playQuiz=quizService.randomQuiz();

        if(!(playQuiz == null)){
            quizForm = makeQuizForm(playQuiz);
        }else{
            model.addAttribute("msg","등록된 문제가 없습니다.");
            return "play";
        }
        model.addAttribute("quizForm",quizForm);
        return "play";
    }

    @PostMapping("/check")
    public String checkQuiz(QuizForm quizForm, Boolean answer, Model model){
        Map input = new HashMap();
        input.put("id",quizForm.getId());
        input.put("answer",answer);

        if(quizService.playQuiz(input)) {
            model.addAttribute("msg", "정답입니다.");
        }else {
            model.addAttribute("msg","오답입니다.");
        }
        return "answer";
    }

    //모델을 사용하기 위한 메서드
    private void makeUpdateModel(QuizForm quizForm, Model model){
        model.addAttribute("id", quizForm.getId());
        quizForm.setNewQuiz(false);
        model.addAttribute("quizForm",quizForm);
        model.addAttribute("title","변경 폼");
    }

    private QuizDto makeQuiz(QuizForm quizForm){
        QuizDto quiz = new QuizDto();
        quiz.setId(quizForm.getId());
        quiz.setQuestion(quizForm.getQuestion());
        quiz.setAnswer(quizForm.getAnswer());
        quiz.setAuthor(quizForm.getAuthor());
        return quiz;
    }

    private QuizForm makeQuizForm(QuizDto quiz){
        QuizForm form = new QuizForm();
        form.setId(quiz.getId());
        form.setQuestion(quiz.getQuestion());
        form.setAnswer(quiz.getAnswer());
        form.setAuthor(quiz.getAuthor());
        form.setNewQuiz(false);
        return form;
    }

}
