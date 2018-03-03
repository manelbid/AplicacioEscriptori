/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package freebooks;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Acer
 */
public class PantallaLoginController implements Initializable {

    private String codiSessio = "";

    public String getCodiSessio() {
        return codiSessio;
    }

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
                    final String codiRequest = "userLogin-" + user.getText() + "-" + pass.getText();

                    try {
                        // Generem el socket client de connexió al servidor
                        Socket socket = new Socket("localhost", 9999);
                        try (BufferedWriter escriptor = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                            // Enviem les dades d'usuari i contrassenya al servidor
                            escriptor.write(codiRequest);
                            escriptor.newLine();
                            escriptor.flush();
                            // Obtenim la resposta del servidor
                            try (BufferedReader lector = new BufferedReader(
                                    new InputStreamReader(socket.getInputStream()))) {
                                codiSessio = lector.readLine();
                            }

                        }
                        socket.close();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    // Si les dades no són vàlides, mostra missatge d'error
                    if (codiSessio.equals("FAIL")) {
                        info.setText("Dades incorrectes");
                    } else {
                        // Si l'usuari es verifica correctament, mostra la pantalla principal
                        info.setText("");
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
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
