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

    public static Token getTokenFromValue(int value) {
        //TODO static
        return switch (value) {
            case 0 -> Token.None;
            case 1 -> Token.Sun;
            case 2 -> Token.Cross;
            case 3 -> Token.Triangle;
            case 4 -> Token.Square;
            case 5 -> Token.Pentagon;
            case 6 -> Token.Star;
            case 7 -> Token.Remover;
            case 8 -> Token.Mover;
            case 9 -> Token.Swapper;
            case 10 -> Token.Replacer;
            default -> null;
        };

    }

    public static String getImagePathFromToken(Token token) {
        return switch (token) {
            //case None -> "src/main/resources/pictures/1 - sun.png";
            case None -> "\\pictures\\1 - sun.png";
            case Sun -> "\\pictures\\1 - sun.png";
            case Cross -> "\\pictures\\2 - cross.png";
            case Triangle -> "\\pictures\\3 - triangle.png";
            case Square -> "\\pictures\\4 - square.png";
            case Pentagon -> "\\pictures\\5 - pentagon.png";
            case Star -> "\\pictures\\6 - star.png";
            case Remover -> "\\pictures\\1 - sun.png";
            case Mover -> "\\pictures\\1 - sun.png";
            case Swapper -> "\\pictures\\1 - sun.png";
            case Replacer -> "\\pictures\\1 - sun.png";

        };
    }

}
