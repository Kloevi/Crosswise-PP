package logic;

public enum Token {
    None(0),
    Sun(1),
    Cross(2),
    Triangle(3),
    Square(4),
    Pentagon(5),
    Star(6),
    Remover(7),
    Mover(8),
    Swapper(9),
    Replacer(10);

    private final int value;
    private Position position;



    Token(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    Position getPosition() {
        return position;
    }

    void setPosition(Position position) {
        this.position = position;
    }

    public Token getTokenFromValue(int value) {
        switch (value) {
            case 0:
                return Token.None;
            case 1:
                return Token.Sun;
            case 2:
                return Token.Cross;
            case 3:
                return Token.Triangle;
            case 4:
                return Token.Square;
            case 5:
                return Token.Pentagon;
            case 6:
                return Token.Star;
            case 7:
                return Token.Remover;
            case 8:
                return Token.Mover;
            case 9:
                return Token.Swapper;
            case 10:
                return Token.Replacer;
            default:
                return null;

        }

    }

}
