package modele.classesMetiers;

import java.util.Collection;

public class QuestionReponse {

    private static int AUTO_ID_QUESTION = 0;
    int idQuestion;
    String question;
    Collection<String> reponsesPossibles;
    String reponseCorrecte;

    public QuestionReponse(String question, Collection<String> reponsesPossible, String reponseCorrecte) {
        idQuestion = AUTO_ID_QUESTION;
        AUTO_ID_QUESTION++;
        this.question = question;
        this.reponsesPossibles = reponsesPossible;
        this.reponseCorrecte = reponseCorrecte;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Collection<String> getReponsesPossibles() {
        return reponsesPossibles;
    }

    public void setReponsesPossibles(Collection<String> reponsesPossibles) {
        this.reponsesPossibles = reponsesPossibles;
    }

    public String getReponseCorrecte() {
        return reponseCorrecte;
    }

    public void setReponseCorrecte(String reponseCorrecte) {
        this.reponseCorrecte = reponseCorrecte;
    }



}
