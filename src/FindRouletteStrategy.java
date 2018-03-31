import GeneticAlgorithm.GeneticAlgorithm;
import GeneticAlgorithm.Simulation;

public class FindRouletteStrategy {
    GeneticAlgorithm GA;
    
    FindRouletteStrategy(Simulation game, int round, int startingFunds, int popsize) {
        assert (game instanceof RouletteSimulation);
        RoulettePlayer[] pop = new RoulettePlayer[popsize];
        
        for (int i = 0; i < pop.length; i++) {
            pop[i] = new RoulettePlayer(round, ((RouletteSimulation) game).getGame(), startingFunds);
        }
        
        GA = new GeneticAlgorithm(game, pop);
    }
    
    public static void main (String[] args) {
        RouletteSimulation p = new RouletteSimulation(100, new EuropeanRoulette(), 0.5);
        FindRouletteStrategy Strategy = new FindRouletteStrategy(p, 20, 100, 1000);
    }
}
