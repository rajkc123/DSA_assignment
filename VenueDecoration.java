//Q.no 1a

public class VenueDecoration {

    public static int minCostToDecorate(int[][] costs) {
        int n = costs.length;     // Number of venues
        int k = costs[0].length;  // Number of themes

        // Create a dp table with the same dimensions as the costs matrix
        int[][] dp = new int[n][k];

        // Initialize the first row of dp with the first row of costs
        for (int j = 0; j < k; j++) {
            dp[0][j] = costs[0][j];
        }

        // Fill the dp table
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < k; j++) {
                int minCost = Integer.MAX_VALUE;
                // Find the minimum cost for the previous venue, excluding the current theme
                for (int m = 0; m < k; m++) {
                    if (m != j) {
                        minCost = Math.min(minCost, dp[i - 1][m]);
                    }
                }
                // Update the dp table with the minimum cost found plus the cost of the current theme
                dp[i][j] = costs[i][j] + minCost;
            }
        }

        // The minimum cost for the last venue is the minimum value in the last row of dp
        int minTotalCost = Integer.MAX_VALUE;
        for (int j = 0; j < k; j++) {
            minTotalCost = Math.min(minTotalCost, dp[n - 1][j]);
        }

        return minTotalCost;
    }

    public static void main(String[] args) {
        int[][] costMatrix = {{1, 3, 2}, {4, 6, 8}, {3, 1, 5}};
        int result = minCostToDecorate(costMatrix);
        System.out.println("Minimum cost to decorate all venues: " + result);
    }
}
