package edu.nju.cpd.core;

import edu.nju.cpd.lexer.Java8Lexer;
import org.antlr.v4.runtime.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 分词器
 */
class SourceFileTokenizer {

    /**
     * 对指定目录中的代码文件进行分词
     * @param params 输入参数
     * @return 分词结果
     */
    TokenManager tokenize(CPDParams params){
        TokenManager tokenManager = new TokenManager();
        Collection<File> files = FileUtils.listFiles(new File(params.getSourceDirPath()), params.getExtensions(), params.isRecursive());
        for (File file : files) {
            List<Token> tokenList = tokenize(file, params.getEncoding(), Java8Lexer.class, params.getExcludeTokenTypes());
            //忽略Token列表小于最小Token数的代码文件
            if (tokenList.size() >= params.getMinTokenNum()) {
                tokenManager.addTokens(file, tokenList);
            }
        }
        return tokenManager;
    }

    /**
     * 对某个代码文件进行分词
     * @param sourceFile 代码文件
     * @param encoding 文件编码
     * @param lexerClass 词法分析器类型
     * @param excludeTokenTypes 要排除的Token类型
     * @return Token列表
     */
    private List<Token> tokenize(File sourceFile, String encoding, Class<? extends Lexer> lexerClass, int[] excludeTokenTypes) {
        try {
            CharStream input = CharStreams.fromFileName(sourceFile.getAbsolutePath(), Charset.forName(encoding));
            Lexer lexer = lexerClass.getConstructor(CharStream.class).newInstance(input);
            CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            tokenStream.fill();
            List<Token> tokens = tokenStream.getTokens();
            tokens.removeIf(token ->
                    ArrayUtils.contains(excludeTokenTypes, token.getType()));
            return tokens;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
