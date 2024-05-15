package com.group3.multiplechoiceAPI.Service;

import com.group3.multiplechoiceAPI.DTO.Share.Request.SharedTopicSetResponse;
import com.group3.multiplechoiceAPI.DTO.TopicSet.*;
import com.group3.multiplechoiceAPI.Model.*;
import com.group3.multiplechoiceAPI.Repository.TopicRepository;
import com.group3.multiplechoiceAPI.Repository.TopicSetRepository;
import com.group3.multiplechoiceAPI.Repository.UserRepository;
import io.micrometer.observation.ObservationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TopicSetService {
    private final TopicSetRepository topicSetRepository;

    private final TopicRepository topicRepository;

    private final UserRepository userRepository;

    @Autowired
    public TopicSetService(TopicSetRepository topicSetRepository, TopicRepository topicRepository, UserRepository userRepository) {
        this.topicSetRepository = topicSetRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }



    public List<Topic_Set> getAllTopicSet(){
        return topicSetRepository.findAll();
    }

    public Topic_Set getTopicSetByID(Long topicSetId) {
        Optional<Topic_Set> topicSet = topicSetRepository.findById(topicSetId);
        return topicSet.orElse(null);
    }

    public List<Topic_Set> getTopicSetByTopicID(Long topicID){
        return topicSetRepository.findAllTopicSetByTopicID(topicID);
    }


    protected TopicSetResponse convertToTopicSetResponse(Topic_Set topicSet){
        TopicSetResponse topicSetResponse = new TopicSetResponse();

        TopicResponse topicResponse = new TopicResponse();
        topicResponse.setTopicCode(topicSet.getTopic().getTopicID());
        topicResponse.setTopicName(topicSet.getTopic().getTopicName());

        topicSetResponse.setTopic(topicResponse);
        topicSetResponse.setTopicSetName(topicSet.getTopicSetName());
        topicSetResponse.setTopicSetCode(topicSet.getTopicSetID());
        topicSetResponse.setCreated(topicSet.getCreated());

        List<QuestionResponse> questions = topicSet.getQuestionList().stream().map(question -> {
            List<SelectionResponse> selections = question.getSelectionList().stream().map(selection -> {
                SelectionResponse selectionResponse = new SelectionResponse();
                selectionResponse.setSelectionContent(selection.getSelectionContent());
                selectionResponse.setSelectionID(selection.getSelectionID());
                return selectionResponse;
            }).collect(Collectors.toList());

            QuestionResponse questionResponse = new QuestionResponse();
            questionResponse.setQuestionContent(question.getQuestionContent());
            questionResponse.setQuestionCode(question.getQuestionID());
            questionResponse.setAnswer(question.getAnswer());
            questionResponse.setSelections(selections);
            return questionResponse;

        }).collect(Collectors.toList());

        topicSetResponse.setQuestions(questions);

        return topicSetResponse;
    }

    @Transactional
    public TopicSetResponse createTopicSetToTopic(Long topicId, TopicSetRequest topicSet) {
        Topic topic = topicRepository.findById(topicId).orElseThrow();
        System.out.println(topicId);
        System.out.println(topic.toString());
        Topic_Set createTopicSet = new Topic_Set();
        createTopicSet.setTopic(topic);
        createTopicSet.setCreated(new Date());
        createTopicSet.setTopicSetName(topicSet.getTopicSetName());
        createTopicSet.setDuration(topicSet.getDuration());

        List<Question> questions = topicSet.getQuestions().stream().map(questionRequest -> {
            Question question = new Question();
            question.setQuestionContent(questionRequest.getQuestionContent());
            question.setAnswer(questionRequest.getAnswer());
            question.setTopicSet(createTopicSet);
            question.setLevel(questionRequest.getLevel());
            List<Selection> selections = questionRequest.getSelection().stream().map(selectionRequest -> {
                Selection selection = new Selection();
                selection.setSelectionContent(selectionRequest.getSelectionContent());
                selection.setQuestion(question);
                return selection;
            }).collect(Collectors.toList());

            question.setSelectionList(selections);
            return question;
        }).collect(Collectors.toList());

        createTopicSet.setQuestionList(questions);

        topic.getTopicSetList().add(createTopicSet);

        topic = topicRepository.save(topic);

        return convertToTopicSetResponse(topic.getTopicSetList().get(topic.getTopicSetList().size() -1 ));
    }

    public List<SharedTopicSetResponse> getShareTopicOfUer(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        List<SharedTopicSetResponse> list = user.getShareList().stream().map(item ->
                new SharedTopicSetResponse(item.getTopicSet().getTopicSetID(), item.getTopicSet().getTopicSetName(),item.getUser().getName(),item.getTopicSet().getDuration())).collect(Collectors.toList());
        return list;
    }
}
