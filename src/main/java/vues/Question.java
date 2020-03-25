package vues;

import com.jfoenix.controls.JFXComboBox;
import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import modele.classesMetiers.QuestionReponse;
import modele.classesMetiers.Questionnaire;

import java.io.IOException;
import java.net.URL;

public class Question {

    private static Stage stage;
    @FXML
    public Label numero;
    @FXML
    public Label question;
    @FXML
    public ComboBox<String> listeReponse;

    private Controleur monControleur;
    private int id;


    public static Question creer(Stage laStageUnique) {
        URL location = Question.class.getResource("/vues/question.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Question vue = fxmlLoader.getController();
        laStageUnique.setTitle("Question");
        laStageUnique.setScene(new Scene(root, 427, 255));
        stage = laStageUnique;
        return vue;
    }

    public void show() {
        stage.show();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMonControleur(Controleur monControleur) {
        this.monControleur = monControleur;
    }

    public void chargerQuestion() {
        QuestionReponse questionReponse = monControleur.next();
        question.setText(questionReponse.getQuestion());
        setId(questionReponse.getIdQuestion());
        numero.setText("Question " + questionReponse.getIdQuestion());
        listeReponse.getItems().clear();
        listeReponse.getItems().addAll(questionReponse.getReponsesPossibles());
    }

    public void validerReponse(ActionEvent actionEvent) {
        monControleur.validerQuestion(listeReponse.getSelectionModel().getSelectedItem(), id);
        if (monControleur.hasNext()) {
            chargerQuestion();
        } else {
            monControleur.validerQuestionnaire();
            monControleur.goToResultat();
        }

    }
}
