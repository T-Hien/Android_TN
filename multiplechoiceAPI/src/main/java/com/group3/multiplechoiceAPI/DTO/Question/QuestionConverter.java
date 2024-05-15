package com.group3.multiplechoiceAPI.DTO.Question;

import com.group3.multiplechoiceAPI.Model.Question;
import com.group3.multiplechoiceAPI.Model.Topic_Set;
import com.group3.multiplechoiceAPI.Service.TopicSetService;

public class QuestionConverter {
    private final TopicSetService topicSetService;

    public QuestionConverter(TopicSetService topicSetService) {
        this.topicSetService = topicSetService;
    }

    public static QuestionDTO toDTO(Question question) {
        if (question == null) return null;

        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestionID(question.getQuestionID());
        questionDTO.setQuestionContent(question.getQuestionContent());
        questionDTO.setAnswer(question.getAnswer());
        questionDTO.setTopicSetID(question.getTopicSet().getTopicSetID());
        questionDTO.setLevel(question.getLevel());
        return questionDTO;
    }

    public Question toEntity(QuestionDTO questionDTO) {
        if (questionDTO == null) return null;

        Question question = new Question();
        question.setAnswer(question.getAnswer());
        question.setQuestionID(questionDTO.getQuestionID());
        question.setQuestionContent(questionDTO.getQuestionContent());
        Topic_Set topicSet = topicSetService.getTopicSetByID(questionDTO.getTopicSetID());
        question.setTopicSet(topicSet);
        return question;
    }
}
