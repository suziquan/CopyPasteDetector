package edu.nju.cpd.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.antlr.v4.runtime.Token;

import java.math.BigInteger;
import java.util.*;

/**
 * 找出重复对
 */
class DuplicateFinder {

    /**
     * 重复对，包含了两个重复代码片段的起始Token，以及重复的Token数
     */
    @Getter
    @AllArgsConstructor
    static class DuplicatePair {
        private Token token1;
        private Token token2;
        private int duplicateTokenCount;
    }


    /**
     * 使用 Rabin Karp算法查找重复对
     * Rabin Karp算法代码参考：http://algs4.cs.princeton.edu/53substring/RabinKarp.java.html
     * 关于代码原理的详细说明，可参考：http://suziquan.me/2016/12/24/PMD-READING-2/
     * @param tokenManager 分词结果
     * @param minTokenNum 最小重复Token数
     * @return 重复对列表
     */
    List<DuplicatePair> find(TokenManager tokenManager, int minTokenNum) {
        List<DuplicatePair> duplicatePairs = new ArrayList<>();
        Map<Long, List<Token>> tokenHashMap = new HashMap<>();
        long q = BigInteger.probablePrime(31, new Random()).longValue();
        int r = 10;
        int m = minTokenNum;
        long rm = 1;

        for (int i = 0; i < m - 1; i++) {
            rm = (r * rm) % q;
        }

        for (List<Token> tokenList : tokenManager.getTokensMap().values()) {
            long hashCode = 0;
            for (int i = 0; i < m; i++) {
                hashCode = (hashCode * r + tokenManager.getTokenId(tokenList.get(i))) % q;
            }
            if (tokenHashMap.get(hashCode) != null) {
                tokenHashMap.get(hashCode).add(tokenList.get(0));
            } else {
                List<Token> tokensWithSameHash = new ArrayList<>();
                tokensWithSameHash.add(tokenList.get(0));
                tokenHashMap.put(hashCode, tokensWithSameHash);
            }
            for (int i = m; i < tokenList.size(); i++) {
                hashCode = (hashCode + q - rm * tokenManager.getTokenId(tokenList.get(i - m)) % q) % q;
                hashCode = (hashCode * r + tokenManager.getTokenId(tokenList.get(i))) % q;

                Token startToken = tokenList.get(i - m + 1);
                if (tokenHashMap.get(hashCode) != null) {
                    tokenHashMap.get(hashCode).add(startToken);
                } else {
                    List<Token> tokensWithSameHash = new ArrayList<>();
                    tokensWithSameHash.add(startToken);
                    tokenHashMap.put(hashCode, tokensWithSameHash);
                }
            }
        }

        for (List<Token> tokenList : tokenHashMap.values()) {
            if (tokenList.size() == 1) {
                continue;
            }
            for (int i = 0; i < tokenList.size(); i++) {
                for (int j = i + 1; j < tokenList.size(); j++) {
                    Token token1 = tokenList.get(i);
                    Token token2 = tokenList.get(j);
                    try {
                        int duplicateTokenCount = getDuplicateTokenCount(tokenManager, token1, token2, minTokenNum);
                        duplicatePairs.add(new DuplicatePair(token1, token2, duplicateTokenCount));
                    } catch (Exception ignored) {
                    }
                }
            }
        }
        return duplicatePairs;
    }

    /**
     * 获取两段代码重复的Token数量
     * @param tokenManager 分词结果
     * @param token1 代码片段1的起始Token
     * @param token2 代码片段2的起始Token
     * @param minTokenNum 最小重复Token数
     * @return 重复Token数量
     * @throws Exception 两段代码不应该放到结果中
     */
    private int getDuplicateTokenCount(TokenManager tokenManager, Token token1, Token token2, int minTokenNum) throws Exception {
        List<Token> tokenList1 = tokenManager.getTokensOf(token1.getTokenSource().getSourceName());
        List<Token> tokenList2 = tokenManager.getTokensOf(token2.getTokenSource().getSourceName());
        int index1 = tokenList1.indexOf(token1);
        int index2 = tokenList2.indexOf(token2);

        // 如果两段代码片段有相同的前导Token，则说明这两个代码片段被包含在另一对重复代码片段中。
        if (index1 > 0 && index2 > 0) {
            Token previousToken1 = tokenList1.get(index1 - 1);
            Token previousToken2 = tokenList2.get(index2 - 1);
            if (previousToken1.getText().equals(previousToken2.getText())) {
                throw new DuplicateAlreadyContainedException();
            }
        }

        int duplicateTokenCount = 0;
        // 向后扩展，累计重复Token数
        for (int i = 0; index1 + i < tokenList1.size() && index2 + i < tokenList2.size(); i++) {
            Token t1 = tokenList1.get(index1 + i);
            Token t2 = tokenList2.get(index2 + i);
            if (!t1.getText().equals(t2.getText())) {
                break;
            }
            duplicateTokenCount++;
        }
        // 如果重复Token数小于minTokenNum，则不是重复片段，这种意外情况是由于算法中的Hash碰撞导致的
        if (duplicateTokenCount < minTokenNum) {
            throw new NotDuplicateException();
        }
        return duplicateTokenCount;
    }

    private static class NotDuplicateException extends Exception {
    }

    private static class DuplicateAlreadyContainedException extends Exception {
    }

}
