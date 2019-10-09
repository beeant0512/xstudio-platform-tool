package com.xstudio.spring.security.params;

import com.alibaba.fastjson.JSON;
import com.xstudio.tool.request.RequestUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author xiaobiao
 * @version 2019/6/4
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginParams extends WebAuthenticationDetails {
    private static final long serialVersionUID = 6972695605279837143L;
    /**
     * ip
     */
    private String ip;

    /**
     * userAgent
     */
    private String userAgent;

    /**
     * 请求参数头
     */
    private HashMap<String, String> headers;

    /**
     * 其他参数
     */
    private HashMap<String, String> extras;

    /**
     * session
     */
    private HashMap<String, Object> sessions;

    public LoginParams(HttpServletRequest request) {
        super(request);
        if (RequestUtil.isFormUrlencoded(request)) {
            // request parameter 参数
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String s = parameterNames.nextElement();
                addExtra(s, request.getParameter(s));
            }
        } else {
            String body;
            // body参数
            try {
                body = RequestUtil.getBody(request);
            } catch (IOException e) {
                body = "";
            }
            if (!StringUtils.isEmpty(body)) {
                Map<String, String> parse = (Map<String, String>) JSON.parse(body);
                if (null != parse) {
                    Set<Map.Entry<String, String>> entries = parse.entrySet();
                    for (Map.Entry<String, String> entry : entries) {
                        addExtra(entry.getKey(), entry.getValue());
                    }
                }
            }
        }

        this.ip = RequestUtil.getIp(request);
        this.userAgent = RequestUtil.getUserAgent(request);

        // header 参数
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            addHeader(key, value);
        }

        // session参数
        Enumeration<String> sessionsNames = request.getSession().getAttributeNames();
        while (sessionsNames.hasMoreElements()) {
            String s = sessionsNames.nextElement();
            addSession(s, request.getSession().getAttribute(s));
        }
    }

    /**
     * 获取属性
     *
     * @param key 属性键
     * @return 属性值
     */
    public Object get(String key) {
        if (null == this.extras) {
            return "";
        }

        return this.extras.get(key);
    }

    /**
     * 添加参数
     *
     * @param key   key
     * @param value 值
     */
    private void addExtra(String key, String value) {
        if (null == this.extras) {
            this.extras = new HashMap<>();
        }
        this.extras.put(key, value);
    }

    /**
     * 添加请求头
     *
     * @param key   key
     * @param value 值
     */
    private void addHeader(String key, String value) {
        if (null == this.headers) {
            this.headers = new HashMap<>();
        }
        this.headers.put(key, value);
    }

    private void addSession(String key, Object value) {
        if (null == this.sessions) {
            this.sessions = new HashMap<>();
        }
        this.sessions.put(key, value);
    }
}
