package edu.nju.cpd.core;

import java.nio.file.Paths;
import org.antlr.v4.runtime.Lexer;
import edu.nju.cpd.lexer.Java8Lexer;

/**
 * 程序启动类
 */
public class CPDLauncher {

    public static void main(String[] args) throws Exception{
//        String sourceDirPath = "d:/MyCPD";
        String sourceDirPath = Paths.get(CPDLauncher.class.getResource("/code").toURI()).toString();
        boolean recursive = true;
        String[] extensions = {"java"};
        String encoding = "GBK";
        Class<? extends Lexer> lexerClass = Java8Lexer.class;
        int minTokenNum = 100;
//        int[] excludeTokenTypes = {Java8Lexer.Identifier, Java8Lexer.EOF};
        int[] excludeTokenTypes = {Java8Lexer.EOF};

        CPDParams params = new CPDParams(sourceDirPath, recursive, extensions, encoding, lexerClass, minTokenNum, excludeTokenTypes);
        new CPDProcessor().process(params);
    }

}
