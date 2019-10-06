package com.xstudio.tool.sensitive;

import com.xstudio.tool.sensitive.conf.Config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


/**
 * 
 * 简单的敏感词处理器，从配置文件读取敏感词初始化，<br>
 * 使用者只需要在classpath放置sensitive-word.properties敏感词文件即可
 * 
 * @author beeant
 * @version 2019-03-31
 *
 */
public class SimpleKeyWordSeekerProcessor extends KeyWordSeekerManage {

    private static volatile SimpleKeyWordSeekerProcessor instance;

    /**
     * 获取实例
     * 
     * @return
     */
    public static SimpleKeyWordSeekerProcessor newInstance() {
        if (null == instance) {
            synchronized (SimpleKeyWordSeekerProcessor.class) {
                if (null == instance) {
                    instance = new SimpleKeyWordSeekerProcessor();
                }
            }
        }
        return instance;
    }

    /**
     * 私有构造器
     */
    private SimpleKeyWordSeekerProcessor() {
        initialize();
    }

    /**
     * 初始化敏感词
     */
    private void initialize() {
        Map<String, String> map = Config.newInstance().getAll();
        Set<Entry<String, String>> entrySet = map.entrySet();

        Map<String, KeyWordSeeker> seekers = new HashMap<String, KeyWordSeeker>();
        Set<KeyWord> kws;

        for (Entry<String, String> entry : entrySet) {
            String[] words = entry.getValue().split(",");
            kws = new HashSet<KeyWord>();
            for (String word : words) {
                kws.add(new KeyWord(word));
            }
            seekers.put(entry.getKey(), new KeyWordSeeker(kws));
        }
        this.seekers.putAll(seekers);
    }
}
