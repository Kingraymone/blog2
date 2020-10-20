package top.king.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.king.common.ApplicationInfo;
import top.king.common.BaseQuery;
import top.king.common.ResultModel;
import top.king.entity.Article;
import top.king.entity.dto.ArticleSearchDTO;
import top.king.service.ArticleService;
import top.king.service.CommonService;
import utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @Autowired
    CommonService commonService;

    @RequestMapping("/search")
    public ResultModel<List<Article>> selectArticles(@RequestBody BaseQuery<ArticleSearchDTO> param) {
        return articleService.selectArticles(param);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/add")
    public ResultModel addArticle(@RequestBody Article article) {
        if (ObjectUtils.isEmpty(article.getUniqueId())) {
            return articleService.addArticle(article);
        } else {
            return articleService.updateArticle(article);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/delete")
    public ResultModel deleteArticle(@RequestBody ArticleSearchDTO articleSearchDTO) {
        return articleService.deleteArticle(articleSearchDTO.getPrimaryKey());
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/update")
    public ResultModel updateArticle(@RequestBody Article article) {
        return articleService.updateArticle(article);
    }

    @RequestMapping("/id")
    public ResultModel<Article> selectArticle(String primaryKey) {
        return articleService.selectArticle(primaryKey);
    }

    @RequestMapping("/all")
    public ResultModel<List<Article>> selectAllArticles() {
        return articleService.selectAllArticles();
    }

    @RequestMapping("/key")
    public ResultModel<List<Article>> selectBySearch(String key) {
        return articleService.selectBySearch(key);
    }

    @RequestMapping("/appreciate")
    public ResultModel updateAppreciate(@RequestBody Article article,HttpServletRequest request) {
        // 增加一张表记录IP的点赞情况，如果已点则不再处理，文章Id+IP唯一索引
        return articleService.updateAppreciate(String.valueOf(article.getUniqueId()));
    }

    /**
     * 文件上传
     *
     * @param
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/img")
    public ResultModel<String> uploadFile(MultipartFile file, HttpServletRequest request) {
        String filename = file.getOriginalFilename();
        String newFileName = UUID.randomUUID() + filename.substring(filename.lastIndexOf("."));
        String path = ApplicationInfo.ROOT + StringUtils.fileParam("img", "article", newFileName);
        File img = new File(path);
        // 将文件保存在服务器
        ResultModel<String> resultModel = commonService.uploadImg(file, img);
        // 返回url
        resultModel.setData("http://"+request.getHeader("host") + "/img/article/" + newFileName);
        return resultModel;
    }
}
