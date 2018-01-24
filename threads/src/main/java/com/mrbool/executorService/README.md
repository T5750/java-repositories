# Working with Java Executor framework in multithreaded application

## Java Executor Service

![Java Executor Service](http://file.mrbool.com/mrbool/articles/Kaushik/JavaExecutor01.png)

## Thread Pool Executor

![Thread Pool Executor](http://file.mrbool.com/mrbool/articles/Kaushik/JavaExecutor02.png)

## Steps
- Create an executor
   - The newFixedThreadPool () returns a ThreadPoolExecutor instance with an initialized and unbounded queue and a fixed number of threads.
   - The newCachedThreadPool () returns a ThreadPoolExecutor instance initialized with an unbounded queue and unbounded number of threads.
- Create one or more tasks and put in the queue
- Submit the task to the Executor
- Execute the task
- Shutdown the Executor

## Links
- [Working with Java Executor framework in multithreaded application](http://mrbool.com/working-with-java-executor-framework-in-multithreaded-application/27560)
