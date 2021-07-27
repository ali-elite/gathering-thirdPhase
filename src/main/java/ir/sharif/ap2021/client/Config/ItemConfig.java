package ir.sharif.ap2021.client.Config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ItemConfig {

    private MainConfig mainConfig = new MainConfig();

    private String anyOne;
    private String noOne;
    private String justFollowers;
    private String deleteColor;
    private String deleteStyle;
    private String deleteText;
    private String ownColor;
    private String ownStyle;


    public ItemConfig() throws IOException {
        setProperties();
    }

    private void setProperties() throws IOException {

        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfig.getItemConfigPath());
        properties.load(fileReader);

        anyOne = properties.getProperty("anyOne");
        noOne = properties.getProperty("noOne");
        justFollowers = properties.getProperty("justFollowers");

        deleteColor = properties.getProperty("deleteColor");
        deleteStyle = properties.getProperty("deleteStyle");
        deleteText = properties.getProperty("deleteText");
        ownColor = properties.getProperty("ownColor");
        ownStyle = properties.getProperty("ownStyle");


    }

    public String getAnyOne() {
        return anyOne;
    }

    public String getNoOne() {
        return noOne;
    }

    public String getJustFollowers() {
        return justFollowers;
    }

    public String getDeleteColor() {
        return deleteColor;
    }

    public String getDeleteStyle() {
        return deleteStyle;
    }

    public String getDeleteText() {
        return deleteText;
    }

    public String getOwnColor() {
        return ownColor;
    }

    public String getOwnStyle() {
        return ownStyle;
    }
}
