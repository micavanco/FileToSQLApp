package resources;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Resources {
    public TextField columnField;
    public TextField query;
    private boolean onLoad;
    private boolean onLoad2;
    public Label queryLabel;
    private String tabel;
    private List<String> columns;
    private String fileName;
    public Label fileLabel;
    public Label fileOutputLabel;
    public TextField staticField;
    public Label checkLanguageLabel;

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

    public void clickedStatic()
    {
        if(staticField.getText().equals("Write language"))
            staticField.setText("");
        if(onLoad2)
        {
            staticField.focusedProperty().addListener((obs, oldV, newV)->{
                if(staticField.getText().isEmpty())
                    staticField.setText("Write language");
            });
            onLoad2=false;
        }

    }

    public void onStaticCheck()
    {
        if(staticField.isVisible())
            staticField.setVisible(false);
        else
            staticField.setVisible(true);
    }

    public Resources()
    {
        onLoad = true;
        onLoad2 = true;
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

    private void setQuery()
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

    public void onLoadFile() throws FileNotFoundException
    {
        if(!staticField.getText().equals("Write language"))
        {
            checkLanguageLabel.setVisible(false);
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Files with values", "*.csv","*.txt"));
            fileChooser.setTitle("Open file with values");
            File file = fileChooser.showOpenDialog(new Stage());
            if(file != null)
            {
                fileName = file.getPath();
                fileLabel.setText(fileLabel.getText()+fileName.substring(fileName.lastIndexOf("\\")+1)+" ("+staticField.getText()+")  ");
                Scanner sc = new Scanner(file, "UTF-8");
                while (sc.hasNext())
                {
                    System.out.println(sc.nextLine());
                }

                try {
                    sc.close();
                }catch (Exception e)
                {
                }

            }
        }else
            checkLanguageLabel.setVisible(true);

    }

    public void onGenerateFile() throws FileNotFoundException {
        fileOutputLabel.setText("Generated as: "+fileName.substring(fileName.lastIndexOf("\\")+1, fileName.length()-3)+".sql");


    }

}
