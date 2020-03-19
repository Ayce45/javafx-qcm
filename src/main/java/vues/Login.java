package vues;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


public class Login {
    private Controleur monControleur;

    @FXML
    private TextField pseudo;

    @FXML
    private PasswordField password;


    public static Login creerEtAfficher(Controleur c, Stage laStageUnique) {
        URL location = Login.class.getResource("/vues/login.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Login vue = fxmlLoader.getController();
        laStageUnique.setTitle("Connexion");
        laStageUnique.setScene(new Scene(root, 427,255 ));
        laStageUnique.show();
        vue.setMonControleur(c);
        return vue;
    }


    public void setMonControleur(Controleur monControleur) {
        this.monControleur = monControleur;
    }

    public void clickOk(ActionEvent actionEvent) {
        boolean condition = pseudo.getText() == null || pseudo.getText().length() == 0 || password.getText() == null || password.getText().length() == 0;
        if (condition) {
            Alert a = new Alert(Alert.AlertType.ERROR,"Les champs login/password sont obligatoires !", ButtonType.OK);
            a.show();
        }
        else {
            monControleur.tenteUnLogin(pseudo.getText(), password.getText());
        }
    }

    public void afficheMessageErreur(String message) {
        Alert a = new Alert(Alert.AlertType.ERROR,message, ButtonType.OK);
        a.show();
    }

    public void quitterApplication(ActionEvent actionEvent) {
        monControleur.quitterApplication();
    }
}
