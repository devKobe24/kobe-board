package kobe.board.like.controller;

import kobe.board.like.service.ArticleLikeService;
import kobe.board.like.service.response.ArticleLikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/article-likes/articles")
@RequiredArgsConstructor
public class ArticleLikeController {
	private final ArticleLikeService articleLikeService;

	@GetMapping("/{articleId}/users/{userId}")
	public ArticleLikeResponse read(
		@PathVariable("articleId") Long articleId,
		@PathVariable("userId") Long userId
	) {
		return articleLikeService.read(articleId, userId);
	}

	@PostMapping("/{articleId}/users/{userId}")
	public void like(
		@PathVariable("articleId") Long articleId,
		@PathVariable("userId") Long userId
	) {
		articleLikeService.like(articleId, userId);
	}

	@DeleteMapping("/{articleId}/users/{userId}")
	public void unlike(
		@PathVariable("articleId") Long articleId,
		@PathVariable("userId") Long userId
	) {
		articleLikeService.unlike(articleId, userId);
	}
}
