/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proytasblafrecmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Asus Zenbook
 */
public class ProyTasblaFrecMaP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        String linea, palabra;
        String[] words;
        
        Map<String, Integer> tf = new HashMap<String, Integer>();

        // leer texto de un archivo y agregar las palabras een ABB
        File entrada = new File("datos.txt");
        Scanner input =  new Scanner(entrada);
        
        while(input.hasNextLine()){
            linea = input.nextLine();
            words = linea.split(" ");
            for(String word: words){
                palabra = word.toLowerCase();
                if( !tf.containsKey(palabra))
                    tf.put(palabra, 1);
                else
                    tf.put(palabra, tf.get(palabra) + 1);
            }
        }
        input.close();
        for(Entry e: tf.entrySet())
            System.out.println(e.getKey() + "  " + e.getValue());
    }
}                                               