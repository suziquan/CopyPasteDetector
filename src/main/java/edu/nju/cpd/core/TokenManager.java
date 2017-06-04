package edu.nju.cpd.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.antlr.v4.runtime.Token;

/**
 * 分词结果
 */
class TokenManager {

    private Map<String, List<Token>> tokensMap = new HashMap<>();

    Map<String, List<Token>> getTokensMap() {
        return tokensMap;
    }

    /**
     * 存储Token和它被分配的ID，这个ID供Rabin-Karp算法使用。两个文本相同的Token会被分配相同的ID
     */
    private Map<ComparableToken, Integer> tokenIdMap = new HashMap<>();

    /**
     * 用于给Token分配ID
     */
    private int idCount = 0;

    /**
     * 如果两个Token的文本相同，则包装这两个Token的ComparableToken相等，这两个Token将会被分配相同的ID
     */
    private class ComparableToken {
        private Token token;

        private ComparableToken(Token token) {
            this.token = token;
        }

        @Override
        public int hashCode() {
            return token.getText().hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof ComparableToken) {
                ComparableToken comparableToken = (ComparableToken) obj;
                return token.getText().equals(comparableToken.token.getText());
            }
            return false;
        }
    }

    /**
     * 将代码文件和它对应的Token列表存放到分词结果中
     *
     * @param sourceFilePath 代码文件路径
     * @param tokens         Token列表，即对单个代码文件的分词结果
     */
    void addTokens(String sourceFilePath, List<Token> tokens) {
        tokensMap.put(sourceFilePath, tokens);
        for (Token token : tokens) {
            ComparableToken comparableToken = new ComparableToken(token);
            tokenIdMap.computeIfAbsent(comparableToken, k -> ++idCount);
        }
    }

    /**
     * 获得某个代码文件的Token列表
     *
     * @param sourceFilePath 代码文件路径
     * @return Token列表，即对单个代码文件的分词结果
     */
    List<Token> getTokensOf(String sourceFilePath) {
        for (Entry<String, List<Token>> entry : tokensMap.entrySet()) {
            try {
                if (Files.isSameFile(Paths.get(sourceFilePath), Paths.get(entry.getKey()))) {
                    return entry.getValue();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取某个Token的ID
     *
     * @param token Token
     * @return 该Token被分配的ID
     */
    int getTokenId(Token token) {
        return tokenIdMap.get(new ComparableToken(token));
    }

}