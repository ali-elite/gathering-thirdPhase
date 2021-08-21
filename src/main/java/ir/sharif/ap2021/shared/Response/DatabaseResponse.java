package ir.sharif.ap2021.shared.Response;

public class DatabaseResponse extends Response{
    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.databaseFailed(this);
    }
}
