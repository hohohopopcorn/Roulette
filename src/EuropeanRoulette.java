public class EuropeanRoulette extends RouletteGame{
    EuropeanRoulette() {
        numbers = 37;
        bet_options = 37 //singles
                + 11 * 3 //vertical splits
                + 12 * 2 //horizontal splits
                + 12     //streets
                + 11 * 2 //corners
                + 11     //six lines
                + 2      //colors
                + 3      //dozens
                + 2      //highs / lows
                + 2      //odds / evens
                + 3      //columns
                + 1;     //holding money
        winnings = new int[bet_options];
        for (int i = 0; i < 37; i++) {
            winnings[i] = 36; //singles
        }
        for (int i = 37; i < 94; i++) {
            winnings[i] = 18; //splits
        }
        for (int i = 94; i < 106; i++) {
            winnings[i] = 12; //streets
        }
        for (int i = 106; i < 128; i++) {
            winnings[i] = 9;  //corners
        }
        for (int i = 128; i < 139; i++) {
            winnings[i] = 6;  //six lines
        }
        for (int i = 139; i < 141; i++) {
            winnings[i] = 2;  //colors
        }
        for (int i = 141; i < 144; i++) {
            winnings[i] = 3;  //dozens
        }
        for (int i = 144; i < 146; i++) {
            winnings[i] = 2;  //highs / lows
        }
        for (int i = 146; i < 148; i++) {
            winnings[i] = 2;  //odds / evens
        }
        for (int i = 148; i < 151; i++) {
            winnings[i] = 3;  //columns
        }
        winnings[bet_options - 1] = 1;  //holding money
    }
    
    void roll_once(RoulettePlayer p) {
        int num = roll();
        int newmoney = win(p, bet_options - 1);
        
        newmoney += win(p, num);
        newmoney += win(p, split(num));
        newmoney += win(p, street(num));
        newmoney += win(p, corner(num));
        newmoney += win(p, sixLine(num));
        newmoney += win(p, color(num));
        newmoney += win(p, dozen(num));
        newmoney += win(p, highLow(num));
        newmoney += win(p, oddEven(num));
        newmoney += win(p, column(num));
        
        update(p, newmoney, num);
    }
    
    private int split(int num) {
    
    }
}
