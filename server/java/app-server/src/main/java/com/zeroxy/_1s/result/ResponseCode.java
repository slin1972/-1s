package com.zeroxy._1s.result;

import com.zeroxy.CommonResult;

/**
 * Created by SEELE on 2017/5/12.
 */
public class ResponseCode {

    public static final CommonResult OK_0 = newOkResult();

    public static final CommonResult ERROR_300 = new CommonResult(300,"认证失败,请退出并重新登录.");
    public static final CommonResult ERROR_302 = new CommonResult(302,"参数错误.");

    public static final CommonResult ERROR_999 = new CommonResult(999,"内部服务器错误.");
    public static final CommonResult ERROR_100 = new CommonResult(100,"请输入正确的用户名和密码.");
    public static final CommonResult ERROR_101 = new CommonResult(101,"用户已存在");

    public static CommonResult newOkResult(){
        return new CommonResult(0,"OK");
    }

}
