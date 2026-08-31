package dp.wine_seller_profit;

public class Code {
    public int maxProfit(final int[] price) {
        final int n = price.length;
        final int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = price[i] * n;
        }
        for (int len = 2; len <= n; len++) {
            final int year = n - len + 1;
            for (int i = 0; i + len - 1 < n; i++) {
                final int j = i + len - 1;
                final int sellLeft = price[i] * year + dp[i + 1][j];
                final int sellRight = price[j] * year + dp[i][j - 1];
                dp[i][j] = Math.max(sellLeft, sellRight);
            }
        }
        return dp[0][n - 1];
    }
}
