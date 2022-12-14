package com.imooc.imooc_mall.model.query;

import java.util.List;

/**
 * 用于查询商品列表，存放参数
 *
 * @author 陈蒙欣
 * @date 2022/12/14 21:30
 */
public class ProductListQuery {

    private String keyword;

    private List<Integer> categoryIds;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<Integer> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }
}
