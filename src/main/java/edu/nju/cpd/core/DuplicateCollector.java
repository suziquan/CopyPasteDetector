package edu.nju.cpd.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.antlr.v4.runtime.Token;

import java.util.*;

/**
 * 合并重复对
 */
class DuplicateCollector {

    /**
     * 重复集，包含了多段相同的代码片段的起始Token，以及这些代码重复的Token数
     */
    @Getter
    @AllArgsConstructor
    static class DuplicateSet {
        private Set<Token> tokenSet;
        private int duplicateTokenCount;
    }

    /**
     * 将多段两两重复的代码片段合并到一起
     * @param duplicatePairs 重复对列表
     * @return  重复集列表，列表按重复集的重复Token数从大到小排列
     */
    List<DuplicateSet> collect(List<DuplicateFinder.DuplicatePair> duplicatePairs) {
        List<DuplicateSet> duplicateSets = new ArrayList<>();

        //以重复Token数量作为Map的Key，将重复Token数量为该值的所有代码片段的起始Token放入一个List中，作为Value。即把重复对按照重复Token数分组。
        Map<Integer, List<DuplicateFinder.DuplicatePair>> duplicateTokenCountMap = new HashMap<>();
        for (DuplicateFinder.DuplicatePair duplicatePair : duplicatePairs) {
            int count = duplicatePair.getDuplicateTokenCount();
            if (duplicateTokenCountMap.get(count) == null) {
                List<DuplicateFinder.DuplicatePair> list = new ArrayList<>();
                list.add(duplicatePair);
                duplicateTokenCountMap.put(count, list);
            } else {
                duplicateTokenCountMap.get(count).add(duplicatePair);
            }
        }

        for (List<DuplicateFinder.DuplicatePair> duplicatePairList : duplicateTokenCountMap.values()) {
            //合并同一分组中的重复对。例如一个分组中有三个重复对，分别为(p1，p2)，（p2，p3），（p4，p5），分合并后的结果为(p1,p2,p3),(p4,p5)。
            for (DuplicateFinder.DuplicatePair duplicatePair : duplicatePairList) {
                boolean merged = false;
                for (DuplicateSet duplicateSet : duplicateSets) {
                    if (duplicatePair.getDuplicateTokenCount() == duplicateSet.getDuplicateTokenCount()) {
                        //如果重复集中已经包含了某个代码片段，就把该代码片段所在重复对的另一个代码片段也加入到这个重复集中。
                        if (duplicateSet.getTokenSet().contains(duplicatePair.getToken1())
                                || duplicateSet.getTokenSet().contains(duplicatePair.getToken2())) {
                            duplicateSet.getTokenSet().add(duplicatePair.getToken1());
                            duplicateSet.getTokenSet().add(duplicatePair.getToken2());
                            merged = true;
                            break;
                        }
                    }
                }

                //如果一个重复对的两个代码片段均未包含在已有的重复集中，则为这一个重复对新建一个重复集。
                if (!merged) {
                    Set<Token> set = new HashSet<>();
                    set.add(duplicatePair.getToken1());
                    set.add(duplicatePair.getToken2());
                    duplicateSets.add(new DuplicateSet(set, duplicatePair.getDuplicateTokenCount()));
                }
            }
        }
        //按照重复Token数从大到小排序
        duplicateSets.sort(Comparator.comparingInt(set -> -set.getDuplicateTokenCount()));
        return duplicateSets;
    }
}
