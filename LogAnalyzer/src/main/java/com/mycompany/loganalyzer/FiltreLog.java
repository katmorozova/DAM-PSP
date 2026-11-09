/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.loganalyzer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Classe que actua com a procés fill en el sistema FiltreLog

Llegeix línies de text des de l'entrada estàndard (System.in), cerca una 
paraula clau ('ERROR' per defecte) i retorna el recompte total 
a través de la seva sortida estàndard (System.out)

 * @author Ekaterina Morozova
 */
public class FiltreLog {
    public static void main(String[] args) {
        
        try{
            //Rep un text línia per línia a través de la seva entrada estàndard
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in)
            );
            
            String linia;
            int comptador = 0;
            
            //Per defecte, compta quantes vegades apareix la paraula clau
            while ((linia = reader.readLine()) != null) {
                if (linia.contains("ERROR")) {
                    comptador++;
                }
            }
            
            //Envia el resultat numèric final com una única línia a la seva sortida estàndard
            System.out.println(comptador);
            System.exit(0);
            
        }catch(Exception e){
            System.err.println("Error en el fil");
            System.exit(1);
        }
    }
}
