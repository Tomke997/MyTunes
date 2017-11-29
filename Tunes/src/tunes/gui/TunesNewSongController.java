/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunes.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tunes.be.Songs;

/**
 * FXML Controller class
 *
 * @author Pepe15224
 */
public class TunesNewSongController implements Initializable {

    @FXML
    private Button cancelButton;
    private TunesViewController parent;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtArtist;
    @FXML
    private TextField txtTime;
    @FXML
    private TextField txtFile;
    @FXML
    private Button saveButton;
    private TunesModel model = new TunesModel();
    @FXML
    private ComboBox<String> categoryBox;
    @FXML
    private Button chooseButton;
    private String filePath;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoryBox.getItems().addAll("Rock","Pop","Hip-Hop");
        categoryBox.getSelectionModel().selectFirst();
    }    

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
       stage.close();
    }
    public void setParent(TunesViewController parent)
    {
        this.parent=parent;
    }

    @FXML
    private void save(ActionEvent event) throws IOException {
        parent.addIceCream(new Songs(-1, txtTitle.getText(),
                txtArtist.getText(),categoryBox.getSelectionModel().getSelectedItem(),
                txtTime.getText(), txtFile.getText()));
    }

    @FXML
    private void chooseLocation(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("select mp3 or wav","*wav","*mp3");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        filePath = file.toString();
        
        txtFile.setText(filePath);
    }
}
