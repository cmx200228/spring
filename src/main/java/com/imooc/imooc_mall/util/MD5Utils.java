package com.imooc.imooc_mall.util;

import com.imooc.imooc_mall.common.Constant;
import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 * @author 陈蒙欣
 * @date 2022/12/8 20:42
 */
public class MD5Utils {
    /**
     * 将原密码加盐混淆并进行MD5加密
     * @param strValue 原字符串
     * @return 加密后的字符串
     * @throws NoSuchAlgorithmException 当前环境MD5算法不可用
     */
    public static String getMD5String(String strValue) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return Base64.encodeBase64String(md5.digest((Constant.SALT + strValue).getBytes()));
    }
}
