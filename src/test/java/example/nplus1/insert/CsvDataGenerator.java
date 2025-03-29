package example.nplus1.insert;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CsvDataGenerator {

    static final int TOTAL_POSTS = 1000;
    static final int COMMENTS_PER_POST = 10;
    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws IOException {
        generatePostsCsv("posts.csv");
        generateCommentsCsv("comments.csv");
    }

    private static void generatePostsCsv(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // 헤더
            writer.write("id,title,content,created_at,updated_at");
            writer.newLine();

            LocalDateTime now = LocalDateTime.now();
            String nowStr = FORMATTER.format(now);

            for (int i = 1; i <= TOTAL_POSTS; i++) {
                writer.write(String.format("%d,Post Title %d,Post Content %d,%s,%s",
                        i, i, i, nowStr, nowStr));
                writer.newLine();

                if (i % 10_000 == 0) {
                    System.out.println("Written posts up to: " + i);
                }
            }
        }
    }

    private static void generateCommentsCsv(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // 헤더
            writer.write("id,post_id,content,created_at,updated_at");
            writer.newLine();

            LocalDateTime now = LocalDateTime.now();
            String nowStr = FORMATTER.format(now);

            long commentId = 1;

            for (int postId = 1; postId <= TOTAL_POSTS; postId++) {
                for (int j = 1; j <= COMMENTS_PER_POST; j++) {
                    writer.write(String.format("%d,%d,Comment %d on Post %d,%s,%s",
                            commentId++, postId, j, postId, nowStr, nowStr));
                    writer.newLine();
                }

                if (postId % 10_000 == 0) {
                    System.out.println("Written comments for posts up to: " + postId);
                }
            }
        }
    }
}
