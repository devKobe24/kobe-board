package kobe.board.comment.controller;

import kobe.board.comment.service.CommentServiceV2;
import kobe.board.comment.service.request.CommentCreateRequestV2;
import kobe.board.comment.service.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

}
