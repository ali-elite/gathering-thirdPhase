package ir.sharif.ap2021.shared.Response;

public abstract class Response {
    public abstract void visit(ResponseVisitor responseVisitor);
}
