package com.imooc.imooc_mall.controller;

import com.imooc.imooc_mall.common.ApiRestResponse;
import com.imooc.imooc_mall.common.Constant;
import com.imooc.imooc_mall.exception.ImoocMallExceptionEnum;
import com.imooc.imooc_mall.model.pojo.Category;
import com.imooc.imooc_mall.model.pojo.User;
import com.imooc.imooc_mall.model.request.AddCategoryRequest;
import com.imooc.imooc_mall.model.request.UpdateCategoryRequest;
import com.imooc.imooc_mall.service.CategoryService;
import com.imooc.imooc_mall.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author 陈蒙欣
 * @date 2022/12/9 20:46
 */
@RestController
public class CategoryController {
    @Resource
    CategoryService categoryService;

    @Resource
    UserService userService;

    /**
     * 添加目录
     * @param session 当前会话
     * @param categoryRequest 参数对象 spring自动装填
     * @return 统一返回对象
     */
    @ApiOperation("添加目录")
    @PostMapping("/admin/category/add")
    public ApiRestResponse<?> addCategory(HttpSession session,
                                       @Validated @RequestBody AddCategoryRequest categoryRequest) {
        User user = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
        if (user == null) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_LOGIN);
        }
        boolean admin = userService.checkAdmin(user);
        if (admin){
            categoryService.add(categoryRequest);
            return ApiRestResponse.success();
        }else {
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_ADMIN);
        }
    }

    @ApiOperation("更新目录")
    @PostMapping("/admin/category/update")
    public ApiRestResponse<?> updateCategory(HttpSession session,
                                       @Validated @RequestBody UpdateCategoryRequest updateCategoryRequest) {
        User user = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
        if (user == null) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_LOGIN);
        }
        boolean admin = userService.checkAdmin(user);
        if (admin){
            Category category = new Category() ;
            BeanUtils.copyProperties(updateCategoryRequest , category);
            categoryService.update(category);
            return ApiRestResponse.success();
        }else {
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_ADMIN);
        }
    }

    @ApiOperation("删除目录")
    @PostMapping("/admin/category/delete")
    public ApiRestResponse<?> deleteCategory(@RequestParam Integer id) {
        categoryService.delete(id);
        return ApiRestResponse.success();
    }

    @ApiOperation("后台目录列表")
    @GetMapping("/admin/category/list")
    public ApiRestResponse<?> listCategoryAdmin(@RequestParam Integer pageNum,
                                                @RequestParam Integer pageSize) {
        return ApiRestResponse.success(categoryService.listForAdmin(pageNum, pageSize));
    }

    @ApiOperation("前台目录列表")
    @PostMapping("/category/list")
    public ApiRestResponse<?> listCategoryCustomer() {
        return ApiRestResponse.success(categoryService.listCategoryForCustomer());
    }
}
