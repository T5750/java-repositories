## Chapter08

### 运行时栈帧结构
![jvm-stack-frame-min](https://www.wailian.work/images/2019/04/19/jvm-stack-frame-min.jpg)

### 基于栈的解释器执行过程
```
public int calc();
  descriptor: ()I
  flags: ACC_PUBLIC
  Code:
    stack=2, locals=4, args_size=1
       0: bipush        100
       2: istore_1
       3: sipush        200
       6: istore_2
       7: sipush        300
      10: istore_3
      11: iload_1
      12: iload_2
      13: iadd
      14: iload_3
      15: imul
      16: ireturn
```