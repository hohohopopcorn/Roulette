public class EuropeanRoulette extends RouletteGame{
    EuropeanRoulette() {
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
                + 3;     //columns
        for (int i = 0; i < 37; i++) {
            winnings[i] = 37; //singles
        }
        for (int i = 37; i < 94; i++) {
            winnings[i] = 17; //splits
        }
        for (int i = 94; i < 106; i++) {
            winnings[i] = 11; //streets
        }
        for (int i = 106; i < 128; i++) {
            winnings[i] = 8;  //corners
        }
        for (int i = 128; i < 139; i++) {
            winnings[i] = 5;  //six lines
        }
        for (int i = 139; i < 141; i++) {
            winnings[i] = 1;  //colors
        }
        for (int i = 141; i < 144; i++) {
            winnings[i] = 2;  //dozens
        }
        for (int i = 144; i < 146; i++) {
            winnings[i] = 1;  //highs / lows
        }
        for (int i = 146; i < 148; i++) {
            winnings[i] = 1;  //odds / evens
        }
        for (int i = 148; i < 151; i++) {
            winnings[i] = 2;  //columns
        }
    }
}
