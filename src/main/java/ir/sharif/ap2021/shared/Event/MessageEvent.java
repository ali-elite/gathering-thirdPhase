package ir.sharif.ap2021.shared.Event;


import ir.sharif.ap2021.shared.Model.Message;

public class MessageEvent extends Event {

    String order;
    Message message;
    String editedText;

    public MessageEvent(String order, Message message) {
        this.order = order;
        this.message = message;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getEditedText() {
        return editedText;
    }

    public void setEditedText(String editedText) {
        this.editedText = editedText;
    }

    @Override
    public void visit(EventVisitor eventVisitor) {
        eventVisitor.doMessage(this);
    }
}
