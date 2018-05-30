package main;

import userInterface.ReversiUi;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;
public class Board {
    
    private Disc[][] board;
    private int blackCount;
    private int whiteCount;
    private ArrayList<Player> players;
    private final Game game;
    
    public Board(Game game){
        this.game = game;
        //конструктор для классной доски
        unitObjects();
    }

    /**
     * @return the board
     */
    public Disc[][] getBoard() {
        return board;
    }

    /**Cоздать экземпляр переменной платы и петли через доску и создает объект диска для каждого пятна на доске.
     * Yстанавливает начальное состояние платы.
     * @param board
     */
    public void setBoard(Disc[][] board) {
        this.board = board;
    }
    
    private void unitObjects(){
        
        //создать экземпляр переменной платы
        board = new Disc[Constants.ROWS][Constants.COLUMNS];
        
        int i, j;
        
        //петли через доску и создает объект диска для каждого пятна на доске
        for(i=0; i<Constants.ROWS; i++){
            for(j=0; j<Constants.COLUMNS; j++){
                board[i][j] = new Disc();
            }
        }
        
        //устанавливает начальное состояние платы
        board[3][3].setDisColoration(Constants.LIGHT);
        board[3][4].setDisColoration(Constants.DARK);
        board[4][3].setDisColoration(Constants.DARK);
        board[4][4].setDisColoration(Constants.LIGHT);
        
    }

    public void calculateScore(){
        
        int k, l;
        
        for(k=0; k<Constants.ROWS; k++){
            for(l=0; l<Constants.COLUMNS; l++){
                //проверяет цвет текущей плитки и соответственно увеличивает счетную переменную
                if(board[k][l].getDisColoration() == Constants.DARK){
                    blackCount++;
                }
                //если текущая плитка не темная, приращение whiteCount
                else if(board[k][l].getDisColoration() == Constants.LIGHT){
                    whiteCount++;
                }
            }
        }
        //код для обновления десятков игроков
        players.get(Constants.PLAYER_1).setScore(blackCount);
        players.get(Constants.PLAYER_2).setScore(whiteCount);
        blackCount = 0;
        whiteCount = 0;
    }
    
    public boolean isValidMove(int row, int col, Color color, int check){
        
        //boolean переменная для проверки правильности перемещения
        boolean result = false;
        
       //если в этом месте уже есть цвет, вы не можете играть здесь.
        if(board[row][col].getDisColoration() != Constants.EMPTY && check == Constants.CHECK_NO){
            gameOver();
            return result;
        }
        
        //Проверяет все возможные позиции для перемещения
        if(checkUp(row, col, color, check)){
            result = true;
        }
        if(checkUpLeft(row, col, color, check)){
            result = true;
        }
        if(checkLeft(row, col, color, check)){
            result = true;
        }
        if(checkDownLeft(row, col, color, check)){
            result = true;
        }
        if(checkDown(row, col, color, check)){
            result = true;
        }
        if(checkDownRight(row, col, color, check)){
            result = true;
        }
        if(checkRight(row, col, color, check)){
            result = true;
        }
        if(checkUpRight(row, col, color, check)){
            result = true;
        }
        
        
        //если ход правильный, обновите пространство в настоящее время, чтобы иметь цвет игрока
        if(result == true && check == Constants.CHECK_NO)
            board[row][col].setDisColoration(color);
        
        //обновляет текущий счет
        calculateScore();
        
        //проверяет, закончилась ли игра, но только если мы не проверяем действительные действия
        if(check == Constants.CHECK_NO && gameOver() == true){
            //если игрок набрал больше очков, показать, что они выиграли
            if(players.get(Constants.PLAYER_1).getScore() > players.get(Constants.PLAYER_2).getScore()){
                JOptionPane.showMessageDialog(null, players.get(Constants.PLAYER_1).getName() + " wins!");
                Game game = new Game();
                ReversiUi Reversi = new ReversiUi(game);
            }
            //если игрок два набрал выше, показать, что они вместо этого выиграли
            else if(players.get(Constants.PLAYER_2).getScore() > players.get(Constants.PLAYER_1).getScore()){
                JOptionPane.showMessageDialog(null, players.get(Constants.PLAYER_2).getName() + " wins!");
                Game game = new Game();
                ReversiUi Reversi = new ReversiUi(game);
            }
            //если игрок равен точке
            else{
                JOptionPane.showMessageDialog(null, "Both players tied with the same number of disks!");
                Game game = new Game();
                ReversiUi Reversi = new ReversiUi(game);
            }
        }
        return result;
    }
    
