package com.imooc.imooc_mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.imooc_mall.model.pojo.Category;
import com.imooc.imooc_mall.model.request.AddCategoryRequest;
import com.imooc.imooc_mall.model.vo.CategoryVO;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * @author 陈蒙欣
 * @date 2022/12/9 21:25
 */
public interface CategoryService {
    void add(AddCategoryRequest request);

    void update(Category updateCategory);

    void delete(Integer id);

    PageInfo<Category> listForAdmin(Integer pageNum, Integer pageSize);

    @Cacheable(value = "listCategoryForCustomer")
    List<CategoryVO> listCategoryForCustomer(Integer parentId);
}
