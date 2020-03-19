package modele.classesMetiers;

import modele.exceptions.QuestionnaireDejaFaitException;
import modele.exceptions.QuestionnaireNonFaitException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ScoresPersos {

    Map<Integer,Double> scoresPerso;


    public ScoresPersos() {
        scoresPerso = new HashMap<>();
    }



    public void ajouterScorePerso(int idQuestionnaire, double pourcentageReussite) throws QuestionnaireDejaFaitException {
        Double score = scoresPerso.get(idQuestionnaire);
        if (score != null)
            throw new QuestionnaireDejaFaitException("Le questionnaire "+idQuestionnaire+ " a déjà été fait !");
        scoresPerso.put(idQuestionnaire,pourcentageReussite);
    }

    public double getScore(int idQuestionnaire) throws QuestionnaireNonFaitException {
        Double score = scoresPerso.get(idQuestionnaire);
        if (score == null) {
            throw new QuestionnaireNonFaitException("Le questionnaire "+idQuestionnaire + "n'a pas été fait");
        }
        return score;
    }


    public Collection<Integer> getIdsQuestionnairesDejaFaits() {
        return scoresPerso.keySet();
    }
}
