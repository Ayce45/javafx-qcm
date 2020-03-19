package modele.facade;

import modele.exceptions.*;
import modele.classesMetiers.QuestionReponse;
import modele.classesMetiers.Questionnaire;

import java.util.Collection;
import java.util.Map;

public interface InterfaceUtilisateur {

    /**
     * Permet de se connecter à l'application si le couple pseudo/mdp est cohérent
     * @param pseudo
     * @param mdp
     * @throws PseudoDejaUtiliseException
     * @throws InformationsSaisiesIncoherentesException
     */

    void connexion(String pseudo,String mdp) throws PseudoDejaUtiliseException, InformationsSaisiesIncoherentesException;


    /**
     * Précise que l'utilisateur "pseudo" veut commencer un nouveau question (id).
     * @param pseudo
     * @param id
     * @throws QuestionnaireEnCoursNonTermineException : un questionnaire est déjà en cours
     */
     void choixQuestionnaire(String pseudo, int id) throws QuestionnaireEnCoursNonTermineException;


    /**
     * Permet de retourner la liste des questionnaires non faits.
     * @param pseudo
     * @return
     */
    Collection<Questionnaire> getListQuestionnairesNonFaits(String pseudo);


    /**
     * Permet de valider un questionnaire (idQuestionnaire) pour l'utilisateur (pseudo)
     * @param pseudo
     * @param idQuestionnaire
     * @return
     * @throws ValidationQuestionnaireException
     */
    double validerQuestionnaire(String pseudo, int idQuestionnaire) throws ValidationQuestionnaireException;





    /**
     * Permet de déconnecter proprement l'utilisateur (pseudo) de l'application
     * @param pseudo
     */
    void deconnexion(String pseudo);


    /**
     * Permet de récupérer la liste des questionnaires faits par l'utilisateur (pseudo)
     * @param pseudo
     * @return
     */
    Collection<Questionnaire> getListeQuestionnairesFaits(String pseudo);


    /**
     * Permet de récupérer les réponses (String) pour chaque question (Integer : identifiantQuestion)
     * pour un pseudo donné et un identifiant de questionnaire donné
     * @param pseudo
     * @param id
     * @return
     */
    Map<Integer,String> getReponsesDonneesPourQuestionnaire(String pseudo, int id);

    /**
     * selon l'avancement de l'utilisateur dans le questionnaire idQuestionnaire,
     * la prochaine question est retournée. S'il s'agit de la dernière question alors on retournera null
     * @param pseudo
     * @param idQuestionnaire
     * @return
     */

    QuestionReponse next(String pseudo, int idQuestionnaire);


    /**
     * Permet de valider une réponse pour une question (idQuestion) du questionnaire courant de l'utilisateur
     * (pseudo)
     * @param pseudo
     * @param idQuestion
     * @param reponse
     */
    void validerQuestion(String pseudo, int idQuestion, String reponse);

    /**
     * Permet de savoir s'il existe une question supplémentaire par rapport à l'avancement de l'
     * utilisateur dans la résolution du questionnaire
     * @param pseudo
     * @param idQuestionnaire
     * @return
     */
    boolean hasNext(String pseudo, int idQuestionnaire);


    /**
     * Permet de récupérer le score fait sur un questionnaire.
     * Exception levée si le questionnaire n'a pas été fait
     * @param pseudo
     * @param idQuestionnaire
     * @return
     * @throws QuestionnaireNonFaitException
     */
    Double getScore(String pseudo, int idQuestionnaire) throws QuestionnaireNonFaitException;




    /**
     * Retourne le questionnaire correspondant à l'ID si le questionnaire a déjà été fait par pseudo.
     * Dans le cas contraire null
     * @param pseudo
     * @param idQuestionnaire
     * @return
     */
    Questionnaire getQuestionnaireById(String pseudo, int idQuestionnaire);

    /**
     * Permet de retourner la question idQuestion du questionnaire idQuestionnaire, si ce dernier a
     * effectivement déjà fait le questionnaire. Sinon la valeur retournée sera null.
     * @param pseudo
     * @param idQuestionnaire
     * @param idQuestion
     * @return
     */

    QuestionReponse getQuestionForAQuestionnaireById(String pseudo, int idQuestionnaire, int idQuestion);
}
