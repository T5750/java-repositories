# 线程池的介绍及简单实现

## 线程池技术如何提高服务器程序的性能
假设在一台服务器完成一项任务的时间为T

- T1 创建线程的时间
- T2 在线程中执行任务的时间，包括线程间同步所需时间
- T3 线程销毁的时间

显然T ＝ T1＋T2＋T3。注意这是一个极度简化的假设。

## 线程池的简单实现及对比测试
一般一个简单线程池至少包含下列组成部分。

- 线程池管理器（ThreadPoolManager）:用于创建并管理线程池
- 工作线程（WorkThread）: 线程池中线程
- 任务接口（Task）:每个任务必须实现的接口，以供工作线程调度任务的执行。
- 任务队列:用于存放没有处理的任务。提供一种缓冲机制。

## 关于高级线程池的探讨

### 方案一：动态增加工作线程

### 方案二：优化工作线程数目

### 方案三：一个服务器提供多个线程池

## 示例
- `TestThreadPool`

## Links
- [线程池的介绍及简单实现](https://www.ibm.com/developerworks/cn/java/l-threadPool/index.html)