/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package freebooks;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author Manel MR
 */
public class Connexio {

    public static String consulta(String dades) {
        String resposta = "";
        try {
            // Generem el socket client de connexió al servidor
            Socket socket = new Socket("localhost", 9999);
            try (BufferedWriter escriptor = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                // Enviem les dades d'usuari i contrassenya al servidor
                escriptor.write(dades);
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
        return resposta;
    }
}
