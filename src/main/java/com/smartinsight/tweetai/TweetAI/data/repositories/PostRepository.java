package com.smartinsight.tweetai.TweetAI.data.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.smartinsight.tweetai.TweetAI.data.models.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByAutobotId(Long autobotId, Pageable pageable);

    ArrayList<Post> fetchAutoBotPostByUserId(Long id);
}

