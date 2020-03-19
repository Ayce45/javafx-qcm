package modele.classesMetiers;

import java.util.HashMap;
import java.util.Map;

public class Utilisateur {


    String pseudo;
    String motdePasse;
    Map<Integer,String> reponsesDonnees;
    Integer idQuestionnaireEnCours;
    QuestionReponse questionReponseCourante;

    public Map<Integer, String> getReponsesDonnees() {
        return reponsesDonnees;
    }

    public QuestionReponse getQuestionReponseCourante() {
        return questionReponseCourante;
    }

    public void setQuestionReponseCourante(QuestionReponse questionReponseCourante) {
        this.questionReponseCourante = questionReponseCourante;
    }

    public Utilisateur(String pseudo,String mdp) {
        this.pseudo = pseudo;
        this.motdePasse = mdp;
        reponsesDonnees = new HashMap<>();
        idQuestionnaireEnCours = null;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Integer getIdQuestionnaireEnCours() {
        return idQuestionnaireEnCours;
    }

    public void setIdQuestionnaireEnCours(Integer idQuestionnaireEnCours) {
        this.idQuestionnaireEnCours = idQuestionnaireEnCours;
    }


    public boolean checkPassword(String mdp) {
        return this.motdePasse.equals(mdp);
    }


    public double transmettreReponses(Questionnaire questionnaire) {
        int[] ids = new int[reponsesDonnees.keySet().size()];
        String[] reponses = new String[reponsesDonnees.keySet().size()];
        int i = 0;
        for (int id : reponsesDonnees.keySet()) {
            ids[i]=id;
            reponses[i] = reponsesDonnees.get(id);
            i++;
        }
        return questionnaire.validerReponses(ids,reponses);
    }

    public void validerQuestion(int idQuestion, String reponse) {
        this.reponsesDonnees.put(idQuestion,reponse);
    }

    public void resetStatus() {
        this.idQuestionnaireEnCours = null;
        this.questionReponseCourante = null;
        this.reponsesDonnees = new HashMap<>();
    }
}
