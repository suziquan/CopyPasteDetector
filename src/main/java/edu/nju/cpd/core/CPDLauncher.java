package edu.nju.cpd.core;

import java.io.IOException;

import edu.nju.cpd.lexer.Java8Lexer;
import org.antlr.v4.runtime.*;

/**
 * 程序启动类
 */
public class CPDLauncher {

    public static void main(String[] args) throws IOException {
//        String sourceDirPath = "d:/MyCPD";
        String sourceDirPath = CPDLauncher.class.getResource("/code").getPath();
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
