/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantallalogin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Acer
 */
public class PantallaLoginController implements Initializable {

    //codi per identificar la sessió
    private String codiSessio = "";
    @FXML
    private AnchorPane paneLogin;
    @FXML
    private Label info;
    @FXML
    private TextField user;
    @FXML
    private TextField pass;

    //ArrayList<String> al = new ArrayList<String>();
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        String botoPremut = ((Control) event.getSource()).getId();
        if (user.getText().isEmpty() || pass.getText().isEmpty()) {
            info.setText("Falten dades");
        } else {
            info.setText("");
            switch (botoPremut) {
                case "login":
                    //System.out.println(user.getText() + "\n" + pass.getText());
                    String codiRequest = "userLogin-"+user.getText() +"-"+pass.getText();
                    try{
                        Socket socket = new Socket("localhost", 9999);
                        try(BufferedWriter escriptor = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))){
                            escriptor.write(codiRequest);
                            escriptor.newLine();
                            escriptor.flush();
                            //Obté el resultat del server
                            try (BufferedReader lector = new BufferedReader(
                                    new InputStreamReader(socket.getInputStream()))) {
                                codiSessio = lector.readLine();
                                if(!codiSessio.equals("FAIL")){
                                    //JOptionPane.showMessageDialog(this, "Username Or Password Are Valid");
                                    System.out.println("Username Or Password Are Valid");   
                                }else {
                                    //JOptionPane.showMessageDialog(this, "Username Or Password Are Invalid");
                                    System.out.println("Username Or Password Are Invalid"); 
                                    codiSessio = "";
                                }
                            }

                        }
                        socket.close();
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    
                    break;
                case "new":
                    /*System.out.println(user.getText() + "\n" + pass.getText());
                    Parent parent = FXMLLoader.load(getClass().getResource("PantallaPrincipal.fxml"));
                    Scene scene = new Scene(parent);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();*/
                    AnchorPane paneMain = FXMLLoader.load(getClass().getResource("PantallaPrincipal.fxml"));
                    paneLogin.getChildren().setAll(paneMain);
                    break;
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}