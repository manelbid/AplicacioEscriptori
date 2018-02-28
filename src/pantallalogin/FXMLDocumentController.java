/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantallalogin;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Acer
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label info;
    @FXML
    private TextField user;
    @FXML
    private TextField pass;

    ArrayList<String> al = new ArrayList<String>();

    @FXML
    private void handleButtonAction(ActionEvent event) {
        String botoPremut = ((Control) event.getSource()).getId();
        if (user.getText().isEmpty() || pass.getText().isEmpty()) {
            info.setText("Falten dades");
        } else {
            info.setText("");
            switch (botoPremut) {
                case "login":
                    System.out.println(user.getText() + "\n" + pass.getText());
                    break;
                case "new":
                    System.out.println(user.getText() + "\n" + pass.getText());
                    break;
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
