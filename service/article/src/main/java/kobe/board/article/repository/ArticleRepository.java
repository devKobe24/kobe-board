package kobe.board.article.repository;

import kobe.board.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

}
