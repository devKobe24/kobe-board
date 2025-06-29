package kobe.board.article.controller;

import kobe.board.article.service.ArticleService;
import kobe.board.article.service.request.ArticleCreateRequest;
import kobe.board.article.service.request.ArticleUpdateRequest;
import kobe.board.article.service.response.ArticlePageResponse;
import kobe.board.article.service.response.ArticleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/articles")
@RequiredArgsConstructor
public class ArticleController {
	private final ArticleService articleService;

	@GetMapping("/{articleId}")
	public ArticleResponse read(@PathVariable Long articleId) {
		return articleService.read(articleId);
	}

	@GetMapping
	public ArticlePageResponse readAll(
		@RequestParam("boardId") Long boardId,
		@RequestParam("page") Long page,
		@RequestParam("pageSize") Long pageSize
	) {
		return articleService.readAll(boardId, page, pageSize);
	}

	@GetMapping("/infinite-scroll")
	public List<ArticleResponse> readAllInfiniteScroll(
		@RequestParam("boardId") Long boardId,
		@RequestParam("pageSize") Long pageSize,
		@RequestParam(value = "lastArticleId", required = false) Long lastArticleId
	) {
		return articleService.readAllInfiniteScroll(boardId, pageSize, lastArticleId);
	}

	@PostMapping
	public ArticleResponse create(@RequestBody ArticleCreateRequest request) {
		return articleService.create(request);
	}

	@PutMapping("/{articleId}")
	public ArticleResponse update(@PathVariable Long articleId, @RequestBody ArticleUpdateRequest request) {
		return articleService.update(articleId, request);
	}

	@DeleteMapping("/{articleId}")
	public void delete(@PathVariable Long articleId) {
		articleService.delete(articleId);
	}

	@GetMapping("/boards/{boardId}/count")
	public Long count(@PathVariable Long boardId) {
		return articleService.count(boardId);
	}
}
