package com.imooc.imooc_mall.controller;

import com.imooc.imooc_mall.common.ApiRestResponse;
import com.imooc.imooc_mall.exception.ImoocMallExceptionEnum;
import com.imooc.imooc_mall.model.pojo.Product;
import com.imooc.imooc_mall.model.request.AddProduct;
import com.imooc.imooc_mall.model.request.UpdateProduct;
import com.imooc.imooc_mall.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;

/**
 * 后台商品管理
 * @author 陈蒙欣
 * @date 2022/12/12 16:19
 */
@RestController
@RequestMapping("/admin")
public class ProductAdminController {

    @Resource
    ProductService productService;

    @ApiOperation("添加商品")
    @PostMapping("/product/add")
    public ApiRestResponse<?> addProduct(@Validated @RequestBody AddProduct addProduct) {
        productService.addProduct(addProduct);
        return ApiRestResponse.success();
    }

    @ApiOperation("上传商品图片")
    @PostMapping("/upload/file")
    public ApiRestResponse<?> uploadImages(HttpServletRequest servletRequest,
                                           @RequestParam("file") MultipartFile file) {
        try {
            return ApiRestResponse.success(productService.uploadImages(servletRequest, file));
        } catch (URISyntaxException e) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.UPLOAD_FAILED);
        }
    }

    @ApiOperation("更新商品")
    @PostMapping("/product/update")
    public ApiRestResponse<?> updateProduct(@Validated @RequestBody UpdateProduct updateProduct) {
        Product product = new Product();
        BeanUtils.copyProperties(updateProduct, product);
        productService.update(product);
        return ApiRestResponse.success();
    }

    @ApiOperation("删除商品")
    @PostMapping("/product/delete")
    public ApiRestResponse<?> deleteProduct(@RequestParam Integer id) {
        productService.deleteProduct(id);
        return ApiRestResponse.success();
    }

    @ApiOperation("批量上下架商品")
    @PostMapping("/product/batchUpdateSellStatus")
    public ApiRestResponse<?> batchUpdateSellStatus(@RequestParam Integer[] ids ,
                                                    @RequestParam Integer sellStatus){
        productService.batchUpdateSellStatus(ids, sellStatus);
        return ApiRestResponse.success();
    }

    @ApiOperation("后台列表展示")
    @GetMapping("/product/list")
    public ApiRestResponse<?> list(@RequestParam Integer pageNum ,
                                                    @RequestParam Integer pageSize){
        return ApiRestResponse.success(productService.listForAdmin(pageNum, pageSize));
    }
}
