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

	@GetMapping("/{articleId}/count")
	public Long count(
		@PathVariable("articleId") Long articleId
	) {
		return articleLikeService.count(articleId);
	}

	@PostMapping("/{articleId}/users/{userId}/pessimistic-lock-1")
	public void likePessimisticLock1(
		@PathVariable("articleId") Long articleId,
		@PathVariable("userId") Long userId
	) {
		articleLikeService.likePessimisticLock1(articleId, userId);
	}

	@DeleteMapping("/{articleId}/users/{userId}/pessimistic-lock-1")
	public void unlikePessimisticLock1(
		@PathVariable("articleId") Long articleId,
		@PathVariable("userId") Long userId
	) {
		articleLikeService.unlikePessimisticLock1(articleId, userId);
	}

	@PostMapping("/{articleId}/users/{userId}/pessimistic-lock-2")
	public void likePessimisticLock2(
		@PathVariable("articleId") Long articleId,
		@PathVariable("userId") Long userId
	) {
		articleLikeService.likePessimisticLock2(articleId, userId);
	}

	@DeleteMapping("/{articleId}/users/{userId}/pessimistic-lock-2")
	public void unlikePessimisticLock2(
		@PathVariable("articleId") Long articleId,
		@PathVariable("userId") Long userId
	) {
		articleLikeService.unlikePessimisticLock2(articleId, userId);
	}

	@PostMapping("/{articleId}/users/{userId}/optimistic-lock")
	public void likeOptimisticLock(
		@PathVariable("articleId") Long articleId,
		@PathVariable("userId") Long userId
	) {
		articleLikeService.likeOptimisticLock(articleId, userId);
	}

	@DeleteMapping("/{articleId}/users/{userId}/optimistic-lock")
	public void unlikeOptimisticLock(
		@PathVariable("articleId") Long articleId,
		@PathVariable("userId") Long userId
	) {
		articleLikeService.unlikeOptimisticLock(articleId, userId);
	}
}
