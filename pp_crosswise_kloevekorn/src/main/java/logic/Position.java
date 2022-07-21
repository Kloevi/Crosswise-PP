package logic;

public class Position {

    private final Integer xCoordinate;

    private final Integer yCoordinate;

    private Token token = Token.None;



    private Integer valueDiff;



    public Position(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public Position(int xCoordinate, int yCoordinate, Token token) {
        this(xCoordinate, yCoordinate);
        this.token = token;
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
