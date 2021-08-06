package ir.sharif.ap2021.client.Config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class NetworkConfig {

    MainConfig mainConfig = new MainConfig();

    private String host;
    private String port;

    public NetworkConfig() throws IOException {
        setProperties();
    }

    private void setProperties() throws IOException {

        FileReader fileReader = new FileReader(mainConfig.getNetworkConfigPath());
        Properties properties = new Properties();
        properties.load(fileReader);

        host = properties.getProperty("host");
        port = properties.getProperty("port");

    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }
}
