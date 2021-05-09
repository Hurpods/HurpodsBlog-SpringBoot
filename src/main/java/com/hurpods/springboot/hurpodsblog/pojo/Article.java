package com.hurpods.springboot.hurpodsblog.pojo;

import com.hurpods.springboot.hurpodsblog.dto.ArticleDTO;
import com.hurpods.springboot.hurpodsblog.vo.UserVo;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class Article implements Serializable {
    private static final long serialVersionUID = -6124243107640328484L;
    private Integer articleId;
    private Integer articleAuthorId;
    private String articleTitle;
    private String articleContent;
    private Integer articleViewCount;
    private Integer articleCommentsCount;
    private Integer articleStatus;
    private Timestamp articleCreateTime;
    private Timestamp articleUpdateTime;
    private String articleSummary;
    private String articleCover;
    private UserVo user;

    public Article(ArticleDTO dto){
        this.articleTitle=dto.getTitle();
        this.articleContent=dto.getContent();
    }
}
