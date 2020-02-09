## 深入理解Java虚拟机 Chapter11

### 解释器与编译器
1. 混合模式（Mixed Mode）
1. 解释模式（Interpreted Mode）
1. 编译模式（Compiled Mode）
```
java -version
java -Xint -version
java -Xcomp -version
```

### 编译优化技术
- 语言无关的经典优化技术之一：公共子表达式消除
- 语言相关的经典优化技术之一：数组范围检查消除
- 最重要的优化技术之一：方法内联
- 最前沿的优化技术之一：逃逸分析