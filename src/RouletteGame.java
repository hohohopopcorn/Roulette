import edu.princeton.cs.introcs.StdRandom;

public abstract class RouletteGame {
    int numbers;
    int bet_options;
    int[] winnings;
    
    protected int roll() {
        return StdRandom.uniform(numbers);
    }
    
    abstract void rollOnce(RoulettePlayer p);
    
    int win(RoulettePlayer p, int option) {
        if (option < 0 || option > bet_options) {
            throw new IllegalArgumentException();
        }
        
        return (int) Math.floor(p.bet(option) * (double) p.getMoney());
    }
    
    void update(RoulettePlayer p, int num, int money) {
        p.update(num, money);
    }
}
