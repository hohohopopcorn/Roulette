package GeneticAlgorithm;

public class SortedGeneMap {
    private Simulation game;
    private GeneMap[] array;
    private int end;
    
    SortedGeneMap(int N, Simulation game){
        array = new GeneMap[N];
        end = 0;
        this.game = game;
    }
    
    void insert(Mutatable m) {
        if (isFilled()) {
            throw new IllegalCallerException();
        }
        array[end] = new GeneMap(m);
        end++;
        swim(end - 1);
    }
    
    void remove(int index) {
        if (isEmpty()) {
            throw new IllegalCallerException();
        }
        array[index] = array[end - 1];
        end--;
        sink(index);
    }
    
    public Mutatable get(int index) {
        if (index < 0 || index >= end) {
            throw new IllegalArgumentException();
        }
        return array[index].getGene();
    }
    public double getFit(int index) {
        if (index < 0 || index >= end) {
            throw new IllegalArgumentException();
        }
        return array[index].getFit();
    }
    
    public int getLength() {
        return end;
    }
    
    public boolean isEmpty() {
        return end == 0;
    }
    public boolean isFilled() {
        return end == array.length;
    }
    public int emptySlots() {
        return array.length - end;
    }
    
    private void swim(int index) {
        for (int i = index; i > 0; i--) {
            if (array[index].getFit() > array[index - 1].getFit()) {
                swap(index, index - 1);
            } else {
                break;
            }
        }
    }
    
    private void sink(int index) {
        for (int i = index; i < end - 1; i++) {
            if (array[index + 1].getFit() > array[index].getFit()) {
                swap(index + 1, index);
            } else {
                break;
            }
        }
    }
    
    private void swap(int a, int b) {
        GeneMap temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }
    
    private class GeneMap {
        private Mutatable Gene;
        private double fit;
        
        GeneMap(Mutatable Gene) {
            this.Gene = Gene;
            this.fit = game.fitness(this.Gene);
        }
        double getFit() {
            return fit;
        }
        Mutatable getGene() {
            return Gene;
        }
    }
}
