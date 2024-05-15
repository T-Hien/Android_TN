package com.group3.multiplechoiceAPI.Repository;

import com.group3.multiplechoiceAPI.Model.Assignment;
import com.group3.multiplechoiceAPI.Model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    @Query("SELECT q FROM Topic q WHERE q.user.username = :username")
    List<Topic> findAllTopicByUsername(@Param("username") String username);

}
