import GeneticAlgorithm.Mutatable;
import edu.princeton.cs.introcs.StdRandom;

import java.io.ObjectInputValidation;

public class RoulettePlayer implements Mutatable {
    private Tree policy;  //amount to bet on each option per turn
    private Tree currentTurn;
    private int startingFund;
    private int money = 0;
    public int maxTurns;
    
    RoulettePlayer(int rounds, RouletteGame game, int startingFund) {
        this.startingFund = startingFund;
        this.policy = new Tree(rounds, game.bet_options);
        this.maxTurns = rounds;
        reset();
    }
    RoulettePlayer(RoulettePlayer p) {
        this.startingFund = p.startingFund;
        this.policy.copy(p.policy);
        this.maxTurns = p.maxTurns;
        reset();
    }
    
    void reset() { //player starts without losing any money
        currentTurn = policy;
        money = startingFund;
    }
    
    void copy(RoulettePlayer p) {  // copy policy of player p
        this.policy.copy(p.policy);
    }
    
    int getMoney() {
        return money;
    }
    
    double bet(int option) {
        return currentTurn.get(option);
    }
    
    void update(int num, int new_money){
        money = new_money;
        currentTurn = currentTurn.next(num);
    }
    
    @Override
    public void mutate() {
        policy.mutate();
    }
    
    @Override
    public void crossover(Mutatable other) {
        assert (other instanceof RoulettePlayer);
        policy.crossover(((RoulettePlayer) other).policy);
    }
    
    @Override
    public Mutatable reproduction(Mutatable other) {
        assert (other instanceof RoulettePlayer);
        
        RoulettePlayer child = new RoulettePlayer(this);
        Tree newpolicy = (Tree) (policy.reproduction(((RoulettePlayer) other).policy));
        child.policy.copy(newpolicy);
        
        return child;
    }
    
    private class Tree implements Mutatable{
        private double[] policy;
        private Tree nextTurn;
        private int length;
        
        Tree(int rounds, int options) {
            length = rounds;
            policy = new double[options];
            init();
            
            nextTurn = null;
            if (rounds > 1) {
                nextTurn = new Tree(rounds - 1, options);
            }
        }
        Tree(Tree p) {
            length = p.length;
            copy(p);
        }
        
        void init() {
            double sum = 0;
            for (int i = 0; i < policy.length; i++) {
                policy[i] = StdRandom.uniform();
                sum += policy[i];
            }
            for (int i = 0; i < policy.length; i++) {
                policy[i] = policy[i] / sum;
            }
        }
        
        void copy(Tree p) {
            System.arraycopy(p.policy, 0, policy, 0, policy.length);
            
            nextTurn = null;
            if(p.nextTurn != null) {
                nextTurn = new Tree(p.nextTurn);
            }
        }
        
        double get(int option) {
            return policy[option];
        }
        
        Tree next(int num) {
            //return nextTurn[num];
            return nextTurn;
        }
    
        @Override
        public void mutate() {
            double mutationType = StdRandom.uniform();
            double[] mutationBin = {0.05, 0.35, 0.4, 1.0};
            
            if (mutationType <= mutationBin[0]) { //complete reinitialize
                init();
            } else if (mutationType <= mutationBin[1]) { //swap
                int p1 = StdRandom.uniform(policy.length);
                int p2 = StdRandom.uniform(policy.length);
                
                double temp = policy[p1];
                policy[p1] = policy[p2];
                policy[p2] = temp;
            } else if (mutationType <= mutationBin[2]) { //scramble
                for (int i = 0; i < policy.length; i++) {
                    int p1 = StdRandom.uniform(policy.length);
                    int p2 = StdRandom.uniform(policy.length);
    
                    double temp = policy[p1];
                    policy[p1] = policy[p2];
                    policy[p2] = temp;
                }
            } else { //displacement
                double[] temp = new double[policy.length];
                System.arraycopy(temp, 0, policy,0, policy.length);
                
                for (int i = 0; i < policy.length; i++) {
                    policy[i] = temp[i] * 0.9 + policy[i] * 0.1;
                }
            }
            
            if (nextTurn != null) {
                nextTurn.mutate();
            }
        }
    
        @Override
        public void crossover(Mutatable other) {
            assert (other instanceof Tree);
            
            //simple single point crossover
            int cut = StdRandom.uniform(length);
            Tree p = this;
            Tree paste = (Tree) other;
            
            for (int i = 0; i < cut; i++) {
                p = p.nextTurn;
                paste = paste.nextTurn;
            }
            if (p != null) {
                p.copy(paste);
            }
        }
    
        @Override
        public Mutatable reproduction(Mutatable other) {
            assert (other instanceof Tree);
            
            Tree child = new Tree(this);
            child.crossover(other);
            child.mutate();
            return child;
        }
    }
}
