package edu.project2.maze.generators;

import edu.project2.maze.Maze;
import edu.project2.maze.cells.CellType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

public class EllerGenerator implements MazeGenerator {
    private static final int CHANCE_WALL_DEMOLITION = 50;
    private static final int MAX_CHANCE = 100;
    private int width;
    private Maze maze;
    private Stack<Integer> freeIndices;
    private int[] clusterIndices;
    private Map<Integer, Set<Integer>> clusters;
    private Random random;

    private void initStructures(int height, int width) {
        this.maze = new Maze(height, width);
        this.width = width;
        clusterIndices = new int[width];
        clusters = new HashMap<>();
        freeIndices = new Stack<>();
        random = new Random();
        int largestIndex = Math.ceilDiv(width, 2);
        for (int i = 1; i <= largestIndex; i++) {
            freeIndices.push(i);
        }
    }

    @Override
    public Maze generateMaze(int height, int width) {
        initStructures(height, width);
        CellType[] row = createEmptyRow();
        divideFirstRowIntoSets(row);
        maze.setRow(0, row);
        for (int rowIndex = 1; rowIndex < height; rowIndex++) {
            if (rowIndex % 2 == 0) {
                row = createEmptyRow();
                divideRowIntoSets(row);
                if (rowIndex == height - 1) {
                    reduceClusters(row);
                }
            } else {
                row = createWalledRow();
                makePassagesDown(row);
            }
            maze.setRow(rowIndex, row);
        }
        return maze;
    }

    private void divideFirstRowIntoSets(CellType[] row) {
        for (int i = 0; i < row.length; i += 2) {
            int identifier = freeIndices.pop();
            clusterIndices[i] = identifier;
            Set<Integer> newSet = new HashSet<>();
            newSet.add(i);
            clusters.put(identifier, newSet);
        }
        randomClusterShuffler(row);
    }

    private void divideRowIntoSets(CellType[] row) {
        clusters.clear();
        for (int i = 0; i < row.length; i += 2) {
            int identifier = clusterIndices[i];
            if (identifier == 0) {
                identifier = freeIndices.pop();
                clusterIndices[i] = identifier;
            }
            if (clusters.containsKey(identifier)) {
                clusters.get(identifier).add(i);
            } else {
                Set<Integer> newSet = new HashSet<>();
                newSet.add(i);
                clusters.put(identifier, newSet);
            }
        }
        randomClusterShuffler(row);
    }

    private void makePassagesDown(CellType[] passageRow) {
        clusterIndices = new int[passageRow.length];
        for (var cluster : clusters.entrySet()) {
            int identifier = cluster.getKey();
            var currentSet = cluster.getValue();
            var passages = getRandomSubset(currentSet);
            for (var passage : passages) {
                passageRow[passage] = CellType.PASSAGE;
                clusterIndices[passage] = identifier;
            }
        }
    }

    private CellType[] createEmptyRow() {
        CellType[] row = new CellType[width];
        for (int i = 0; i < width; i += 2) {
            row[i] = CellType.PASSAGE;
        }
        for (int i = 1; i < width; i += 2) {
            row[i] = CellType.WALL;
        }
        return row;
    }

    private CellType[] createWalledRow() {
        CellType[] row = new CellType[width];
        Arrays.fill(row, CellType.WALL);
        return row;
    }

    private void randomClusterShuffler(CellType[] row) {
        random = new Random();
        for (int i = 1; i < width; i += 2) {
            if (clusterIndices[i + 1] != clusterIndices[i - 1]
                && random.nextInt(MAX_CHANCE) < CHANCE_WALL_DEMOLITION) {
                row[i] = CellType.PASSAGE;
                mergeClusters(clusterIndices[i - 1], clusterIndices[i + 1]);
            }
        }
    }

    private void reduceClusters(CellType[] row) {
        for (int i = 1; i < width; i += 2) {
            if (clusterIndices[i + 1] != clusterIndices[i - 1]) {
                row[i] = CellType.PASSAGE;
                mergeClusters(clusterIndices[i - 1], clusterIndices[i + 1]);
            }
        }
    }

    private Set<Integer> getRandomSubset(Set<Integer> set) {
        List<Integer> list = new ArrayList<>(set);
        Collections.shuffle(list);
        int listSize = list.size();
        int startIndex = random.nextInt(listSize);
        int endIndex = random.nextInt(listSize - startIndex) + startIndex + 1;
        return new HashSet<>(list.subList(startIndex, endIndex));
    }

    private void mergeClusters(int baseIndex, int subIndex) {
        for (int index : clusters.get(subIndex)) {
            clusterIndices[index] = baseIndex;
        }
        clusters.get(baseIndex).addAll(clusters.get(subIndex));
        clusters.remove(subIndex);
        freeIndices.push(subIndex);
    }
}
