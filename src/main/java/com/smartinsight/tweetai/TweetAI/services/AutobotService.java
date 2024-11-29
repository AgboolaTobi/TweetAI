package com.smartinsight.tweetai.TweetAI.services;

import com.smartinsight.tweetai.TweetAI.data.models.Autobot;
import com.smartinsight.tweetai.TweetAI.data.models.Comment;
import com.smartinsight.tweetai.TweetAI.data.models.Post;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public interface AutobotService {
    void generateAutobots(); // Scheduled task to create Autobots and their data

    List<Comment> fetchCommentsForPost(Long postId, RestTemplate restTemplate);

    List<Autobot> getAllAutobots(int page, int size); // Paginated retrieval

    List<Post> getPostsByAutobot(Long autobotId, int page, int size); // Retrieve posts by Autobot

    List<Comment> getCommentsByPost(Long postId, int page, int size); // Retrieve comments by Post
}
