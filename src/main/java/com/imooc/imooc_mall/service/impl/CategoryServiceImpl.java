package com.imooc.imooc_mall.service.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.imooc.imooc_mall.exception.ImoocMallException;
import com.imooc.imooc_mall.exception.ImoocMallExceptionEnum;
import com.imooc.imooc_mall.model.dao.CategoryMapper;
import com.imooc.imooc_mall.model.pojo.Category;
import com.imooc.imooc_mall.model.request.AddCategoryRequest;
import com.imooc.imooc_mall.model.vo.CategoryVO;
import com.imooc.imooc_mall.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 目录
 * @author 陈蒙欣
 * @date 2022/12/9 21:25
 */
@Service("CategoryService")
public class CategoryServiceImpl implements CategoryService {
    @Resource
    CategoryMapper categoryMapper;

    @Override
    public void add(AddCategoryRequest request) {
        Category category = new Category();
        BeanUtils.copyProperties(request , category);
        Category categoryOld = categoryMapper.selectByName(request.getName());
        if (categoryOld != null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }

        int count = categoryMapper.insertSelective(category);
        if (count == 0) {
            throw new ImoocMallException(ImoocMallExceptionEnum.CREAT_FAILED);
        }
    }

    /**
     * 更新目录
     * @param updateCategory 传入的目录对象
     */
    @Override
    public void update(Category updateCategory) {
        if (updateCategory != null) {
            Category categoryOld = categoryMapper.selectByName(updateCategory.getName());
            if (categoryOld != null && !categoryOld.getId().equals(updateCategory.getId())) {
                throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
            }
        }
        int count = categoryMapper.updateByPrimaryKeySelective(updateCategory);
        if (count != 1) {
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }
    }

    /**
     * 删除目录
     * @param id 目录id
     */
    @Override
    public void delete(Integer id) {
        Category categoryOld = categoryMapper.selectByPrimaryKey(id);
        if (categoryOld == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        }
        int count = categoryMapper.deleteByPrimaryKey(id);
        categoryMapper.updateAfter(id);
        categoryMapper.updateAfterDelete(id);
        if (count != 1) {
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        }
    }

    /**
     * 后台管理员分页查看目录
     * @param pageNum 页号
     * @param pageSize 页面大小
     * @return pageInfo对象
     */
    @Override
    public PageInfo<Category> listForAdmin(Integer pageNum, Integer pageSize) {
        PageMethod.startPage(pageNum, pageSize, "type,order_num");
        List<Category> categoryList = categoryMapper.selectList();
        return new PageInfo<>(categoryList);
    }

    /**
     * 前台获取目录
     * @return 目录集合
     */
    @Cacheable(value = "listCategoryForCustomer")
    @Override
    public List<CategoryVO> listCategoryForCustomer() {
        ArrayList<CategoryVO> categoryVOList = new ArrayList<>();
        recursivelyFindCategories(categoryVOList, 0);
        return categoryVOList ;
    }

    /**
     * 递归查找子目录
     * @param categoryVOList 存放数据的集合
     * @param parentId 父id
     */
    private void recursivelyFindCategories(List<CategoryVO> categoryVOList, Integer parentId) {
        List<Category> categoryList = categoryMapper.selectCategoriesByParentId(parentId);
        //递归获取所有子类别，并组合成为一个“目录树”
        if (!CollectionUtils.isEmpty(categoryList)) {
            categoryList.forEach(category -> {
                CategoryVO categoryVO = new CategoryVO();
                BeanUtils.copyProperties(category , categoryVO);
                categoryVOList.add(categoryVO);
                recursivelyFindCategories(categoryVO.getChildCategory() , category.getId());
            });
        }
    }
}
