package ir.sharif.ap2021.shared.Event;

public class MainMenuEvent extends Event {

    private String order;
    private int id;
    private String username;
    private String password;
    private String lastSeen;
    private String selectedUser;
    private boolean isDiactive;

    public MainMenuEvent(String order, int id) {
        this.order = order;
        this.id = id;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(String selectedUser) {
        this.selectedUser = selectedUser;
    }

    public boolean isDiactive() {
        return isDiactive;
    }

    public void setDiactive(boolean diactive) {
        isDiactive = diactive;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void visit(EventVisitor eventVisitor) {
        eventVisitor.doMainMenu(this);
    }
}
