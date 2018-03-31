public abstract class RouletteGame {
    int bet_options;
    int[] winnings;
    
    void win(RoulettePlayer p, int option, int money) {
        p.win(p.bet(option) * winnings[option] * money);
    }
    void lose(RoulettePlayer p, int money) {
        p.lose((1 - p.bet(bet_options - 1)) * money);
    }
}
