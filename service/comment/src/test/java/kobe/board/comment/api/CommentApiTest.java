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

	@Test
	void readAll() {
		CommentPageResponse response = restClient.get()
			.uri("/v1/comments?articleId=1&page=1&pageSize=10")
			.retrieve()
			.body(CommentPageResponse.class);

		System.out.println("response.getCommentCount() = " + response.getCommentCount());
		for (CommentResponse comment : response.getComments()) {
			if (!comment.getCommentId().equals(comment.getParentCommentId())) {
				System.out.print("\t");
			}
			System.out.println("comment.getCommentId() = " +comment.getCommentId());
		}
		/**
		 * 1번 페이지 수행 결과
		 * comment.getCommentId() = 196398155207913472
		 * 	comment.getCommentId() = 196398155719618560
		 * 	comment.getCommentId() = 196398155774144512
		 * comment.getCommentId() = 196403959728697344
		 * 	comment.getCommentId() = 196403959770640384
		 * comment.getCommentId() = 196403959728697345
		 * 	comment.getCommentId() = 196403959770640392
		 * comment.getCommentId() = 196403959728697346
		 * 	comment.getCommentId() = 196403959770640390
		 * comment.getCommentId() = 196403959728697347
		 */
	}

	@Test
	void readAllInfiniteScroll() {
		List<CommentResponse> responses1 = restClient.get()
			.uri("/v1/comments/infinite-scroll?articleId=1&pageSize=5")
			.retrieve()
			.body(new ParameterizedTypeReference<List<CommentResponse>>() {
			});

		System.out.println("firstPage");
		for (CommentResponse comment : responses1) {
			if (!comment.getCommentId().equals(comment.getParentCommentId())) {
				System.out.print("\t");
			}
			System.out.println("comment.getCommentId() = " + comment.getCommentId());
		}

		Long lastParentCommentId = responses1.getLast().getParentCommentId();
		Long lastCommentId = responses1.getLast().getCommentId();

		List<CommentResponse> responses2 = restClient.get()
			.uri("/v1/comments/infinite-scroll?articleId=1&pageSize=5&lastParentCommentId=%s&lastCommentId=%s"
				.formatted(lastParentCommentId, lastCommentId))
			.retrieve()
			.body(new ParameterizedTypeReference<List<CommentResponse>>() {
			});

		System.out.println("secondPage");
		for (CommentResponse comment : responses2) {
			if (!comment.getCommentId().equals(comment.getParentCommentId())) {
				System.out.print("\t");
			}
			System.out.println("comment.getCommentId() = " + comment.getCommentId());
		}
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
