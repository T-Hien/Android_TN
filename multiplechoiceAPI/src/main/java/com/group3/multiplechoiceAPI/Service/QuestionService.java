
package com.group3.multiplechoiceAPI.Service;

import com.group3.multiplechoiceAPI.Model.Question;
import com.group3.multiplechoiceAPI.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getAllQuestion(){
        return questionRepository.findAll();
    }

    public List<Question> getAllQuestionsByTopicSetID(Long topicSet) {
        return questionRepository.findAllQuestionsByTopicSetID(topicSet);
    }
    public List<Question> getAllQuestionsByTopicSetIDAndLevel(Long topicSetID,int level) {
        return questionRepository.findAllQuestionsByTopicSetIDAndLevel(topicSetID,level);
    }
    public Question getQuestionByID(Long questionID) {
        return questionRepository.findById(questionID).get();
    }
    public List<Integer> checkLevel(Long topicSetID) {
        return questionRepository.checkLevel(topicSetID);
    }

}
