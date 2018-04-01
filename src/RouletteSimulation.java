import GeneticAlgorithm.Mutatable;
import GeneticAlgorithm.Simulation;
import edu.princeton.cs.introcs.StdStats;

public class RouletteSimulation implements Simulation{
    private double[] money;
    private int numbers;
    private RouletteGame game;
    
    RouletteSimulation(RouletteGame game) {
        this.numbers = game.numbers;
        this.game = game;
    }
    
    private void run(RoulettePlayer player, int start, int turns) {
        double expected = 0;
        RoulettePlayer p = new RoulettePlayer(player);
        
        for (int i = 0; i < numbers; i++) {
            p.reset();
            for (int j = 0; j < turns; j++) {
                p.update(start);
            }
            game.rollOnce(p, i);
            expected += (double) p.getMoney();
        }
        expected = expected / (double) numbers;
        money[turns + 1] = expected;
        if (turns + 1 < player.maxTurns) {
            run(player, (int) expected, turns + 1);
        }
    }
    
    RouletteGame getGame() {
        return game;
    }
    
    @Override
    public double fitness(Mutatable player) {
        assert (player instanceof RoulettePlayer);
        ((RoulettePlayer) player).reset();
        money = new double[((RoulettePlayer) player).maxTurns + 1];
        money[0] = (double) ((RoulettePlayer) player).getMoney();
        run((RoulettePlayer) player, (int) money[0], 0);
        
        double fitness = 0;
        ((RoulettePlayer) player).reset();
        for (int i = 1; i <= ((RoulettePlayer) player).maxTurns; i++) {
            fitness += ((double) money[i] - (double) money[0]) / (double) money[0] * (double) i;
        }
        
        return fitness;
    }
    
    public void show(RoulettePlayer player) {
        fitness(player);
        System.out.printf("%5d ", (int) money[0]);
        for (int i = 0; i < player.maxTurns; i++) {
            System.out.printf("%5d ", (int) money[i + 1]);
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        RouletteSimulation p = new RouletteSimulation(new EuropeanRoulette());
        
        System.out.println(p.fitness(new RoulettePlayer(20, new EuropeanRoulette(), 100)));
    }
}
