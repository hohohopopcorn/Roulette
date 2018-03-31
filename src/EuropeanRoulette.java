public class EuropeanRoulette extends RouletteGame {
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
    
    void rollOnce(RoulettePlayer p) {
        int num = roll();
        int newmoney = win(p, bet_options - 1);
        
        newmoney += single(p, num);
        newmoney += split(p, num);
        newmoney += street(p, num);
        newmoney += corner(p, num);
        newmoney += sixLine(p, num);
        newmoney += color(p, num);
        newmoney += dozen(p, num);
        newmoney += highLow(p, num);
        newmoney += oddEven(p, num);
        newmoney += column(p, num);
        
        update(p, num, newmoney);
    }
    
    private int single(RoulettePlayer p, int num) {
        return win(p, num);
    }
    
    private int split(RoulettePlayer p, int num) {
        if (num == 0) {
            return 0;
        }
        int newmoney = 0;
        int col = col(num);
        int row = row(num);
        //vertical splits
        if (row != 11) {
            newmoney += win(p, 37 + num - 1); //bottom split
        }
        if (row != 0) {
            newmoney += win(p, 37 + (num - 3) - 1);  //top split
        }
        //horizontal splits
        if (col != 0) {
            newmoney += win(p, 70 + (num - 1) - row - 1);  //left split
        }
        if (col != 2) {
            newmoney += win(p, 70 + num - row - 1); //right split
        }
        
        return newmoney;
    }
    
    private int street(RoulettePlayer p, int num) {
        if (num == 0) {
            return 0;
        }
        return win(p, 94 + row(num));
    }
    
    private int corner(RoulettePlayer p, int num) {
        if (num == 0) {
            return 0;
        }
        int newmoney = 0;
        int col = col(num);
        int row = row(num);
        //top left
        if(col != 2 && row != 11) {
            newmoney += win(p, 106 + num - row - 1);
        }
        //top right
        if(col != 0 && row != 11) {
            newmoney += win(p, 106 + (num - 1) - row - 1);
        }
        //bottom left
        if(col != 2 && row != 0) {
            newmoney += win(p, 106 + (num - 3) - row - 1);
        }
        //bottom right
        if(col != 0 && row != 0) {
            newmoney += win(p, 106 + (num - 4) - row - 1);
        }
        
        return newmoney;
    }
    
    private int sixLine(RoulettePlayer p, int num) {
        if (num == 0) {
            return 0;
        }
        int newmoney = 0;
        int row = row(num);
        //top row
        if (row != 11) {
            newmoney += win(p, 128 + row);
        }
        //bottom row
        if (row != 0) {
            newmoney += win(p, 128 + row - 1);
        }
        return newmoney;
    }
    
    private int color(RoulettePlayer p, int num) {
        if (num == 0) {
            return 0;
        }
        //0 is black, 1 is red
        int[] coloring = {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1,
                          0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0,
                          1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1};
        return win(p, 139 + coloring[num - 1]);
    }
    
    private int dozen(RoulettePlayer p, int num) {
        if (num == 0) {
            return 0;
        }
        return win(p, 141 + (num - 1) / 12);
    }
    
    private int highLow(RoulettePlayer p, int num) {
        if (num == 0) {
            return 0;
        }
        return win(p, 144 + (num - 1) / 18);
    }
    
    private int oddEven(RoulettePlayer p, int num) {
        if (num == 0) {
            return 0;
        }
        // 0 is even, 1 is odd
        return win(p, 146 + num % 2);
    }
    
    private int column(RoulettePlayer p, int num) {
        if (num == 0) {
            return 0;
        }
        return win(p, 148 + col(num));
    }
    
    private int row(int num) {
        return (num - 1) / 3;
    }
    private int col(int num) {
        return (num - 1) % 3;
    }
}
