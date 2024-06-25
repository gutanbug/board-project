package org.gutanbug.boardproject.repository;

import org.gutanbug.boardproject.config.JpaConfig;
import org.gutanbug.boardproject.domain.Article;
import org.gutanbug.boardproject.domain.ArticleComment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTest(@Autowired ArticleRepository articleRepository,
                             @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        List<Article> articles = articleRepository.findAll();

        assertThat(articles)
                .isNotNull()
                .hasSize(0);
    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine() {
        long previousCount = articleRepository.count();
        Article article = Article.of("new article", "new content", "#spring");

        Article savedArticle = articleRepository.save(article);

        assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine() {
        Article article = Article.of("new article", "new content", "#spring");
        articleRepository.save(article);
        String updatedHashtag = "#springboot";
        article.setHashTag(updatedHashtag);

        Article updatedArticle = articleRepository.saveAndFlush(article);

        assertThat(updatedArticle).hasFieldOrPropertyWithValue("hashTag", updatedHashtag);
    }
}