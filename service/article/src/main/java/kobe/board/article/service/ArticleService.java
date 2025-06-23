package kobe.board.article.service;

import kobe.board.article.entity.Article;
import kobe.board.article.repository.ArticleRepository;
import kobe.board.article.service.request.ArticleCreateRequest;
import kobe.board.article.service.response.ArticleResponse;
import kobe.board.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {
	private final Snowflake snowflake = new Snowflake();
	private final ArticleRepository articleRepository;

	@Transactional
	public ArticleResponse create(ArticleCreateRequest request) {
		Article article = articleRepository.save(
			Article.create(snowflake.nextId(), request.getTitle(), request.getContent(), request.getBoardId(), request.getWriteId())
		);
		return ArticleResponse.from(article);
	}
}
