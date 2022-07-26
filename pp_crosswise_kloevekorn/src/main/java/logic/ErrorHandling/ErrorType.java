package logic.ErrorHandling;

public enum ErrorType {
    noTokensOnHand,
    noPossibleMoves,
    IdOutOfBounds;

    private String msg;

    static {
        noTokensOnHand.msg = "Auf der Hand befinden sich keine Spielsteine mehr";
        noPossibleMoves.msg = "Der Computer kann keine gültigen Züge mehr vornehmen";
        IdOutOfBounds.msg = "Die mitgelieferte ID ist nicht gültig";
    }

    public String getErrorMessage() {
        return this.msg;
    }
}
