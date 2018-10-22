# Redis笔记

## 1.1 NoSQL简介
NoSQL，泛指非关系型数据库，四大分类：
- 键值(Key-Value)存储数据库：这类数据库主要会用到一个哈希表，这个表中有一个特定的键和一个指针指向特定的数据。如Redis，Voldemort，Oracle BDB
- 列存储数据库：通常是用来应对分布式存储的海量数据。键仍然存在，但是它们的特点是指向了多个列。如HBase，Riak
- 文档型数据库：该类型的数据模型是版本化的文档，半结构化的文档以特定的格式存储，比如JSON。文档数据库可以看作是键值数据库的升级版，允许之间嵌套键值。而且，文档数据库比键值数据库的查询效率更高。如CouchDB，MongoDB
- 图形(Graph)数据库：该数据库同其它数据不同，它是使用灵活的图形模型，并且能够扩展到多个服务器上。

NoSQL数据库没有标准的查询语句(SQL)，因此，进行数据库查询需要定制数据模型。许多NoSQL数据库都有REST式的数据接口或者查询API。如Neo4J，InfoGrid，Infinite Graph

## 1.2 非关系型数据库特点
1. 数据模型比较简单
1. 需要灵活性更强的IT系统
1. 对数据库性能要求较高
1. 不需要高度的数据一致性
1. 对于给定key，比较容易映射复杂值的环境

## 1.3 Redis简介
优点：
- 对数据高并发读写
- 对海量数据的高效率存储和访问
- 对数据的可扩展性和高可用性

缺点：
- Redis（ACID处理非常简单）
- 无法做到太复杂的关系数据库模型

Redis是以key-value store存储，data structure service数据结构服务器。数据都是缓存在内存中，也可以周期性地把更新的数据写入硬盘或者把修改操作写入追加到文件。3种
1. 主从
1. 哨兵
1. 集群

- Redis：多实例，串行
- Memcached：单实例，并行

## 1.4 Redis的安装
[Redis下载](https://redis.io/download)
1. 安装gcc，把redis-3.0.0-rc2.tar.gz放到/usr/local文件夹下
1. 解压`tar -zxvf redis-3.0.0-rc2.tar.gz`
1. 进入redis-3.0.0目录下，进行编译`make`
1. 键入src安装`make install`，验证（`ll`查看src目录有redis-server、redis-cli即可）
1. 建立2个文件夹存放Redis命令和配置文件
    - `mkdir -p /usr/local/redis/etc`
    - `mkdir -p /usr/local/redis/bin`
1. 把redis-3.0.0下的redis.conf移动到/usr/local/redis/etc下，
    - `cp redis.conf /usr/local/redis/etc/`
1. 把redis-3.0.0/src里的mkreleasehdr.sh、redis-benchmark、redis-check-aof、redis-check-dump、redis-cli、redis-server文件移动到bin下，命令：
    - `mv mkreleasehdr.sh redis-benchmark redis-check-aof redis-check-dump redis-cli redis-server /usr/local/redis/bin`
1. 启动时指定配置文件：`./redis-server /usr/local/redis/etc/redis.conf`（注意要使用后台启动，修改redis.conf里的daemonize改为yes）
1. 验证启动是否成功：
    - `ps -ef | grep redis`查看是否有Redis服务，或查看端口：`netstat -tunpl | grep 6379`
    - 进入Redis客户端`./redis-cli`，退出客户端`quit`
    - 退出Redis服务：
        1. `pkill redis-server`
        1. kill 进程号
        1. `/usr/local/redis/bin/redis-cli shutdown`

## 2.1 String类型（一）
Redis一共分为五种基本数据类型：`String`、`Hash`、`List`、`Set`、`ZSet`

`String`类型是包含很多种类型的特殊类型，并且是二进制安全的。

`set`和`get`方法：
- 设置值：`set name bhz`
- 取值：`get name`
- 删除值：`del name`

使用`setnx`（not exist）
- name如果不存在进行设置，存在就不需要进行设置了，返回0

使用`setex`（expired）
- `setex color 10 red`设置color的有效期为10秒，10秒后返回`nil`（在Redis里`nil`表示空）

使用`setrange`替换字符串：
- `set email 123@gmail.com`
- `setrange email 10 ww`（10表示从第几位开始替换，后面跟上替换的字符串）

