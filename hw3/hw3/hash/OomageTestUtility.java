package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        // 初始化桶计数数组
        int[] bucketCounts = new int[M];

        // 计算每个 Oomage 的桶索引，并统计每个桶中的 Oomage 数量
        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            bucketCounts[bucketNum]++;
        }

        // 计算 N
        int N = oomages.size();

        // 检查每个桶中的 Oomage 数量是否符合要求
        for (int count : bucketCounts) {
            if (count < N / 50 || count > N / 2.5) {
                return false;
            }
        }

        return true;

    }
}
