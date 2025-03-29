package example.nplus1;

import example.nplus1.entity.Comment;

import java.time.LocalDateTime;

public record CommentDto(
        long id,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CommentDto of(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
