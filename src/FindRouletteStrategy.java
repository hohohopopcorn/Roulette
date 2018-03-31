import GeneticAlgorithm.GeneticAlgorithm;
import GeneticAlgorithm.Simulation;

public class FindRouletteStrategy {
    GeneticAlgorithm GA;
    
    public FindRouletteStrategy(Simulation game, int round, int startingFunds, int popsize, double rate) {
        assert (game instanceof RouletteSimulation);
        RoulettePlayer[] pop = new RoulettePlayer[popsize];
        
        for (int i = 0; i < pop.length; i++) {
            pop[i] = new RoulettePlayer(round, ((RouletteSimulation) game).getGame(), startingFunds);
        }
        
        GA = new GeneticAlgorithm(game, pop, rate);
    }
    
    public RoulettePlayer run(int iterations, RouletteSimulation game) {
        RoulettePlayer best = null;
        for (int i = 0; i < iterations; i++) {
            best = (RoulettePlayer) GA.run();
            System.out.printf("%5d %10f    ", i + 1, game.fitness(best));
            game.show(best);
            best.show();
        }
        return best;
    }
    
    public static void main (String[] args) {
        RouletteSimulation p = new RouletteSimulation(1000, new EuropeanRoulette(), 0.75);
        FindRouletteStrategy Strategy = new FindRouletteStrategy(p, 1, 100, 1000000, 0.99);
        RoulettePlayer best = Strategy.run(1000000, p);
    }
}
