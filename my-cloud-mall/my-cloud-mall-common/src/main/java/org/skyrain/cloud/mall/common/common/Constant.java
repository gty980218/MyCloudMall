package org.skyrain.cloud.mall.common.common;

import org.skyrain.cloud.mall.common.exception.CloudMallException;
import org.skyrain.cloud.mall.common.exception.CloudMallExceptionEnum;

public class Constant {
    private static final String salt="%jgvyt@yrcii";

    public static final String CURRENT_USER="current_user";

    public static final Integer SELECTED=1;

    public static final Integer UNSELECTED=0;

    public static final Integer ONSALE=1;

    public static final Integer NOTONSALE=0;

    public static String getSalt() {
        return salt;
    }


    public enum Role{
        USER(1),
        ADMIN(2);

        Integer role;

        Role(Integer role) {
            this.role = role;
        }

        public Integer getRole() {
            return role;
        }
    }
//订单状态: 0用户已取消，10未付款（初始状态），20已付款，30已发货，40交易完成
    public enum OrderStatusEnum{
        CANCELED(0,"用户已取消"),
        UNPAID(10,"未付款"),
        PAID(20,"已付款"),
        SHIPPED(30,"已发货"),
        FINISHED(40,"交易完成");


        Integer code;
        String status;

        OrderStatusEnum(Integer code, String status) {
            this.code = code;
            this.status = status;
        }

        public static OrderStatusEnum valueOf(Integer code){
            for(OrderStatusEnum orderStatusEnum:values()){
                if(orderStatusEnum.getCode()==code){
                    return orderStatusEnum;
                }
            }
            throw new CloudMallException(CloudMallExceptionEnum.NO_ENUM);
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
}



}
