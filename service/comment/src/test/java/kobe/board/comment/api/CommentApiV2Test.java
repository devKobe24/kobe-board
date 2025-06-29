package kobe.board.comment.api;

import kobe.board.comment.service.response.CommentPageResponse;
import kobe.board.comment.service.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class CommentApiV2Test {
	RestClient restClient = RestClient.create("http://localhost:9001");

	@Test
	void create() {
		CommentResponse response1 = create(new CommentCreateRequestV2(1L, "my comment1", null, 1L));
		CommentResponse response2 = create(new CommentCreateRequestV2(1L, "my comment2", response1.getPath(), 1L));
		CommentResponse response3 = create(new CommentCreateRequestV2(1L, "my comment3", response2.getPath(), 1L));

		System.out.println("response1.getPath() = " + response1.getPath());
		System.out.println("response1.getCommentId() = " + response1.getCommentId());
		System.out.println("\tresponse2.getPath() = " + response2.getPath());
		System.out.println("\tresponse2.getCommentId() = " + response2.getCommentId());
		System.out.println("\t\tresponse3.getPath() = " + response3.getPath());
		System.out.println("\t\tresponse3.getCommentId() = " + response3.getCommentId());

		/**
		 * response1.getPath() = 00003
		 * response1.getCommentId() = 197071251903549440
		 * 	response2.getPath() = 0000300000
		 * 	response2.getCommentId() = 197071252079710208
		 * 		response3.getPath() = 000030000000000
		 * 		response3.getCommentId() = 197071252130041856
		 */
	}

	@Test
	void read() {
		CommentResponse response = restClient.get()
			.uri("/v2/comments/{commentId}", 197071251903549440L)
			.retrieve()
			.body(CommentResponse.class);
		System.out.println("[READ] response = " + response);
	}

	@Test
	void delete() {
		restClient.delete()
			.uri("/v2/comments/{commentId}", 197071251903549440L)
			.retrieve();
	}

	CommentResponse create(CommentCreateRequestV2 request) {
		return restClient.post()
			.uri("/v2/comments")
			.body(request)
			.retrieve()
			.body(CommentResponse.class);
	}

	@Test
	void readAll() {
		CommentPageResponse response = restClient.get()
			.uri("/v2/comments?articleId=1&pageSize=10&page=1")
			.retrieve()
			.body(CommentPageResponse.class);

		System.out.println("response.getCommentCount() = " + response.getCommentCount());
		for (CommentResponse comment : response.getComments()) {
			System.out.println("comment.getCommentId() = " + comment.getCommentId());
		}
		/**
		 * response.getCommentCount() = 101
		 * comment.getCommentId() = 197070554351562752
		 * comment.getCommentId() = 197070555022651392
		 * comment.getCommentId() = 197070555089760256
		 * comment.getCommentId() = 197070715074580480
		 * comment.getCommentId() = 197070715716308992
		 *
		 * comment.getCommentId() = 197070715770834944
		 * comment.getCommentId() = 197070958088359936
		 * comment.getCommentId() = 197070958285492224
		 * comment.getCommentId() = 197070958335823872
		 * comment.getCommentId() = 197071251903549440
		 */
	}

	@Test
	void readAllInfiniteScroll() {
		List<CommentResponse> responses1 = restClient.get()
			.uri("/v2/comments/infinite-scroll?articleId=1&pageSize=5")
			.retrieve()
			.body(new ParameterizedTypeReference<List<CommentResponse>>(){
			});

		System.out.println("firstPage");
		for (CommentResponse response : responses1) {
			System.out.println("response.getCommentId() = " + response.getCommentId());
		}

		String lastPath = responses1.getLast().getPath();
		List<CommentResponse> responses2 = restClient.get()
			.uri("/v2/comments/infinite-scroll?articleId=1&pageSize=5&lastPath=%s".formatted(lastPath))
			.retrieve()
			.body(new ParameterizedTypeReference<List<CommentResponse>>() {
			});

		System.out.println("secondPage");
		for (CommentResponse response : responses2) {
			System.out.println("response.getCommentId() = " + response.getCommentId());
		}
	}

	@Test
	void countTest() {
		CommentResponse commentResponse = create(new CommentCreateRequestV2(2L, "MY COMMENT 1", null, 1L));

		Long count1 = restClient.get()
			.uri("/v2/comments/articles/{articleId}/count", 2L)
			.retrieve()
			.body(Long.class);
		System.out.println("[COUNT 1] = " + count1);

		restClient.delete()
			.uri("/v2/comments//{commentId}", commentResponse.getCommentId())
			.retrieve();

		Long count2 = restClient.get()
			.uri("/v2/comments/articles/{articleId}/count", 2L)
			.retrieve()
			.body(Long.class);
		System.out.println("[COUNT 2] = " + count2);
	}

	@Getter
	@AllArgsConstructor
	public class CommentCreateRequestV2 {
		private Long articleId;
		private String content;
		private String parentPath;
		private Long writerId;
	}
}
