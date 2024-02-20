// Q.no 2b


import java.util.*;

public class SecretSharing {

    public static List<Integer> whoKnowsSecret(int n, int[][] intervals, int firstPerson) {
        // Track who knows the secret
        boolean[] knowsSecret = new boolean[n];
        knowsSecret[firstPerson] = true;

        // Queue for BFS
        Queue<Integer> queue = new LinkedList<>();

        // Add the first person to the queue
        queue.add(firstPerson);

        // Process each interval
        for (int[] interval : intervals) {
            int start = interval[0];
            int end = interval[1];

            // Perform BFS for each interval
            while (!queue.isEmpty()) {
                int person = queue.poll();

                // Share the secret with others in the interval
                for (int i = start; i <= end; i++) {
                    if (!knowsSecret[i]) {
                        knowsSecret[i] = true;
                        queue.add(i);
                    }
                }
            }
        }

        // Collect all individuals who know the secret
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (knowsSecret[i]) {
                result.add(i);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int n = 5;
        int[][] intervals = {{0, 2}, {1, 3}, {2, 4}};
        int firstPerson = 0;
        List<Integer> individualsWhoKnow = whoKnowsSecret(n, intervals, firstPerson);
        System.out.println("Individuals who eventually know the secret: " + individualsWhoKnow);
    }
}