    public boolean checkUp(int row, int col, Color color, int check){
        
        int squaresFlipped = 0;
        int checkRow = row-1;
        boolean sameColor = false;
        boolean isValid = false;
        boolean emptySpace = false;
        
        while(checkRow >= 0 && sameColor == false){
            //проверяет, равен ли текущий цвет квадрата
            if(board[checkRow][col].getDisColoration() == Constants.EMPTY){
                isValid = false;
                emptySpace = true;
            }
            //если текущий цвет плитки не совпадает с пройденным цветом, увеличьте переменную squaresFlipped
            else if(board[checkRow][col].getDisColoration() != color){
                squaresFlipped++;
            }
            else{
                sameColor = true;
            }
            checkRow--;
        }
        
        //если мы просто проверяем правильность движения и это правда, верните true без обновления дисков
        if(check == Constants.CHECK_YES && sameColor == true && squaresFlipped > 0 && emptySpace == false){
            return true;
        }
        
        if(sameColor == true && squaresFlipped > 0 && emptySpace == false){
            do{
                row--;
                squaresFlipped--;
                board[row][col].setDisColoration(color);
            }while(squaresFlipped > 0);
            isValid = true;
        }
        else{
            isValid = false;
        }
        return isValid;
    }
    
    public boolean checkUpLeft(int row, int col, Color color, int check){
        
        int squaresFlipped = 0;
        int checkRow = row-1;
        int checkCol = col-1;
        boolean sameColor = false;
        boolean isValid = false;
        boolean emptySpace = false;
     
        while(checkRow >= 0 && checkCol >= 0 && sameColor == false){
            //проверяет, равен ли текущий цвет квадрата
            if(board[checkRow][checkCol].getDisColoration() == Constants.EMPTY){
                isValid = false;
                emptySpace = true;
            }
            //если текущий цвет плитки не совпадает с пройденным цветом, увеличьте переменную squaresFlipped
            else if(board[checkRow][checkCol].getDisColoration() != color){
                squaresFlipped++;
            }
            else{
                sameColor = true;
            }
            checkCol--;
            checkRow--;
        }
        
        //если мы просто проверяем правильность движения и это правда, верните true без обновления дисков
        if(check == Constants.CHECK_YES && sameColor == true && squaresFlipped > 0 && emptySpace == false){
            return true;
        }
        
        if(sameColor == true && squaresFlipped > 0 && emptySpace == false){
            do{
                row--;
                col--;
                squaresFlipped--;
                board[row][col].setDisColoration(color);
            }while(squaresFlipped > 0);
            isValid = true;
        }
        else{
            isValid = false;
        }
        return isValid;
    }
    
    public boolean checkLeft(int row, int col, Color color, int check){
        
        int squaresFlipped = 0;
        int checkCol = col-1;
        boolean sameColor = false;
        boolean isValid;
        boolean emptySpace = false;
        
        while(checkCol >= 0 && sameColor == false){
            //проверяет, равен ли текущий цвет квадрата
            if(board[row][checkCol].getDisColoration() == Constants.EMPTY){
                isValid = false;
                emptySpace = true;
            }
            //если текущий цвет плитки не совпадает с пройденным цветом, увеличьте переменную squaresFlipped
            else if(board[row][checkCol].getDisColoration() != color){
                squaresFlipped++;
            }
            else{
                sameColor = true;
            }
            checkCol--;
        }
        
        //если мы просто проверяем правильность движения и это правда, верните true без обновления дисков
        if(check == Constants.CHECK_YES && sameColor == true && squaresFlipped > 0 && emptySpace == false){
            return true;
        }
        
        if(sameColor == true && squaresFlipped > 0 && emptySpace == false){
            do{
                col--;
                squaresFlipped--;
                board[row][col].setDisColoration(color);
                
            }while(squaresFlipped > 0);
            isValid = true;
        }
        else{
            isValid = false;
        }
        return isValid;
    }
    
