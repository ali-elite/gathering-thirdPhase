package ir.sharif.ap2021.shared.Response;

public class LoginResponse extends Response{

    private boolean isEntered;
    private String message;

    public LoginResponse(boolean isEntered, String message) {
        this.isEntered = isEntered;
        this.message = message;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.login(isEntered,message);
    }

    public boolean isEntered() {
        return isEntered;
    }

    public String getMessage() {
        return message;
    }
}
