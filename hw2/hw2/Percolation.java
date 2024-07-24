package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufNoBottom; // 用于防止 backwash
    private int n;
    private int openSitesCount;
    private int topVirtualSite;
    private int bottomVirtualSite;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N must be greater than 0");
        }
        this.n = n;
        grid = new boolean[n][n];
        uf = new WeightedQuickUnionUF(n * n + 2); // +2 for virtual top and bottom
        ufNoBottom = new WeightedQuickUnionUF(n * n + 1); // +1 for virtual top
        topVirtualSite = n * n;
        bottomVirtualSite = n * n + 1;
        openSitesCount = 0;
    }

    public void open(int row, int col) {
        if (!isValid(row, col)) {
            throw new IllegalArgumentException("Invalid row or column index");
        }
        if (!grid[row][col]) {
            grid[row][col] = true;
            openSitesCount++;
            int currentSite = row * n + col;

            if (row == 0) {
                uf.union(currentSite, topVirtualSite);
                ufNoBottom.union(currentSite, topVirtualSite);
            }
            if (row == n - 1) {
                uf.union(currentSite, bottomVirtualSite);
            }

            connectIfOpen(currentSite, row, col, row - 1, col);
            connectIfOpen(currentSite, row, col, row + 1, col);
            connectIfOpen(currentSite, row, col, row, col - 1);
            connectIfOpen(currentSite, row, col, row, col + 1);
        }
    }

    private void connectIfOpen(int currentSite, int row, int col, int adjRow, int adjCol) {
        if (isValid(adjRow, adjCol) && isOpen(adjRow, adjCol)) {
            int adjacentSite = adjRow * n + adjCol;
            uf.union(currentSite, adjacentSite);
            ufNoBottom.union(currentSite, adjacentSite);
        }
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < n;
    }

    public boolean isOpen(int row, int col) {
        if (!isValid(row, col)) {
            throw new IllegalArgumentException("Invalid row or column index");
        }
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        if (!isValid(row, col)) {
            throw new IllegalArgumentException("Invalid row or column index");
        }
        int currentSite = row * n + col;
        return ufNoBottom.connected(currentSite, topVirtualSite);
    }

    public int numberOfOpenSites() {
        return openSitesCount;
    }

    public boolean percolates() {
        return uf.connected(topVirtualSite, bottomVirtualSite);
    }
}
