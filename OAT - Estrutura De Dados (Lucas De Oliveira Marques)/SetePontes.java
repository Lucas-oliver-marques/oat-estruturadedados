import java.util.*;

class Graph {
    private Map<String, List<String>> graph;
    private Set<String> vertices;

    public Graph(Set<String> vertices) {
        this.vertices = vertices;
        this.graph = new HashMap<>();
        for (String vertex : vertices) {
            graph.put(vertex, new ArrayList<>());
        }
    }

    public void addEdge(String u, String v) {
        graph.get(u).add(v);
        graph.get(v).add(u);
    }

    public boolean isConnected() {
        Map<String, Boolean> visited = new HashMap<>();
        for (String vertex : vertices) {
            visited.put(vertex, false);
        }
        
        Stack<String> stack = new Stack<>();
        String startVertex = null;

        for (String vertex : vertices) {
            if (graph.get(vertex).size() > 0) {
                startVertex = vertex;
                break;
            }
        }

        if (startVertex == null) {
            return true;
        }

        stack.push(startVertex);
        visited.put(startVertex, true);

        while (!stack.isEmpty()) {
            String u = stack.pop();
            for (String v : graph.get(u)) {
                if (!visited.get(v)) {
                    visited.put(v, true);
                    stack.push(v);
                }
            }
        }

        for (String vertex : vertices) {
            if (!visited.get(vertex) && graph.get(vertex).size() > 0) {
                return false;
            }
        }

        return true;
    }

    public boolean isEulerian() {
        if (!isConnected()) {
            return false;
        }

        int oddDegreeVertices = 0;
        for (String vertex : vertices) {
            if (graph.get(vertex).size() % 2 != 0) {
                oddDegreeVertices++;
            }
        }

        return oddDegreeVertices == 0 || oddDegreeVertices == 2;
    }

    public List<String> findEulerianPath() {
        if (!isEulerian()) {
            return null;
        }

        Stack<String> stack = new Stack<>();
        List<String> path = new ArrayList<>();
        String currentVertex = null;

        for (String vertex : vertices) {
            if (graph.get(vertex).size() % 2 != 0) {
                currentVertex = vertex;
                break;
            }
        }

        if (currentVertex == null) {
            currentVertex = vertices.iterator().next();
        }

        stack.push(currentVertex);

        while (!stack.isEmpty()) {
            if (graph.get(currentVertex).isEmpty()) {
                path.add(currentVertex);
                currentVertex = stack.pop();
            } else {
                stack.push(currentVertex);
                String nextVertex = graph.get(currentVertex).remove(0);
                graph.get(nextVertex).remove(currentVertex);
                currentVertex = nextVertex;
            }
        }

        path.add(currentVertex);
        return path;
    }

    public static void main(String[] args) {
        Set<String> vertices = new HashSet<>(Arrays.asList("A", "B", "C", "D"));
        List<String[]> edges = Arrays.asList(
                new String[]{"A", "B"}, new String[]{"A", "B"}, 
                new String[]{"A", "C"}, new String[]{"A", "D"}, 
                new String[]{"B", "C"}, new String[]{"B", "D"}, 
                new String[]{"C", "D"}
        );

        Graph g = new Graph(vertices);
        for (String[] edge : edges) {
            g.addEdge(edge[0], edge[1]);
        }

        if (g.isEulerian()) {
            List<String> path = g.findEulerianPath();
            System.out.println("Caminho Euleriano encontrado: " + path);
        } else {
            System.out.println("Não é possível encontrar um Caminho Euleriano no grafo dado.");
        }
    }
}
