import edu.princeton.cs.introcs.StdRandom;

public abstract class RouletteGame {
    int numbers;
    int bet_options;
    int[] winnings;
    
    int roll() {
        return StdRandom.uniform(numbers);
    }
    
    abstract void roll_once(RoulettePlayer p);
    
    int win(RoulettePlayer p, int option) {
        return (int)Math.floor(p.bet(option) * (double) p.getMoney());
    }
    
    void update(RoulettePlayer p, int money, int num) {
        p.update(money, num);
    }
}
