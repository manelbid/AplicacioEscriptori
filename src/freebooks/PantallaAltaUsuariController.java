/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package freebooks;

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
public class PantallaAltaUsuariController {
    @FXML
    private AnchorPane paneNew;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        // Controlem quin botó s'ha premut
        String botoPremut = ((Control) event.getSource()).getId();
        switch (botoPremut) {
            case "cancel":
                // En el cas de Cancel·la, tornem a la pantalla de login
                AnchorPane paneLogin = FXMLLoader.load(getClass().getResource("PantallaLogin.fxml"));
                paneNew.getChildren().setAll(paneLogin);
                break;
        }
    }
}
