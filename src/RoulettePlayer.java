import edu.princeton.cs.introcs.StdRandom;

public class RoulettePlayer {
    private int rounds;
    private Tree policy;  //amount to bet on each option per turn
    private int optionSize; // number of options to bet
    private int[] history;  //history of wins and loses
    private int money = 0;
    
    RoulettePlayer(int rounds, int optionSize, int startingFund) {
        this.money = startingFund;
        this.rounds = rounds;
        this.optionSize = optionSize;
        this.policy = new Tree(this.rounds, this.optionSize);
        this.history = new int[this.rounds];
        
        for (int i = 0; i < this.rounds; i++) {
            history[i] = 0;
        }
    }
    
    void copy(RoulettePlayer p) {  // copy policy of player p
        this.policy.copy(p.policy);
    }
    
    public static void main(String[] Args) {
        RoulettePlayer p1 = new RoulettePlayer(10, 37, 100);
        
    }
    
    private class Tree {
        private double[] policy;
        private Tree lose;
        private Tree win;
        Tree(int rounds, int options) {
            this.policy = new double[options];
    
            double sum = 0;
            for (int i = 0; i < this.policy.length; i++) {
                this.policy[i] = StdRandom.uniform();
                sum += this.policy[i];
            }
            for (int i = 0; i < this.policy.length; i++) {
                this.policy[i] = this.policy[i] / sum;
            }
            
            if (rounds > 1) {
                this.lose = new Tree(rounds - 1, options);
                this.win = new Tree(rounds - 1, options);
            }
        }
        
        void copy(Tree p) {
            System.arraycopy(p.policy, 0, this.policy, 0, this.policy.length);
            if(p.lose != null) {
                p.lose.copy(p.lose);
            }
            if(p.win != null) {
                p.win.copy(p.win);
            }
        }
    }
}
