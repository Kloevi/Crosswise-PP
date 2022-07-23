package logic;

public class Calculation {

    private final Integer pointsChange;

    private final boolean isCreatingLoss;

    private final boolean isGameWinning;

    public Calculation(Integer pointsChange, boolean isCreatingLoss, boolean isGameWinning) {
        this.pointsChange = pointsChange;
        this.isCreatingLoss = isCreatingLoss;
        this.isGameWinning = isGameWinning;
    }

    public Integer getPointsChange() {
        return pointsChange;
    }

    public boolean isCreatingLoss() {
        return isCreatingLoss;
    }

    public boolean isGameWinning() {
        return isGameWinning;
    }
}
