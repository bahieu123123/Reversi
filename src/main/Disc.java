package main;

import java.awt.Color;

public class Disc {
    //создает переменную типа Color
    private Color disColoration;

    /**
     * @return обесцвечивание
     */
    public Color getDisColoration() {
        return disColoration;
    }

    /**
     * @param disColoration обесцвечивание
     */
    public void setDisColoration(Color disColoration) {
        this.disColoration = disColoration;
    }
}
