package userInterface;

import core.Constants;
import core.Disc;
import core.Game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
public class BoardUi extends JPanel {
    
    JButton[][] board;
    BoardListener listener;
    Game game;
    GameUi gameUi;
    
    public BoardUi(Game game, GameUi gameUi){
        this.game = game;
        this.gameUi = gameUi;
        initComponents();
        listener.updateUi();
    }
    
    private void initComponents(){
        
        //создает размер по умолчанию BoardUi
        this.setPreferredSize(new Dimension(800, 300));
        this.setMinimumSize(new Dimension(800, 300));
        this.setLayout(new GridLayout(8, 8));
       
        //создает массив JButton
        board = new JButton[8][8];
        
        //создает новый объект BoardListener
        listener = new BoardListener();
        
        int i, j;
        
        //петли через каждое пятно на доске и придает каждой коробке разные свойства
        for(i = 0; i<8; i++){
            for(j=0; j<8; j++){
                
                //Выдает каждый из ящиков на доске
                board[i][j] = new JButton();
                
                //добавляет свойство клиента к каждой отдельной кнопке массива кнопок
                board[i][j].putClientProperty("row", i);
                board[i][j].putClientProperty("col", j);
                board[i][j].putClientProperty("color", Constants.EMPTY);
                board[i][j].setBackground(Color.ORANGE);
                board[i][j].addActionListener(listener);
                
                //добавляет каждую кнопку в BoardUi
                this.add(board[i][j]);
            }
        }
        
        
    }
    
    private class BoardListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            
            //если нажата кнопка, нажмите кнопку, затем выполните этот код
          
            if(ae.getSource() instanceof JButton){
                //захватывает местоположение выбранной кнопки
                JButton button = (JButton) ae.getSource();
                int row = (int)button.getClientProperty("row");
                int col = (int)button.getClientProperty("col");
                
                //если цвет не был заполнен на этой кнопке, разместите изображение на черном диске
                if(isValidMove(row, col, game.getCurrentPlayer().getDiscColor())){
                    updateUi();
                    changePlayer();
                }
                else{
                    JOptionPane.showMessageDialog(button, "Move is not valid, select another.");
                }
                
            }
        }
        
        public void updateUi(){
            
            //получает массив дисков
            Disc [][] discs = game.getBoard().getBoard();
            ImageIcon disc =  null;
            
            int j, k;
            
            for(j=0; j<Constants.ROWS; j++){
                for(k=0; k<Constants.COLUMNS; k++){
                    //проверяет цвет кнопки
                    if(discs[j][k].getDiscolor() == Constants.DARK){
                        disc = new ImageIcon(getClass().getResource("/images/blackDisc.png"));
                        disc = imageResize(disc);
                        board[j][k].setIcon(disc);
                    }
                    else if(discs[j][k].getDiscolor() == Constants.LIGHT){
                        disc = new ImageIcon(getClass().getResource("/images/whiteDisc.png"));
                        disc = imageResize(disc);
                        board[j][k].setIcon(disc);
                    }
                }
            }
            //обновляет счет обоих игроков
            gameUi.getScore1().setText(String.valueOf(game.getPlayers().get(Constants.PLAYER_1).getScore()));
            gameUi.getScore2().setText(String.valueOf(game.getPlayers().get(Constants.PLAYER_2).getScore()));
        }
        
        public boolean isValidMove(int row, int col, Color color){
            
            boolean isValid = false;
            //если в месте нет цвета, это недействительный ход
            if(color == Constants.EMPTY){
                isValid = false;
            }
            //calls the isValidMove
            if(game.getBoard().isValidMove(row, col, color, Constants.CHECK_NO)){
                isValid = true;
            }
            return isValid;
        }
        
        public void changePlayer(){
            //изменяет игрока после окончания хода
            if(game.getCurrentPlayer() == game.getPlayers().get(Constants.PLAYER_1)){
                game.setCurrentPlayer(game.getPlayers().get(Constants.PLAYER_2));
            }else{
                game.setCurrentPlayer(game.getPlayers().get(Constants.PLAYER_1));
            }
        }
        
        private ImageIcon imageResize(ImageIcon icon){
            
            Image image = icon.getImage();
            Image newImage = image.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(newImage);
            return icon;
        }
        
    }
    
}
