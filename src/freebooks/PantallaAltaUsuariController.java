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
 * @author Acer
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
        //Recuperem el codi de sessió
        PantallaLoginController plc = new PantallaLoginController();
        String codiSessio = plc.getCodiSessio();
        switch (botoPremut) {
            case "cancel":
                // En el cas de Cancel·la, tornem a la pantalla de login
                AnchorPane paneLogin = FXMLLoader.load(getClass().getResource("PantallaLogin.fxml"));
                paneNew.getChildren().setAll(paneLogin);
                break;
            case "reg":
                if (user.getText().isEmpty() || pass.getText().isEmpty()) { //|| email.getText().isEmpty()) 
                    info.setText("Falten dades");
                } else if (!pass.getText().equals(passRepeat.getText())) {
                    info.setText("Contrasenya diferent");
                } else {
                    info.setText("");
                    String nouUser = "nouLogin-" + user.getText() + "-" + pass.getText();
                    String resposta = "";
                    try {
                        // Generem el socket client de connexió al servidor
                        Socket socket = new Socket("localhost", 9999);
                        try (BufferedWriter escriptor = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                            // Enviem les dades d'usuari i contrassenya al servidor
                            escriptor.write(nouUser);
                            escriptor.newLine();
                            escriptor.flush();
                            // Obtenim la resposta del servidor
                            try (BufferedReader lector = new BufferedReader(
                                    new InputStreamReader(socket.getInputStream()))) {
                                resposta = lector.readLine();
                            }
                        }
                        socket.close();

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    if (resposta.equals("FAIL")) {
                        new Alert(AlertType.ERROR, "Usuari no inserit a la BD").showAndWait();
                    } else if (resposta.equals("OK")) {
                        new Alert(AlertType.INFORMATION, "Usuari inserit amb èxit").showAndWait();
                        paneLogin = FXMLLoader.load(getClass().getResource("PantallaLogin.fxml"));
                        paneNew.getChildren().setAll(paneLogin);
                    } else {
                        new Alert(AlertType.WARNING, "Servidor no respon").showAndWait();
                    }
                }
        }
    }
}