    public boolean checkDownLeft(int row, int col, Color color, int check){
        
        int squaresFlipped = 0;
        int checkRow = row+1;
        int checkCol = col-1;
        boolean sameColor = false;
        boolean isValid;
        boolean emptySpace = false;
        
        while(checkRow <= 7 && checkCol >=0 && sameColor == false){
            //проверяет, равен ли текущий цвет квадрата
            if(board[checkRow][checkCol].getDisColoration() == Constants.EMPTY){
                isValid = false;
                emptySpace = true;
            }
            //если текущий цвет плитки не совпадает с пройденным цветом, увеличьте переменную squaresFlipped
            else if(board[checkRow][checkCol].getDisColoration() != color){
                squaresFlipped++;
            }
            else{
                sameColor = true;
            }
            checkRow++;
            checkCol--;
        }
        if(check == Constants.CHECK_YES && sameColor == true && squaresFlipped > 0 && emptySpace == false){
            return true;
        }
        
        if(sameColor == true && squaresFlipped > 0 && emptySpace == false){
            do{
                row++;
                col--;
                squaresFlipped--;
                board[row][col].setDisColoration(color);
            }while(squaresFlipped > 0);
            isValid = true;
        }
        else{
            isValid = false;
        }
        return isValid;
    }
    
    public boolean checkDown(int row, int col, Color color, int check){
        
        int squaresFlipped = 0;
        int checkRow = row+1;
        boolean sameColor = false;
        boolean isValid;
        boolean emptySpace = false;
        
        while(checkRow <= 7 && sameColor == false){
            //проверяет, равен ли текущий цвет квадрата
            if(board[checkRow][col].getDisColoration() == Constants.EMPTY){
                isValid = false;
                emptySpace = true;
            }
            //если текущий цвет плитки не совпадает с пройденным цветом, увеличьте переменную squaresFlipped
            else if(board[checkRow][col].getDisColoration() != color){
                squaresFlipped++;
            }
            else{
                sameColor = true;
            }
            checkRow++;
        }
        if(check == Constants.CHECK_YES && sameColor == true && squaresFlipped > 0 && emptySpace == false){
            return true;
        }
        
        if(sameColor == true && squaresFlipped > 0 && emptySpace == false){
            do{
                row++;
                squaresFlipped--;
                board[row][col].setDisColoration(color);
            }while(squaresFlipped > 0);
            isValid = true;
        }
        else{
            isValid = false;
        }
        return isValid;
    }
    
    public boolean checkDownRight(int row, int col, Color color, int check){
        
        int squaresFlipped = 0;
        int checkRow = row+1;
        int checkCol = col+1;
        boolean sameColor = false;
        boolean isValid;
        boolean emptySpace = false;
        
        while(checkRow <= 7 && checkCol <= 7 && sameColor == false){
            //проверяет, равен ли текущий цвет квадрата
            if(board[checkRow][checkCol].getDisColoration() == Constants.EMPTY){
                isValid = false;
                emptySpace = true;
            }
            //если текущий цвет плитки не совпадает с пройденным цветом, увеличьте переменную squaresFlipped
            else if(board[checkRow][checkCol].getDisColoration() != color){
                squaresFlipped++;
            }
            else{
                sameColor = true;
            }
            checkCol++;
            checkRow++;
        }
        if(check == Constants.CHECK_YES && sameColor == true && squaresFlipped > 0 && emptySpace == false){
            return true;
        }
        
        if(sameColor == true && squaresFlipped > 0 && emptySpace == false){
            do{
                row++;
                col++;
                squaresFlipped--;
                board[row][col].setDisColoration(color);
            }while(squaresFlipped > 0);
            isValid = true;
        }
        else{
            isValid = false;
        }
        return isValid;
    }
    
