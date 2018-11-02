# Working with Java Executor framework in multithreaded application

## Java Executor Service

![Java Executor Service](http://www.wailian.work/images/2018/11/02/JavaExecutor01-min.png)

## Thread Pool Executor

![Thread Pool Executor](http://www.wailian.work/images/2018/11/02/JavaExecutor02-min.png)

## Steps
- Create an executor
   - The newFixedThreadPool () returns a ThreadPoolExecutor instance with an initialized and unbounded queue and a fixed number of threads.
   - The newCachedThreadPool () returns a ThreadPoolExecutor instance initialized with an unbounded queue and unbounded number of threads.
- Create one or more tasks and put in the queue
- Submit the task to the Executor
- Execute the task
- Shutdown the Executor

## Examples
- `ExecutorServiceExample`

## Links
- [Working with Java Executor framework in multithreaded application](http://mrbool.com/working-with-java-executor-framework-in-multithreaded-application/27560)
