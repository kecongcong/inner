package com.inner.api.base.bean;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;

@Data
public class SolrDataDTO implements Serializable {


    private static final long serialVersionUID = 1L;

    @Field("id")
    private Integer id;
    @Field("title")
    private String title;
    @Field("labels")
    private String labels;

}