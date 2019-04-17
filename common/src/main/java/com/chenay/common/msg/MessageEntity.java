package com.chenay.common.msg;

import android.support.annotation.Keep;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/* *
 * @Author chenay
 * @Description 前后端统一消息定义协议 Message  之后前后端数据交互都按照规定的类型进行交互
 * {
 *   meta:{"code":code,“msg”:message}
 *   data:{....}
 * }
 * @Date 10:48 2018/2/14
 */
@Keep
public class MessageEntity<B extends BaseBean> implements Serializable {


    public static final String KEY_SUCCESS = "success";
    public static final String KEY_CODE = "code";
    public static final String KEY_RTN_CODE = "rtnCode";
    public static final String KEY_MSG = "msg";
    public static final String KEY_TIMESTAMP = "timestamp";

    public static final String KEY_DATA = "bodyList";
    public static final String KEY_BEAN = "javaBean";

    // 消息头meta 存放状态信息 code message
    private Map<String, Object> meta;
    // 消息内容  存储实体交互数据
    private Map<String, Object> data;

    private List<B> beanList;

    public Map<String, Object> getMeta() {
        return meta;
    }

    public MessageEntity<B> addMeta(String key, Object object) {
        if (meta == null) {
            meta = new HashMap<>();
        }
        this.meta.put(key, object);
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public MessageEntity<B> addData(String key, Object object) {
        if (data == null) {
            data = new HashMap<>();
        }
        data.put(key, object);
        return this;
    }

    public List<B> getBeanList() {
        return beanList;
    }

    public MessageEntity<B> addBean(B bean) {
        if (beanList == null) {
            beanList = new ArrayList<>();
        }
        beanList.add(bean);
        return this;
    }

    public MessageEntity<B> addBeanList(List<B> list) {
        if (beanList == null) {
            beanList = list;
        } else {
            for (int i = 0; i < list.size(); i++) {
                addBean(list.get(i));
            }
        }
        return this;
    }


    public MessageEntity addData(CodeEntity codeEntity, Object object) {
        addData(codeEntity.getKey(), object);
        return this;
    }


    public MessageEntity ok() {
        return ok(CodeEntity.SUCCESS);

    }

    public MessageEntity error() {
        return error(CodeEntity.ERROR);
    }

    public MessageEntity ok(CodeEntity codeEntity) {
        this.addMeta(KEY_SUCCESS, Boolean.TRUE);

        this.addMeta(KEY_CODE, codeEntity.getCode());
        this.addMeta(KEY_RTN_CODE, codeEntity.getCode() + "");
        this.addMeta(KEY_MSG, codeEntity.getMsg());
        this.addMeta(KEY_TIMESTAMP, new Timestamp(System.currentTimeMillis()));
        return this;
    }

    public MessageEntity error(CodeEntity codeEntity) {
        this.addMeta(KEY_SUCCESS, Boolean.FALSE);
        this.addMeta(KEY_CODE, codeEntity.getCode());
        this.addMeta(KEY_RTN_CODE, codeEntity.getCode() + "");
        this.addMeta(KEY_MSG, codeEntity.getMsg());
        this.addMeta(KEY_TIMESTAMP, new Timestamp(System.currentTimeMillis()));
        return this;
    }


    public MessageEntity ok(int statusCode, String statusMsg) {
        this.addMeta(KEY_SUCCESS, Boolean.TRUE);
        this.addMeta(KEY_CODE, statusCode);
        this.addMeta(KEY_RTN_CODE, statusCode + "");
        this.addMeta(KEY_MSG, statusMsg);
        this.addMeta(KEY_TIMESTAMP, new Timestamp(System.currentTimeMillis()));
        return this;
    }

    public MessageEntity error(int statusCode, String statusMsg) {
        this.addMeta(KEY_SUCCESS, Boolean.FALSE);
        this.addMeta(KEY_CODE, statusCode);
        this.addMeta(KEY_RTN_CODE, statusCode + "");
        this.addMeta(KEY_MSG, statusMsg);
        this.addMeta(KEY_TIMESTAMP, new Timestamp(System.currentTimeMillis()));
        return this;
    }

    public String getMsg() {
        return (String) meta.get(KEY_MSG);
    }

    public String getRtnCode() {
        return (String) meta.get(KEY_RTN_CODE);
    }
}
