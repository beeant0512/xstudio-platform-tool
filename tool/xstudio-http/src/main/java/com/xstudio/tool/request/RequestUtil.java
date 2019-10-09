package com.xstudio.tool.request;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * http请求工具类
 * <p>
 *
 * @author xiaobiao on 2017/3/15.
 */
public class RequestUtil {

    /**
     * 默认content 类型
     */
    private static final String DEFAULT_CONTENT_TYPE = "application/json";
    /**
     * form表单提交类型
     */
    private static final String X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
    /**
     * 设置超时毫秒数
     */
    private static final int CONNECT_TIMEOUT = 30000;
    /**
     * 设置传输毫秒数
     */
    private static final int SOCKET_TIMEOUT = 10000;
    /**
     * 获取请求超时毫秒数
     */
    private static final int REQUEST_CONNECT_TIMEOUT = 5000;


    /**
     * 设置重用连接时间
     */
    private static final int VALIDATE_TIME = 30000;
    /**
     * 设置每个路由的基础连接数
     */
    private static final int CONNECT_ROUTE = 20;
    /**
     * 最大连接数
     */
    private static final int MAX_CONNECT = 200;

    private static final String LOCALHOST = "127.0.0.1";

    private static final String USER_AGENT = "User-Agent";

    /**
     * 发送请求的客户端单例
     */
    private static CloseableHttpClient httpClient = null;
    /**
     * 连接池管理类
     */
    private static PoolingHttpClientConnectionManager manager = null;

    /**
     * 日志
     */
    private static Logger logger = LogManager.getLogger(RequestUtil.class);

