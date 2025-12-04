/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proydesencriptarhydra;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author Asus Zenbook
 */
public class ProyDesencriptarHydra {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        Map<String, Character> tDesencriptar = crearTablaDesencriptar();
        Map<Character, String> tEncriptar = crearTablaEncriptar(tDesencriptar);
                
        encriptar(tEncriptar);
        desencriptar(tDesencriptar);
    }
    
    public static void desencriptar(Map<String, Character> tDesencriptar) throws FileNotFoundException{
        File entrada1 = new File("encriptado.txt");
        Scanner input = new Scanner(entrada1);
        
        File salida1 = new File("textollano.txt");
        PrintWriter output = new PrintWriter(salida1);
        
        while( input.hasNextLine()){
            
            String linea = input.nextLine();
            System.out.println(linea);
            int i = 0;
            while( i< linea.length()){
                StringBuilder key = new StringBuilder();
                key.append(linea.charAt(i));
                key.append(linea.charAt(++i));
                key.append(linea.charAt(++i));
                key.append(linea.charAt(++i));
                key.append(linea.charAt(++i));
                i++;
                char c = tDesencriptar.get(key.toString());
                System.out.println(c+" "+key.toString());
                output.print(c);
            }
        }
        input.close();
        output.close();
        
    }
    public static void encriptar( Map<Character, String> tEncriptar) throws FileNotFoundException{
    
        
        File entrada = new File("datos.txt");
        Scanner input = new Scanner(entrada);
        
        File salida = new File("encriptado.txt");
        PrintWriter output = new PrintWriter(salida);
        
        while( input.hasNextLine()){
            String linea = input.nextLine();
            for(int i = 0; i< linea.length(); i++){
                String encrip = tEncriptar.get((char)linea.charAt(i));
                output.print(encrip);
            }
        }
        input.close();
        output.close();
    }
    public static Map<String, Character> crearTablaDesencriptar(){
            Map<String, Character> tc = new HashMap<>();
            char[] h = new char[5];
            int num, complemento;
            for(int i = 32; i < 127; i++){ 
                num = i*37;
                if( num <= 78)
                    complemento = 79 + i - 32;
                else 
                    complemento = 32 + i - 79;
                
                int d0 = num % 10;
                int d1 = (num/10) % 10;
                int d2 = ( num/100)% 10;
                int d3 = (num / 1000);
                h[0] = (char)(d3*d3 + complemento);
                h[1] = (char)(d2*d2 + complemento);
                h[2] = (char)(d1*d1 + complemento);
                h[3] = (char)(d0*d0 + complemento);
                h[4] = (char)complemento;
                String hydra = new String(h);
                tc.put(hydra,(char)i);
            }
        return tc;
    }
    public static Map<Character, String> crearTablaEncriptar(Map<String, Character> tc){
        Set<Entry<String, Character>> entrys = tc.entrySet();
        Map<Character, String> td = new HashMap<>();
        
        for( Entry<String, Character> entry: entrys)
            td.put(entry.getValue(), entry.getKey());

        return td;
    }
}
