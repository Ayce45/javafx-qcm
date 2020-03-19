package controleur;


import javafx.stage.Stage;
import modele.classesMetiers.QuestionReponse;
import modele.classesMetiers.Questionnaire;
import modele.exceptions.*;
import modele.facade.GestionQCMImpl;
import modele.facade.InterfaceUtilisateur;
import vues.Login;
import vues.Menu;
import vues.Question;
import vues.Resultat;

import java.util.Collection;


public class Controleur {



    private Stage laStageUnique;

    private Login login;
    private Menu menu;
    private Question question;
    private Resultat resultat;


    private InterfaceUtilisateur facade;

    private String pseudo;

    private int idQuestionnaire;

    public Controleur(Stage primaryStage) {
        this.facade = new GestionQCMImpl() ;
        this.laStageUnique = primaryStage;
        this.login = Login.creerEtAfficher(this,laStageUnique);
    }

   public void tenteUnLogin(String pseudo,String motDePasse) {
        try {
            facade.connexion(pseudo,motDePasse);
            // OK on est log
            this.pseudo = pseudo;

            this.goToMenu();
            // afficher le menu
            // changer la Scene de la stage pour le menu
        } catch (PseudoDejaUtiliseException exceptionLoginDejaPris) {
            // pas OK : on reste sur le login
            login.afficheMessageErreur("Login déjà connecté !!!");
        } catch (InformationsSaisiesIncoherentesException e) {
            login.afficheMessageErreur("Couple Login/mot de passe incohérent");
        }
    }

    public void goToMenu() {
        menu = Menu.creer(laStageUnique);
        menu.setMonControleur(this);
        menu.chargerQuestionnaire();
        menu.show();
    }


    public void quitter() {
        this.facade.deconnexion(pseudo);
        this.login = Login.creerEtAfficher(this,laStageUnique);
    }

    public void quitterApplication() {
        laStageUnique.close();
    }

    public Collection<Questionnaire> getListQuestionnairesNonFaits(String pseudo) {
        return facade.getListQuestionnairesNonFaits(pseudo);
    }

    public String getPseudo() {
        return pseudo;
    }

    public void choixQuestionnaire(int id) {
        try {
            idQuestionnaire = id;
            facade.choixQuestionnaire(pseudo, id);
        } catch (QuestionnaireEnCoursNonTermineException e) {
            e.printStackTrace();
        }
    }

    public QuestionReponse next() {
        return facade.next(pseudo, idQuestionnaire);
    }

    public void validerQuestion(String reponse) {
        facade.validerQuestion(pseudo, idQuestionnaire, reponse);
    }

    public void goToQuestion() {
        question = Question.creer(laStageUnique);
        question.setMonControleur(this);
        question.chargerQuestion();
        question.show();
    }

    public boolean hasNext() {
        return facade.hasNext(pseudo, idQuestionnaire);
    }

    public Double getScore(){
        try {
            return facade.getScore(pseudo, idQuestionnaire);
        } catch (QuestionnaireNonFaitException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void goToResultat() {
        resultat = Resultat.creer(laStageUnique);
        resultat.setMonControleur(this);
        resultat.chargerResultat();
        resultat.show();
    }

    public void validerQuestionnaire() {
        try {
            facade.validerQuestionnaire(pseudo, idQuestionnaire);
        } catch (ValidationQuestionnaireException e) {
            e.printStackTrace();
        }
    }
}
