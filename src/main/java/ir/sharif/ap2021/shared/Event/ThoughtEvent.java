package ir.sharif.ap2021.shared.Event;


public class ThoughtEvent extends Event {

    private String order;
    private int thoughtId;
    private String mentionText;
    private byte[] mentionImg;




    public ThoughtEvent(String order, int thoughtId) {
        this.thoughtId = thoughtId;
        this.order = order;

    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getThoughtId() {
        return thoughtId;
    }

    public void setThoughtId(int thoughtId) {
        this.thoughtId = thoughtId;
    }

    public String getMentionText() {
        return mentionText;
    }

    public void setMentionText(String mentionText) {
        this.mentionText = mentionText;
    }

    public byte[] getMentionImg() {
        return mentionImg;
    }

    public void setMentionImg(byte[] mentionImg) {
        this.mentionImg = mentionImg;
    }

    @Override
    public void visit(EventVisitor eventVisitor) {
        eventVisitor.doThought(this);
    }
}
