/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantallalogin;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Acer
 */
public class PantallaPrincipalController {

    @FXML
    private AnchorPane paneMain;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        String botoPremut = ((Control) event.getSource()).getId();
        switch (botoPremut) {
            case "logout":
                AnchorPane paneLogin = FXMLLoader.load(getClass().getResource("PantallaLogin.fxml"));
                paneMain.getChildren().setAll(paneLogin);
                break;
        }
    }
}
