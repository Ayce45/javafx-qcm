package modele.exceptions;

public class ValidationQuestionnaireException extends Exception {
    public ValidationQuestionnaireException() {
    }

    public ValidationQuestionnaireException(int id) {
        super("Le questionnaire "+id + "n'est pas le questionnaire courant");
    }

    public ValidationQuestionnaireException(String s) {
        super(s);
    }
}
