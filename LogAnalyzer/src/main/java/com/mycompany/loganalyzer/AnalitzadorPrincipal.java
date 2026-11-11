/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loganalyzer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
            ProcessBuilder pbError = new ProcessBuilder(
                    "java", 
                    "-cp", 
                    classPath, 
                    "com.mycompany.loganalyzer.FiltreLog", 
                    "ERROR"
            );
            ProcessBuilder pbWarning = new ProcessBuilder(
                    "java", 
                    "-cp", 
                    classPath, 
                    "com.mycompany.loganalyzer.FiltreLog", 
                    "WARNING"
            );
            
            // Redirigir la sortida d'errors (stderr) cap a un fitxer físic
            File logErrorFile = new File("errors_filtre.log");
            pbError.redirectError(logErrorFile);
            pbWarning.redirectError(logErrorFile);
            
            //Iniciar ambdós processos de manera concurrent
            Process procesError = pbError.start();
            Process procesWarning = pbWarning.start();

            String textoPrueba = "2026-09-30 INFO Inicio de app\n" +
                                   "2026-09-30 ERROR Fallo en base de datos\n" +
                                   "2026-09-30 WARNING Alerta de memoria\n" +
                                   "2026-09-30 ERROR Timeout en red\n" +
                                   "2026-09-30 WARNING Espacio disco bajo\n" +
                                   "2026-09-30 ERROR Error adicional critico\n" +
                                   "2026-09-30 WARNING Alerta adicional\n";
            
//Envia un text de prova de múltiples línies al fill a través del seu OutputStream
            BufferedWriter outError = new BufferedWriter(
                    new OutputStreamWriter(procesError.getOutputStream())
            );
            outError.write(textoPrueba);
            //Executa out.flush() i out.close() per indicar la fi de les dades
            outError.flush();
            outError.close();
            
            BufferedWriter outWarning = new BufferedWriter(
                    new OutputStreamWriter(procesWarning.getOutputStream())
            );
            outWarning.write(textoPrueba);
            outWarning.flush();
            outWarning.close();

            //Llegeix la resposta del fill pel seu InputStream, la mostra per pantalla i espera la finalització amb p.waitFor()
            BufferedReader inError = new BufferedReader(
                    new InputStreamReader(procesError.getInputStream())
            );
            BufferedReader inWarning = new BufferedReader(
                    new InputStreamReader(procesWarning.getInputStream())
            );
            
            String resErrorStr = inError.readLine();
            String resWarningStr = inWarning.readLine();

            // Esperar a que el hijo finalice
            procesError.waitFor();
            procesWarning.waitFor();
            
            int numErrors = 0;
            if (resErrorStr != null && !resErrorStr.isEmpty()) {
                numErrors = Integer.parseInt(resErrorStr.trim());
            }
            
            int numWarnings = 0;
            if (resWarningStr != null && !resWarningStr.isEmpty()) {
                numWarnings = Integer.parseInt(resWarningStr.trim());
            }
            
            int finalExitCode;
            if (procesError.exitValue() == 0 && procesWarning.exitValue() == 0){
                finalExitCode = 0;
            } else {
                finalExitCode = 1;
            }
            
            System.out.println(
                    "RESULTAT: ERRORS=" + numErrors + 
                            " | WARNINGS=" + numWarnings + 
                            " | EXIT_CODE=" + finalExitCode
            );
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
