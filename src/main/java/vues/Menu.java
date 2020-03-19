package vues;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import modele.classesMetiers.Questionnaire;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;


public class Menu {
    private static Stage stage;

    @FXML
    private ComboBox<Questionnaire> listeQuestionnaire;

    private Controleur monControleur;

    public static Menu creer(Stage laStageUnique) {
        URL location = Menu.class.getResource("/vues/menu.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Menu vue = fxmlLoader.getController();
        laStageUnique.setTitle("Question");
        laStageUnique.setScene(new Scene(root, 427, 255));
        stage = laStageUnique;
        return vue;
    }


    public void setMonControleur(Controleur monControleur) {
        this.monControleur = monControleur;
    }

    public void choixQuestionnaire() {
        Questionnaire questionnaire = listeQuestionnaire.getSelectionModel().getSelectedItem();
        monControleur.choixQuestionnaire(questionnaire.getIdQuestionnaire());
        monControleur.goToQuestion();
    }

    public void quitterApplication(ActionEvent actionEvent) {
        monControleur.quitter();
    }

    public void chargerQuestionnaire() {
        Collection<Questionnaire> collectionQuestionnaire = monControleur.getListQuestionnairesNonFaits(monControleur.getPseudo());
        listeQuestionnaire.getItems().addAll(collectionQuestionnaire);
        listeQuestionnaire.setConverter(new StringConverter<>() {
            @Override
            public String toString(Questionnaire object) {
                return object.getThematiqueQuestionnaire();
            }

            @Override
            public Questionnaire fromString(String s) {
                return null;
            }
        });
    }

    public void show() {
        stage.show();
    }
}
