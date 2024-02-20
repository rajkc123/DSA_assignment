//q no 5a 

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AntColonyOptimization {
    private double[][] distance;
    private double[][] pheromones;
    private List<Ant> ants = new ArrayList<>();
    private Random rand = new Random();
    private double bestTourLength = Double.MAX_VALUE;
    private List<Integer> bestTourOrder = new ArrayList<>();
    private int cityCount;
    private int antCount;
    private double evaporation;
    private double alpha;
    private double beta;

    public AntColonyOptimization(int cityCount, int antCount, double evaporation, double alpha, double beta) {
        this.cityCount = cityCount;
        this.antCount = antCount;
        this.evaporation = evaporation;
        this.alpha = alpha;
        this.beta = beta;
        
        distance = new double[cityCount][cityCount];
        pheromones = new double[cityCount][cityCount];
        
        // Example distance matrix initialization
        // In a real scenario, you would initialize it with actual distances
        for (int i = 0; i < cityCount; i++) {
            for (int j = 0; j < cityCount; j++) {
                distance[i][j] = Math.abs(i - j) + 1; // Replace with actual distances
                pheromones[i][j] = 0.1;
            }
        }
        
        for (int i = 0; i < antCount; i++) {
            ants.add(new Ant(cityCount));
        }
    }

    public void solve() {
        for (int i = 0; i < 100; i++) { // Number of iterations
            for (Ant ant : ants) {
                ant.visitCity(rand.nextInt(cityCount));
            }

            for (int j = 0; j < cityCount - 1; j++) {
                for (Ant ant : ants) {
                    int nextCity = selectNextCity(ant);
                    ant.visitCity(nextCity);
                }
            }

            updatePheromones();
        }

        System.out.println("Best tour length: " + bestTourLength);
        System.out.println("Best tour order: " + bestTourOrder);
    }

    private int selectNextCity(Ant ant) {
        int currentCity = ant.currentCity();
        List<Double> probabilities = new ArrayList<>();
        double pheromoneSum = 0.0;

        for (int i = 0; i < cityCount; i++) {
            if (!ant.visited(i)) {
                pheromoneSum += Math.pow(pheromones[currentCity][i], alpha) * Math.pow(1.0 / distance[currentCity][i], beta);
            }
        }

        for (int i = 0; i < cityCount; i++) {
            if (ant.visited(i)) {
                probabilities.add(0.0);
            } else {
                double probability = Math.pow(pheromones[currentCity][i], alpha) * Math.pow(1.0 / distance[currentCity][i], beta);
                probability /= pheromoneSum;
                probabilities.add(probability);
            }
        }

        return rouletteWheel(probabilities);
    }

    private int rouletteWheel(List<Double> probabilities) {
        double randomValue = rand.nextDouble();
        double cumulativeProbability = 0.0;
        for (int i = 0; i < cityCount; i++) {
            cumulativeProbability += probabilities.get(i);
            if (randomValue <= cumulativeProbability) {
                return i;
            }
        }
        return -1;
    }

    private void updatePheromones() {
        // Evaporation
        for (int i = 0; i < cityCount; i++) {
            for (int j = 0; j < cityCount; j++) {
                pheromones[i][j] *= (1 - evaporation);
            }
        }
    
        // Intensification
        for (Ant ant : ants) {
            double contribution = 1.0 / ant.tourLength();
            for (int i = 0; i < cityCount - 1; i++) {
                int from = ant.getTour().get(i);
                int to = ant.getTour().get(i + 1);
                pheromones[from][to] += contribution;
                pheromones[to][from] += contribution; // If the graph is undirected
            }
            // Add contribution for the return to the starting city
            int from = ant.getTour().get(cityCount - 1);
            int to = ant.getTour().get(0);
            pheromones[from][to] += contribution;
            pheromones[to][from] += contribution; // If the graph is undirected
        }
    
    for (Ant ant : ants) {
        double contribution = 1.0 / ant.tourLength();
        for (int i = 0; i < cityCount - 1; i++) {
            pheromones[ant.getTour().get(i)][ant.getTour().get(i + 1)] += contribution;
            pheromones[ant.getTour().get(i + 1)][ant.getTour().get(i)] += contribution; // For undirected graph
        }
        // Add contribution for the return to the starting city
        pheromones[ant.getTour().get(cityCount - 1)][ant.getTour().get(0)] += contribution;
        pheromones[ant.getTour().get(0)][ant.getTour().get(cityCount - 1)] += contribution; // For undirected graph

        // Update the best tour found
        if (ant.tourLength() < bestTourLength) {
            bestTourLength = ant.tourLength();
            bestTourOrder = new ArrayList<>(ant.getTour());
        }

        ant.clear(); // Clear the visited cities for the next iteration
    }
}

class Ant {
    private List<Integer> tour = new ArrayList<>();
    private boolean[] visited;
    private int lastVisitedCity;

    public Ant(int cityCount) {
        visited = new boolean[cityCount];
        lastVisitedCity = -1;
    }

    public void visitCity(int city) {
        tour.add(city);
        lastVisitedCity = city;
        visited[city] = true;
    }

    public boolean visited(int city) {
        return visited[city];
    }

    public int currentCity() {
        return lastVisitedCity;
    }

    public double tourLength() {
        double length = 0.0;
        for (int i = 0; i < tour.size() - 1; i++) {
            length += distance[tour.get(i)][tour.get(i + 1)];
        }
        length += distance[tour.get(tour.size() - 1)][tour.get(0)]; // Return to the starting city
        return length;
    }

    public List<Integer> getTour() {
        return tour;
    }

    public void clear() {
        Arrays.fill(visited, false);
        tour.clear();
    }
}

public static void main(String[] args) {
    int cityCount = 5;
    int antCount = 10;
    double evaporation = 0.5;
    double alpha = 1;
    double beta = 5;
    
    AntColonyOptimization aco = new AntColonyOptimization(cityCount, antCount, evaporation, alpha, beta);
    aco.solve();
}
}
