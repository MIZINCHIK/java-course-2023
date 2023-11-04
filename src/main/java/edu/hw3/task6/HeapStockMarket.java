package edu.hw3.task6;

import java.util.PriorityQueue;

public class HeapStockMarket implements StockMarket {
    private final PriorityQueue<Stock> heap = new PriorityQueue<>();

    @Override
    public void add(Stock stock) {
        heap.add(stock);
    }

    @Override
    public void remove(Stock stock) {
        heap.remove(stock);
    }

    @Override
    public Stock mostValuableStock() {
        return heap.peek();
    }
}
