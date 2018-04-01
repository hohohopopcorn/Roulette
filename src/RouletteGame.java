import edu.princeton.cs.introcs.StdRandom;

public abstract class RouletteGame {
    int numbers;
    int bet_options;
    int[] winnings;
    
    abstract void rollOnce(RoulettePlayer p, int num);
    
    int win(RoulettePlayer p, int option) {
        if (option < 0 || option > bet_options) {
            throw new IllegalArgumentException();
        }
        
        return (int) Math.floor(p.bet(option) * (double) p.getMoney()) * winnings[option];
    }
    
    void update(RoulettePlayer p, int money) {
        p.update(money);
    }
}
