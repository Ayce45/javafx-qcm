package modele.facade;

import modele.exceptions.*;
import modele.classesMetiers.QuestionReponse;
import modele.classesMetiers.Questionnaire;
import modele.classesMetiers.ScoresPersos;
import modele.classesMetiers.Utilisateur;

import java.util.*;
import java.util.stream.Collectors;

public class GestionQCMImpl implements InterfaceUtilisateur {

    Map<String, ScoresPersos> scoresPersosMap;
    Map<Integer, Questionnaire> questionnaires;
    List<String> pseudosConnectes;
    Map<String, Utilisateur> utilisateursMap;
    Map<String,Integer> questionnairesEnCours;


    Map<String,Map<Integer,Map<Integer,String>>> historiqueDesReponses;



    public GestionQCMImpl() {
        scoresPersosMap = new HashMap<>();
        questionnaires = new HashMap<>();
        pseudosConnectes = new ArrayList<>();
        questionnairesEnCours = new HashMap<>();
        utilisateursMap = new HashMap<>();
        historiqueDesReponses = new HashMap<>();
        this.initialiserDAO();

    }

    private void initialiserDAO() {

        this.utilisateursMap.put("yohan",new Utilisateur("yohan","yohan"));
        this.utilisateursMap.put("fred",new Utilisateur("fred","fred"));



        /**
         * Mathématiques
         */
        QuestionReponse questionReponse1 = new QuestionReponse("1+1", new ArrayList<>(Arrays.asList("2", "3", "4", "5")), "2");
        QuestionReponse questionReponse2 = new QuestionReponse("12+35", new ArrayList<>(Arrays.asList("12", "38", "47", "52")), "47");
        QuestionReponse questionReponse3 = new QuestionReponse("1980+37", new ArrayList<>(Arrays.asList("2017", "2018", "2019", "2020")), "2017");
        QuestionReponse questionReponse4 = new QuestionReponse("0*3+1*3", new ArrayList<>(Arrays.asList("2", "3", "4", "5")), "3");


        /**
         * Mathématiques moins drôles
         */
        QuestionReponse questionReponse11 = new QuestionReponse("12*12", new ArrayList<>(Arrays.asList("122", "136", "144", "156")), "144");
        QuestionReponse questionReponse12 = new QuestionReponse("12*35", new ArrayList<>(Arrays.asList("410", "420", "510", "520")), "420");
        QuestionReponse questionReponse13 = new QuestionReponse("16*16", new ArrayList<>(Arrays.asList("276", "266", "256", "246")), "256");


        List<QuestionReponse> questions1 = new ArrayList<QuestionReponse>(Arrays.asList(questionReponse1, questionReponse2, questionReponse3,questionReponse4));

        List<QuestionReponse> questions2 = new ArrayList<QuestionReponse>(Arrays.asList(questionReponse11, questionReponse12, questionReponse13));

        Questionnaire questionnaire1 = new Questionnaire("Maths pour les nuls",questions1);
        Questionnaire questionnaire2 = new Questionnaire("Maths : level up",questions2);
        questionnaires.put(questionnaire1.getIdQuestionnaire(),questionnaire1);
        questionnaires.put(questionnaire2.getIdQuestionnaire(),questionnaire2);
       }

    @Override
    public void connexion(String pseudo,String mdp) throws PseudoDejaUtiliseException,InformationsSaisiesIncoherentesException {
        if (pseudosConnectes.contains(pseudo))
            throw new PseudoDejaUtiliseException();

        Utilisateur u = this.utilisateursMap.get(pseudo);

        if (u == null) {
            throw new InformationsSaisiesIncoherentesException();

        }

        if (u.checkPassword(mdp)) {
            this.pseudosConnectes.add(pseudo);
            return;
        }
        throw new InformationsSaisiesIncoherentesException();

    }

    @Override
    public void choixQuestionnaire(String pseudo, int id) throws QuestionnaireEnCoursNonTermineException {
        Integer choisi = questionnairesEnCours.get(pseudo);

        if (choisi != null)
            throw new QuestionnaireEnCoursNonTermineException();

        questionnairesEnCours.put(pseudo,id);
        Utilisateur u = utilisateursMap.get(pseudo);
        u.setIdQuestionnaireEnCours(id);
        return;
    }

