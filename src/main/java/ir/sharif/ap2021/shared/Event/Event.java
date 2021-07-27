package ir.sharif.ap2021.shared.Event;


public abstract class Event {
    public abstract void visit(EventVisitor eventVisitor);
}
