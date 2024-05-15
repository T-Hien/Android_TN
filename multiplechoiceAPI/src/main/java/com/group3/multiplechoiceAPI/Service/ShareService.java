package com.group3.multiplechoiceAPI.Service;

import com.group3.multiplechoiceAPI.DTO.Share.Request.ShareDtoRequest;
import com.group3.multiplechoiceAPI.Model.Share;
import com.group3.multiplechoiceAPI.Model.ShareKey;
import com.group3.multiplechoiceAPI.Model.Topic_Set;
import com.group3.multiplechoiceAPI.Model.User;
import com.group3.multiplechoiceAPI.Repository.ShareRepository;
import com.group3.multiplechoiceAPI.Repository.TopicSetRepository;
import com.group3.multiplechoiceAPI.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShareService implements IShareService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicSetRepository topicSetRepository;

    @Autowired
    private ShareRepository shareRepository;

    @Transactional
    @Override
    public boolean shareTopicToUsers(Long topicId, ShareDtoRequest shareDtoRequest, String username) {

        Topic_Set topicSet = topicSetRepository.findById(topicId).orElseThrow();

        List<User> users = shareDtoRequest.getUsers().stream().map(
                item -> userRepository.findById(item.getUsername()).orElseThrow()).collect(Collectors.toList());

        for(User user: users){
            Share share = new Share();
            ShareKey sharePK = new ShareKey();
            sharePK.setUsername(user.getUsername());
            sharePK.setTopicSetID(topicSet.getTopicSetID());
            share.setUser(user);
            share.setTopicSet(topicSet);
            share.setId(sharePK);
            share.setSharedDate(new Date());
            share.setShareContent(username + " đã chia sẽ bộ đề " + topicSet.getTopicSetName() + " cho bạn");
            topicSet.getShareList().add(share);
            user.getShareList().add(share);
            shareRepository.save(share);
            userRepository.save(user);
        }

        topicSetRepository.save(topicSet);

        return true;
    }
}
