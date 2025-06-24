package kobe.board.article.api;

import kobe.board.article.service.response.ArticlePageResponse;
import kobe.board.article.service.response.ArticleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class ArticleApiTest {
	RestClient restClient = RestClient.create("http://localhost:9000");

	@Test
	void createTest() {
		ArticleResponse response = create(
			new ArticleCreateRequest(
				"Test Title",
				"Test Content",
				1L, // writerId
				1L  // boardId
			)
		);
		System.out.println("[CREATE] response = " + response);
	}

	@Test
	void readTest() {
		ArticleResponse response = read(195672956478894080L);
		System.out.println("[READ] response = " + response);
	}

	ArticleResponse read(Long articleId) {
		return restClient.get()
			.uri("/v1/articles/{articleId}", articleId)
			.retrieve()
			.body(ArticleResponse.class);
	}

	@Test
	void updateTest() {
		update(195672956478894080L);
		ArticleResponse response = read(195672956478894080L);
		System.out.println("[UPDATE] response = " + response);
	}

	void update(Long articleId) {
		restClient.put()
			.uri("/v1/articles/{articleId}", articleId)
			.body(new ArticleUpdateRequest("Updated Title", "Updated Content"))
			.retrieve();
	}

	@Test
	void deleteTest() {
		restClient.delete()
			.uri("/v1/articles/{articleId}", 195672956478894080L)
			.retrieve();
	}

	@Test
	void readAllTest() {
		ArticlePageResponse response = restClient.get()
			.uri("/v1/articles?boardId=1&pageSize=30&page=50000")
			.retrieve()
			.body(ArticlePageResponse.class);

		System.out.println("[READ ALL] response getArticleCount() = " + response.getArticleCount());
		for (ArticleResponse article: response.getArticles()) {
			System.out.println("[READ ALL] articleId = " + article.getArticleId());
		}
	}

	ArticleResponse create(ArticleCreateRequest request) {
		return restClient.post()
			.uri("/v1/articles")
			.body(request)
			.retrieve()
			.body(ArticleResponse.class);
	}

	@Getter
	@AllArgsConstructor
	static class ArticleCreateRequest {
		private String title;
		private String content;
		private Long writerId;
		private Long boardId;
	}

	@Getter
	@AllArgsConstructor
	static class ArticleUpdateRequest {
		private String title;
		private String content;
	}
}
