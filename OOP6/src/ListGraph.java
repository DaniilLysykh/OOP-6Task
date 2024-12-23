import java.util.*;

public class ListGraph<T> implements Graph<T> {
    private Map<T, List<T>> adjList = new HashMap<>();

    @Override
    public void addVertex(T vertex) {
        adjList.putIfAbsent(vertex, new ArrayList<>());
    }

    @Override
    public void addEdge(T source, T destination) {
        adjList.get(source).add(destination);
        adjList.get(destination).add(source); // For undirected graph
    }

    @Override
    public List<T> getNeighbors(T vertex) {
        return adjList.getOrDefault(vertex, new ArrayList<>());
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
        return new ArrayList<>(adjList.keySet());
    }

    @Override
    public void removeVertex(T vertex) {
        adjList.values().forEach(edges -> edges.remove(vertex));
        adjList.remove(vertex);
    }

    @Override
    public boolean hasEdge(T source, T destination) {
        return adjList.getOrDefault(source, new ArrayList<>()).contains(destination);
    }
}
