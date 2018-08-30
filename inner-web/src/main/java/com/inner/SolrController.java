package com.inner;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 优化：抽取 Id、labels 为一个 JavaBean
 *
 * @author linhongcun
 */
@RestController
@RequestMapping("/test")
@EnableSwagger2
public class SolrController {

    @Autowired
    private SolrClient client;

    /**
     * 1、增
     *
     * @param message
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    @PostMapping("/insert")
    public String insert(String message) throws IOException, SolrServerException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String dateString = sdf.format(new Date());
        try {
            SolrInputDocument doc = new SolrInputDocument();
            doc.setField("id", dateString);
            doc.setField("title", message);
            doc.setField("labels", message);

            /*
             * 如果 spring.data.solr.host 里面配置到 core了, 那么这里就不需要传 collection1 这个参数 下面都是一样的 即
             * client.commit();
             */

            client.add("articles", doc);
            client.commit("articles");
            return dateString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    /**
     * 2、查 id
     *
     * @param id
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
    @GetMapping("/get/{id}")
    public String getDocumentById(@PathVariable String id) throws SolrServerException, IOException {
        SolrDocument document = client.getById("articles", id);
        System.out.println(document);
        return document.toString();

    }

    /**
     * 3、删 id
     *
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public String getAllDocuments(@PathVariable String id) {
        try {
            client.deleteById("articles", id);
            client.commit("articles");
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    /**
     * 4、删 all
     *
     * @return
     */
    @DeleteMapping("deleteAll")
    public String deleteAll() {
        try {

            client.deleteByQuery("articles", "*:*");
            client.commit("articles");
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    /**
     * 5、改
     *
     * @param message
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    @PutMapping("/update")
    public String update(String id, String message) throws IOException, SolrServerException {
        try {
            SolrInputDocument doc = new SolrInputDocument();
            doc.setField("id", id);
            doc.setField("title", message);
            doc.setField("labels", message);

            /*
             * 如果 spring.data.solr.host 里面配置到 core了, 那么这里就不需要传 articles 这个参数 下面都是一样的 即
             * client.commit();
             */
            client.add("articles", doc);
            client.commit("articles");
            return doc.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    /**
     * 6、全：还没实现，也感觉没有必要实现
     *
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
    @GetMapping("/get/all")
    public Map<String, Object> getAll()
            throws SolrServerException, IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        return map;
    }

    /**
     * 7、查  ++:关键字、高亮、分页  ✔
     *
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
    @GetMapping("/select/{q}/{page}/{size}")
    public Map<String, Object> select(@PathVariable String q, @PathVariable Integer page, @PathVariable Integer size)
            throws SolrServerException, IOException {
        SolrQuery params = new SolrQuery();

        // 查询条件
        params.set("q", q);

        // 排序
        params.addSort("id", SolrQuery.ORDER.desc);

        // 分页
        params.setStart(page);
        params.setRows(size);

        // 默认域
        params.set("df", "labels");

        // 只查询指定域
        params.set("fl", "id,title,labels");

        // 开启高亮
        params.setHighlight(true);
        // 设置前缀
        params.setHighlightSimplePre("<span style='color:red'>");
        // 设置后缀
        params.setHighlightSimplePost("</span>");

        // solr数据库是 articles
        QueryResponse queryResponse = client.query("articles", params);
        SolrDocumentList results = queryResponse.getResults();

        // 数量，分页用
        long total = results.getNumFound();// JS 使用 size=MXA 和 data.length 即可知道长度了（但不合理）

        Map<String, Map<String, List<String>>> highlight = queryResponse.getHighlighting();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", total);
        map.put("data", highlight);
        return map;

    }
}
