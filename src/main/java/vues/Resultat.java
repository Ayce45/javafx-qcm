package vues;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Resultat {
    private static Stage stage;
    private Controleur monControleur;
    @FXML
    public Label pourcent;

    public static Resultat creer(Stage laStageUnique) {
        URL location = Resultat.class.getResource("/vues/resultat.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Resultat vue = fxmlLoader.getController();
        laStageUnique.setTitle("Resultat");
        laStageUnique.setScene(new Scene(root, 427, 255));
        stage = laStageUnique;
        return vue;
    }

    public void goToMenu(ActionEvent actionEvent) {
        monControleur.goToMenu();
    }

    public void setMonControleur(Controleur monControleur) {
        this.monControleur = monControleur;
    }

    public void quitterApplication(ActionEvent actionEvent) {
        monControleur.quitterApplication();
    }

    public void show() {
        stage.show();
    }

    public void chargerResultat() {
        pourcent.setText("Vous avez " + monControleur.getScore() + " % de bonnes réponses");
    }
}
