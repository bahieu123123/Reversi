package reversi;

import core.Game;
import userInterface.ReversiUi;

public class Reversi {

    /**
     * @param args
     */
    public static void main(String[] args) {
        //создает новый игровой объект
        Game game = new Game();
        ReversiUi othello = new ReversiUi(game);
    }

}
