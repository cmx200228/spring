package com.imooc.imooc_mall.model.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 管理员添加类别时接受前端请求参数
 *
 * @author 陈蒙欣
 * @date 2022/12/9 20:49
 */

public class AddCategoryRequest {
    @NotNull(message = "名称不能为空")
    @Size(min = 2 , max = 5 , message = "名称最短为2，最长为5")
    private String name;

    @NotNull(message = "type不能为空")
    @Max(3)
    private Integer type;

    @NotNull(message = "父目录不能为空")
    private Integer parentId;

    @NotNull(message = "排序不能为空")
    private Integer orderNum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
