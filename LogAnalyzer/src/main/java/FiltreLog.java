/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */



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
        
        // Determinar la paraula clau a cercar (per defecte "ERROR")
        String paraulaClau = (args.length > 0) ? args[0] : "ERROR";
        
        try{
            //Rep un text línia per línia a través de la seva entrada estàndard
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in)
            );
            
            String linia;
            int comptador = 0;
            boolean teDades = false;
            
            //Per defecte, compta quantes vegades apareix la paraula clau
            while ((linia = reader.readLine()) != null) {
                teDades = true;
                if (linia.contains(paraulaClau)) {
                    comptador++;
                }
            }
            
            // Validació si no ha rebut dades (text buit o nul)
            if (!teDades) {
                System.err.println("Error: Text buit");
                System.exit(1);
            }
            
            //Envia el resultat numèric final com una única línia a la seva sortida estàndard
            System.out.println(comptador);
            System.exit(0);
            
        }catch(Exception e){
            System.err.println("Error en el fil: " + e.getMessage());
            System.exit(1);
        }
    }
}
