package learn.springboot3.service;

import learn.springboot3.model.Post;
import org.springframework.web.service.annotation.GetExchange;
import java.util.List;

public interface JsonPlaceholderService {
    @GetExchange("/posts")
    List<Post> loadPosts();
}
