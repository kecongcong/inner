package com.inner.api.article.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class Article implements Serializable {

    private Long articleId;

    private String articleName;

    private String title;

    private String content;
}