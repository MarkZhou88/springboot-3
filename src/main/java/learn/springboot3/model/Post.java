package learn.springboot3.model;

import lombok.Data;

@Data
public class Post {
    private Integer id;
    private Integer userId;
    private String title;
    private String body;
}
