package com.group3.multiplechoiceAPI.Repository;

import com.group3.multiplechoiceAPI.Model.Assignment;
import com.group3.multiplechoiceAPI.Model.Share;
import com.group3.multiplechoiceAPI.Model.ShareKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShareRepository extends JpaRepository<Share, ShareKey> {
}
