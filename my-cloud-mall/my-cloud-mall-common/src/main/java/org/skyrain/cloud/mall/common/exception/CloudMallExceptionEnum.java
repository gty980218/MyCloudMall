package org.skyrain.cloud.mall.common.exception;

public enum CloudMallExceptionEnum {
    USERNAME_EXISTED(10001,"用户名存在"),
    PASSWORD_EMPTY(10002,"密码不能为空"),
    PASSWORD_TOO_SHORT(10003,"密码不能少于8位"),
    USER_EMPTY(10004,"用户名不能为空"),
    USER_NOT_EXISTED(10005,"用户不存在"),
    PASSWORD_ERROR(10006,"密码错误"),
    NEED_LOGIN(10007,"用户未登录"),
    SYSTEM_ERROR(20000,"系统异常"),
    UPDATE_FAILED(10008,"更新失败"),
    NEED_ADMIN(10009,"需要管理员登录"),
    CATEGORY_EXISTED(10010,"该分类已存在"),
    TYPE_ERROR(10011,"分类层级不能超过三级"),
    INSERT_FAILED(10012,"插入数据失败"),
    CATEGORY_NOT_EXISTED(10013,"该分类不存在"),
    DELETE_FAILED(10014,"删除失败"),
    PRODUCT_EXISTED(10015,"该商品已存在"),
    PRICE_ERROR(10016,"价格不得小于零"),
    STOCK_ERROR(10017,"库存不得小于零"),
    PRODUCT_NOT_EXISTED(10018,"商品不存在"),
    BATCH_ERROR(10019,"批量操作失败"),
    STOCK_NOT_ENOUGH(10020,"库存不足"),
    CART_EMPTY(10021,"购物车为空"),
    PRODUCT_NOT_ON_SALE(10022,"商品已下架"),
    NO_ENUM(10023,"枚举异常"),
    ORDER_NOT_EXISTED(10024,"订单不存在"),
    ORDER_ITEM_EMPTY(10025,"订单商品为空"),
    ORDER_USER_NOT_MATCH(10026,"用户订单不匹配"),
    ORDER_CANCEL_FAILED(10027,"订单取消失败"),
    ORDER_PAY_FAILED(10028,"订单支付失败"),
    ORDER_DELIVERED_FAILED(10029,"发货失败"),
    ORDER_NOT_DELIVERED(10030,"订单未发货"),
    ORDER_FINISHED_FAILED(10031,"订单完结失败"),
    ORDER_NOT_PAID(10032,"订单未付款"),
    REQUEST_PARAM_ERROR(20001,"请求参数错误");

    Integer code;

    String message;

    CloudMallExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }
}
