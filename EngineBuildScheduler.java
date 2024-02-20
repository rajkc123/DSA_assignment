//Q no 1b

import java.util.PriorityQueue;
import java.util.Collections;

public class EngineBuildScheduler {

    public static int minTimeToBuildEngines(int[] engines, int splitCost) {
        // Using a priority queue as a max heap
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        // Add all engines to the max heap
        for (int engineTime : engines) {
            maxHeap.add(engineTime);
        }

        // Total time for splits
        int totalSplitTime = 0;

        // Keep splitting the engineer working on the longest task until the number of tasks equals the number of engineers
        while (maxHeap.size() < engines.length) {
            // Remove the largest element (longest task)
            int longestTask = maxHeap.poll();

            // Split the engineer (add split cost)
            totalSplitTime += splitCost;

            // Half the longest task and add it back to the heap twice
            int newTaskTime = longestTask / 2;
            maxHeap.add(newTaskTime);
            maxHeap.add(newTaskTime);
        }

        // The total time is the maximum of the times in the heap plus the total split time
        return totalSplitTime + maxHeap.peek();
    }

    public static void main(String[] args) {
        int[] engines = {3, 4, 5, 2};
        int splitCost = 2;
        int result = minTimeToBuildEngines(engines, splitCost);
        System.out.println("Minimum time to build all engines: " + result);
    }
}
