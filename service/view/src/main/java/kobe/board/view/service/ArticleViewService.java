package kobe.board.view.service;

import kobe.board.view.repository.ArticleViewCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleViewService {
	private final ArticleViewCountRepository articleViewCountRepository;
	private final ArticleViewCountBackUpProcessor articleViewCountBackUpProcessor;
	private static final int BACK_UP_BACH_SIZE = 100;

	public Long increase(Long articleId, Long userId) {
		Long count = articleViewCountRepository.increase(articleId);
		System.out.println("[INCREASE] articleId = " + articleId + ", count = " + count);
		if (count % BACK_UP_BACH_SIZE == 0) {
			System.out.println("[BACKUP] Triggering DB backup for articleId = " + articleId);
			articleViewCountBackUpProcessor.backUp(articleId, count);
		}
		return count;
	}

	public Long count(Long articleId) {
		return articleViewCountRepository.read(articleId);
	}
}
