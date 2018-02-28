/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import persistence.GestorPersistencia;
import persistence.Login;
import persistence.UtilitatPersistenciaException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author ferranb
 */
public class freeBooksServer {
    protected Socket socket;
    private String codiSessio = "";
    
    
    
    public void setSocket(Socket socket){
        this.socket = socket;
    }
    
    public void executa(){
        try{
            BufferedWriter escriptor;
            //Llegeix el missatge del client i l'analitza
            try(BufferedReader lector = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
                String request = lector.readLine();
                
                escriptor = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream()));
                String resposta = "";
                switch(request.split("-")[0]){
                    case "userLogin":
                        resposta = userLogin(request);
                        if (!resposta.equals("FAIL")){
                            codiSessio = resposta;
                        }
                }
                escriptor.write(resposta);
                escriptor.newLine();
                escriptor.flush();                
            }
            
            //escriptor.close();
            
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    
    private String userLogin(String dades) throws UtilitatPersistenciaException{
        String result = "";
        GestorPersistencia gp = new GestorPersistencia("PantallaLoginPU");
        gp.obrir();
        List<Login> llista = gp.findUsersByNameAndPass(dades);
        gp.tancar();
        result = llista.isEmpty()?"FAIL":"OK"+dades.split("-")[1]+new java.util.Date().toString();
        return result;
    }
    
    public static void main(String[]args) throws Exception{
        int port = 9999;
        System.out.println("El server de FreeBooks està en execució");
        ServerSocket serverSocket = new ServerSocket(port);
        freeBooksServer fbs = new freeBooksServer();
        while(true){
            Socket socket = serverSocket.accept();
            fbs.setSocket(socket);
            fbs.executa();
        }
        
        //serverSocket.close();
    }
}