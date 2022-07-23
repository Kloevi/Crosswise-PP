package logic;

public class TokenMove {

    /**
     * Position, an den der Stein platziert werden soll. Falls es sich um einen Aktionsstein,
     * handelt, gelten die Folgenden Regeln:
     *  Remover - Position von dem der Stein entfernt wird,
     *  Mover - Position, an den der Stein platziert wird
     *  Swapper - Position, die gewechselt wird
     *  Replacer - Position, des auszutauschenden Tokens auf dem Board
     */
    private final Position primaryMovePosition;
    /**
     * Position für Aktionssteine mit folgenden Regeln:
     *  Mover - Position, an den der Stein plaziert werden soll
     *  Swapper - Position, die gewechselt wird
     *  Replacer - Position, des auszutauschenden Tokens auf der Hand
     */
    private Position secondaryMovePosition = null;
    /**
     * Änderung der Punktezahl bei Tätigung dieses Zuges
     */
    private final Integer relativeChange;
    /**
     * Das zu benutzende Token
     */
    private final Token token;
    /**
     * boolean, ob der Zug einen Gewinn erzeugt
     */
    private final boolean isGameWinning;
    /**
     * boolean, ob der Zug eine Niederlage verhindert
     */
    private final boolean isPreventingLoss;


    public TokenMove(Position primaryMovePosition, Integer relativeChange,
            Token token, boolean isGameWinning, boolean isPreventingLoss) {
        this.primaryMovePosition = primaryMovePosition;
        this.relativeChange = relativeChange;
        this.token = token;
        this.isGameWinning = isGameWinning;
        this.isPreventingLoss = isPreventingLoss;
    }

    public TokenMove(Position primaryMovePosition, Position secondaryMovePosition, Integer relativeChange,
            Token token, boolean isGameWinning, boolean isPreventingLoss) {
        this(primaryMovePosition, relativeChange,token, isGameWinning, isPreventingLoss);
        this.secondaryMovePosition = secondaryMovePosition;

    }

    public Position getPrimaryMovePosition() {
        return primaryMovePosition;
    }

    public Position getSecondaryMovePosition() {
        return secondaryMovePosition;
    }

    public Integer getRelativeChange() {
        return relativeChange;
    }

    public Token getToken() {
        return token;
    }

    public boolean isGameWinning() {
        return isGameWinning;
    }

    public boolean isPreventingLoss() {
        return isPreventingLoss;
    }
}
