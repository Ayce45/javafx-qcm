package modele.classesMetiers;


import modele.exceptions.QuestionnaireEnCoursTermineException;

import java.util.*;

public class Questionnaire {

    private int idQuestionnaire;
    Map<Integer,QuestionReponse> questionsReponses;
    List<QuestionReponse> questionReponsesList;

    String thematiqueQuestionnaire;

    public String getThematiqueQuestionnaire() {
        return thematiqueQuestionnaire;
    }

    public void setThematiqueQuestionnaire(String thematiqueQuestionnaire) {
        this.thematiqueQuestionnaire = thematiqueQuestionnaire;
    }

    private static int ID_AUTO = 0;


    public Questionnaire(String theme,List<QuestionReponse> questionReponses) {
        this.idQuestionnaire = ID_AUTO;
        this.thematiqueQuestionnaire = theme;
        this.questionsReponses = new HashMap<>();
        for(QuestionReponse q : questionReponses) {
            this.questionsReponses.put(q.idQuestion,q);
        }
        questionReponsesList = questionReponses;
        ID_AUTO++;
    }


    public List<QuestionReponse> getQuestionsReponses() {
        return questionReponsesList;

    }


    public int getIdQuestionnaire() {
        return idQuestionnaire;
    }


    public double validerReponses(int[] idsQuestions, String[] reponses) {
        double nbQuestions = getQuestionsReponses().size();

        int nbReponsesCorrectes = 0;
        for (int i = 0; i<idsQuestions.length;i++) {
            if (reponses[i].equals(questionsReponses.get(idsQuestions[i]).getReponseCorrecte())) {
                nbReponsesCorrectes++;
            }
        }

        return (nbReponsesCorrectes*100)/nbQuestions;
    }


    public QuestionReponse getNext(QuestionReponse q) throws QuestionnaireEnCoursTermineException {
        int indice = this.questionReponsesList.indexOf(q);
        indice++;
        if (indice == questionReponsesList.size()) {
            throw new QuestionnaireEnCoursTermineException();
        }
        return questionReponsesList.get(indice);
    }

    public QuestionReponse getQuestionById(int idQuestion) {
        return questionsReponses.get(idQuestion);
    }
}
