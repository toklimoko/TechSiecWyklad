package Frontend;

import Backend.HttpStuff;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import org.json.JSONObject;

import java.io.IOException;

public class LogPage {

    @FXML
    private TextField login;
    @FXML
    private PasswordField password;
    @FXML
    private Label msg;
    @FXML
    private AnchorPane pane;

    public void logIn(ActionEvent event) throws Exception {

        String mail = login.getText();
        String pass = password.getText();

        JSONObject response = null;

        try {
            response = HttpStuff.doPost(mail, pass);
        } catch (IOException e) {
            System.out.println("Zły login lub hasło, spróbuj ponownie");
        }

        if (response != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/FXMLs/AppPage.fxml"));
            loader.load();
            AppPage appPage = loader.getController();
            appPage.pullObject(response);
            Parent parent = loader.getRoot();
            pane.getChildren().setAll(parent);


        } else {
            msg.setText("Zły login lub hasło!");
        }
    }
}
