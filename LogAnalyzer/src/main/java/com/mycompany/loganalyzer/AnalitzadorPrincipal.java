/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loganalyzer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Classe que actua com a procés pare en el sistema LogAnalyzer
 * 
 * S'encarrega d'instanciar el procés fill (FiltreLog) mitjançant ProcessBuilder,
 * enviar-li dades de text a través del flux de sortida, llegir-ne el resultat 
 * processat i gestionar el cicle de vida del subprocep

 * @author Ekaterina Morozova
 */
public class AnalitzadorPrincipal {
    public static void main(String[] args) {
        try{
            //Instancia el procés fill executant la classe FiltreLog mitjançant ProcessBuilder
            String classPath = System.getProperty("java.class.path");
            ProcessBuilder pb = new ProcessBuilder(
                    "java", 
                    "-cp", 
                    classPath, 
                    "com.mycompany.loganalyzer.FiltreLog"
            );
            Process filtreLog = pb.start();

            //Envia un text de prova de múltiples línies al fill a través del seu OutputStream
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(filtreLog.getOutputStream())
            );

            String textoPrueba = "2026-09-30 INFO Inicio de app\n" +
                                 "2026-09-30 ERROR Fallo en base de datos\n" +
                                 "2026-09-30 ERROR Timeout en red\n";

            out.write(textoPrueba);

            //Executa out.flush() i out.close() per indicar la fi de les dades
            out.flush();
            out.close();

            //Llegeix la resposta del fill pel seu InputStream, la mostra per pantalla i espera la finalització amb p.waitFor()
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(filtreLog.getInputStream())
            );
            String resultado = in.readLine();

            // Esperar a que el hijo finalice
            int exitCode = filtreLog.waitFor();
          
            int numErrors = 0;
            if (resultado != null && !resultado.isEmpty()) {
                numErrors = Integer.parseInt(resultado.trim());
            }
            System.out.println(
                    "RESULTAT: ERRORS=" + numErrors + 
                            " | WARNINGS=0 | EXIT_CODE=" + exitCode
            );
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
