/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package freebooks;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Manel MR
 */
public class PantallaLoginController implements Initializable {

    @FXML
    private AnchorPane paneLogin;
    @FXML
    private Label info;
    @FXML
    private TextField user, pass;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        // Controlem quin botó s'ha premut
        String botoPremut = ((Control) event.getSource()).getId();
        switch (botoPremut) {
            case "login":
                // En el cas d'Inicia sessió, verifiquem que no hi ha cap camp de text buit
                if (user.getText().isEmpty() || pass.getText().isEmpty()) {
                    info.setText("Falten dades");
                } else {
                    // Obtenim l'usuari i contrassenya introduïts
                    String codiRequest = "userLogin-" + user.getText() + "-" + pass.getText() + "-Desktop";
                    // Executem la consulta
                    String codiSessio = Connexio.consulta(codiRequest);
                    // Si les dades no són vàlides, mostra missatge d'error
                    if (codiSessio.equals("FAIL")) {
                        info.setText("Dades incorrectes");
                    } // Si el servidor no respon, també avisem
                    else if (codiSessio.isEmpty()) {
                        info.setText("");
                        new Alert(Alert.AlertType.WARNING, "Servidor no respon").showAndWait();
                    } // Si l'usuari es verifica correctament, mostra la pantalla principal
                    else {
                        AnchorPane paneMain = FXMLLoader.load(getClass().getResource("PantallaPrincipal.fxml"));
                        paneLogin.getChildren().setAll(paneMain);
                    }
                }
                break;
            case "new":
                // En el cas de Nou usuari, mostra la pantalla d'alta d'usuari
                AnchorPane paneNew = FXMLLoader.load(getClass().getResource("PantallaAltaUsuari.fxml"));
                paneLogin.getChildren().setAll(paneNew);
                break;
            case "exit":
                System.exit(0);
                break;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
