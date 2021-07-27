package ir.sharif.ap2021.client.Config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainConfig {

    private final String mainConfigPath =
            "src/main/resources/config/mainConfig";

    private String resourcesPath;
    private String imageConfigPath;
    private String fxmlConfigPath;
    private String errorConfigPath;
    private String itemConfigPath;


    public MainConfig() throws IOException {
        setProperties();
    }

    private void setProperties() throws IOException {

        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfigPath);
        properties.load(fileReader);

        resourcesPath = properties.getProperty("resourcesPath");
        imageConfigPath = properties.getProperty("imageConfigPath");
        fxmlConfigPath = properties.getProperty("fxmlConfigPath");
        errorConfigPath = properties.getProperty("errorConfigPath");
        itemConfigPath = properties.getProperty("itemConfigPath");
    }


    public String getResourcesPath() {
        return resourcesPath;
    }

    public String getImageConfigPath() {
        return imageConfigPath;
    }

    public String getFxmlConfigPath() {
        return fxmlConfigPath;
    }

    public String getErrorConfigPath() {
        return errorConfigPath;
    }

    public String getItemConfigPath() {
        return itemConfigPath;
    }
}
