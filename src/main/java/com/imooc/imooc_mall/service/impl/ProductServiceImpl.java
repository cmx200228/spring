package com.imooc.imooc_mall.service.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.imooc.imooc_mall.common.Constant;
import com.imooc.imooc_mall.exception.ImoocMallException;
import com.imooc.imooc_mall.exception.ImoocMallExceptionEnum;
import com.imooc.imooc_mall.model.dao.ProductMapper;
import com.imooc.imooc_mall.model.pojo.Product;
import com.imooc.imooc_mall.model.request.AddProduct;
import com.imooc.imooc_mall.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

/**
 * @author 陈蒙欣
 * @date 2022/12/12 17:51
 */
@Service("ProductService")
public class ProductServiceImpl implements ProductService {

    @Resource
    ProductMapper productMapper;

    /**
     * 添加商品
     *
     * @param addProduct 要添加的商品
     */
    @Override
    public void addProduct(AddProduct addProduct) {
        Product product = new Product();
        BeanUtils.copyProperties(addProduct, product);
        Product productOld = productMapper.selectByName(addProduct.getName());
        if (productOld != null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }

        int count = productMapper.insertSelective(product);
        if (count != 1) {
            throw new ImoocMallException(ImoocMallExceptionEnum.CREAT_FAILED);
        }
    }

    /**
     * 上传图片
     * @param request 请求
     * @param file 文件
     * @return 图片保存在服务器的URI
     */
    @Override
    public String uploadImages(HttpServletRequest request, MultipartFile file) throws URISyntaxException {
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));

        //生成文件UUID名称
        UUID uuid = UUID.randomUUID();
        String newFileName = uuid + suffixName;
        //创建文件夹
        File fileDirectory = new File(Constant.FILE_UPLOAD_DIR);
        //创建文件
        File destFile = new File(Constant.FILE_UPLOAD_DIR + newFileName);
        if (!fileDirectory.exists()) {
            if (!fileDirectory.mkdir()) {
                throw new ImoocMallException(ImoocMallExceptionEnum.MKDIR_FAILED);
            }
        }
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return getHost(new URI(request.getRequestURL() + ""))+"/images/"+newFileName;
    }

    /**
     * 从原uri中截取uri前缀
     * @param uri 原请求中的uri
     * @return 截取后的uri
     */
    private URI getHost(URI uri) {
        URI effectiveURI;
        try {
            effectiveURI = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost() , uri.getPort() ,
                    null , null , null);
        } catch (URISyntaxException e) {
            effectiveURI = null;
        }
        return effectiveURI;
    }

    /**
     * 更新商品
     * @param updateProduct 前端传回的要更新的商品信息
     */
    @Override
    public void update(Product updateProduct) {
        Product productOld = productMapper.selectByName(updateProduct.getName());
        //名字相同id不同不允许修改
        if (productOld != null && !productOld.getId().equals(updateProduct.getId())) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        int count = productMapper.updateByPrimaryKeySelective(updateProduct);
        if (count != 1) {
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }
    }

    /**
     * 删除商品
     * @param id 要删除的目录
     */
    @Override
    public void deleteProduct(Integer id) {
        Product productOld = productMapper.selectByPrimaryKey(id);
        //查不到，商品不存在
        if (productOld == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NOT_FOND);
        }
        int count = productMapper.deleteByPrimaryKey(id);
        if (count != 1) {
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        }
        productMapper.updateAfter(id);
        productMapper.updateAfterDelete(id);
    }

    /**
     * 批量上下架
     * @param ids 要更改上下架信息的id集合
     * @param sellStatus 状态
     */
    @Override
    public void batchUpdateSellStatus(Integer[] ids, Integer sellStatus) {
        productMapper.batchUpdateSellStatus(ids, sellStatus);
    }

    @Override
    public PageInfo<Product> listForAdmin(Integer pageNum, Integer pageSize) {
        PageMethod.startPage(pageNum, pageSize);
        List<Product> products = productMapper.selectListForAdmin();
        return new PageInfo<>(products);
    }

    /**
     * 查看商品详情
     * @param id 对应商品id
     * @return 当前id的商品对象
     */
    @Override
    public Product detail(Integer id) {
        return productMapper.selectByPrimaryKey(id);
    }
}
