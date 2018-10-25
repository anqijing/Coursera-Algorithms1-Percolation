import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF wuf;
    private boolean[][] grid;
    private int count_open_sites = 0;
    private int tail;
    private int num;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("The number of n should be greater than 0.");
        num = n;
        tail = n * n + 1;
        wuf = new WeightedQuickUnionUF(tail + 1);

        // Initialize the value of grid.
        grid = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                grid[i][j] = false;
        }
    }

    private boolean argument_check(int a, int b) {
        if (a <= 0 || a > b)
            return false;
        else return true;
    }

    private int get_array_index(int row, int col) {
        if (argument_check(row, num) && argument_check(col, num))
            return ((row - 1) * num + col);
        else
            throw new IllegalArgumentException("The argument is illegal.");
    }


    public void open(int row, int col) {    // open site (row, col) if it is not open already
        if (argument_check(row, num) && argument_check(col, num)) {
            if (!isOpen(row, col)) {
                //open the site
                grid[row - 1][col - 1] = true;
                int idx = get_array_index(row, col);
                if (row == 1)
                    wuf.union(idx, 0);
                if (row == num)
                    wuf.union(idx, tail);

                // check node at the top
                if (argument_check(row - 1, num) && isOpen(row - 1, col))
                    wuf.union(get_array_index(row - 1, col), idx);
                // check node at the bottom
                if (argument_check(row + 1, num) && isOpen(row + 1, col))
                    wuf.union(get_array_index(row + 1, col), idx);
                // check node at the left
                if (argument_check(col - 1, num) && isOpen(row, col - 1))
                    wuf.union(get_array_index(row, col - 1), idx);
                // check node at the right
                if (argument_check(col + 1, num) && isOpen(row, col + 1))
                    wuf.union(get_array_index(row, col + 1), idx);

                count_open_sites++;
            }
        } else
            throw new IllegalArgumentException("The argument is illegal.");
    }

    public boolean isOpen(int row, int col) {  // is site (row, col) open?
        if (argument_check(row, num) && argument_check(col, num))
            return grid[row - 1][col - 1];
        else
            throw new IllegalArgumentException("The argument is illegal.");
    }

    public boolean isFull(int row, int col) {  // is site (row, col) full?
        if (argument_check(row, num) && argument_check(col, num))
            return wuf.connected(0, get_array_index(row, col));
        else
            throw new IllegalArgumentException("The argument is illegal.");
    }

    public int numberOfOpenSites() {       // number of open sites
        return count_open_sites;
    }

    public boolean percolates() {              // does the system percolate?
        return wuf.connected(0, tail);
    }


    public static void main(String[] args) {
    }
}

