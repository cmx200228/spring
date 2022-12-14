package com.imooc.imooc_mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.imooc_mall.model.pojo.Product;
import com.imooc.imooc_mall.model.request.AddProduct;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;

/**商品
 * @author 陈蒙欣
 * @date 2022/12/12 17:49
 */
public interface ProductService {

    /**
     * 添加商品
     * @param addProduct 要添加的商品
     */
    void addProduct(AddProduct addProduct);

    String uploadImages(HttpServletRequest request, MultipartFile file) throws URISyntaxException;

    void update(Product updateProduct);

    void deleteProduct(Integer id);

    void batchUpdateSellStatus(Integer[] ids, Integer sellStatus);

    PageInfo<Product> listForAdmin(Integer pageNum, Integer pageSize);

    Product detail(Integer id);
}
