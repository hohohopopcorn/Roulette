import edu.princeton.cs.introcs.StdRandom;

public class RoulettePlayer {
    private Tree policy;  //amount to bet on each option per turn
    private Tree currentTurn;
    private int startingFund;
    private int money = 0;
    
    RoulettePlayer(int rounds, int optionSize, int startingFund, int numbers) {
        this.startingFund = startingFund;
        this.policy = new Tree(rounds, optionSize, numbers);
        reset();
    }
    
    void reset() { //player starts without losing any money
        this.currentTurn = this.policy;
        this.money = this.startingFund;
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
        currentTurn = currentTurn.nextTurn[num];
    }
    
    private class Tree {
        private double[] policy;
        private Tree[] nextTurn;
        
        Tree(int rounds, int options, int numbers) {
            this.policy = new double[options];
            this.nextTurn = new Tree[numbers];
            
            double sum = 0;
            for (int i = 0; i < this.policy.length; i++) {
                this.policy[i] = StdRandom.uniform();
                sum += this.policy[i];
            }
            for (int i = 0; i < this.policy.length; i++) {
                this.policy[i] = this.policy[i] / sum;
            }
            
            for (int i = 0; i < this.nextTurn.length; i++) {
                this.nextTurn[i] = null;
                if (rounds > 1) {
                    this.nextTurn[i] = new Tree(rounds - 1, options, numbers);
                }
            }
        }
        
        void copy(Tree p) {
            System.arraycopy(p.policy, 0, this.policy, 0, this.policy.length);
            
            for(int i = 0; i < nextTurn.length; i++) {
                if(p.nextTurn[i] != null) {
                    p.nextTurn[i].copy(p.nextTurn[i]);
                }
            }
        }
        
        double get(int option) {
            return policy[option];
        }
    }
}
