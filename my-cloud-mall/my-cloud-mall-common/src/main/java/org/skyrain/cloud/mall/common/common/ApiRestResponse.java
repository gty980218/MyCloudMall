package org.skyrain.cloud.mall.common.common;

import io.swagger.models.auth.In;
import org.skyrain.cloud.mall.common.exception.CloudMallException;
import org.skyrain.cloud.mall.common.exception.CloudMallExceptionEnum;

public class ApiRestResponse<T> {
    Integer status;

    String msg;

    T data;

    static final Integer successStatus=10000;

    static final String successMsg="SUCCESS";

    public ApiRestResponse(){
        this.status=successStatus;
        this.msg=successMsg;
        this.data=null;
    }
    public ApiRestResponse(T data){
        this.status=successStatus;
        this.msg=successMsg;
        this.data=data;
    }
    public ApiRestResponse(Integer status,String msg){
        this.status=status;
        this.msg=msg;
    }
    public static <T> ApiRestResponse<T> success(){
        return new ApiRestResponse<>();
    }
    public static <T> ApiRestResponse<T> success(T data){
        return new ApiRestResponse<>(data);
    }
    public static <T> ApiRestResponse<T> error(CloudMallExceptionEnum e){
        return new ApiRestResponse<>(e.getCode(),e.getMsg());
    }
    public static <T> ApiRestResponse<T> error(Integer code,String msg){
        return new ApiRestResponse(code,msg);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static Integer getSuccessStatus() {
        return successStatus;
    }

    public static String getSuccessMsg() {
        return successMsg;
    }

    @Override
    public String toString() {
        return "ApiRestResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
