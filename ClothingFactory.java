//Q no 2a

public class ClothingFactory {

    public static int minMovesToEqualize(int[] machines) {
        int totalDresses = 0;
        for (int dresses : machines) {
            totalDresses += dresses;
        }

        int n = machines.length;
        if (totalDresses % n != 0) {
            // It's impossible to distribute dresses equally
            return -1;
        }

        int avg = totalDresses / n;
        int moves = 0, runningDifference = 0;

        for (int dresses : machines) {
            int difference = dresses - avg;
            runningDifference += difference;
            moves = Math.max(moves, Math.abs(runningDifference));
        }

        return moves;
    }

    public static void main(String[] args) {
        int[] machines = {2, 1, 3, 0, 2};
        System.out.println("Minimum moves to equalize: " + minMovesToEqualize(machines));
    }
}
