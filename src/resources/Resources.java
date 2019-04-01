package resources;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    private int id;
    // Queries
    private String queryString;
    private List<String> output;

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
        output = new ArrayList<String>();
        id = 0;
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
        queryString = "(";
        if(!columns.isEmpty())
        {
            for(String s: columns)
                queryString+=(s+", ");
            queryString=queryString.substring(0, queryString.length()-2);
        }

        queryString = "insert into "+tabel+" "+queryString+")";
        queryLabel.setText(queryString);
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
                int counter = 1;
                fileName = file.getPath();
                fileLabel.setText(fileLabel.getText()+fileName.substring(fileName.lastIndexOf("\\")+1)+" ("+staticField.getText()+")  ");
                Scanner sc = new Scanner(file, "UTF-8");
                while (sc.hasNext())
                {
                    String[] temp = sc.nextLine().split(",");
                    output.add(queryString+" values ("+id+++", "+temp[0]+", "+temp[1]+", "+staticField.getText()+", "+counter+++");\n");
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

    public void onGenerateFile() {
        if(output.size() > 1)
        {
            output.add(0, "delete from "+query.getText()+";\n\n");
            String nameOfFile = "data.sql";
            fileOutputLabel.setText("Generated as: "+nameOfFile);
            fileLabel.setText("");
            query.setText("");
            staticField.setText("");
            queryLabel.setText("");
            id = 0;
            String path = System.getProperty("user.dir")+"\\output\\"+nameOfFile;
            File file = new File(path);
            OutputStream os = null;
                try {
                    os = new FileOutputStream(file);
                    for(String s:output)
                        os.write(s.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        os.close();
                    }catch (IOException e)
                    {}
                }

            output.clear();
        }else
            fileOutputLabel.setText("file not loaded or is empty");

    }

}
