//Q NO 4A

import java.util.*;

public class MazeSolver {
    static final int[] dx = {-1, 1, 0, 0};
    static final int[] dy = {0, 0, -1, 1};

    static class State {
        int x, y, keys, moves;

        State(int x, int y, int keys, int moves) {
            this.x = x;
            this.y = y;
            this.keys = keys;
            this.moves = moves;
        }
    }

    public static int minMovesToCollectKeys(String[] grid) {
        int m = grid.length;
        int n = grid[0].length();
        Queue<State> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        int allKeys = 0;

        // Find the starting point and the number of keys
        int startX = 0, startY = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char cell = grid[i].charAt(j);
                if (cell == 'S') {
                    startX = i;
                    startY = j;
                } else if (cell >= 'a' && cell <= 'f') {
                    allKeys |= (1 << (cell - 'a'));
                }
            }
        }

        queue.offer(new State(startX, startY, 0, 0));
        visited.add(startX + "," + startY + ",0");

        while (!queue.isEmpty()) {
            State current = queue.poll();

            if (current.keys == allKeys) { // All keys collected
                return current.moves;
            }

            for (int i = 0; i < 4; i++) {
                int newX = current.x + dx[i];
                int newY = current.y + dy[i];
                int newKeys = current.keys;

                if (newX >= 0 && newX < m && newY >= 0 && newY < n) {
                    char cell = grid[newX].charAt(newY);
                    if (cell == 'W') continue; // Wall
                    if (cell >= 'A' && cell <= 'F' && (newKeys & (1 << (cell - 'A'))) == 0) continue; // Locked door without key

                    if (cell >= 'a' && cell <= 'f') { // Key
                        newKeys |= (1 << (cell - 'a'));
                    }

                    String visitedState = newX + "," + newY + "," + newKeys;
                    if (!visited.contains(visitedState)) {
                        queue.offer(new State(newX, newY, newKeys, current.moves + 1));
                        visited.add(visitedState);
                    }
                }
            }
        }

        return -1; // Impossible to collect all keys
    }

    public static void main(String[] args) {
        String[] grid1 = {"SPaPP", "WWWPW", "bPAPB"};
        System.out.println("Minimum moves to collect all keys: " + minMovesToCollectKeys(grid1));
    }
}
