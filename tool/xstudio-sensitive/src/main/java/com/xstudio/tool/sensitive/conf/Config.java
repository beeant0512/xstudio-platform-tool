package com.xstudio.tool.sensitive.conf;

import com.xstudio.tool.exception.EmptyKeyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 配置工具类-单例<br>
 *
 * @author beeant
 * @version 2019-03-31
 */
public final class Config {
    private static Logger logger = LogManager.getLogger(Config.class);

    /**
     * 实例-volatile
     */
    private static volatile Config conf;

    /**
     * 配置文件名
     */
    private final String CONF_FILE_NAME = "sensitive-word.properties";

    /**
     * 缓存配置数据
     */
    private Map<String, String> cacheConfig = new HashMap<String, String>();

    /**
     * 当前部署环境根元素
     */
    private String root;

    private Config() {
        InputStream in = null;
        try {
            in = getClass().getClassLoader().getResourceAsStream(CONF_FILE_NAME);
            Properties prop = new Properties();
            prop.load(in);

            // 一次性装载
            Set<Object> keySet = prop.keySet();
            Object value;
            for (Object key : keySet) {
                value = prop.get(key);
                cacheConfig.put(String.valueOf(key), String.valueOf(value));
            }
            // root根元素配置
            if (null != cacheConfig.get("root") && !"".equals(cacheConfig.get("root"))) {
                root = cacheConfig.get("root");
            }
        } catch (IOException e) {
            logger.error("", e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("", e);
            }
        }
    }

    /**
     * 获取唯一实例
     *
     * @return
     */
    public static Config newInstance() {
        if (null == conf) {
            synchronized (Config.class) {
                if (null == conf) {
                    conf = new Config();
                }
            }
        }
        return conf;
    }

    /**
     * 获取全部配置数据
     *
     * @return
     */
    public Map<String, String> getAll() {
        return cacheConfig;
    }

    /**
     * 基于root根元素的配置获取.<br>
     * key格式：<br>
     * 如配置为:develop.img_upload_server_path 则key为：img_upload_server_path<br>
     * 如配置为:img_upload_server_path 则key为：img_upload_server_path<br>
     * 默认以root.key获取.获取不到则直接根据key获取<br>
     * 如果未配置root元素，则直接以key获取<br>
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        String value = null;
        // root为前缀获取
        if (null != root) {
            value = cacheConfig.get(root + "." + key);
        }
        // 无前缀.直接key获取
        if (null == value || "".equals(value)) {
            value = cacheConfig.get(key);
        }
        if (null == value) {
            throw new EmptyKeyException("config key is not found !");
        }
        return value;
    }

    /**
     * 获取int
     *
     * @param key
     * @return
     */
    public int getInt(String key) {
        String propertie = getString(key);
        return Integer.valueOf(propertie);
    }

    /**
     * 获取Long
     *
     * @param key
     * @return
     */
    public long getLong(String key) {
        String propertie = getString(key);
        return Long.valueOf(propertie);
    }

    /**
     * 获取Boolean
     *
     * @param key
     * @return
     */
    public boolean getBoolean(String key) {
        String propertie = getString(key);
        return Boolean.valueOf(propertie);
    }

    /**
     * 当前是运行在生产环境
     *
     * @return
     */
    public boolean isRuningProduc() {
        return null == root || "".equals(root) ? false : root.endsWith("produc");
    }
}
