package top.king.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.king.common.BaseQuery;
import top.king.common.ResultModel;
import top.king.entity.Article;
import top.king.entity.dto.ArticleSearchDTO;
import top.king.service.ArticleService;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @RequestMapping("/search")
    public ResultModel<List<Article>> selectArticles(@RequestBody BaseQuery<ArticleSearchDTO> param) {
        return articleService.selectArticles(param);
    }

    @RequestMapping("/add")
    public ResultModel addArticle(@RequestBody Article article) {
        return articleService.addArticle(article);
    }

    @RequestMapping("/delete")
    public ResultModel deleteArticle(String primaryKey) {
        return articleService.deleteArticle(primaryKey);
    }

    @RequestMapping("/update")
    public ResultModel updateArticle(@RequestBody Article article) {
        return articleService.updateArticle(article);
    }

    @RequestMapping("/id")
    public ResultModel<Article> verifyUser(String primaryKey) {
        return articleService.selectArticle(primaryKey);
    }
}
