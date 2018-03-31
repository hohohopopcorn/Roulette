package GeneticAlgorithm;

public class GeneticAlgorithm {
    GeneMap[] population;
    Simulation game;
    
    public GeneticAlgorithm(Simulation game, Mutatable[] population) {
        this.game = game;
        this.population = population;
    }
    
    public Mutatable run(int generations) {
        
        return population[0];
    }
    
    
    
    private void mutate(Mutatable obj) {
        obj.mutate();
    }
    private Mutatable[] reproduction (Mutatable[] obj) {
        return obj;
    }
    
    private Mutatable[] selection(Mutatable[] obj) {
        return obj;
    }
    
    private class GeneMap {
        Mutatable Gene;
        double fit;
        
        GeneMap ()
    }
}
