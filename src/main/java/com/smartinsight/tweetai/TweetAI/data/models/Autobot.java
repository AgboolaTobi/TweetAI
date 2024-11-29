package com.smartinsight.tweetai.TweetAI.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Autobot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String username;

    @OneToMany(mappedBy = "autobot", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();
}
