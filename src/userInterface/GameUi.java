package userInterface;

import main.Constants;
import main.Game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class GameUi extends JPanel{
    
    //создает переменные JLabel
    private JLabel name1;
    private JLabel name2;
    private JLabel score1;
    private JLabel score2;
    private Game game;
    
    //конструктор, используемый для автоматического создания JPanel при создании игрового объекта
    public GameUi(Game game){
        this.game = game;
        initComponents();
    }
    
    private void initComponents(){
                
        //устанавливает размер и цвет по умолчанию JPanel
        this.setPreferredSize(new Dimension(600, 50));
        this.setMinimumSize(new Dimension(600, 50));
        this.setBackground(Color.LIGHT_GRAY);
        
        //создает первый диск
        ImageIcon discOne = new ImageIcon(getClass().getResource("/images/blackDisc.png"));
        discOne = imageResize(discOne);
        
        //устанавливает метку для первого игрока
        name1 = new JLabel();
        name1.setIcon(discOne);
        name1.setText(game.getPlayers().get(Constants.PLAYER_1).getName());
        name1.setMinimumSize(new Dimension(100, 50));
        name1.setPreferredSize(new Dimension(100, 50));
        name1.setFont(new Font("Seri", Font.ROMAN_BASELINE, 12));
        
        //устанавливает метку оценки для первого игрока
        setScore1(new JLabel());
        getScore1().setText(game.getPlayers().get(Constants.PLAYER_1).getName());
        getScore1().setMinimumSize(new Dimension(100, 50));
        getScore1().setPreferredSize(new Dimension(100, 50));
        
        //создает второй диск
        ImageIcon discTwo = new ImageIcon(getClass().getResource("/images/whiteDisc.png"));
        discTwo = imageResize(discTwo);
        
        //устанавливает метку имени для второго игрока
        name2 = new JLabel();
        name2.setIcon(discTwo);
        name2.setText(game.getPlayers().get(Constants.PLAYER_2).getName());
        name2.setFont(new Font("Serif", Font.BOLD, 12));
        name2.setMinimumSize(new Dimension(150, 50));
        name2.setPreferredSize(new Dimension(150, 50));
              
        //устанавливает метку оценки для второго игрока
        setScore2(new JLabel());
        getScore2().setText(game.getPlayers().get(Constants.PLAYER_2).getName());
        getScore2().setMinimumSize(new Dimension(100, 50));
        getScore2().setPreferredSize(new Dimension(100, 50));
        
        //добавляет все ярлыки в JPanel
        this.add(name1);
        this.add(getScore1());
        this.add(name2);
        this.add(getScore2());
        
        
    }

    /**
     * @return the game
     */
    public Game getGame() {
        return game;
    }

    /**
     * @param game
     */
    public void setGame(Game game) {
        this.game = game;
    }
    
    private ImageIcon imageResize(ImageIcon icon){
            
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImage);
        return icon;
    }

    /**
     * @return the score1
     */
    public JLabel getScore1() {
        return score1;
    }

    /**
     * @param score1
     */
    public void setScore1(JLabel score1) {
        this.score1 = score1;
    }

    /**
     * @return the score2
     */
    public JLabel getScore2() {
        return score2;
    }

    /**
     * @param score2
     */
    public void setScore2(JLabel score2) {
        this.score2 = score2;
    }
    
}
