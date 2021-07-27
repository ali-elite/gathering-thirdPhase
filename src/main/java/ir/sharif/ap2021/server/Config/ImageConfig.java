package ir.sharif.ap2021.server.Config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ImageConfig {

    private MainConfig mainConfig = new MainConfig();

    private String logo;
    private String group;
    private String follow;
    private String unfollow;
    private String fillLike;
    private String like;
    private String fillRet;
    private String ret;
    private String user;


    public ImageConfig() throws IOException {
        setProperties();
    }

    private void setProperties() throws IOException {

        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfig.getImageConfigPath());
        properties.load(fileReader);

        logo = properties.getProperty("logo");
        group = properties.getProperty("group");
        follow = properties.getProperty("follow");
        unfollow = properties.getProperty("unfollow");
        fillLike = properties.getProperty("fillLike");
        like = properties.getProperty("like");
        fillRet = properties.getProperty("fillRet");
        ret = properties.getProperty("ret");
        user = properties.getProperty("user");

    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }

    public String getLogo() {
        return logo;
    }

    public String getGroup() {
        return group;
    }

    public String getFollow() {
        return follow;
    }

    public String getUnfollow() {
        return unfollow;
    }

    public String getFillLike() {
        return fillLike;
    }

    public String getLike() {
        return like;
    }

    public String getFillRet() {
        return fillRet;
    }

    public String getRet() {
        return ret;
    }

    public String getUser() {
        return user;
    }
}
