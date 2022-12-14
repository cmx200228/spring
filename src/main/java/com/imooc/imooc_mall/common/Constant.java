package com.imooc.imooc_mall.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author 陈蒙欣
 * @date 2022/12/8 20:46
 */
@Component
public class Constant {
    public static final String SALT = "asdfewrtefs..,]";
    public static final String IMOOC_MALL_USER = "imooc_mall_user";

    public static String FILE_UPLOAD_DIR;

    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir) {
        FILE_UPLOAD_DIR = fileUploadDir;
    }

    public interface ProductListOrderBy {
        Set<String> PRICE_ASC_DESC = Set.of("price desc", "price asc");
    }

}
