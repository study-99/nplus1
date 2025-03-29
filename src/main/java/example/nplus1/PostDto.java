package example.nplus1;

import example.nplus1.entity.Comment;
import example.nplus1.entity.Post;

import java.time.LocalDateTime;
import java.util.List;

public record PostDto(
        long id,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<CommentDto> comments
) {
    public static PostDto of(Post post) {
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getComments().stream().map(CommentDto::of).toList()
        );
    }
}
