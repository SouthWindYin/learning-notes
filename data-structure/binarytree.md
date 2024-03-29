# 二叉树binary tree的原理

二叉树(BinaryTree)是n(n≥0)个结点的有限集，它或者是空集(n=0)，或者由一个根结点及两棵互不相交的、分别称作这个根的左子树和右子树的二叉树组成。

## 二叉树基本性质

二叉树第i层上的结点数目最多为2<sup>i-1</sup>(i≥1)。  
深度为k的二叉树至多有2<sup>k</sup>-1个结点(k≥1)。  
在任意一棵二叉树中，若终端结点的个数为n0，度为2的结点数为n2，则no=n2+1。

## 特殊的二叉树

### 满二叉树

一棵深度为k且有2<sup>k</sup>-1个结点的二叉树称为满二叉树。

### 完全二叉树

若一棵二叉树至多只有最下面的两层上结点的度数可以小于2，并且最下一层上的结点都集中在该层最左边的若干位置上，则此二叉树称为完全二叉树。

## 二叉搜索树（binary search tree）

若一棵二叉树的每个节点的值都大于等于其左子树中任意结点的值，小于等于其右节点任意节点的值，则此二叉树为二叉搜索树。

### 二叉树的增删查

在搜索x元素的时候，将x和根结点进行比较：
1. 如果x等于根结点，停止搜索，返回结果
2. 如果x小于根结点，搜索左子树
3. 如果x大于根结点，搜索右子树
二叉搜索树所需要的操作次数最多与树的深度相等。n个节点的二叉搜索树的深度最多为n，最少为log(n)。

### 增加结点

1. 若当前二叉树为空，则插入该元素为根节点

2. 若该元素的值大于当前二叉树的根结点值，则进入右子树

3. 若该元素的值小于当前二叉树的根结点值，则进入左子树

### 删除结点

普通删除和懒惰删除算法不同，懒惰删除是给待删除结点增加一个删除标记，以下列出为普通删除结点算法：
1. 若删除的是叶子结点，则结束
2. 若删除的是非叶结点，则搜索左子树中最大的子结点
3. 删除该结点，将该结点补到刚才删除的结点位置
4. 回到步骤1

### “平衡”问题

![二叉搜索树的平衡问题](https://ooo.0o0.ooo/2017/04/17/58f48760ee6e7.png)

在实际使用过程中，经过一系列的增删结点可能会导致右图这种线性的二叉搜索树，其速度已经没有优势了。将二叉搜索树转成平衡二叉树，是保持速度的关键。保持“平衡”需要使用平衡算法