package core;

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
        initObjects();
    }

    /**
     * @return the board
     */
    public Disc[][] getBoard() {
        return board;
    }

    /**Cоздать экземпляр переменной платы и петли через доску и создает объект диска для каждого пятна на доске.
     * Yстанавливает начальное состояние платы.
     * @param board the board to set
     */
    public void setBoard(Disc[][] board) {
        this.board = board;
    }
    
    private void initObjects(){
        
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
        board[3][3].setDiscolor(Constants.LIGHT);
        board[3][4].setDiscolor(Constants.DARK);
        board[4][3].setDiscolor(Constants.DARK);
        board[4][4].setDiscolor(Constants.LIGHT);
        
    }

    public void calculateScore(){
        
        int k, l;
        
        for(k=0; k<Constants.ROWS; k++){
            for(l=0; l<Constants.COLUMNS; l++){
                //проверяет цвет текущей плитки и соответственно увеличивает счетную переменную
                if(board[k][l].getDiscolor() == Constants.DARK){
                    blackCount++;
                }
                //если текущая плитка не темная, приращение whiteCount
                else if(board[k][l].getDiscolor() == Constants.LIGHT){
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
        if(board[row][col].getDiscolor() != Constants.EMPTY && check == Constants.CHECK_NO){
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
            board[row][col].setDiscolor(color);
        
        //обновляет текущий счет
        calculateScore();
        
        //проверяет, закончилась ли игра, но только если мы не проверяем действительные действия
        if(check == Constants.CHECK_NO && gameOver() == true){
            //если игрок набрал больше очков, показать, что они выиграли
            if(players.get(Constants.PLAYER_1).getScore() > players.get(Constants.PLAYER_2).getScore()){
                JOptionPane.showMessageDialog(null, players.get(Constants.PLAYER_1).getName() + " wins!");
                System.exit(0);
            }
            //если игрок два набрал выше, показать, что они вместо этого выиграли
            else if(players.get(Constants.PLAYER_2).getScore() > players.get(Constants.PLAYER_1).getScore()){
                JOptionPane.showMessageDialog(null, players.get(Constants.PLAYER_2).getName() + " wins!");
                System.exit(0);
            }
            //если игрок равен точке
            else{
                JOptionPane.showMessageDialog(null, "Both players tied with the same number of disks!");
                System.exit(0);
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
            if(board[checkRow][col].getDiscolor() == Constants.EMPTY){
                isValid = false;
                emptySpace = true;
            }
            //если текущий цвет плитки не совпадает с пройденным цветом, увеличьте переменную squaresFlipped
            else if(board[checkRow][col].getDiscolor() != color){
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
                board[row][col].setDiscolor(color);
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
            if(board[checkRow][checkCol].getDiscolor() == Constants.EMPTY){
                isValid = false;
                emptySpace = true;
            }
            //если текущий цвет плитки не совпадает с пройденным цветом, увеличьте переменную squaresFlipped
            else if(board[checkRow][checkCol].getDiscolor() != color){
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
                board[row][col].setDiscolor(color);
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
            if(board[row][checkCol].getDiscolor() == Constants.EMPTY){
                isValid = false;
                emptySpace = true;
            }
            //если текущий цвет плитки не совпадает с пройденным цветом, увеличьте переменную squaresFlipped
            else if(board[row][checkCol].getDiscolor() != color){
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
                board[row][col].setDiscolor(color);
                
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
            if(board[checkRow][checkCol].getDiscolor() == Constants.EMPTY){
                isValid = false;
                emptySpace = true;
            }
            //если текущий цвет плитки не совпадает с пройденным цветом, увеличьте переменную squaresFlipped
            else if(board[checkRow][checkCol].getDiscolor() != color){
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
                board[row][col].setDiscolor(color);
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
            if(board[checkRow][col].getDiscolor() == Constants.EMPTY){
                isValid = false;
                emptySpace = true;
            }
            //если текущий цвет плитки не совпадает с пройденным цветом, увеличьте переменную squaresFlipped
            else if(board[checkRow][col].getDiscolor() != color){
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
                board[row][col].setDiscolor(color);
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
            if(board[checkRow][checkCol].getDiscolor() == Constants.EMPTY){
                isValid = false;
                emptySpace = true;
            }
            //если текущий цвет плитки не совпадает с пройденным цветом, увеличьте переменную squaresFlipped
            else if(board[checkRow][checkCol].getDiscolor() != color){
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
                board[row][col].setDiscolor(color);
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
            if(board[row][checkCol].getDiscolor() == Constants.EMPTY){
                isValid = false;
                emptySpace = true;
            }
            //если текущий цвет плитки не совпадает с пройденным цветом, увеличьте переменную squaresFlipped
            else if(board[row][checkCol].getDiscolor() != color){
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
                board[row][col].setDiscolor(color);
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
            if(board[checkRow][checkCol].getDiscolor() == Constants.EMPTY){
                isValid = false;
                emptySpace = true;
            }
            //если текущий цвет плитки не совпадает с пройденным цветом, увеличьте переменную squaresFlipped
            else if(board[checkRow][checkCol].getDiscolor() != color){
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
                board[row][col].setDiscolor(color);
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
        for(a=0; a<Constants.ROWS; a++){
            for(b=0; b<Constants.COLUMNS; b++){
                //если есть еще доступные места для воспроизведения диска, игра будет продолжена
                if(board[a][b].getDiscolor() == Constants.EMPTY){
                    if(isValidMove(a, b, currentColor, Constants.CHECK_YES)){
                        return false;
                    }
                }
            }
        }
        
        
        //изменяет текущего игрока, а затем проверяет, может ли другой игрок совершать какие-либо действия
        changePlayer();
        
        //если он доберется до этого момента, переключите игроков и проверьте, могут ли они сделать ход, если они
        //не может, тогда игра окончена.
        // проверяет, заполнены ли все плитки
        for(a=0; a<Constants.ROWS; a++){
            for(b=0; b<Constants.COLUMNS; b++){
                //если есть еще доступные места для воспроизведения диска, игра будет продолжена
                if(board[a][b].getDiscolor() == Constants.EMPTY){
                    if(isValidMove(a, b, currentColor, Constants.CHECK_YES)){
                        //игра еще не закончена, поэтому теперь мы позволяем другому игроку двигаться
                        return false;
                    }
                }
            }
        }
        
        //если мы достигнем этого момента, игра закончится, и мы должны объявить об этом
        JOptionPane.showMessageDialog(null, "No more possible moves for either player. Game over.");
        
        //игра заканчивается, если метод достигает этого утверждения.
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
     * @param blackCount the darkCount to set
     */
    public void setBlackCount(int blackCount) {
        this.blackCount = blackCount;
    }

    /**
     * @return the whiteCount
     */
    public int getWhiteCount() {
        return whiteCount;
    }

    /**
     * @param whiteCount the lightCount to set
     */
    public void setWhiteCount(int whiteCount) {
        this.whiteCount = whiteCount;
    }

    /**
     * @return the players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * @param players the players to set
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
}