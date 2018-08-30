package com.inner.service.article.dao;


import com.inner.api.article.bean.Article;
import org.springframework.stereotype.Component;

@Component
public interface ArticleMapper {
    int insert(Article record);
}
