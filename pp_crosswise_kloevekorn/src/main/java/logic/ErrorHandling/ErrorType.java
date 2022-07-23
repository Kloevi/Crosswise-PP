package logic.ErrorHandling;

public enum ErrorType {
    noTokensOnHand,
    noPossibleMoves;

    private String msg;

    static {
        noTokensOnHand.msg = "Auf der Hand befinden sich keine Spielsteine mehr";
        noPossibleMoves.msg = "Der Computer kann keine gültigen Züge mehr vornehmen";
    }

    public String getErrorMessage() {
        return this.msg;
    }
}
