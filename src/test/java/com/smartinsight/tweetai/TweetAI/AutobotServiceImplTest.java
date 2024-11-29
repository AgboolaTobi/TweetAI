package com.smartinsight.tweetai.TweetAI;

import com.smartinsight.tweetai.TweetAI.data.models.Autobot;
import com.smartinsight.tweetai.TweetAI.data.models.Post;
import com.smartinsight.tweetai.TweetAI.data.models.User;
import com.smartinsight.tweetai.TweetAI.data.repositories.AutobotRepository;
import com.smartinsight.tweetai.TweetAI.data.repositories.PostRepository;
import com.smartinsight.tweetai.TweetAI.services.AutobotServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AutobotServiceImplTest {

    @MockBean
    private AutobotRepository autobotRepository;

    @Autowired
    private AutobotServiceImpl autobotService;


    @Test
    public void testGenerateAutobots() {
        // Mock RestTemplate and repository save calls
        autobotService.generateAutobots();
        verify(autobotRepository, times(20)).save(any(Autobot.class));
    }


}
