package resources;

import javafx.scene.control.TextField;

public class Resources {
    public TextField columnField;
    public boolean onLoad;

    public void clickedColumnLabel()
    {
        if(columnField.getText().equals("Write name of column"))
            columnField.setText("");

        if(onLoad)
        {
            columnField.focusedProperty().addListener((obs, oldV, newV)->{
                if(columnField.getText().isEmpty())
                    columnField.setText("Write name of column");

            });
            onLoad = false;
        }

    }

    public Resources()
    {
        onLoad = true;
    }

}
