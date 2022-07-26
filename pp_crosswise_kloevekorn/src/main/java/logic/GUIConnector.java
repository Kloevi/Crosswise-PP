package logic;

import logic.ErrorHandling.CrosswiseExceptionHandler;

public interface GUIConnector {

    public void showPlayerHand(int ID, Token[] hand) throws CrosswiseExceptionHandler;

    public void hidePlayerHand(int ID);

    public void setAnimationSpeed(float speed);
}
