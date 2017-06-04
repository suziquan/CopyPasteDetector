package edu.nju.cpd.core;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;

import edu.nju.cpd.lexer.Java8Lexer;

/**
 * 分词器
 */
class SourceFileTokenizer {

	/**
	 * 对指定目录中的代码文件进行分词
	 * 
	 * @param params
	 *            输入参数
	 * @return 分词结果
	 */
	TokenManager tokenize(CPDParams params) {
		TokenManager tokenManager = new TokenManager();
		List<String> files = listFiles(params.getSourceDirPath(), params.getExtensions(), params.isRecursive());
		for (String filePath : files) {
			List<Token> tokenList = tokenize(filePath, params.getEncoding(), Java8Lexer.class,
					params.getExcludeTokenTypes());
			// 忽略Token列表小于最小Token数的代码文件
			if (tokenList.size() >= params.getMinTokenNum()) {
				tokenManager.addTokens(filePath, tokenList);
			}
		}
		return tokenManager;
	}

	private List<String> listFiles(String baseDirPath, String[] extensions, boolean isRecursive) {
			try {
				return Files.find(Paths.get(baseDirPath), isRecursive ? Integer.MAX_VALUE : 1, (filePath, fileAttr) -> {
					if (!fileAttr.isRegularFile()) {
						return false;
					}
					for (String extension : extensions) {
						if (filePath.toString().endsWith("." + extension)) {
							return true;
						}
					}
					return false;
				}).map(path->path.toString()).collect(Collectors.toList());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	}

	/**
	 * 对某个代码文件进行分词
	 * 
	 * @param sourceFilePath
	 *            代码文件路径
	 * @param encoding
	 *            文件编码
	 * @param lexerClass
	 *            词法分析器类型
	 * @param excludeTokenTypes
	 *            要排除的Token类型
	 * @return Token列表
	 */
	private List<Token> tokenize(String sourceFilePath, String encoding, Class<? extends Lexer> lexerClass,
			int[] excludeTokenTypes) {
		try {
			CharStream input = CharStreams.fromFileName(sourceFilePath, Charset.forName(encoding));
			Lexer lexer = lexerClass.getConstructor(CharStream.class).newInstance(input);
			CommonTokenStream tokenStream = new CommonTokenStream(lexer);
			tokenStream.fill();
			List<Token> tokens = tokenStream.getTokens();

			tokens.removeIf(token -> {
				for (int excludeTokenType : excludeTokenTypes) {
					if (excludeTokenType == token.getType()) {
						return true;
					}
				}
				return false;
			});

			return tokens;
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

}
