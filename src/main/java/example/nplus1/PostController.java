package example.nplus1;

import example.nplus1.entity.Post;
import example.nplus1.repository.PostRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;

    @GetMapping
    public String test() {
        List<Post> posts = postRepository.findAll();
        List<PostDto> dto = posts.stream().map(PostDto::of).toList();
        System.out.println("total post: " + dto.size());
        return "ok";
    }

    @GetMapping("/fetch")
    public String fetchJoin() {
        List<Post> posts = postRepository.findAllPost();
        List<PostDto> dto = posts.stream().map(PostDto::of).toList();
        System.out.println("total post: " + dto.size());
        return "ok";
    }

    @GetMapping("/test")
    public String test1() {
        return "ok";
    }
}
