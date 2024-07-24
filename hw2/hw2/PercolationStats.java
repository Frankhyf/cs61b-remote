package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double[] thresholds; // Array to store percolation thresholds
    private int T; // Number of experiments

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T should be greater than 0");
        }

        this.T = T;
        thresholds = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation perc = pf.make(N);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                }
            }
            thresholds[i] = (double) perc.numberOfOpenSites() / (N * N);
        }
    }

    // Sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // Sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // Low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    // High endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }
}
