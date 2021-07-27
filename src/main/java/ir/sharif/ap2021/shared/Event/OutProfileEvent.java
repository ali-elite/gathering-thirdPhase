package ir.sharif.ap2021.shared.Event;


public class OutProfileEvent extends Event {

    private int userid;
    private String order;


    public OutProfileEvent(int userId, String order) {
        this.userid = userId;
        this.order = order;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public void visit(EventVisitor eventVisitor) {
        eventVisitor.doOutProfile(this);
    }

}
