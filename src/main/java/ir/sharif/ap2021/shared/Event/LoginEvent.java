package ir.sharif.ap2021.shared.Event;


public class LoginEvent extends Event {

    private String username;
    private String password;


    public LoginEvent(String username, String password) {

        this.username = username;
        this.password = password;

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    @Override
    public void visit(EventVisitor eventVisitor) {
        eventVisitor.login(username,password);
    }
}
