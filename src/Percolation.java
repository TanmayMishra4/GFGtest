import edu.princeton.cs.algs4.WeightedQuickUnionUF;

@SuppressWarnings("ALL")
public class Percolation {

    private final WeightedQuickUnionUF quickUnionStructure;
    private final WeightedQuickUnionUF quickUnionStructureForIsFull;

    private int gridSize;
    private final boolean[][] grid;

    private int virtualTopSite;
    private int virtualBottomSite;


    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N must be at least 1");
        }
        gridSize = n;
        grid = new boolean[n][n];

        quickUnionStructure = new WeightedQuickUnionUF(n * n + 2);
        quickUnionStructureForIsFull = new WeightedQuickUnionUF(n * n + 1);

        virtualTopSite = 0;
        virtualBottomSite = n * n + 1;
    }

    public void open(int i, int j) {
        if (!isOpen(i, j)) {
            int fieldIndex = getFieldIndexInQuickUnionStructure(i, j);

            if (i == 1) {
                quickUnionStructure.union(virtualTopSite, fieldIndex);
                quickUnionStructureForIsFull.union(virtualTopSite, fieldIndex);
            }
            if (i == gridSize) {
                quickUnionStructure.union(virtualBottomSite, fieldIndex);
            }

            connectIfOpen(fieldIndex, i + 1, j);
            connectIfOpen(fieldIndex, i - 1, j);
            connectIfOpen(fieldIndex, i, j - 1);
            connectIfOpen(fieldIndex, i, j + 1);

            grid[i - 1][j - 1] = true;
        }
    }

    public boolean isOpen(int i, int j) {
        return grid[i - 1][j - 1];
    }

    public boolean isFull(int i, int j) {
        if (isOpen(i, j)) {
            int fieldIndex = getFieldIndexInQuickUnionStructure(i, j);
            return quickUnionStructureForIsFull.connected(virtualTopSite, fieldIndex);
        }
        return false;
    }

    public boolean percolates() {
        return quickUnionStructure.connected(virtualTopSite, virtualBottomSite);
    }

    private void connectIfOpen(int fieldIndex, int i, int j) {
        try {
            if (isOpen(i, j)) {
                int neighbourFieldIndex = getFieldIndexInQuickUnionStructure(i, j);
                quickUnionStructure.union(neighbourFieldIndex, fieldIndex);
                quickUnionStructureForIsFull.union(neighbourFieldIndex, fieldIndex);
            }
        } catch (IndexOutOfBoundsException e) {
            
        }
    }

    private int getFieldIndexInQuickUnionStructure(int i, int j) {
        return (i - 1) * gridSize + j;
    }

}