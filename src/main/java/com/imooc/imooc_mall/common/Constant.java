package com.imooc.imooc_mall.common;

import com.imooc.imooc_mall.exception.ImoocMallException;
import com.imooc.imooc_mall.exception.ImoocMallExceptionEnum;
import io.lettuce.core.internal.LettuceSets;
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
        Set<String> PRICE_ASC_DESC = LettuceSets.newHashSet("price desc", "price asc");
    }

    public interface SaleStatus {
        //商品下架状态
        int NOT_SALE = 0;
        //商品上架状态
        int SALE = 1;
    }

    public interface Cart {
        //未选中状态
        int UN_CHECKED = 0;
        //选中状态
        int CHECKED = 1;
    }

    /**
     * 订单状态
     */
    public enum OrderStatusEum {
        CANCELED(0, "已取消"),
        NO_PAY(10, "未付款"),
        PAID(20, "已付款"),
        SHIPPED(40, "已发货"),
        ORDER_SUCCESS(50, "订单完成"),
        ORDER_CLOSE(60, "订单关闭");

        Integer code;
        String value;

        OrderStatusEum(Integer code, String value) {
            this.code = code;
            this.value = value;
        }

        public static OrderStatusEum codeOf(Integer code) {
            for (OrderStatusEum orderStatusEum : values()) {
                if (orderStatusEum.getCode().equals(code)) {
                    return orderStatusEum;
                }
            }
            throw new ImoocMallException(ImoocMallExceptionEnum.NO_ENUM);
        }

        public Integer getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }
}
