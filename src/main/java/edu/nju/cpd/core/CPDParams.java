package edu.nju.cpd.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.antlr.v4.runtime.Lexer;

/**
 * 程序需要的参数
 */
@Getter
@AllArgsConstructor
class CPDParams {
    /**
     * 代码目录
     */
    private String sourceDirPath;
    /**
     * 是否检查子目录
     */
    private boolean recursive;
    /**
     * 代码文件扩展名
     */
    private String[] extensions;
    /**
     * 代码文件编码
     */
    private String encoding;
    /**
     * 分词器类型
     */
    private Class<? extends Lexer> lexerClass;
    /**
     * 最小重复token数。两段代码连续相同的token数大于该值，就认为这这两段代码重复
     */
    private int minTokenNum;
    /**
     * 在分词过程中除去某些token类型
     */
    private int[] excludeTokenTypes;
}
