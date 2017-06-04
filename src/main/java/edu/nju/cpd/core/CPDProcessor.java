package edu.nju.cpd.core;

import java.util.List;

/**
 * 控制处理流程
 */
class CPDProcessor {

    void process(CPDParams params) {
        //先对所有的代码文件进行分词
        SourceFileTokenizer tokenizer = new SourceFileTokenizer();
        TokenManager tokenManager = tokenizer.tokenize(params);

        //使用 Rabin Karp算法查找重复的代码片段对
        DuplicateFinder duplicateFinder = new DuplicateFinder();
        List<DuplicateFinder.DuplicatePair> duplicatePairs = duplicateFinder.find(tokenManager, params.getMinTokenNum());

        //如果有多段代码两两重复，将它们归到一起
        DuplicateCollector duplicateCollector = new DuplicateCollector();
        List<DuplicateCollector.DuplicateSet> duplicateSets = duplicateCollector.collect(duplicatePairs);

        //将结果输出到控制台
        DuplicateOutputter duplicateOutputter = new DuplicateOutputter();
        duplicateOutputter.print(tokenManager, duplicateSets);
    }
}
