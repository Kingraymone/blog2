package top.king.service;

import top.king.common.BaseQuery;
import top.king.common.ResultModel;
import top.king.entity.blog.Article;
import top.king.entity.dto.ArticleSearchDTO;

import java.util.List;

public interface ArticleService {
    ResultModel<List<Article>> selectArticles(BaseQuery<ArticleSearchDTO> param);

    ResultModel addArticle(Article article);

    ResultModel updateArticle(Article article);

    ResultModel deleteArticle(List<String> primaryKey);
    
    ResultModel<Article> selectArticle(String primaryKey);

    ResultModel<List<Article>> selectAllArticles();

    ResultModel<List<Article>> selectBySearch(String key);

    ResultModel updateAppreciate(String key);
}
