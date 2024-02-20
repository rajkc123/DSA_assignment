// q .No 3 a

import java.util.PriorityQueue;
import java.util.Collections;

public class ScoreTracker {
    private PriorityQueue<Double> lowerHalf; // Max heap
    private PriorityQueue<Double> upperHalf; // Min heap

    public ScoreTracker() {
        lowerHalf = new PriorityQueue<>(Collections.reverseOrder()); // Max heap
        upperHalf = new PriorityQueue<>(); // Min heap
    }

    public void addScore(double score) {
        if (lowerHalf.isEmpty() || score <= lowerHalf.peek()) {
            lowerHalf.add(score);
        } else {
            upperHalf.add(score);
        }

        // Balance the heaps
        if (lowerHalf.size() > upperHalf.size() + 1) {
            upperHalf.add(lowerHalf.poll());
        } else if (upperHalf.size() > lowerHalf.size()) {
            lowerHalf.add(upperHalf.poll());
        }
    }

    public double getMedianScore() {
        if (lowerHalf.size() == upperHalf.size()) {
            return (lowerHalf.peek() + upperHalf.peek()) / 2.0;
        } else {
            return lowerHalf.peek();
        }
    }

    public static void main(String[] args) {
        ScoreTracker scoreTracker = new ScoreTracker();
        scoreTracker.addScore(85.5);
        scoreTracker.addScore(92.3);
        scoreTracker.addScore(77.8);
        scoreTracker.addScore(90.1);
        double median1 = scoreTracker.getMedianScore();
        System.out.println("Median after 4 scores: " + median1);

        scoreTracker.addScore(81.2);
        scoreTracker.addScore(88.7);
        double median2 = scoreTracker.getMedianScore();
        System.out.println("Median after 6 scores: " + median2);
    }
}
