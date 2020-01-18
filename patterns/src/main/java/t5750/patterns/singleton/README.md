# Singleton

## Singleton Pattern Structure

![Singleton Pattern Structure](https://www.ibm.com/developerworks/cn/java/designpattern/singleton/fig1.gif)

## 实现 Singleton 模式的办法通常有三种.

一. 用静态方法实现 Singleton 这种方法是使用静态方法来监视实例的创建.为了防止创建一个以上的实例,我们最好把构造器声明为 private.

二. 以静态变量为标志实现 Singleton 在类中嵌入一个静态变量做为标志,每次都在进入构造器的时候进行检查.

三. 用注册器机制来创建 Singleton 首先用集合中的Hashtable 和Enumeration来实现addItem(Object key, Object value),getItem(Object key), ,removeItem(Object key)等方法实现一个管理器,将key和value一一关联起来,客户程序员创建实例前首先用addItem方法进行注册,再用getItem方法获取实例.Hashtable中的key是唯一的,从而保证创建的实例是唯一的,具体实现限于篇幅不再细说,在Prototype模型的应用一文中我将会给出一个实现注册器的代码.用注册器机制来创建 Singleton模式的好处是易于管理,可以同时控制多个不同类型的Singleton 实例.

## Links
- [在Java中应用设计模式--Singleton](https://www.ibm.com/developerworks/cn/java/designpattern/singleton/index.html)
- [Simply Singleton](http://www.javaworld.com/article/2073352/core-java/simply-singleton.html)
