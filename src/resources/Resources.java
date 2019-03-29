package resources;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class Resources {
    public TextField columnField;
    public TextField query;
    public boolean onLoad;
    public Label queryLabel;
    public String tabel;
    public List<String> columns;

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
        columns = new ArrayList<String>();
    }

    public void typingTableName()
    {
        tabel = query.getText();
        setQuery();
    }

    public void onAddColumn()
    {
        columns.add("'"+columnField.getText()+"'");
        setQuery();
        columnField.setText("");
    }

    public void onRemoveColumn()
    {
        String temp = "'"+columnField.getText()+"'";
        if(columns.contains(temp))
        {
            columns.remove(temp);
            setQuery();
            columnField.setText("");
        }
    }

    public void setQuery()
    {
        String columnsString = "(";
        if(!columns.isEmpty())
        {
            for(String s: columns)
                columnsString+=(s+", ");
            columnsString=columnsString.substring(0, columnsString.length()-2);
        }

        queryLabel.setText("insert from "+tabel+" "+columnsString+")");
    }

}
