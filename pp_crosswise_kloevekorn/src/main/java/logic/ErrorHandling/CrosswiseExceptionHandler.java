package logic.ErrorHandling;

public class CrosswiseExceptionHandler extends Exception{

    public CrosswiseExceptionHandler(ErrorType errorType) {
        super(errorType.getErrorMessage());
    }
}
