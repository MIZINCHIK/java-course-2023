package edu.hw3.task6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HeapStockMarketTest {
    @Test
    @DisplayName("No stocks on empty market")
    void mostValuableStock_emptyMarket_null() {
        HeapStockMarket stockMarket = new HeapStockMarket();
        assertThat(stockMarket.mostValuableStock()).isNull();
    }

    @Test
    @DisplayName("No stocks on empty market")
    void mostValuableStock_someMarketOperations_stockWithHighestPriceInDollars() {
        HeapStockMarket stockMarket = new HeapStockMarket();
        stockMarket.add(new Stock(11, ""));
        stockMarket.add(new Stock(2.5, ""));
        stockMarket.add(new Stock(0, ""));
        stockMarket.add(new Stock(-10, ""));
        stockMarket.add(new Stock(1e17, ""));
        stockMarket.add(new Stock(3.2, ""));
        stockMarket.add(new Stock(0.00001, ""));
        stockMarket.add(new Stock(1_000, ""));
        stockMarket.add(new Stock(32, ""));
        assertThat(stockMarket.mostValuableStock().priceInDollars()).isEqualTo(1e17);
    }
}
