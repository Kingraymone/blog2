package top.king.mapper.article;

import top.king.common.BaseQuery;
import top.king.entity.blog.Article;
import top.king.entity.dto.ArticleSearchDTO;

import java.util.List;

public interface ArticleMapper {
    List<Article> selectArticles(BaseQuery<ArticleSearchDTO> param);

    void deleteArticle(List<String> primaryKey);

    void insertArticle(Article article);

    void updateArticle(Article article);

    Article selectArticleById(String primaryKey);

    List<Article> selectAllArticles();

    List<Article> selectBySearch(String key);

    int updateAppreciate(String primaryKey);
}
