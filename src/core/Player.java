package core;

import java.awt.Color;

public class Player {
    //переменные, описывающие каждого игрока
    private String name;
    private Color discColor;
    private int score;
    
    /**
     * @return имя
     */
    public String getName() {
        return name;
    }

    /**
     * @param name имя для установки
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return discColor
     */
    public Color getDiscColor() {
        return discColor;
    }

    /**
     * @param discColor
     */
    public void setDiscColor(Color discColor) {
        this.discColor = discColor;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }
}
