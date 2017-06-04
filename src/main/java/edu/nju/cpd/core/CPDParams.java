package edu.nju.cpd.core;

import org.antlr.v4.runtime.Lexer;

/**
 * 程序需要的参数
 */
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
    
	public CPDParams(String sourceDirPath, boolean recursive, String[] extensions, String encoding,
			Class<? extends Lexer> lexerClass, int minTokenNum, int[] excludeTokenTypes) {
		super();
		this.sourceDirPath = sourceDirPath;
		this.recursive = recursive;
		this.extensions = extensions;
		this.encoding = encoding;
		this.lexerClass = lexerClass;
		this.minTokenNum = minTokenNum;
		this.excludeTokenTypes = excludeTokenTypes;
	}
	
	public String getSourceDirPath() {
		return sourceDirPath;
	}
	public boolean isRecursive() {
		return recursive;
	}
	public String[] getExtensions() {
		return extensions;
	}
	public String getEncoding() {
		return encoding;
	}
	public Class<? extends Lexer> getLexerClass() {
		return lexerClass;
	}
	public int getMinTokenNum() {
		return minTokenNum;
	}
	public int[] getExcludeTokenTypes() {
		return excludeTokenTypes;
	}
    
}
