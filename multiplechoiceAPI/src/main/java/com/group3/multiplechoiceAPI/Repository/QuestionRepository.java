
package com.group3.multiplechoiceAPI.Repository;

import com.group3.multiplechoiceAPI.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM Question q WHERE q.topicSet.topicSetID = :topicSetID")
    List<Question> findAllQuestionsByTopicSetID(@Param("topicSetID") Long topicSetID);
    @Query("SELECT q FROM Question q WHERE q.topicSet.topicSetID = :topicSetID And q.level = :level")
    List<Question> findAllQuestionsByTopicSetIDAndLevel(@Param("topicSetID") Long topicSetID,@Param("level") int level);
    //SELECT DISTINCT my_column FROM my_table;
    @Query("SELECT DISTINCT q.level FROM Question q WHERE q.topicSet.topicSetID = :topicSetID")
    List<Integer> checkLevel(@Param("topicSetID") Long topicSetID);

}