    static {
        ConnectionSocketFactory csf = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory lsf = createSSLConnSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", csf).register("https", lsf).build();
        manager = new PoolingHttpClientConnectionManager(registry);
        // 将最大连接数增加到 MAX_CONNECT
        manager.setMaxTotal(MAX_CONNECT);
        // 将每个路由基础的连接增加到 CONNECT_ROUTE
        manager.setDefaultMaxPerRoute(CONNECT_ROUTE);
        // 可用空闲连接过期时间,重用空闲连接时会先检查是否空闲时间超过这个时间，如果超过，释放socket重新建立
        manager.setValidateAfterInactivity(VALIDATE_TIME);
        // 设置socket超时时间
        SocketConfig config = SocketConfig.custom().setSoTimeout(SOCKET_TIMEOUT).build();
        manager.setDefaultSocketConfig(config);
        RequestConfig requestConf = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
                .setConnectionRequestTimeout(REQUEST_CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpClient = HttpClients.custom().setConnectionManager(manager).setDefaultRequestConfig(requestConf).setRetryHandler(
                //实现了HttpRequestRetryHandler接口的
                //public boolean retryRequest(IOException exception, int executionCount, HttpContext context)方法
                (exception, executionCount, context) -> {
                    if (executionCount >= 3) {
                        return false;
                    }
                    // 如果服务器断掉了连接那么重试
                    if (exception instanceof NoHttpResponseException) {
                        return true;
                    }
                    //不重试握手异常
                    if (exception instanceof SSLHandshakeException) {
                        return false;
                    }
                    //IO传输中断重试
                    if (exception instanceof InterruptedIOException) {
                        return true;
                    }
                    //未知服务器
                    if (exception instanceof UnknownHostException) {
                        return false;
                    }
                    // 超时就重试
                    if (exception instanceof ConnectTimeoutException) {
                        return true;
                    }
                    if (exception instanceof SSLException) {
                        return false;
                    }

                    HttpClientContext cliContext = HttpClientContext.adapt(context);
                    HttpRequest request = cliContext.getRequest();
                    // 如果请求是幂等的，就再次尝试
                    return !(request instanceof HttpEntityEnclosingRequest);
                }).build();
        if (manager != null && manager.getTotalStats() != null) {
            logger.info("客户池状态：{}", manager.getTotalStats());
        }
    }

    /**
     * SSL的socket工厂创建
     *
     * @return {@link SSLConnectionSocketFactory}
     */
    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        SSLContext context;
        try {
            // 创建TrustManager() 用于解决javax.net.ssl.SSLPeerUnverifiedException: peer not authenticated
            X509TrustManager x509m = new X509TrustManager() {

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }
            };
            context = SSLContext.getInstance(SSLConnectionSocketFactory.SSL);
            // 初始化SSLContext实例
            try {
                //最关键的必须有这一步，否则抛出SSLContextImpl未被初始化的异常
                context.init(null,
                        new TrustManager[]{x509m},
                        new java.security.SecureRandom());
            } catch (KeyManagementException e) {
                logger.error("SSL上下文初始化失败， 由于 {}", e.getLocalizedMessage());
            }
            // 创建SSLSocketFactory
            // 不校验域名 ,取代以前验证规则
            sslsf = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);
        } catch (NoSuchAlgorithmException e) {
            logger.error("SSL上下文创建失败，由于 {}", e.getLocalizedMessage());
        }
        return sslsf;
    }

    /**
     * 是否是IE
     *
     * @param request {@link HttpServletRequest}
     * @return
     */
    public static boolean isIE(HttpServletRequest request) {
        return request.getHeader(USER_AGENT).contains("MSIE");
    }

    /**
     * 是否form表单提交
     * @param request
     * @return
     */
    public static boolean isFormUrlencoded(HttpServletRequest request) {
        String contentType = request.getHeader("Content-Type");
        if (contentType != null && contentType.startsWith("application/x-www-form-urlencoded")) {
            return true;
        }

        return false;
    }

    /**
     * 获取响应状态码
     *
     * @param response {@link HttpResponse}
     * @return
     */
    public static int getStatus(HttpResponse response) {
        return response.getStatusLine().getStatusCode();
    }

    /**
     * 获取响应的body
     *
     * @param response {@link HttpResponse}
     * @return
     */
    public static String getBody(HttpResponse response) {
        // 获取返回
        HttpEntity httpEntity = response.getEntity();

        // 获取结果
        String body = "";
        try {
            body = EntityUtils.toString(httpEntity, "UTF-8");
        } catch (IOException e) {
            logger.error("获取请求返回返回失败", e);
        }
        return body;
    }

    /**
     * 获取请求服务客户端的IP
     *
     * @param request {@link HttpServletRequest}
     * @return IP
     */
    public static String getIp(HttpServletRequest request) {
        String serverIp = "unknown";
        try {
            String ip = request.getHeader("X-Forwarded-For");
            if (StringUtils.isEmpty(ip) || serverIp.equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Real-IP");
            }
            if (StringUtils.isEmpty(ip) || serverIp.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || serverIp.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || serverIp.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            int index = ip.indexOf(',');
            if (index != -1) {
                ip = ip.substring(0, index);
            }
            serverIp = "0:0:0:0:0:0:0:1".equals(ip) ? LOCALHOST : ip;
        } catch (Exception e) {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes == null) {
                try {
                    Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
                    InetAddress ip;
                    while (netInterfaces.hasMoreElements()) {
                        NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                        ip = ni.getInetAddresses().nextElement();
                        serverIp = ip.getHostAddress();
                        if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
                                && !ip.getHostAddress().contains(":")) {
                            serverIp = ip.getHostAddress();
                            break;
                        }
                    }
                } catch (Exception e1) {
                    logger.trace("InternetAddress has no more elements", e1);
                }
            }
        }
        return serverIp;
    }

    /**
     * 获取服务器信息
     */
    public static String getServerInfo() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        if (ra == null) {
            try {
                InetAddress inetAddress = InetAddress.getLocalHost();
                return inetAddress.getHostName();
            } catch (UnknownHostException e) {
                logger.trace("get server name failed", e);
            }
            return "";
        }
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        StringBuilder sb = new StringBuilder();
        sb.append("服务器名称：");
        sb.append(getServerName(request));
        sb.append("；");
        sb.append("服务器端口号：");
        sb.append(getServerPort(request));
        sb.append("；");
        sb.append("客户端IP：");
        String ip = getIp(request);
        sb.append(ip);
        sb.append("；");
        sb.append("host：");
        String host = request.getRemoteHost();
        sb.append(host);
        sb.append("；");
        sb.append("UserAgent：");
        sb.append(request.getHeader(USER_AGENT));
        return sb.toString();
    }

    /**
     * get User Agent
     *
     * @param request {@link HttpServletRequest}
     * @return user Agent
     */
    public static String getUserAgent(HttpServletRequest request) {
        String userAgent = "";
        try {
            userAgent = request.getHeader(USER_AGENT);
        } catch (Exception e) {
            logger.trace("header获取失败", e);
        }
        return userAgent;
    }

    /**
     * get server name
     *
     * @param request {@link HttpServletRequest}
     * @return String
     */
    public static String getServerName(HttpServletRequest request) {

        String serverName = "";
        try {
            serverName = request.getHeader(USER_AGENT);
        } catch (Exception e) {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes == null) {
                try {
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    serverName = inetAddress.getHostName();
                } catch (Exception e1) {
                    logger.trace("get server name failed:", e1);
                }
            }
        }
        return serverName;
    }

    /**
     * @param request {@link HttpServletRequest}
     * @return Integer
     */
    public static Integer getServerPort(HttpServletRequest request) {
        Integer port = 0;
        try {
            port = request.getServerPort();
        } catch (Exception e) {
            logger.trace("", e);
        }
        return port;
    }

    /**
     * 获取请求的Body内容
     *
     * @param request {@link HttpServletRequest}
     * @return body
     * @throws IOException
     */
    public static String getBody(HttpServletRequest request) throws IOException {
        BufferedReader br = request.getReader();
        String str;
        StringBuilder sb = new StringBuilder();
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * json输出
     *
     * @param response {@link HttpServletResponse}
     * @param object   输出对象
     */
    public static void writeJson(HttpServletResponse response, Object object) {
        write(response, object, DEFAULT_CONTENT_TYPE);
    }

    /**
     * 输出
     *
     * @param response {@link HttpServletResponse}
     * @param object   输出对象
     */
    public static void write(HttpServletResponse response, Object object, String contentType) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(contentType);
        PrintWriter out;
        try {
            out = response.getWriter();
            out.println(JSON.toJSONString(object));
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 发送HTTP_GET请求
     *
     * @param url 请求地址(含参数)
     * @return 远程主机响应正文
     * @see 1)该方法会自动关闭连接,释放资源
     * @see 2)方法内设置了连接和读取超时时间,单位为毫秒,超时或发生其它异常时方法会自动返回"通信失败"字符串
     * @see 3)请求参数含中文时,经测试可直接传入中文,HttpClient会自动编码发给Server,应用时应根据实际效果决
     * 定传入前是否转码
     * @see 4)该方法会自动获取到响应消息头中[Content-Type:text/html; charset=GBK]的charset值作为响应报文的
     * 解码字符集
     * @see 5)若响应消息头中无Content-Type属性,则会使用HttpClient内部默认的ISO-8859-1作为响应报文的解码字符
     * 集
     */
    public static ClientResponse get(String url, String param) {
        if (null != param) {
            url += "?" + param;
        }
        // 响应内容
        HttpGet httpget = new HttpGet(url);
        return res(httpget);
    }

    /**
     * 关闭系统时关闭httpClient
     */
    public static void releaseHttpClient() {
        try {
            httpClient.close();
        } catch (IOException e) {
            logger.error("关闭httpClient异常", e);
        } finally {
            if (manager != null) {
                manager.shutdown();
            }
        }
    }

    private static ClientResponse res(HttpRequestBase method) {
        HttpClientContext context = HttpClientContext.create();
        ClientResponse clientResponse = new ClientResponse();
        try (CloseableHttpResponse response = httpClient.execute(method, context)) {
            // 获取响应实体
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                Charset respCharset = ContentType.getOrDefault(entity).getCharset();
                clientResponse.setContent(EntityUtils.toString(entity, respCharset));
                clientResponse.setOrigin(response);
                EntityUtils.consume(entity);
            }
        } catch (Exception cte) {
            logger.error("请求连接超时，由于 {}", cte.getLocalizedMessage(), cte);
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }
        return clientResponse;
    }

    /**
     * get请求
     *
     * @param url 请求地址
     * @return ClientResponse
     */
    public static ClientResponse get(String url) {
        HttpGet get = new HttpGet(url);
        return res(get);
    }

    /**
     * get请求
     *
     * @param url    请求地址
     * @param cookie Cookie内容
     * @return ClientResponse
     */
    public static ClientResponse getByCookie(String url, String cookie) {
        HttpGet get = new HttpGet(url);
        if (StringUtils.isNotBlank(cookie)) {
            get.addHeader("cookie", cookie);
        }
        return res(get);
    }

    /**
     * post请求
     *
     * @param url    地址
     * @param params 参数
     * @return ClientResponse
     */
    public static ClientResponse postForm(String url, Map<String, String> params) {
        HttpPost post = new HttpPost(url);
        //   填入各个表单域的值
        List<NameValuePair> nvps = new ArrayList<>();
        for (Map.Entry<String, String> param : params.entrySet()) {
            nvps.add(new BasicNameValuePair(param.getKey(), param.getValue()));
        }
        try {
            post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("请求参数设置失败", e);
        }
        return res(post);
    }

    /**
     * post请求
     *
     * @param url     地址
     * @param data    请求的body数据
     * @param headers 请求的headers
     * @return ClientResponse
     */
    public static ClientResponse post(String url, String data, Map<String, String> headers) {
        HttpPost post = new HttpPost(url);
        if (StringUtils.isNotBlank(data)) {
            post.addHeader("Content-Type", DEFAULT_CONTENT_TYPE);
        }
        if (null != headers && !headers.isEmpty()) {
            Set<Map.Entry<String, String>> entries = headers.entrySet();
            for (Map.Entry<String, String> header : entries) {
                post.addHeader(header.getKey(), header.getValue());
            }
        }
        post.setEntity(new StringEntity(data, ContentType.APPLICATION_JSON));
        return res(post);
    }

    /**
     * post请求
     *
     * @param url  地址
     * @param data 请求的body数据
     * @return ClientResponse
     */
    public static ClientResponse post(String url, String data) {
        HttpPost post = new HttpPost(url);
        if (StringUtils.isNotBlank(data)) {
            post.addHeader("Content-Type", DEFAULT_CONTENT_TYPE);
        }
        post.setEntity(new StringEntity(data, ContentType.APPLICATION_JSON));
        return res(post);
    }

    /**
     * 文件上传
     *
     * @param url         地址
     * @param filename    文件名
     * @param contentBody 上传的内容
     * @return ClientResponse
     */
    public static ClientResponse post(String url, String filename, ContentBody contentBody) {
        HttpPost post = new HttpPost(url);
        MultipartEntityBuilder reqBuilder = MultipartEntityBuilder.create();
        reqBuilder.addPart(filename, contentBody);
        HttpEntity reqEntity = reqBuilder.build();
        post.setEntity(reqEntity);

        return res(post);
    }

    /**
     * 获取所有请求头
     *
     * @param request 请求
     * @return Map
     */
    public Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

}
