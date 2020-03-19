package modele.exceptions;

public class QuestionnaireEnCoursNonTermineException extends Exception {

    public QuestionnaireEnCoursNonTermineException() {
    }

    public QuestionnaireEnCoursNonTermineException(int id) {
        super("Il y a déjà un questionnaire en cours : "+id);
    }
}
