正确的使用单例模式！
    不要new单例模式对象
    使用getInstance()方法获取单例对象

被服务的Instance端口掌握在自己手里
    服务Instance的端口要注意管理分配

我们希望有tcp的明确 却不希望有tcp的繁琐 所以设计了这种模式

bean 里面不能有单例！

11：24
client能够收到server的新房间
client的加入申请传不回去


注意流程！
服务端：
开监听
收
发
客户端：
发
开监听
收

储存步数的vector再遍历时遇到了麻烦，似乎没有同时访问和内部修改但还是加上了：
<code>
synchronized(chesses) {//使用同步代码块处理chesses</br>
for (Chess c : chesses) {
if (c.getCol() == x && c.getRow() == y) {
return c.getCondition();
}
}
}
</code>

记住三大件：本地储存、网络连接、视图刷新：少一样都不行

使用while循环阻塞等待状态改变是多此一举，似乎就因此if判断没有用
如果在gameloop中判断输赢，那么它们就会判断结果一样，尽管实际的输赢肯定不同，
但是他们进入了同一分支，这样就会导致输赢的判断不准确。