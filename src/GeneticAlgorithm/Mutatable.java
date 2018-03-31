package GeneticAlgorithm;

public interface Mutatable {
    void mutate();
    void crossover(Mutatable other);
    Mutatable reproduction(Mutatable other);
}
