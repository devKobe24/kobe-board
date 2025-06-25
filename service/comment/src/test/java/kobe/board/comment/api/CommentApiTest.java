package kobe.board.comment.api;

import kobe.board.comment.service.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class CommentApiTest {
	RestClient restClient = RestClient.create("http://127.0.0.1:9001");

	@Test
	void create() {
		CommentResponse response1 = createComment(
			new CommentCreateRequest(
				1L,
				"My Comment 1",
				null,
				1L
			)
		);

		CommentResponse response2 = createComment(
			new CommentCreateRequest(
				1L,
				"My Comment 2",
				response1.getCommentId(),
				1L
			)
		);

		CommentResponse response3 = createComment(
			new CommentCreateRequest(
				1L,
				"My Comment 3",
				response1.getCommentId(),
				1L
			)
		);

		System.out.println("commentId=%s".formatted(response1.getCommentId()));
		System.out.println("\tcommentId=%s".formatted(response2.getCommentId()));
		System.out.println("\tcommentId=%s".formatted(response3.getCommentId()));
	}

	CommentResponse createComment(CommentCreateRequest request) {
		return restClient.post()
			.uri("/v1/comments")
			.body(request)
			.retrieve()
			.body(CommentResponse.class);

		/**
		 *commentId=196400616783863808
		 * 	commentId=196400617232654336
		 * 	commentId=196400617282985984
		 */
	}

	@Test
	void read() {
		CommentResponse response = restClient.get()
			.uri("/v1/comments/{commentId}", 196400616783863808L)
			.retrieve()
			.body(CommentResponse.class);

		System.out.println("[READ] response = " + response);
	}

	@Test
	void delete() {
		/**
		 *commentId=196400616783863808
		 * 	commentId=196400617232654336
		 * 	commentId=196400617282985984
		 */

		restClient.delete()
			.uri("/v1/comments/{commentId}", 196400617282985984L)
			.retrieve();
	}

	@Getter
	@AllArgsConstructor
	public class CommentCreateRequest {
		private Long articleId;
		private String content;
		private Long parentCommentId;
		private Long writerId;
	}
}
