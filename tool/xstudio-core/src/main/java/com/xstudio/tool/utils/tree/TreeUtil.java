package com.xstudio.tool.utils.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaobiao
 * @version 2019/6/13
 */
public class TreeUtil {
    public static List<Tree> build(List<? extends Tree> list) {
        List<Tree> roots = new ArrayList<>();
        // 所有列表的构建子节点项
        for (Tree top : list) {
            // 获取第一层节点
            for (Tree childMenu : list) {
                if (top.getId().equals(childMenu.getPid())) {
                    if (top.getChildren() == null) {
                        top.setChildren(new ArrayList<>());
                    }
                    top.getChildren().add(childMenu);
                }
            }
        }
        // 获取grade菜单是1的节点
        for (Tree topMenu : list) {
            if (topMenu.getGrade() == 1) {
                roots.add(topMenu);
            }
        }
        return roots;
    }
}