    @Override
    public Collection<Questionnaire> getListQuestionnairesNonFaits(String pseudo) {

        Collection<Integer> questionnairesDejaFaits = scoresPersosMap.get(pseudo)==null ? new ArrayList<>() : scoresPersosMap.get(pseudo).getIdsQuestionnairesDejaFaits();

        Collection<Integer> questionnairesPotentiels = questionnaires.keySet();
        questionnairesPotentiels.removeAll(questionnairesDejaFaits);

        List<Questionnaire> collect = questionnairesPotentiels.stream().map(x -> questionnaires.get(x)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public double validerQuestionnaire(String pseudo, int idquestionnaire) throws ValidationQuestionnaireException {
        if (this.questionnairesEnCours.get(pseudo) == null || this.questionnairesEnCours.get(pseudo) != idquestionnaire ) {
            throw new ValidationQuestionnaireException();
        }
        Questionnaire questionnaire = this.questionnaires.get(idquestionnaire);
        double score =this.utilisateursMap.get(pseudo).transmettreReponses(questionnaire);

        ScoresPersos scoresPersos = this.scoresPersosMap.get(pseudo);
        if (scoresPersos == null) {
            scoresPersos = new ScoresPersos();
            this.scoresPersosMap.put(pseudo,scoresPersos);
        }
        try {
            scoresPersos.ajouterScorePerso(questionnaire.getIdQuestionnaire(),score);
        } catch (QuestionnaireDejaFaitException e) {
            throw new ValidationQuestionnaireException("Le questionnaire "+questionnaire.getIdQuestionnaire()+" a déjà été fait par "+pseudo);
        }
        this.questionnairesEnCours.remove(pseudo);


        Utilisateur u = this.utilisateursMap.get(pseudo);
        Map<Integer,Map<Integer,String>> mapsDesReponsesPourPseudo = this.historiqueDesReponses.get(pseudo);
        if (mapsDesReponsesPourPseudo == null) {
            mapsDesReponsesPourPseudo = new HashMap<>();
            this.historiqueDesReponses.put(pseudo,mapsDesReponsesPourPseudo);
        }

        mapsDesReponsesPourPseudo.put(u.getIdQuestionnaireEnCours(),u.getReponsesDonnees());

        u.resetStatus();
        return score;
    }


    @Override
    public void deconnexion(String pseudo) {
        this.pseudosConnectes.remove(pseudo);

    }


    @Override
    public QuestionReponse next(String pseudo, int idQuestionnaire) {
        Utilisateur u = this.utilisateursMap.get(pseudo);
        QuestionReponse next = null;
        if (u.getIdQuestionnaireEnCours() == idQuestionnaire) {
            if (u.getQuestionReponseCourante()==null) {
                next = this.questionnaires.get(idQuestionnaire).getQuestionsReponses().get(0);
            }
            else {
                if (hasNext(pseudo,idQuestionnaire)) {
                    try {
                        next = this.questionnaires.get(idQuestionnaire).getNext(u.getQuestionReponseCourante());
                    } catch (QuestionnaireEnCoursTermineException e) {
                        return null;
                    }
                }
                else {
                    return null;
                }
            }
        }
        u.setQuestionReponseCourante(next);
        return next;
    }


    @Override
    public void validerQuestion(String pseudo, int idQuestion, String reponse)  {
        this.utilisateursMap.get(pseudo).validerQuestion(idQuestion,reponse);
        //QuestionReponse next = this.questionnaires.get(this.questionnairesEnCours.get(pseudo)).getNext(this.utilisateursMap.get(pseudo).getQuestionReponseCourante());
        //this.utilisateursMap.get(pseudo).setQuestionReponseCourante(next);

    }


    @Override
    public boolean hasNext(String pseudo, int idQuestionnaire)  {
        Utilisateur u = utilisateursMap.get(pseudo);
        if (u.getIdQuestionnaireEnCours() != null && u.getQuestionReponseCourante() == null) {
            return true;
        }
        else
        {
            if (u.getIdQuestionnaireEnCours() == idQuestionnaire) {
                try {
                    this.questionnaires.get(idQuestionnaire).getNext(u.getQuestionReponseCourante());
                    return true;
                } catch (QuestionnaireEnCoursTermineException e) {
                    return false;
                }
            }
            else
                return false;
            }

        }

    @Override
    public Double getScore(String pseudo, int idQuestionnaire) throws QuestionnaireNonFaitException {
        System.out.println(pseudo + " " + idQuestionnaire);
        System.out.println(scoresPersosMap.get(pseudo));
        return scoresPersosMap.get(pseudo).getScore(idQuestionnaire);
    }

    @Override
    public Questionnaire getQuestionnaireById(String pseudo, int idQuestionnaire) {
        return this.questionnaires.get(idQuestionnaire);
    }

    @Override
    public QuestionReponse getQuestionForAQuestionnaireById(String pseudo, int idQuestionnaire, int idQuestion) {

        if (this.scoresPersosMap.get(pseudo).getIdsQuestionnairesDejaFaits().contains(idQuestionnaire)) {
            return questionnaires.get(idQuestionnaire).getQuestionById(idQuestion);
        }
        else
            return null;

    }


    @Override
    public Collection<Questionnaire> getListeQuestionnairesFaits(String pseudo) {
        Collection<Integer> questionnairesDejaFaits = scoresPersosMap.get(pseudo)==null ? new ArrayList<>() : scoresPersosMap.get(pseudo).getIdsQuestionnairesDejaFaits();
        List<Questionnaire> collect = questionnairesDejaFaits.stream().map(x -> questionnaires.get(x)).collect(Collectors.toList());
        return collect;


    }

    @Override
    public Map<Integer, String> getReponsesDonneesPourQuestionnaire(String pseudo, int id) {
        if (this.scoresPersosMap.get(pseudo).getIdsQuestionnairesDejaFaits().contains(id)) {
            return historiqueDesReponses.get(pseudo).get(id);
        }
        else
            return null;
    }






}

