package example.nplus1;

import example.nplus1.entity.Post;
import example.nplus1.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Nplus1Test extends AbstractJpaTest {
    @Autowired
    PostRepository postRepository;

    @Test
    void test1() {
        List<Post> posts = postRepository.findAll();
        List<PostDto> dto = posts.stream().map(PostDto::of).toList();
        System.out.println("total post: " + dto.size());
    }
}
