package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] P;
    private WeightedQuickUnionUF F;
    private int N;
    private int count;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be greater than 0");
        }
        this.N = N;
        P = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                P[i][j] = false;
            }
        }
        F = new WeightedQuickUnionUF(N * N + 2); // the last two items are used to connect to the 1st row and the last row
        count = 0;
    }

    public void open(int row, int col) {
        if (!isValid(row, col)) {
            throw new IndexOutOfBoundsException("row or column index out of bounds");
        }
        if (!P[row][col]) {
            P[row][col] = true;
            count++;
            if (row == 0) F.union(row * N + col, N * N); // connect to virtual top
            if (row == N - 1) F.union(row * N + col, N * N + 1); // connect to virtual bottom
            connectIfOpen(row, col, row - 1, col);
            connectIfOpen(row, col, row + 1, col);
            connectIfOpen(row, col, row, col - 1);
            connectIfOpen(row, col, row, col + 1);
        }
    }

    private void connectIfOpen(int row, int col, int adjRow, int adjCol) {//benefits: dont need to make judgements in every case
        if (isValid(adjRow, adjCol) && isOpen(adjRow, adjCol)) {
            F.union(row * N + col, adjRow * N + adjCol);
        }
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < N;
    }

    public boolean isOpen(int row, int col) {
        if (!isValid(row, col)) {
            throw new IndexOutOfBoundsException("row or column index out of bounds");
        }
        return P[row][col];
    }

    public boolean isFull(int row, int col) {
        if (!isValid(row, col)) {
            throw new IndexOutOfBoundsException("row or column index out of bounds");
        }
        return F.connected(row * N + col, N * N);
    }

    public int numberOfOpenSites() {
        return count;
    }

    public boolean percolates() {
        return F.connected(N * N, N * N + 1);
    }
}
