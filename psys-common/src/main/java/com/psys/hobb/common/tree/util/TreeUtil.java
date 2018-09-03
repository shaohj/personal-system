package com.psys.hobb.common.tree.util;

import java.util.List;
import java.util.function.Predicate;

import com.psys.hobb.common.tree.bean.ITreeNode;
import com.psys.hobb.common.sys.util.AssertUtil;

/**
 * 树工具类
 * 编  号：<br/>
 * 名  称：TreeUtil<br/>
 * 描  述：<br/>
 * 完成日期：2017年12月9日 下午7:18:56<br/>
 * 编码作者：shaohj<br/>
 */
public class TreeUtil {

	 /** 
	  * 两层循环实现建树 
	  * @Title: bulid 
	  * @param treeNodes 传入的需要构建树节点的数据集合
	  * @param rootPredicate 根节点判断条件
	  * @return
	  */
	public static <T> T buildByHasRootTree(List<? extends ITreeNode<T>> treeNodes, Predicate<? super ITreeNode<T>> rootPredicate) {
		AssertUtil.notNull(treeNodes, "treeNodes参数不能为空");
		
		/* 找到并返回根节点 */
		ITreeNode<T> rootNode = treeNodes.stream().filter(rootPredicate)
        		.findFirst().orElseThrow(IllegalArgumentException::new); 
        
		/* 两层循环遍历treeNodes,追加每个节点的子节点 */
        for (ITreeNode<T> treeNode : treeNodes) {  
            for (ITreeNode<T> it : treeNodes) {  
                if (treeNode.getTreeId().equals(it.getTreeParentId())) {  
                    treeNode.addChild(it.getRealNode());  
                }  
            }  
        }  
        
        return rootNode.getRealNode();  
    }  
	
	public static <T> T buildByRecursive(List<? extends ITreeNode<T>> treeNodes, ITreeNode<T> rootNode) {
		AssertUtil.notNull(rootNode, "rootNode参数不能为空");
    	AssertUtil.notNull(treeNodes, "treeNodes参数不能为空");
		
        for (ITreeNode<T> treeNode : treeNodes) {  
            if (rootNode.getTreeId().equals(treeNode.getTreeParentId())) {
            	rootNode.addChild(findChildren(treeNode, treeNodes).getRealNode());  
            }  
        }  
        return rootNode.getRealNode();  
    }
	
	/**
	 * 使用递归方法建树,treeNodes中含有树的root节点
	 * @Title: buildRecursiveByHasRootTree 
	 * @param treeNodes 传入的需要构建树节点的数据集合
	 * @param rootPredicate 根节点判断条件
	 * @return
	 */
    public static <T> T buildByRecursiveByHasRootTree(List<? extends ITreeNode<T>> treeNodes, Predicate<? super ITreeNode<T>> rootPredicate) {
    	AssertUtil.notNull(treeNodes, "treeNodes参数不能为空");
		
		//设置根节点
        ITreeNode<T> rootNode = treeNodes.stream().filter(rootPredicate)
        		.findFirst().orElseThrow(IllegalArgumentException::new);
    	
        for (ITreeNode<T> treeNode : treeNodes) {  
            if (rootNode.getTreeId().equals(treeNode.getTreeParentId())) {
            	rootNode.addChild(findChildren(treeNode, treeNodes).getRealNode());  
            }  
        }  
        return rootNode.getRealNode();  
    }  
  
    /** 
     * 递归查找子节点 
     * @param treeNodes 
     * @return 
     */  
    public static <T> ITreeNode<T> findChildren(ITreeNode<T> treeNode, List<? extends ITreeNode<T>> treeNodes) {  
        for (ITreeNode<T> it : treeNodes) {  
            if(treeNode.getTreeId().equals(it.getTreeParentId())) {  
            	treeNode.addChild(findChildren(it,treeNodes).getRealNode());
            }  
        }  
        return treeNode;  
    }  
	
}
