package ir.sharif.ap2021.shared.Event;


public class ExitEvent extends Event{


    @Override
    public void visit(EventVisitor eventVisitor) {
        eventVisitor.exit();
    }
}
