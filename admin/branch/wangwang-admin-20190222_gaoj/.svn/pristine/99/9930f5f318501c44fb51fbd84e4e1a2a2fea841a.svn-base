package com.sandu.util;

import com.sandu.api.restexture.model.bo.TreeBO;
import com.sandu.api.restexture.output.Tree;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author  by bvvy
 */
public class TreeUtil {

    public static List<Tree> genTree(List<TreeBO> allItem) {
        List<TreeBO> parents = allParentsTree(allItem);
        List<Tree> trees = new ArrayList<>();
        for (TreeBO parent : parents) {
            trees.add(genTree(parent, allItem));
        }
        return trees;
    }

    private static Tree genTree(TreeBO parent, List<TreeBO> allItems) {
        final List<Tree> childs = new ArrayList<>();
        Tree tparent = new Tree();
        tparent.setLabel(parent.getLabel());
        tparent.setValue(parent.getValue());
        for (int i = 0; i < allItems.size(); i++) {
            TreeBO item = allItems.get(i);
            if (item.getPid().equals(parent.getId())) {
                Tree child = genTree(item, allItems);
                childs.add(child);
            }

        }
        tparent.setChildren(childs);
        return tparent;
    }

    private  static  List<TreeBO> allParentsTree(List<TreeBO> allItem) {
        List<TreeBO> parents = allItem.stream()
                .filter(treeBO -> treeBO.getPid() == null).collect(toList());
        allItem.removeAll(parents);
        return parents;
    }

    public static  void main(String[] args) {
        List<TreeBO> treeBOS = new ArrayList<TreeBO>() {{

            add(TreeBO.builder().label("一级菜单").value("1").id("1").build());
            add(TreeBO.builder().label("一级菜单").value("2").id("2").build());
            add(TreeBO.builder().label("二级菜单").value("3").id("3").pid("1").build());
            add(TreeBO.builder().label("二级菜单").value("4").id("4").pid("1").build());
            add(TreeBO.builder().label("二级菜单").value("5").id("5").pid("1").build());
            add(TreeBO.builder().label("二级菜单").value("6").id("6").pid("2").build());
            add(TreeBO.builder().label("二级菜单").value("7").id("7").pid("2").build());
            add(TreeBO.builder().label("san级菜单").value("9").id("9").pid("3").build());
        }};
        System.out.println(genTree(treeBOS));
    }
}
