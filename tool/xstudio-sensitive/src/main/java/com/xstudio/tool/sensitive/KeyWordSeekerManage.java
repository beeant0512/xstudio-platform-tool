package com.xstudio.tool.sensitive;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * 敏感词管理器
 * 
 * @author beeant
 * @version 2019-03-31
 *
 */
public class KeyWordSeekerManage {

    /**
     * 敏感词模块. key为模块名，value为对应的敏感词搜索器
     */
    protected Map<String, KeyWordSeeker> seekers = new ConcurrentHashMap<String, KeyWordSeeker>();

    /**
     * 初始化
     */
    public KeyWordSeekerManage() {
    }

    /**
     * 
     * @param seekers
     */
    public KeyWordSeekerManage(Map<String, KeyWordSeeker> seekers) {
        this.seekers.putAll(seekers);
    }

    /**
     * 获取一个敏感词搜索器
     * 
     * @param wordType
     * @return
     */
    public KeyWordSeeker getKWSeeker(String wordType) {
        return seekers.get(wordType);
    }

    /**
     * 加入一个敏感词搜索器
     * 
     * @param wordType
     * @param keyWordSeeker
     * @return
     */
    public void putKWSeeker(String wordType, KeyWordSeeker keyWordSeeker) {
        seekers.put(wordType, keyWordSeeker);
    }

    /**
     * 移除一个敏感词搜索器
     * 
     * @param wordType
     * @return
     */
    public void removeKWSeeker(String wordType) {
        seekers.remove(wordType);
    }

    /**
     * 清除空搜索器
     */
    public void clear() {
        seekers.clear();
    }

}
