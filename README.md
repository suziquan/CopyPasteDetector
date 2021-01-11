# CopyPasteDetector

## 简介
检测项目中的重复代码，原理参考了 [PMD 工具的 CPD 部分](https://pmd.github.io/) 。由于 IntelliJ IDEA 等开发工具和 PMD 等分析工具已经包含了重复代码检测的功能，本项目的实际使用意义不大。

## 运行方法
将该项目作为 Maven 项目导入到 IDE 中，在 `CPDLauncher` 中设置参数，然后运行。

## 原理

**CPD 原理：** [PMD 源码阅读（2）— 复制粘贴检测](https://suziquan.cn/article/14)

### 检测过程主要分为三个步骤：
#### 1. 对代码文件进行分词。

本项目中使用 [ANTLR v4](http://www.antlr.org/) 生成的 Lexer 进行分词。  
使用的语法文件： [antlr/grammars-v4](https://github.com/antlr/grammars-v4)。

#### 2. 使用Rabin-Karp查找重复的代码片段对。
算法参考： [Rabin-Karp的Java实现](http://algs4.cs.princeton.edu/53substring/RabinKarp.java.html)

#### 3. 将两两重复的多段代码片段合并到一起。


