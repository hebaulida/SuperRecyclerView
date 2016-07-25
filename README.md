# SuperRecyclerView
试试RecyclerView
* 使用虚拟的数据，通过recycleView显示出来
* 实现了下拉刷新，上拉加载更多功能
上拉监听，滑动是否结束，最后可见Item+1与ItemCount比较，当停止滑动，并且滑动到列表快要结束时，进行加载动作。
在recycleView的底部添加footview，显示加载中、上拉加载更多字样。
* 实现了监听点击事件
点击弹出一个toast，长点击删除该item
* 让item显示不同布局，好吧，其实在加footview的时候就已经算是这种方法了

