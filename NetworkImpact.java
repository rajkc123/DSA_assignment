
//q no 5b
import java.util.*;

public class NetworkImpact {

    private Map<Integer, List<Integer>> buildGraph(int[][] edges) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int[] edge : edges) {
            graph.putIfAbsent(edge[0], new ArrayList<>());
            graph.putIfAbsent(edge[1], new ArrayList<>());
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]); // Assuming it's an undirected graph
        }
        return graph;
    }

    private void dfs(Map<Integer, List<Integer>> graph, int source, Set<Integer> visited) {
        visited.add(source);
        if (graph.containsKey(source)) {
            for (int neighbor : graph.get(source)) {
                if (!visited.contains(neighbor)) {
                    dfs(graph, neighbor, visited);
                }
            }
        }
    }

    public List<Integer> getImpactedDevices(int[][] edges, int targetDevice) {
        Map<Integer, List<Integer>> graph = buildGraph(edges);
        Set<Integer> visited = new HashSet<>();
        dfs(graph, targetDevice, visited);
        visited.remove(targetDevice); // Remove the target device itself if not considering it as impacted
        return new ArrayList<>(visited);
    }

    public static void main(String[] args) {
        NetworkImpact networkImpact = new NetworkImpact();
        int[][] edges = {{0,1},{0,2},{1,3},{1,6},{2,4},{4,6},{4,5},{5,7}};
        int targetDevice = 4;
        List<Integer> impactedDevices = networkImpact.getImpactedDevices(edges, targetDevice);
        System.out.println("Impacted Device List = " + impactedDevices);
    }
}
