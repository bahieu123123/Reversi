package reversiGameplay;

import main.Game;
import userInterface.ReversiUi;

public class Reversi {

    /**
     * @param args
     */
    public static void main(String[] args) {
        //создает новый игровой объект
        Game game = new Game();
        ReversiUi Reversi = new ReversiUi(game);
    }


}
