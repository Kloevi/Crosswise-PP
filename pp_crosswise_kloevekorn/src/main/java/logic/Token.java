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

    Token(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

}
