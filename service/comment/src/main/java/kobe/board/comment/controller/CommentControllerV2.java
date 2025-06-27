package kobe.board.comment.controller;

import kobe.board.comment.service.CommentServiceV2;
import kobe.board.comment.service.request.CommentCreateRequestV2;
import kobe.board.comment.service.response.CommentPageResponse;
import kobe.board.comment.service.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v2/comments")
@RequiredArgsConstructor
public class CommentControllerV2 {

	private final CommentServiceV2 commentService;

	@GetMapping("/{commentId}")
	public CommentResponse read(@PathVariable("commentId") Long commentId) {
		return commentService.read(commentId);
	}

	@PostMapping
	public CommentResponse create(@RequestBody CommentCreateRequestV2 request) {
		return commentService.create(request);
	}

	@DeleteMapping("/{commentId}")
	public void delete(@PathVariable("commentId") Long commentId) {
		commentService.delete(commentId);
	}

	@GetMapping
	public CommentPageResponse readAll(
		@RequestParam("articleId") Long articleId,
		@RequestParam("page") Long page,
		@RequestParam("pageSize") Long pageSize
	) {
		log.info("[readAll] called with articleId={}, page={}, pageSize={}", articleId, page, pageSize);
		return commentService.readAll(articleId, page, pageSize);
	}

	@GetMapping("/infinite-scroll")
	public List<CommentResponse> readAllInfiniteScroll(
		@RequestParam("articleId") Long articleId,
		@RequestParam(value = "lastPath", required = false) String lastPath,
		@RequestParam("pageSize") Long pageSize
	) {
		return commentService.readAllInfiniteScroll(articleId, lastPath, pageSize);
	}
}
