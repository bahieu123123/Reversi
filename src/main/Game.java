package main;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Game {
    
    private ArrayList<Player> players;
    private Board board;
    private Player currentPlayer;
    public Game(){
        initObjects();
    }

    
    public void calculateScore(){
        //обновляет текущие оценки игроков
        board.calculateScore();
        players.get(Constants.PLAYER_1).setScore(board.getBlackCount());
        players.get(Constants.PLAYER_2).setScore(board.getWhiteCount());
    }
    /**
     * @return the players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * @param players игрокам установить
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @param board
     */
    public void setBoard(Board board) {
        this.board = board;
    }
    
    public void initObjects(){
        //создает экземпляр переменной
        board = new Board(this);
        
        //называет методы createrplayers и printplayers
        createPlayers();
        
        //пропускают игроков на доску
        board.setPlayers(players);
        //установить текущий проигрыватель
        currentPlayer = players.get(Constants.PLAYER_1);
    }
    
    public void createPlayers(){
        //создает новый объект arraylist
        players = new ArrayList();
        
        int i;
        //перебирает количество игроков и генерирует информацию для каждого из них
        for(i=0; i<Constants.MAX_PLAYERS; i++){
            //инструктирует программу запрашивать у игрока диалоговое окно
            String name = JOptionPane.showInputDialog(null, "Enter player's name");
            Player player = new Player();
            player.setName(name);

            //если игрок является первым, назначьте им темный цвет
            if(i == Constants.PLAYER_1){
                player.setDiscColor(Constants.DARK);
            }
            //в противном случае присвойте им белый цвет
            else{
                player.setDiscColor(Constants.LIGHT);
            }
            //добавляет игрока к текущему arraylist
            players.add(player);
        }
    }
    /**
     * @return currentPlayer
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * @param currentPlayer
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
