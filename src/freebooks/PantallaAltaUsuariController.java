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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Manel MR
 */
public class PantallaAltaUsuariController {

    @FXML
    private AnchorPane paneNew;
    @FXML
    private TextField user, pass, passRepeat, email;
    @FXML
    private Label info;

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
            case "reg":
                // En el cas de Registra't, comprovem que no hi ha camps buits
                if (user.getText().isEmpty() || pass.getText().isEmpty()) {
                    info.setText("Falten dades");
                    // També comprovem que s'ha escrit la mateixa contraseña 2 cops
                } else if (!pass.getText().equals(passRepeat.getText())) {
                    info.setText("Contrasenya diferent");
                } else {
                    info.setText("");
                    // Creem el nou usuari
                    String nouUser = "nouLogin-" + user.getText() + "-" + pass.getText();
                    String resposta = Connexio.consulta(nouUser);
                    // Si falla l'alta del nou usuari, mostrem avís d'error
                    if (resposta.equals("FAIL")) {
                        new Alert(AlertType.ERROR, "L'usuari ja existeix").showAndWait();
                        // Si es crea correctament el nou usuari, mostrem avís de confirmació
                    } else if (resposta.equals("OK")) {
                        new Alert(AlertType.INFORMATION, "Usuari creat amb èxit").showAndWait();
                        paneLogin = FXMLLoader.load(getClass().getResource("PantallaLogin.fxml"));
                        paneNew.getChildren().setAll(paneLogin);
                        // Si el servidor no respon, també avisem
                    } else {
                        new Alert(AlertType.WARNING, "Servidor no respon").showAndWait();
                    }
                }
        }
    }
}
