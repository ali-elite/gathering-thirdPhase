package ir.sharif.ap2021.shared.Response;

public class ExitResponse extends Response{
    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.exit();
    }
}