    public boolean checkRight(int row, int col, Color color, int check){
        
        int squaresFlipped = 0;
        int checkCol = col+1;
        boolean sameColor = false;
        boolean isValid;
        boolean emptySpace = false;
        
        while(checkCol <= 7 && sameColor == false){
            //проверяет, равен ли текущий цвет квадрата
            if(board[row][checkCol].getDisColoration() == Constants.EMPTY){
                isValid = false;
                emptySpace = true;
            }
            //если текущий цвет плитки не совпадает с пройденным цветом, увеличьте переменную squaresFlipped
            else if(board[row][checkCol].getDisColoration() != color){
                squaresFlipped++;
            }
            else{
                sameColor = true;
            }
            checkCol++;
        }
        if(check == Constants.CHECK_YES && sameColor == true && squaresFlipped > 0 && emptySpace == false){
            return true;
        }
        
        if(sameColor == true && squaresFlipped > 0 && emptySpace == false){
            do{
                col++;
                squaresFlipped--;
                board[row][col].setDisColoration(color);
            }while(squaresFlipped > 0);
            isValid = true;
        }
        else{
            isValid = false;
        }
        return isValid;
    }
    
    public boolean checkUpRight(int row, int col, Color color, int check){
        
        int squaresFlipped = 0;
        int checkCol = col+1;
        int checkRow = row-1;
        boolean sameColor = false;
        boolean isValid;
        boolean emptySpace = false;
        
        while(checkRow >= 0 && checkCol <= 7 && sameColor == false){
            //проверяет, равен ли текущий цвет квадрата
            if(board[checkRow][checkCol].getDisColoration() == Constants.EMPTY){
                isValid = false;
                emptySpace = true;
            }
            //если текущий цвет плитки не совпадает с пройденным цветом, увеличьте переменную squaresFlipped
            else if(board[checkRow][checkCol].getDisColoration() != color){
                squaresFlipped++;
            }
            else{
                sameColor = true;
            }
            checkCol++;
            checkRow--;
        }
        if(check == Constants.CHECK_YES && sameColor == true && squaresFlipped > 0 && emptySpace == false){
            return true;
        }
        
        if(sameColor == true && squaresFlipped > 0 && emptySpace == false){
            do{
                row--;
                col++;
                squaresFlipped--;
                board[row][col].setDisColoration(color);
            }while(squaresFlipped > 0);
            isValid = true;
        }
        else{
            isValid = false;
        }
        return isValid;
    }
    
    public boolean gameOver(){
        
        int a, b;
        boolean gameOver= true;
        Color currentColor = game.getCurrentPlayer().getDiscColor();
      
        //проверяет, заполнены ли все плитки
        if((players.get(Constants.PLAYER_1).getScore()+(players.get(Constants.PLAYER_2).getScore())<=64)
        &&(players.get(Constants.PLAYER_1)).getScore()>0&&(players.get(Constants.PLAYER_2)).getScore()>0){
            return false;
        }

        for(a=0; a<Constants.ROWS; a++){
            for(b=0; b<Constants.COLUMNS; b++){
                //если есть еще доступные места для воспроизведения диска, игра будет продолжена
                if(board[a][b].getDisColoration() == Constants.EMPTY){
                    if(isValidMove(a, b, currentColor, Constants.CHECK_YES)){
                        return true;
                    }
                }
            }
        }
        changePlayer();
        if((players.get(Constants.PLAYER_1).getScore()+(players.get(Constants.PLAYER_2).getScore())==64)
                &&(players.get(Constants.PLAYER_1)).getScore()>0&&(players.get(Constants.PLAYER_2)).getScore()>0){
            return false;
        }

        for(a=0; a<Constants.ROWS; a++){
            for(b=0; b<Constants.COLUMNS; b++){
                //если есть еще доступные места для воспроизведения диска, игра будет продолжена
                if(board[a][b].getDisColoration() == Constants.EMPTY){
                    if(isValidMove(a, b, currentColor, Constants.CHECK_YES)){
                        return true;
                    }
                }
            }
        }
        JOptionPane.showMessageDialog(null, "No more possible moves for either player. Game over.");
        return gameOver;
    }
    
    
    public void changePlayer(){
          //изменяет игрока после окончания хода
            if(game.getCurrentPlayer() == game.getPlayers().get(Constants.PLAYER_1)){
                game.setCurrentPlayer(game.getPlayers().get(Constants.PLAYER_2));
            }else{
                game.setCurrentPlayer(game.getPlayers().get(Constants.PLAYER_1));
            }
        }
    
    /**
     * @return the blackCount
     */
    public int getBlackCount() {
        return blackCount;
    }
    /**
     * @return the whiteCount
     */
    public int getWhiteCount() {
        return whiteCount;
    }

    /**
     * @param players
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
}
