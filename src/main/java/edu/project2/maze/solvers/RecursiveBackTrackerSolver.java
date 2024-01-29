package edu.project2.maze.solvers;

import edu.project2.maze.Maze;
import edu.project2.maze.cells.CellType;
import edu.project2.maze.cells.Coordinate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class RecursiveBackTrackerSolver implements MazeSolver {
    @Override
    public List<Coordinate> getPath(Maze maze, Coordinate start, Coordinate end) {
        var result = new LinkedList<Coordinate>();
        var noWay = new HashSet<Coordinate>();
        if (maze.getCell(start) == CellType.WALL || maze.getCell(end) == CellType.WALL) {
            return result;
        }
        Coordinate currentCoordinate = start;
        while (!currentCoordinate.equals(end)) {
            Coordinate unvisitedNeighbour = getUnvisitedNeighbour(currentCoordinate, maze, noWay);
            if (unvisitedNeighbour == null) {
                maze.setCellType(currentCoordinate, CellType.PASSAGE);
                noWay.add(currentCoordinate);
                if (result.isEmpty()) {
                    return result;
                }
                currentCoordinate = result.removeLast();
            } else {
                result.addLast(currentCoordinate);
                maze.setCellType(currentCoordinate, CellType.PATH);
                currentCoordinate = unvisitedNeighbour;
            }
        }
        result.addLast(currentCoordinate);
        return result;
    }

    private Coordinate getUnvisitedNeighbour(Coordinate point, Maze maze, Set<Coordinate> noWay) {
        return point.getNeighbours(1).stream()
            .filter(neighbour -> maze.getCell(neighbour) == CellType.PASSAGE
            && !noWay.contains(neighbour))
            .findAny()
            .orElse(null);
    }
}
