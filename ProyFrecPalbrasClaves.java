/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyfrecpalbrasclaves;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Asus Zenbook
 */
public class ProyFrecPalbrasClaves {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        // Cargar palabras claves a una tabla de hash
        String[] claves = {"abstract", "continue", "for", "new", "switch",
            "assert", "default", "goto", "package", "synchronized", "boolean",
            "do", "if", "elseif", "private", "this", "break", "double", 
            "implements", "protected", "throw", "byte", "else", "import",
            "public", "throws", "case", "enum", "instanceof", "return",
            "transient", "catch", "extends", "int", "short", "try", "char",
            "final", "interface", "static", "void", "class", "finally",
            "long", "volatile", "const", "float", "native", "super", "while"};
        
        HashSet<String> palabrasClaves = new HashSet<>();
        
        Map<String, Integer> tFPC = new HashMap<String, Integer>();
        
        
        for(String clave: claves)
            palabrasClaves.add(clave.toLowerCase());
        
        File entrada = new File("codigo.txt");
        Scanner input = new Scanner(entrada);
        
        while(input.hasNext()){
            String linea = input.nextLine();
            
            String lineaLimpia = limpiarLinea(linea);
            if(linea != null){
            String[] words = lineaLimpia.split(" ");
            
            for(String word: words){
                String palabra = word.toLowerCase();
                if( palabrasClaves.contains(palabra))
                    if( !tFPC.containsKey(palabra.toLowerCase()))
                        tFPC.put(palabra, 1);
                    else
                        tFPC.put(palabra, tFPC.get(palabra) + 1);
            }
            }
        }
        input.close();
        imprimir(tFPC);
    }
    public static String limpiarLinea(String linea){
       StringBuilder str = new StringBuilder(); 
       int i = 0;
       int n = linea.length()-1;

       while( i < n ){
           if( Character.isLetter(linea.charAt(i)))
               str.append(linea.charAt(i));
           else if( linea.charAt(i) == ' ')
               str.append(linea.charAt(i));
           i++;
       }
       StringBuilder salida = new StringBuilder();
       for(int j = 0; j < str.length(); j++){
           if(str.charAt(j) == ' '){
               salida.append(str.charAt(j));
               while(i == ' ')
                   j++;
           }
           else
               salida.append(str.charAt(j));
       }
    return salida.toString();
    }
    public static void imprimir(Map<String, Integer> tfc){

        Map<String, Integer> tf = new TreeMap<>(tfc);

        for(Entry<String, Integer> entry : tfc.entrySet())
            System.out.println(entry.getKey() +" " + entry.getValue());
    } 
}
