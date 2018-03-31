import GeneticAlgorithm.Mutatable;
import GeneticAlgorithm.Simulation;
import edu.princeton.cs.introcs.StdStats;

public class RouletteSimulation implements Simulation{
    private int money[][];
    private double fitness[];
    private int trials;
    private RouletteGame game;
    private double RISK_AVERSE = 1.0;
    
    RouletteSimulation(int N, RouletteGame game, double risk) {
        fitness = new double[N];
        RISK_AVERSE = risk;
        trials = N;
        this.game = game;
    }
    
    private void run(RoulettePlayer player) {
        money = new int[trials][player.maxTurns + 1];
        for (int i = 0; i < trials; i++) {
            player.reset();
            money[i][0] = player.getMoney();
            
            System.out.printf("%10d ", money[i][0]);
            
            for (int j = 0; j < player.maxTurns; j++) {
                game.rollOnce(player);
                money[i][j + 1] = player.getMoney();
                System.out.printf("%10d ", money[i][j + 1]);
            }
            System.out.println();
        }
    }
    
    RouletteGame getGame() {
        return game;
    }
    
    @Override
    public double fitness(Mutatable player) {
        assert (player instanceof RoulettePlayer);
        run((RoulettePlayer) player);
        
        for (int i = 0; i < trials; i++) {
            fitness[i] = 0;
            for (int j = 1; j < money[i].length; j++) {
                fitness[i] += (double) (money[i][j] - money[i][0]) / (double) money[i][0] * (double) j;
            }
        }
        
        return RISK_AVERSE * confidenceLow() + (1.0 - RISK_AVERSE) * mean();
    }
    
    private double mean() {
        return StdStats.mean(fitness);
    }
    private double stddev() {
        return StdStats.stddev(fitness);
    }
    private double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt((double) fitness.length);
    }
    private double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt((double) fitness.length);
    }
    
    public static void main(String[] args) {
        RouletteSimulation p = new RouletteSimulation(100, new EuropeanRoulette(), 0.5);
        
        System.out.println(p.fitness(new RoulettePlayer(20, new EuropeanRoulette(), 100)));
    }
}
