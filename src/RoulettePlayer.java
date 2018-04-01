import GeneticAlgorithm.Mutatable;
import edu.princeton.cs.introcs.StdRandom;

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
        this.maxTurns = p.maxTurns;
        this.copy(p);
        reset();
    }
    
    void reset() { //player starts without losing any money
        currentTurn = policy;
        money = startingFund;
    }
    
    void copy(RoulettePlayer p) {  // copy policy of player p
        this.policy = new Tree(p.maxTurns, p.policy.length);
        this.policy.copy(p.policy);
    }
    
    int getMoney() {
        return money;
    }
    
    double bet(int option) {
        return currentTurn.get(option);
    }
    
    void update(int new_money){
        money = new_money;
        currentTurn = currentTurn.next();
    }
    
    void show() {
        policy.show();
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
        
        void show() {
            for (int i = 0; i < policy.length; i++) {
                System.out.printf("%2d ", (int)(100.0 * policy[i]));
            }
            System.out.println();
            if (nextTurn != null) {
                nextTurn.show();
            }
        }
        
        void init() {
            double sum = 0;
            for (int j = 0; j < 20; j++) {
                int i = StdRandom.uniform(policy.length);
                policy[i] = Math.abs(StdRandom.gaussian());
                sum += policy[i];
            }
            for (int i = 0; i < policy.length; i++) {
                policy[i] = policy[i] / sum;
            }
        }
        
        void copy(Tree p) {
            this.policy = new double[p.policy.length];
            System.arraycopy(p.policy, 0, policy, 0, policy.length);
            
            nextTurn = null;
            if(p.nextTurn != null) {
                nextTurn = new Tree(p.nextTurn);
            }
        }
        
        double get(int option) {
            return policy[option];
        }
        
        Tree next() {
            //return nextTurn[num];
            return nextTurn;
        }
    
        @Override
        public void mutate() {
            double mutationType = StdRandom.uniform();
            double[] mutationBin = {0.05, 0.15, 0.45, 0.8, 1.0};
            
            if (mutationType <= mutationBin[0]) { //complete reinitialize
                init();
            } else if (mutationType <= mutationBin[1]) { //swap
                int p1 = StdRandom.uniform(policy.length);
                int p2 = StdRandom.uniform(policy.length);
                
                double temp = policy[p1];
                policy[p1] = policy[p2];
                policy[p2] = temp;
            } else if (mutationType <= mutationBin[2]) { //displacement
                int idx = StdRandom.uniform(policy.length);
                
                policy[idx] += 0.01;
                double sum = 0;
                for (int i = 0; i < policy.length; i++) {
                    sum += policy[i];
                }
                for (int i = 0; i < policy.length; i++) {
                    policy[i] = policy[i] / sum;
                }
            } else if (mutationType <= mutationBin[3]) { //large displacement
                double change = Math.min(Math.abs(StdRandom.gaussian()), 0.1);
                for (int i = 0; i < policy.length; i++) {
                    policy[i] = policy[i] * (1.0 - change);
                }
                int idx = StdRandom.uniform(policy.length);
                policy[idx] += change;
            }
            
            if (nextTurn != null) {
                nextTurn.mutate();
            }
        }
    
        @Override
        public void crossover(Mutatable other) {
            assert (other instanceof Tree);
            
            //simple percentage cut
            double p;
            double sum = 0;
            for (int i = 0; i < policy.length; i++) {
                p = StdRandom.uniform();
                if (p >= 0.5) {
                    policy[i] = ((Tree) other).policy[i];
                }
                sum += policy[i];
            }
            for (int i = 0; i < policy.length; i++) {
                policy[i] = policy[i] / sum;
            }
            
            if (nextTurn != null) {
                nextTurn.crossover(((Tree) other).nextTurn);
            }
        }
    
        @Override
        public Mutatable reproduction(Mutatable other) {
            assert (other instanceof Tree);
            
            Tree child = new Tree(this);
            child.crossover(other);
            return child;
        }
    }
    
    public static void main (String[] args) {
        RoulettePlayer p1 = new RoulettePlayer(1, new EuropeanRoulette(), 100);
        RoulettePlayer p2 = new RoulettePlayer(1, new EuropeanRoulette(), 100);
        RoulettePlayer child;
        
        p1.show();
        p2.show();
        System.out.println();
        for (int i = 0; i < 1; i++) {
            child = (RoulettePlayer) p1.reproduction(p2);
            child.show();
        }
        System.out.println();
        p1.show();
        p2.show();
    }
}
