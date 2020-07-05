# DoubleSuckTopDemo
仿京东首页双层吸顶效果demo

今天已经是端午节假日最后一天了，快乐的时光总是很短暂的.<br><br>
端午节期间用叮咚买菜App买菜，发现他们的App首页有两个吸顶功能，而下面的tabLayout那个吸顶，居然把上部分和下部分的手指滑动事件给阻断了，没能将滑动事件传递给到另一部分接着滑动.<br><br>
叮咚买菜应该用户量不少，但对于我个人来说这用户体验还是不大能接受，用着感觉不太爽，可能是大厂的APP太溜了的原因吧，像京东这些也有双层的吸顶，但就不会有这种问题.<br><br>
这放假疫情期间也不太敢出去，就打算来研究研究这个双层的吸顶效果.

所以仿着京东首页的效果，实现了双层吸顶的效果，并且不会阻断滑动事件的传递，下面看下效果图：<br>
![image](https://github.com/weioule/DoubleSuckTopDemo/blob/master/app/img/img01.jpg)&nbsp;&nbsp;
![image](https://github.com/weioule/DoubleSuckTopDemo/blob/master/app/img/img02.jpg)&nbsp;&nbsp;
![image](https://github.com/weioule/DoubleSuckTopDemo/blob/master/app/img/img03.jpg)&nbsp;&nbsp;

思路解析：

第一个吸顶采用搜索框悬浮view固定显示隐藏实现，第二个吸顶是原生的CollapsingToolbarLayout + AppBarLayout实现，本来想着用两层CollapsingToolbarLayout + AppBarLayout嵌套，但经过验证后确实是行不通的.

第二个吸顶的位置得悬停在第一个吸顶的底部，所以用了Toolbar做占位，它的高度正是悬浮的搜索框的高度.

因为首页大多都会有刷新，所以还要考虑到与刷新的滑动冲突，这里设置了监听只有AppBarLayout是完全关闭状态时才可用.
