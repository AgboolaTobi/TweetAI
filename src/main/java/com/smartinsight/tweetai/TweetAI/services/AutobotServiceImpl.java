package com.smartinsight.tweetai.TweetAI.services;

import com.smartinsight.tweetai.TweetAI.data.models.Autobot;
import com.smartinsight.tweetai.TweetAI.data.models.Comment;
import com.smartinsight.tweetai.TweetAI.data.models.Post;
import com.smartinsight.tweetai.TweetAI.data.models.User;
import com.smartinsight.tweetai.TweetAI.data.repositories.AutobotRepository;
import com.smartinsight.tweetai.TweetAI.data.repositories.CommentRepository;
import com.smartinsight.tweetai.TweetAI.data.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class AutobotServiceImpl implements AutobotService {

    @Autowired
    private AutobotRepository autobotRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    private final String USER_API_URL = "https://jsonplaceholder.typicode.com/users";
    private final String POST_API_URL = "https://jsonplaceholder.typicode.com/posts";
    private final String COMMENT_API_URL = "https://jsonplaceholder.typicode.com/comments";

    @Override
    @Scheduled(fixedRate = 3600000) // Run every hour
    public void generateAutobots() {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<User[]> userResponse = restTemplate.getForEntity(USER_API_URL, User[].class);
        User[] users = userResponse.getBody();

        if (users != null) {
            for (User user : users) {
                Autobot autobot = new Autobot();
                autobot.setName(user.getName());
                autobot.setEmail(user.getEmail());
                autobot.setUsername(user.getUsername());

                List<Post> posts = fetchPostsForAutobot(user.getId(), restTemplate);
                autobot.setPosts(posts);


                autobotRepository.save(autobot);
            }
        }
    }

    private List<Post> fetchPostsForAutobot(Long userId, RestTemplate restTemplate) {
        List<Post> posts = new ArrayList<>();
        ResponseEntity<Post[]> postResponse = restTemplate.getForEntity(POST_API_URL + "?userId=" + userId, Post[].class);
        Post[] userPosts = postResponse.getBody();

        if (userPosts != null) {
            for (Post post : userPosts) {
                Post newPost = new Post();
                newPost.setTitle(post.getTitle());
                newPost.setBody(post.getBody());

                List<Comment> comments = fetchCommentsForPost(post.getId(), restTemplate);
                newPost.setComments(comments);

                posts.add(newPost);
            }
        }
        return posts;
    }

    @Override
    public List<Comment> fetchCommentsForPost(Long postId, RestTemplate restTemplate) {
        List<Comment> comments = new ArrayList<>();
        ResponseEntity<Comment[]> commentResponse = restTemplate.getForEntity(COMMENT_API_URL + "?postId=" + postId, Comment[].class);
        Comment[] postComments = commentResponse.getBody();

        if (postComments != null) {
            for (Comment comment : postComments) {
                Comment newComment = new Comment();
                newComment.setBody(comment.getBody());
                comments.add(newComment);
            }
        }
        return comments;
    }

    @Override
    public List<Autobot> getAllAutobots(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return autobotRepository.findAll(pageable).getContent();
    }

    @Override
    public List<Post> getPostsByAutobot(Long autobotId, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return postRepository.findByAutobotId(autobotId, pageable).getContent();
    }

    @Override
    public List<Comment> getCommentsByPost(Long postId, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return commentRepository.findByPostId(postId, pageable).getContent();
    }
}
