package kobe.board.common.event;

import kobe.board.common.event.payload.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum EventType {
	ARTICLE_CREATED(ArticleCreatedEventPayload.class, Topic.KOBE_BOARD_ARTICLE),
	ARTICLE_UPDATED(ArticleUpdatedEventPayload.class, Topic.KOBE_BOARD_ARTICLE),
	ARTICLE_DELETED(ArticleDeletedEventPayload.class, Topic.KOBE_BOARD_ARTICLE),
	COMMENT_CREATED(CommentCreatedEventPayload.class, Topic.KOBE_BOARD_COMMENT),
	COMMENT_DELETED(CommentDeletedEventPayload.class, Topic.KOBE_BOARD_COMMENT),
	ARTICLE_LIKED(ArticleLikedEventPayload.class, Topic.KOBE_BOARD_LIKE),
	ARTICLE_UNLIKED(ArticleUnlikedEventPayload.class, Topic.KOBE_BOARD_LIKE),
	ARTICLE_VIEWED(ArticleViewedEventPayload.class, Topic.KOBE_BOARD_VIEW)
	;

	private final Class<? extends EventPayload> payloadClass;
	private final String topic;

	public static EventType from(String type) {
		try {
			return valueOf(type);
		} catch (Exception e) {
			log.error("[EventType.from] type={}", type, e);
			return null;
		}
	}

	public static class Topic {
		public static final String KOBE_BOARD_ARTICLE = "kobe-board-article";
		public static final String KOBE_BOARD_COMMENT = "kobe-board-comment";
		public static final String KOBE_BOARD_LIKE = "kobe-board-like";
		public static final String KOBE_BOARD_VIEW = "kobe-board-view";
	}
}
