package com.inner.service.article.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.inner.api.article.ArticleService;
import com.inner.api.article.bean.Article;
import com.inner.service.article.dao.ArticleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户service
 *
 * @author kecc
 */
@Service //dubbo的service，注入接口
@Component
public class articleServiceImpl implements ArticleService {

    private final Logger logger = LoggerFactory.getLogger(articleServiceImpl.class);

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public int addArticle(Article article) {
        return articleMapper.insert(article);
    }
}
