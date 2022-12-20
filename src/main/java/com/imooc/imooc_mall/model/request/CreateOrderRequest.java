package com.imooc.imooc_mall.model.request;

import javax.validation.constraints.NotNull;

/**
 *
 * @author 陈蒙欣
 * @date 2022/11/29
 */

public class CreateOrderRequest {

    @NotNull(message = "收货人不能为空")
    private String receiverName;

    @NotNull(message = "收货电话不能为空")
    private String receiverMobile;

    @NotNull(message = "收货地址不能为空")
    private String receiverAddress;

    private Integer orderStatus;

    private Integer postage = 0;

    private Integer paymentType = 1;

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPostage() {
        return postage;
    }

    public void setPostage(Integer postage) {
        this.postage = postage;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }
}
