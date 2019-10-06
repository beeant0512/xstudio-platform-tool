package com.xstudio.spring.security.params;

import com.alibaba.fastjson.JSON;
import com.xstudio.tool.request.RequestUtil;
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
        String body;
        // body参数
        try {
            body = RequestUtil.getBody(request);
        } catch (IOException e) {
           body = "";
        }
        this.ip = RequestUtil.getIp(request);
        this.userAgent = RequestUtil.getUserAgent(request);
        if (!StringUtils.isEmpty(body)) {
            Map<String, String> parse = (Map<String, String>) JSON.parse(body);
            if (null != parse) {
                Set<Map.Entry<String, String>> entries = parse.entrySet();
                for (Map.Entry<String, String> entry : entries) {
                    addExtra(entry.getKey(), entry.getValue());
                }
            }
        }
        // request parameter 参数
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String s = parameterNames.nextElement();
            addExtra(s, request.getParameter(s));
        }
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
    public void addExtra(String key, String value) {
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
    public void addHeader(String key, String value) {
        if (null == this.headers) {
            this.headers = new HashMap<>();
        }
        this.headers.put(key, value);
    }

    public void addSession(String key, Object value) {
        if (null == this.sessions) {
            this.sessions = new HashMap<>();
        }
        this.sessions.put(key, value);
    }

    /**
     * Getter for property 'ip'.
     *
     * @return Value for property 'ip'.
     */
    public String getIp() {
        return ip;
    }

    /**
     * Setter for property 'ip'.
     *
     * @param ip Value to set for property 'ip'.
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Getter for property 'userAgent'.
     *
     * @return Value for property 'userAgent'.
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * Setter for property 'userAgent'.
     *
     * @param userAgent Value to set for property 'userAgent'.
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * Getter for property 'headers'.
     *
     * @return Value for property 'headers'.
     */
    public HashMap<String, String> getHeaders() {
        return headers;
    }

    /**
     * Setter for property 'headers'.
     *
     * @param headers Value to set for property 'headers'.
     */
    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    /**
     * Getter for property 'extras'.
     *
     * @return Value for property 'extras'.
     */
    public HashMap<String, String> getExtras() {
        return extras;
    }

    /**
     * Setter for property 'extras'.
     *
     * @param extras Value to set for property 'extras'.
     */
    public void setExtras(HashMap<String, String> extras) {
        this.extras = extras;
    }

    /**
     * Getter for property 'sessions'.
     *
     * @return Value for property 'sessions'.
     */
    public HashMap<String, Object> getSessions() {
        return sessions;
    }

    /**
     * Setter for property 'sessions'.
     *
     * @param sessions Value to set for property 'sessions'.
     */
    public void setSessions(HashMap<String, Object> sessions) {
        this.sessions = sessions;
    }
}
