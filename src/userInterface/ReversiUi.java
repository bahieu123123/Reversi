package userInterface;

import main.Game;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;

public class ReversiUi extends JFrame {
    
    Game game;
    GameUi gameUi;
    BoardUi boardUi;
    
    public ReversiUi(Game game){
        this.game = game;
        initComponents();
    }
    
    private void initComponents(){
        
        // создает кадр и устанавливает его размеры
        this.setPreferredSize(new Dimension(600, 600));
        this.setMinimumSize(new Dimension(600,600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //создает два экземпляра классов gameUi и BoardUi
        gameUi = new GameUi(game);
        boardUi = new BoardUi(game, gameUi);
        
        //добавляет объекты в фактический кадр
        this.add(boardUi, BorderLayout.CENTER);
        this.add(gameUi, BorderLayout.NORTH);
        
        //делает реальную рамку видимой
        this.setVisible(true);
        
    }
}
