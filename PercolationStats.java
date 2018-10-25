import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] open_num;
    private int T;
    private double mean;
    private double std;
    private double sqrtT;

    public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
    {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("The numbers of n and trials should be greater than 0.");
        T = trials;
        open_num = new double[trials];
        for (int t = 0; t < trials; t++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                // generate a random site
                int i = StdRandom.uniform(1, n + 1);
                int j = StdRandom.uniform(1, n + 1);
                if (!p.isOpen(i, j))
                    p.open(i, j);
            }
            int count = p.numberOfOpenSites();
            open_num[t] = (double) count / (n * n);
        }
    }

    public double mean()                          // sample mean of percolation threshold
    {
        mean = StdStats.mean(open_num);
        return mean;
    }

    public double stddev()                        // sample standard deviation of percolation threshold
    {
        std = StdStats.stddev(open_num);
        return std;
    }

    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return mean - 1.96 * std / sqrtT;
    }

    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        return mean + 1.96 * std / sqrtT;
    }

    public static void main(String[] args)        // test client (described below)
    {
        int n = Integer.parseInt(args[0]);          // number of vertices
        int trials = Integer.parseInt(args[1]);     // number of trials
        PercolationStats p = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + p.mean());
        StdOut.println("stddev                  = " + p.stddev());
        StdOut.println("95% confidence interval = " + "[" + p.confidenceLo() + ", " + p.confidenceHi() + "]");
    }
}

