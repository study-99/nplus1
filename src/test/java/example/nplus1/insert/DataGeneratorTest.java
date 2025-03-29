package example.nplus1.insert;

import example.nplus1.AbstractJpaTest;
import example.nplus1.entity.Comment;
import example.nplus1.entity.Post;
import example.nplus1.repository.PostRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class DataGeneratorTest extends AbstractJpaTest {
    @Autowired
    PostRepository postRepository;

    @Autowired
    EntityManager em;

    private static final int TOTAL_POSTS = 1_000_000;
    private static final int COMMENTS_PER_POST = 10;
    private static final int BATCH_SIZE = 1000;

    @Test
    void test1() {
        postRepository.findAll();
    }

    @DisplayName("post와 comment 데이터 생성")
    @Test
    void generate() {
        for (int i = 1; i <= TOTAL_POSTS; i++) {
            LocalDateTime now = LocalDateTime.now();

            Post post = new Post(
                    "post title" + i,
                    "post content" + "i",
                    now,
                    now
            );
            em.persist(post);

            for (int j = 1; j <= COMMENTS_PER_POST; j++) {
                Comment comment = new Comment(
                        post,
                        "Comment " + j + " on Post " + i,
                        now,
                        now
                );
                em.persist(comment);
            }

            if (i % BATCH_SIZE == 0) {
                em.flush();
                em.clear(); // 영속성 컨텍스트 비우기 (메모리 관리)
                System.out.println("Inserted up to post " + i);
            }
        }
    }
}
