package ir.sharif.ap2021.shared.Response;

import ir.sharif.ap2021.shared.Model.Thought;
import ir.sharif.ap2021.shared.Model.User;

import java.util.ArrayList;
import java.util.List;

public class ThoughtResponse extends Response {

    private String order;
    private Thought thought;
    private User myUser;
    private Thought parent;
    private final List<Thought> opinions;

    public ThoughtResponse(String order) {
        this.order = order;
        opinions = new ArrayList<>();
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Thought getThought() {
        return thought;
    }

    public void setThought(Thought thought) {
        this.thought = thought;
    }

    public User getMyUser() {
        return myUser;
    }

    public void setMyUser(User myUser) {
        this.myUser = myUser;
    }

    public Thought getParent() {

        return parent;
    }

    public void setParent(Thought parent) {
        this.parent = parent;
    }

    public List<Thought> getOpinions() {
        return opinions;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.doThought(this);
    }
}
