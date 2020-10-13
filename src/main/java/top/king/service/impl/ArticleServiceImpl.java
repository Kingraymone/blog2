package top.king.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.king.common.BaseQuery;
import top.king.common.BaseService;
import top.king.common.ResultModel;
import top.king.entity.Article;
import top.king.entity.dto.ArticleSearchDTO;
import top.king.mapper.ArticleMapper;
import top.king.service.ArticleService;

import java.util.List;

@Service
public class ArticleServiceImpl extends BaseService implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;

    @Override
    public ResultModel<List<Article>> selectArticles(BaseQuery<ArticleSearchDTO> param) {
        ResultModel<List<Article>> resultModel = new ResultModel<>();
        try {
            PageHelper.startPage(param.getPageNum(), param.getPageSize());
            Page<Article> articles = (Page<Article>) articleMapper.selectArticles(param);
            resultModel.setData(articles);
            resultModel.setCount(articles.getTotal());
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("查询文章信息出错！", e);
            resultModel.setMsg("查询文章信息出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel addArticle(Article article) {
        ResultModel resultModel = new ResultModel();
        try {
            articleMapper.insertArticle(article);
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("添加文章出错！", e);
            resultModel.setMsg("添加文章出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel updateArticle(Article article) {
        ResultModel resultModel = new ResultModel();
        try {
            articleMapper.updateArticle(article);
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("修改文章出错！", e);
            resultModel.setMsg("修改文章出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel deleteArticle(List<String> primaryKey) {
        ResultModel resultModel = new ResultModel();
        try {
            articleMapper.deleteArticle(primaryKey);
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("删除文章出错！", e);
            resultModel.setMsg("删除文章出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }

    @Override
    public ResultModel<Article> selectArticle(String primaryKey) {
        ResultModel<Article> resultModel = new ResultModel<>();
        try {
            Article article = articleMapper.selectArticleById(primaryKey);
            resultModel.setData(article);
            return resultModel;
        } catch (Exception e) {
            bLogger.debug("查询文章信息出错！", e);
            resultModel.setMsg("查询文章信息出错！");
            resultModel.setResult(false);
            return resultModel;
        }
    }
}
