package com.xstudio.tool.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xstudio.tool.enums.EnError;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 统一消息返回对象
 * <p>
 *
 * @author xiaobiao on 2016/12/28.
 */
@Data
public class Msg<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code = 0;

    /**
     * 返回的数据，可以任意集合或对象
     */
    private T data;

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 结果说明
     */
    private String msg = "";

    private List<String> msgs;

    public Msg() {
    }

    public Msg(EnError error) {
        this.code = error.getCode();
        this.msg = error.getDescription();
    }

    public Msg(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Msg(T data) {
        this.data = data;
    }

    public Msg(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public Boolean getSuccess() {
        return 0 == this.code;
    }

    /**
     * 设置结果
     *
     * @param result
     */
    public void setResult(EnError result) {
        this.code = result.getCode();
        this.msg = result.getDescription();
    }

    /**
     * 设置结果
     *
     * @param code
     * @param msg
     */
    public void setResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 设置失败消息
     *
     * @param errorMsg
     */
    public void setErrorResult(Msg errorMsg) {
        this.msg = errorMsg.getMsg();
        this.msgs = errorMsg.getMsgs();
        this.code = errorMsg.getCode();
    }

    public void setMessage(Integer code, String msg, Object obj) {
        this.code = code;
        this.data = JSON.parseObject(JSON.toJSONString(obj), new TypeReference<T>() {
        });
        this.msg = msg;
    }

    public void addMsg(String msg) {
        if (CollectionUtils.isEmpty(msgs)) {
            msgs = new ArrayList<>();
        }
        msgs.add(msg);
    }
}
