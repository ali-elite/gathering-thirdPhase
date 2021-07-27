package ir.sharif.ap2021.shared.Response;


public class SignupResponse extends Response {


    private boolean isEntered;
    private String message;

    public SignupResponse(boolean isEntered, String message) {
        this.isEntered = isEntered;
        this.message = message;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.signup(isEntered,message);
    }

    public boolean isEntered() {
        return isEntered;
    }

    public String getMessage() {
        return message;
    }


}
