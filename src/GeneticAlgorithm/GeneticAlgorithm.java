package GeneticAlgorithm;

import edu.princeton.cs.introcs.StdRandom;

public class GeneticAlgorithm {
    private SortedGeneMap population;
    private double selectionRate;
    
    public GeneticAlgorithm(Simulation game, Mutatable[] population, double rate) {
        this.selectionRate = rate;
        this.population = new SortedGeneMap(population.length, game);
        for (int i = 0; i < population.length; i++) {
            this.population.insert(population[i]);
        }
    }
    
    public Mutatable run() {
        selection();
        reproduction();
        mutation();
        return population.get(0);
    }
    
    private int findPartner(int maxNum) {
        double num = Math.abs(StdRandom.gaussian(0, (double) maxNum / 3.0));
        if (num >= (double) maxNum) {
            num = 0;
        }
        return (int) Math.floor(num);
    }
    
    private void reproduction () {
        int emptySlots = population.emptySlots();
        Mutatable[] children = new Mutatable[emptySlots];
        
        for (int i = 0; i < emptySlots; i++) {
            Mutatable p1 = population.get(findPartner(population.getLength()));
            Mutatable p2 = population.get(findPartner(population.getLength()));
            children[i] = p1.reproduction(p2);
        }
        for (int i = 0; i < emptySlots; i++) {
            population.insert(children[i]);
        }
    }
    
    private void mutation() {
        double mutationRate = 0.001;
        
        for (int i = 0; i < (int) Math.floor((double) population.getLength() * mutationRate); i++) {
            int idx = StdRandom.uniform(population.getLength());
            Mutatable mutatedGene = population.get(idx);
            population.remove(idx);
            
            mutatedGene.mutate();
            population.insert(mutatedGene);
        }
    }
    
    private void selection() {
        //simple cutoff selection
        int cutoff= (int) Math.floor((double) population.getLength() * selectionRate);
        while (population.getLength() != cutoff) {
            population.remove(population.getLength() - 1);
        }
    }
}
