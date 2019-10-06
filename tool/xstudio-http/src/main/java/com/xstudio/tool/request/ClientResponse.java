package com.xstudio.tool.request;

import lombok.Data;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicStatusLine;

import java.io.Serializable;

/**
 * @author xiaobiao
 * @version 2019/6/11
 */
@Data
public class ClientResponse implements Serializable {
    /**
     * 请求对象
     */
    private CloseableHttpResponse origin;

    /**
     * 返回内容
     */
    private String content;

    /**
     * 获取statusLine
     *
     * @return {@see StatusLine}
     */
    public StatusLine statusLine() {
        if (null == this.getOrigin()) {
            return new BasicStatusLine(
                    HttpVersion.HTTP_1_1,
                    500,
                    "origin为空");
        }
        return this.getOrigin().getStatusLine();
    }

    /**
     * 获取statusCode
     *
     * @return {@see StatusLine}
     */
    public int statusCode() {
        if (null == this.getOrigin()) {
            return 500;
        }
        return this.getOrigin().getStatusLine().getStatusCode();
    }
}
