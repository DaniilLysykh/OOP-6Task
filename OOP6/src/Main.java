import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    private Graph<String> graph = new MatrixGraph<>(); // or new ListGraph<>();
    private Map<String, Circle> vertexCircles = new HashMap<>();
    private Map<String, Map<String, Line>> edges = new HashMap<>();
    private Pane graphPane = new Pane();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Graph Demo");

        VBox vbox = new VBox();
        Label label = new Label("Graph Operations");
        TextField vertexField = new TextField();
        vertexField.setPromptText("Enter vertex");
        Button addVertexButton = new Button("Add Vertex");
        TextField edgeField = new TextField();
        edgeField.setPromptText("Enter edge (source-destination)");
        Button addEdgeButton = new Button("Add Edge");
        Button dfsButton = new Button("DFS");
        Button bfsButton = new Button("BFS");
        Button removeVertexButton = new Button("Remove Vertex");
        Button checkEdgeButton = new Button("Check Edge");
        Button showVerticesButton = new Button("Show Vertices");
        Button getNeighborsButton = new Button("Get Neighbors");

        addVertexButton.setOnAction(e -> {
            String vertex = vertexField.getText();
            graph.addVertex(vertex);
            addVertexToPane(vertex);
            vertexField.clear();
        });

        addEdgeButton.setOnAction(e -> {
            String[] edge = edgeField.getText().split("-");
            if (edge.length == 2) {
                graph.addEdge(edge[0], edge[1]);
                addEdgeToPane(edge[0], edge[1]);
            }
            edgeField.clear();
        });

        dfsButton.setOnAction(e -> {
            String startVertex = vertexField.getText();
            System.out.println("DFS: " + graph.depthFirstSearch(startVertex));
        });

        bfsButton.setOnAction(e -> {
            String startVertex = vertexField.getText();
            System.out.println("BFS: " + graph.breadthFirstSearch(startVertex));
        });

        removeVertexButton.setOnAction(e -> {
            String vertex = vertexField.getText();
            graph.removeVertex(vertex);
            removeVertexFromPane(vertex);
            vertexField.clear();
        });

        checkEdgeButton.setOnAction(e -> {
            String[] edge = edgeField.getText().split("-");
            if (edge.length == 2) {
                boolean hasEdge = graph.hasEdge(edge[0], edge[1]);
                System.out.println("Has Edge: " + hasEdge);
            }
            edgeField.clear();
        });

        showVerticesButton.setOnAction(e -> {
            System.out.println("All Vertices: " + graph.getAllVertices());
        });

        getNeighborsButton.setOnAction(e -> {
            String vertex = vertexField.getText();
            System.out.println("Neighbors of " + vertex + ": " + graph.getNeighbors(vertex));
            vertexField.clear();
        });

        vbox.getChildren().addAll(label, vertexField, addVertexButton, edgeField, addEdgeButton, dfsButton, bfsButton, removeVertexButton, checkEdgeButton, showVerticesButton, getNeighborsButton, graphPane);

        Scene scene = new Scene(vbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addVertexToPane(String vertex) {
        Circle circle = new Circle(10, Color.BLUE);
        circle.setCenterX(Math.random() * 700 + 50);
        circle.setCenterY(Math.random() * 500 + 50);
        vertexCircles.put(vertex, circle);
        graphPane.getChildren().add(circle);
    }

    private void addEdgeToPane(String source, String destination) {
        Circle sourceCircle = vertexCircles.get(source);
        Circle destCircle = vertexCircles.get(destination);
        if (sourceCircle != null && destCircle != null) {
            Line line = new Line(sourceCircle.getCenterX(), sourceCircle.getCenterY(), destCircle.getCenterX(), destCircle.getCenterY());
            graphPane.getChildren().add(line);
            edges.computeIfAbsent(source, k -> new HashMap<>()).put(destination, line);
            edges.computeIfAbsent(destination, k -> new HashMap<>()).put(source, line);
        }
    }

    private void removeVertexFromPane(String vertex) {
        Circle circle = vertexCircles.remove(vertex);
        if (circle != null) {
            graphPane.getChildren().remove(circle);
        }
        Map<String, Line> vertexEdges = edges.remove(vertex);
        if (vertexEdges != null) {
            for (Line line : vertexEdges.values()) {
                graphPane.getChildren().remove(line);
            }
        }
        for (Map<String, Line> otherEdges : edges.values()) {
            Line line = otherEdges.remove(vertex);
            if (line != null) {
                graphPane.getChildren().remove(line);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}


