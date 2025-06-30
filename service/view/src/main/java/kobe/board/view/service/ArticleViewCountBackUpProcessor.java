package kobe.board.view.service;

import kobe.board.view.entity.ArticleViewCount;
import kobe.board.view.repository.ArticleViewCountBackUpRepository;
import kobe.board.view.repository.ArticleViewCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.View;

@Component
@RequiredArgsConstructor
public class ArticleViewCountBackUpProcessor {
	private final ArticleViewCountBackUpRepository articleViewCountBackUpRepository;
	private final View view;
	private final ArticleViewCountRepository articleViewCountRepository;

	@Transactional
	public void backUp(Long articleId, Long viewCount) {
		int result = articleViewCountBackUpRepository.updateViewCount(articleId, viewCount);
		System.out.println("[BACKUP] update result = " + result);

		if (result == 1) { // update 성공
			System.out.println("[BACKUP] Successfully updated. no insert needed :)");
		} else { // update 실패
			articleViewCountBackUpRepository.findById(articleId)
				.ifPresentOrElse(
					ignored -> System.out.println("[BACKUP] record exists. skip insert."),
					() -> {
						articleViewCountBackUpRepository.save(ArticleViewCount.init(articleId, viewCount));
						System.out.println("[BACKUP] Inserted new article view count");
					}
				);
		}
	}
}
