package Frontend;

import Backend.Kitten;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONObject;

import java.util.List;

import static Backend.HttpStuff.doGet;

public class AppPage {


    @FXML
    private ImageView image;
    @FXML
    private Label name;
    @FXML
    private Label votes;
    @FXML
    private Button prevKitten;
    @FXML
    private Button nextKitten;
    @FXML
    private Button prevPage;
    @FXML
    private Button nextPage;


    private JSONObject JsonObject;
    private List<Kitten> kittens;
    private static int counter = 0;
    private static int pageNumber = 1;


    public void pullObject(JSONObject obj) throws Exception {
        this.JsonObject = obj;

        kittens = doGet(JsonObject, pageNumber); //pulling an object
        name.setText(kittens.get(counter).getName()); //setting name
        votes.setText(String.valueOf(kittens.get(counter).getVotes())); //setting votes value
        Image tempImage = new Image(kittens.get(counter).getURL()); //setting an image
        image.setImage(tempImage);
        counter++;

        //setting visibility
        prevKitten.setDisable(true);
        prevPage.setDisable(true);
    }

    public void nextKitten(ActionEvent event) {
        counter++;
        try {
            name.setText(kittens.get(counter).getName());
            votes.setText(String.valueOf(kittens.get(counter).getVotes()));
            Image tempImage = new Image(kittens.get(counter).getURL());
            image.setImage(tempImage);
            if (counter + 1 == kittens.size()) { //if counter reaches 25 kittens per page
                nextKitten.setDisable(true);
            }
        } catch (Exception e) {

        }

        prevKitten.setDisable(false);

    }

    public void previousKitten(ActionEvent event) {
        counter--;
        try {
            name.setText(kittens.get(counter).getName());
            votes.setText(String.valueOf(kittens.get(counter).getVotes()));
            Image tempImage = new Image(kittens.get(counter).getURL());
            image.setImage(tempImage);
            nextKitten.setDisable(false);
            if (counter == 0) {
                prevKitten.setDisable(true);
            }
        } catch (Exception e) {
            prevKitten.setDisable(true);

        }
    }

    public void nextPage(ActionEvent event) {
        prevPage.setDisable(false);
        pageNumber++;
        counter = 0;
        try {
            kittens = doGet(JsonObject, pageNumber);
            name.setText(kittens.get(counter).getName());
            votes.setText(String.valueOf(kittens.get(counter).getVotes()));
            Image tempImage = new Image(kittens.get(counter).getURL());
            image.setImage(tempImage);
            nextKitten.setDisable(false);
            prevKitten.setDisable(true);
        } catch (Exception e) {
            nextPage.setDisable(true);
            nextKitten.setDisable(false);
            prevKitten.setDisable(true);
        }


    }


    public void previousPage(ActionEvent event) {
        pageNumber--;
        counter = 0;
        try {
            kittens = doGet(JsonObject, pageNumber);
            name.setText(kittens.get(counter).getName());
            votes.setText(String.valueOf(kittens.get(counter).getVotes()));
            Image tempImage = new Image(kittens.get(counter).getURL());
            image.setImage(tempImage);
            nextPage.setDisable(false);
            prevKitten.setDisable(true);
            if (pageNumber == 1) {
                prevPage.setDisable(true);
            }
        } catch (Exception e) {
        }

    }
}
