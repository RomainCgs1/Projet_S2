package fr.insa.empire;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class MyB extends Button {

    public MyB(String name)
    {
        this.setText(name);
        this.setFont(Font.font("Helvetica Neue"));
    }
}
