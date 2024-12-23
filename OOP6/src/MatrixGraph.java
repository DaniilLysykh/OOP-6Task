import java.util.*;

public class MatrixGraph<T> implements Graph<T> {
    private Map<T, Integer> vertexMap = new HashMap<>();
    private List<T> vertices = new ArrayList<>();
    private boolean[][] adjMatrix;

    @Override
    public void addVertex(T vertex) {
        if (!vertexMap.containsKey(vertex)) {
            vertexMap.put(vertex, vertices.size());
            vertices.add(vertex);
            if (adjMatrix == null) {
                adjMatrix = new boolean[1][1];
            } else {
                boolean[][] newMatrix = new boolean[vertices.size()][vertices.size()];
                for (int i = 0; i < adjMatrix.length; i++) {
                    System.arraycopy(adjMatrix[i], 0, newMatrix[i], 0, adjMatrix[i].length);
                }
                adjMatrix = newMatrix;
            }
        }
    }

    @Override
    public void addEdge(T source, T destination) {
        int srcIndex = vertexMap.get(source);
        int destIndex = vertexMap.get(destination);
        adjMatrix[srcIndex][destIndex] = true;
        adjMatrix[destIndex][srcIndex] = true; // For undirected graph
    }

    @Override
    public List<T> getNeighbors(T vertex) {
        List<T> neighbors = new ArrayList<>();
        int vertexIndex = vertexMap.get(vertex);
        for (int i = 0; i < vertices.size(); i++) {
            if (adjMatrix[vertexIndex][i]) {
                neighbors.add(vertices.get(i));
            }
        }
        return neighbors;
    }

    @Override
    public List<T> depthFirstSearch(T startVertex) {
        List<T> visited = new ArrayList<>();
        Set<T> visitedSet = new HashSet<>();
        dfs(startVertex, visited, visitedSet);
        return visited;
    }

    private void dfs(T vertex, List<T> visited, Set<T> visitedSet) {
        visited.add(vertex);
        visitedSet.add(vertex);
        for (T neighbor : getNeighbors(vertex)) {
            if (!visitedSet.contains(neighbor)) {
                dfs(neighbor, visited, visitedSet);
            }
        }
    }

    @Override
    public List<T> breadthFirstSearch(T startVertex) {
        List<T> visited = new ArrayList<>();
        Set<T> visitedSet = new HashSet<>();
        Queue<T> queue = new LinkedList<>();
        queue.add(startVertex);
        visitedSet.add(startVertex);
        while (!queue.isEmpty()) {
            T vertex = queue.poll();
            visited.add(vertex);
            for (T neighbor : getNeighbors(vertex)) {
                if (!visitedSet.contains(neighbor)) {
                    visitedSet.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return visited;
    }

    @Override
    public List<T> getAllVertices() {
        return new ArrayList<>(vertices);
    }

    @Override
    public void removeVertex(T vertex) {
        int vertexIndex = vertexMap.get(vertex);
        vertices.remove(vertexIndex);
        vertexMap.remove(vertex);

        // Update vertexMap indices
        for (T v : vertexMap.keySet()) {
            int index = vertexMap.get(v);
            if (index > vertexIndex) {
                vertexMap.put(v, index - 1);
            }
        }

        // Create a new adjacency matrix
        boolean[][] newMatrix = new boolean[vertices.size()][vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                if (i < vertexIndex && j < vertexIndex) {
                    newMatrix[i][j] = adjMatrix[i][j];
                } else if (i < vertexIndex && j >= vertexIndex) {
                    newMatrix[i][j] = adjMatrix[i][j + 1];
                } else if (i >= vertexIndex && j < vertexIndex) {
                    newMatrix[i][j] = adjMatrix[i + 1][j];
                } else if (i >= vertexIndex && j >= vertexIndex) {
                    newMatrix[i][j] = adjMatrix[i + 1][j + 1];
                }
            }
        }
        adjMatrix = newMatrix;
    }

    @Override
    public boolean hasEdge(T source, T destination) {
        int srcIndex = vertexMap.get(source);
        int destIndex = vertexMap.get(destination);
        return adjMatrix[srcIndex][destIndex];
    }
}


