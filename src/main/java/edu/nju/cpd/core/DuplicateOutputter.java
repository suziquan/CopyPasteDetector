package edu.nju.cpd.core;

import org.antlr.v4.runtime.Token;

import java.util.List;

/**
 * 输出结果
 */
class DuplicateOutputter {

    /**
     * 将结果输出到控制台
     *
     * @param tokenManager  分词结果
     * @param duplicateSets 重复集列表
     */
    void print(TokenManager tokenManager, List<DuplicateCollector.DuplicateSet> duplicateSets) {
        for (DuplicateCollector.DuplicateSet duplicateSet : duplicateSets) {
            System.out.println("-------------------------------------");
            System.out.println("重复代码片段");
            System.out.println("重复了" + duplicateSet.getDuplicateTokenCount() + "个Token");
            for (Token token : duplicateSet.getTokenSet()) {
                System.out.println("File: " + token.getTokenSource().getSourceName());
                System.out.println("\tStart Token: " + token.getText());
                System.out.println("\tStart Line: " + token.getLine());
                System.out.println("\tStart Line Index: " + token.getCharPositionInLine());
                List<Token> tokenList = tokenManager.getTokensOf(token.getTokenSource().getSourceName());
                Token endToken = tokenList.get(tokenList.indexOf(token) + duplicateSet.getDuplicateTokenCount() - 1);
                System.out.println("\tEnd Token: " + endToken.getText());
                System.out.println("\tEnd Line: " + endToken.getLine());
                System.out.println("\tEnd Line Index: " + endToken.getCharPositionInLine());
            }
        }
    }
}
