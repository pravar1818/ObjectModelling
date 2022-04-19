package com.crio.codingame.services;

import java.util.ArrayList;
import java.util.List;

import com.crio.codingame.entities.Level;
import com.crio.codingame.entities.Question;
import com.crio.codingame.repositories.IQuestionRepository;

public class QuestionService implements IQuestionService{
    private final IQuestionRepository questionRepository;
    private Integer autoIncrement = 0;

    public QuestionService(IQuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    // TODO: CRIO_TASK_MODULE_SERVICES
    // Create and store Question into the repository.

    @Override
    public Question create(String title, Level level, Integer difficultyScore) {
        autoIncrement++;
        String id = Integer.toString(autoIncrement);
        Question entity = new Question(id,title,level,difficultyScore);
        questionRepository.save(entity);
        return entity;
    }

    // TODO: CRIO_TASK_MODULE_SERVICES
    // Get All Questions if level is not specified.
    // Or
    // Get List of Question which matches the level provided.

    @Override
    public List<Question> getAllQuestionLevelWise(Level level) {
        List<Question> questionList = new ArrayList<>();
        if(level==null){
            questionList = questionRepository.findAll();
        }else{
            questionList = questionRepository.findAllQuestionLevelWise(level);
        }
        return questionList;
    }
    
}
