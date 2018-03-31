import edu.princeton.cs.introcs.StdRandom;

public class RoulettePlayer {
    private Tree policy;  //amount to bet on each option per turn
    private Tree currentTurn;
    private int startingFund;
    private int money = 0;
    public int maxTurns;
    
    RoulettePlayer(int rounds, RouletteGame game, int startingFund) {
        this.startingFund = startingFund;
        this.policy = new Tree(rounds, game.bet_options, game.numbers);
        this.maxTurns = rounds;
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
    
    private class Tree {
        private double[] policy;
        private Tree[] nextTurn;
        
        Tree(int rounds, int options, int numbers) {
            policy = new double[options];
            //nextTurn = new Tree[numbers];
            nextTurn = new Tree[1];
            
            double sum = 0;
            for (int i = 0; i < policy.length; i++) {
                policy[i] = StdRandom.uniform();
                sum += policy[i];
            }
            for (int i = 0; i < policy.length; i++) {
                policy[i] = policy[i] / sum;
            }
            
            for (int i = 0; i < nextTurn.length; i++) {
                nextTurn[i] = null;
                if (rounds > 1) {
                    nextTurn[i] = new Tree(rounds - 1, options, numbers);
                }
            }
        }
        
        void copy(Tree p) {
            System.arraycopy(p.policy, 0, policy, 0, policy.length);
            
            for(int i = 0; i < nextTurn.length; i++) {
                if(p.nextTurn[i] != null) {
                    p.nextTurn[i].copy(p.nextTurn[i]);
                }
            }
        }
        
        double get(int option) {
            return policy[option];
        }
        
        Tree next(int num) {
            //return nextTurn[num];
            return nextTurn[0];
        }
    }
}
