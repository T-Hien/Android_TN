package com.group3.multiplechoiceAPI.Repository;

import com.group3.multiplechoiceAPI.Model.Topic;
import com.group3.multiplechoiceAPI.Model.Topic_Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicSetRepository extends JpaRepository<Topic_Set, Long> {
    @Query("SELECT q FROM Topic_Set q WHERE q.topic.topicID = :topicID")
    List<Topic_Set> findAllTopicSetByTopicID(@Param("topicID") Long topicID);
}
