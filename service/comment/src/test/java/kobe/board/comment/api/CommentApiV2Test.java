package kobe.board.comment.api;

import kobe.board.comment.service.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.internal.build.AllowNonPortable;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

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

	@Getter
	@AllArgsConstructor
	public class CommentCreateRequestV2 {
		private Long articleId;
		private String content;
		private String parentPath;
		private Long writerId;
	}

}
