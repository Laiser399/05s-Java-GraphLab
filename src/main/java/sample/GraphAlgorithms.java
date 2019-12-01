package sample;

import Jama.Matrix;
import javafx.collections.transformation.SortedList;
import sample.Graph.Elements.Edge;
import sample.Graph.Elements.Vertex;
import sample.Graph.GraphPath;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.min;

public class GraphAlgorithms {
    public static class GraphCharacteristic {
        public double radius, diameter;
        public List<Double> eccentricities;

        public GraphCharacteristic(double radius, double diameter, List<Double> eccentricities) {
            this.radius = radius;
            this.diameter = diameter;
            this.eccentricities = eccentricities;
        }
    }

    //2
    public static GraphPath breadthSearch(Vertex vertexFrom, Vertex vertexTo) {
        Set<Vertex> usedVertices = new HashSet<>();
        Deque<GraphPath> pathDeque = new ArrayDeque<>();
        pathDeque.addLast(new GraphPath(vertexFrom));
        usedVertices.add(vertexFrom);

        while (pathDeque.size() > 0) {
            GraphPath currentPath = pathDeque.removeFirst();
            Vertex pathLastVertex = currentPath.getLastVertex();
            if (pathLastVertex == vertexTo)
                return currentPath;

            List<Vertex.EdgeWithVertex> nextVertices = pathLastVertex.getNextVertices();
            for (Vertex.EdgeWithVertex pair : nextVertices) {
                if (usedVertices.contains(pair.vertex))
                    continue;

                GraphPath newPath = new GraphPath(currentPath);
                newPath.add(pair.edge, pair.vertex);
                pathDeque.addLast(newPath);
                usedVertices.add(pair.vertex);
            }
        }
        return null;
    }

    static int testCounter = 0;// TODO del
    public static Optional<GraphPath> AStarSearch(Vertex start, Vertex goal, Collection<Edge> edges) {
        testCounter = 0;

        double weightToDistanceCoef = calcAverageWeightToDistance(edges);
        Set<Vertex> closed = new HashSet<>();
        SortedSet<GraphPath> pathsQueue = new TreeSet<>((p1, p2) -> {
            System.out.println("Compare paths: " + testCounter++);
            double f1 = p1.getLength() + calcHeuristic(p1.getLastVertex(), goal, weightToDistanceCoef);
            double f2 = p2.getLength() + calcHeuristic(p2.getLastVertex(), goal, weightToDistanceCoef);
            return Double.compare(f1, f2);
        });
        pathsQueue.add(new GraphPath(start));

        while (!pathsQueue.isEmpty()) {
            GraphPath currentPath = pathsQueue.first();
            pathsQueue.remove(currentPath);
            Vertex lastVertex = currentPath.getLastVertex();
            if (closed.contains(lastVertex))
                continue;
            if (lastVertex == goal)
                return Optional.of(currentPath);

            closed.add(lastVertex);
            for (Vertex.EdgeWithVertex pair : lastVertex.getNextVertices()) {
                GraphPath newPath = new GraphPath(currentPath);
                newPath.add(pair.edge, pair.vertex);
                pathsQueue.add(newPath);
            }
        }

        return Optional.empty();
    }

    private static double calcAverageWeightToDistance(Collection<Edge> edges) {
        // среднее отношение веса ребра к расстоянию между вершинами
        double sum = 0;
        for (Edge edge : edges) {
            Vertex v1 = edge.getFirstVertex(), v2 = edge.getSecondVertex();
            double distance = v1.getCenterPos().distance(v2.getCenterPos());
            sum += edge.getWeight() / distance;
        }
        return sum / edges.size();
    }

    private static double calcHeuristic(Vertex v1, Vertex goal, double weightToDistanceCoef) {
        return v1.getCenterPos().distance(goal.getCenterPos()) * weightToDistanceCoef;
    }

    //3
    public static Matrix floydAlgorithm(Matrix adjacencyMatrix) {
        Matrix result = adjacencyMatrix.copy();

        for (int row = 0; row < result.getRowDimension(); ++row) {
            for (int col = 0; col < result.getColumnDimension(); ++col) {
                if (row == col)
                    result.set(row, col, 0);
                else if (result.get(row, col) == 0)
                    result.set(row, col, Double.POSITIVE_INFINITY);
            }
        }

        for (int i = 0; i < result.getRowDimension(); ++i) {
            Matrix nextMatrix = result.copy();
            for (int row = 0; row < nextMatrix.getRowDimension(); ++row) {
                for (int col = 0; col < nextMatrix.getColumnDimension(); ++col) {
                    double val1 = result.get(row, col),
                           val2 = result.get(row, i),
                           val3 = result.get(i, col);
                    nextMatrix.set(row, col, min(val1, val2 + val3));
                }
            }
            result = nextMatrix;
        }
        return result;
    }

    //4
    public static GraphCharacteristic graphCharacteristic(Matrix adjacencyMatrix) {
        Matrix floydMatrix = floydAlgorithm(adjacencyMatrix);

        List<Double> eccentricities = new ArrayList<>();
        double radius = Double.POSITIVE_INFINITY, diameter = Double.NEGATIVE_INFINITY;
        for (double[] row : floydMatrix.getArray()) {
            double eccentricity = maxInRow(row);
            eccentricities.add(eccentricity);
            if (eccentricity < radius)
                radius = eccentricity;
            if (eccentricity > diameter)
                diameter = eccentricity;
        }

        return new GraphCharacteristic(radius, diameter, eccentricities);
    }

    private static double maxInRow(double[] row) {
        double result = Double.NEGATIVE_INFINITY;
        for (double val : row) {
            if (val > result)
                result = val;
        }
        return result;
    }

    //5
    public static boolean checkIsomorphism(Matrix adjacencyMtx1, Matrix adjacencyMtx2) {

        // TODO
        return false;
    }

    //6
    public static List<List<Integer> > findConnectivityComponents(Matrix adjacencyMatrix) {
        Matrix shortestDistMtx = floydAlgorithm(adjacencyMatrix);
        List<List<Integer> > components = new ArrayList<>();
        Set<Integer> unusedIndices = IntStream.range(0, shortestDistMtx.getRowDimension())
                .boxed().collect(Collectors.toSet());

        while (!unusedIndices.isEmpty()) {
            List<Integer> component = makeStrongComponentBy(unusedIndices.iterator().next(), shortestDistMtx);
            for (int i : component) unusedIndices.remove(i);
            components.add(component);
        }

        return components;
    }

    private static List<Integer> makeStrongComponentBy(int i, Matrix shortestDistMtx) {
        List<Integer> component = new ArrayList<>();
        for (int j = 0; j < shortestDistMtx.getColumnDimension(); ++j) {
            if (shortestDistMtx.get(i, j) < Double.POSITIVE_INFINITY &&
                    shortestDistMtx.get(j, i) < Double.POSITIVE_INFINITY)
            {
                component.add(j);
            }
        }
        return component;
    }





}
