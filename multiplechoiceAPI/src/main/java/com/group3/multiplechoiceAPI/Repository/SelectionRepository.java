package com.group3.multiplechoiceAPI.Repository;

import com.group3.multiplechoiceAPI.Model.Question;
import com.group3.multiplechoiceAPI.Model.Selection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SelectionRepository extends JpaRepository<Selection,Long> {

    @Query(value = "SELECT q FROM Selection q WHERE q.question.questionID= :questionID")
    List<Selection> findAllSelectionsByQuestionID(Long questionID);
}

