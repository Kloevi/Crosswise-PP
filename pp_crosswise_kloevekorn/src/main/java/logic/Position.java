package logic;

public class Position {

    private Integer xCoordinate = null;

    private Integer yCoordinate = null;

    private Token token = Token.None;

    private final boolean isHand;

    private Integer handPosition = null;

    private Integer valueDiff;



    public Position(int xCoordinate, int yCoordinate) {
        this.isHand = false;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public Position(int xCoordinate, int yCoordinate, Token token) {
        this(xCoordinate, yCoordinate);
        this.token = token;
    }

    public Position(int handPosition) {
        this.isHand = true;
        this.handPosition = handPosition;
    }

    public boolean isHand() {
        return isHand;
    }

    public Integer getHandPosition() {
        return handPosition;
    }

    public Integer getValueDiff() {
        return valueDiff;
    }

    public void setValueDiff(Integer valueDiff) {
        this.valueDiff = valueDiff;
    }
    public void setToken(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return this.token;
    }

    public Integer getXCoordinate() {
        return this.xCoordinate;
    }

    public Integer getYCoordinate() {
        return this.yCoordinate;
    }

}
