import java.util.List;

public interface Graph<T> {
    void addVertex(T vertex);
    void addEdge(T source, T destination);
    List<T> getNeighbors(T vertex);
    List<T> depthFirstSearch(T startVertex);
    List<T> breadthFirstSearch(T startVertex);
    List<T> getAllVertices();
    void removeVertex(T vertex);
    boolean hasEdge(T source, T destination);
}
