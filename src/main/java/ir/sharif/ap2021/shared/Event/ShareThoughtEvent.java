package ir.sharif.ap2021.shared.Event;

public class ShareThoughtEvent extends Event {

    private String text;
    private byte[] picture;
    private int userId;
    private String change;

    public ShareThoughtEvent(String text, int userId) {
        this.text = text;
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    @Override
    public void visit(EventVisitor eventVisitor) {
        eventVisitor.shareThought(this);
    }

}
