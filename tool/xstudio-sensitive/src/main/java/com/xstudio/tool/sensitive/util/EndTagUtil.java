package com.xstudio.tool.sensitive.util;


import com.xstudio.tool.sensitive.KeyWord;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * 关键词尾节点
 *
 * @beeant xiaobiao
 * @createDate 2016年5月22日
 */
public class EndTagUtil implements Serializable {

    /**
     * 尾节点key
     */
    public static final String TREE_END_TAG = "end";
    /**
     *
     */
    private static final long serialVersionUID = 8278503553932163596L;

    public static Map<String, Map> buind(KeyWord KeyWord) {
        Map<String, Map> endTag = new HashMap<String, Map>();
        endTag.put(TREE_END_TAG, new HashMap<String, String>());
        return endTag;
    }

}
