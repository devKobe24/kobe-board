package kobe.board.comment.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.client.RestClient;

public class CommentApiTest {
	RestClient restClient = RestClient.create("http://localhost:9001");

	@Getter
	@AllArgsConstructor
	public class CommentCreateRequest {
		private Long articleId;
		private String content;
		private Long parentCommentId;
		private Long writerId;
	}
}
