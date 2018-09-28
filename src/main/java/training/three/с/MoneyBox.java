package training.three.с;

class MoneyBox {
    private final int weight;
    private final int[] weights;
    private final int[] prices;

    private final int[] maxs;
    private final int[] mins;

    MoneyBox(int weight, int[] weights, int[] prices) {
        this.weight = weight;
        this.weights = weights;
        this.prices = prices;
        this.maxs = new int[weight + 1];
        this.mins = new int[weight + 1];
    }

    int calculateMin() {
        for (int i = 1; i < mins.length; i++) {
            int minPrice = Integer.MAX_VALUE;
            for (int j = 0; j < weights.length; j++) {
                int indexPrevWeight = i - weights[j];
                if (indexPrevWeight < 0) {
                    continue;
                }
                if (mins[indexPrevWeight] == -1) {
                    continue;
                }
                int currentPrice = mins[indexPrevWeight] + prices[j];
                if (currentPrice < minPrice) {
                    minPrice = currentPrice;
                }
            }
            if (minPrice == Integer.MAX_VALUE) {
                mins[i] = -1;
            } else {
                mins[i] = minPrice;
            }
        }
        return mins[weight];
    }

    int calculateMax() {
        for (int i = 1; i < maxs.length; i++) {
            int maxPrice = -1;
            for (int j = 0; j < weights.length; j++) {
                int indexPrevWeight = i - weights[j];
                if (indexPrevWeight < 0) {
                    continue;
                }
                if (maxs[indexPrevWeight] == -1) {
                    continue;
                }
                int currentPrice = maxs[indexPrevWeight] + prices[j];
                if (currentPrice > maxPrice) {
                    maxPrice = currentPrice;
                }
            }
            maxs[i] = maxPrice;
        }
        return maxs[weight];
    }

}
