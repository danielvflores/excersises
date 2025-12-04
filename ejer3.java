import java.io.*;
import java.util.*;

/**
 * Ejercicio 3: Contar consonantes y vocales
 * Escribe un programa que solicite al usuario que ingrese un nombre del archivo de texto 
 * y muestre el número de vocales y consonantes en el archivo.
 * Usar un conjunto para almacenar las vocales A, E, I, O y U.
 */
public class ejer3 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        // Conjunto de vocales (incluye acentos)
        Set<Character> vocales = new HashSet<>();
        vocales.add('a'); vocales.add('e'); vocales.add('i'); vocales.add('o'); vocales.add('u');
        vocales.add('á'); vocales.add('é'); vocales.add('í'); vocales.add('ó'); vocales.add('ú');
        vocales.add('ü'); vocales.add('A'); vocales.add('E'); vocales.add('I'); vocales.add('O'); 
        vocales.add('U'); vocales.add('Á'); vocales.add('É'); vocales.add('Í'); vocales.add('Ó'); 
        vocales.add('Ú'); vocales.add('Ü');
        
        // Mapas para contar cada vocal y consonante específica
        Map<Character, Integer> contadorVocales = new HashMap<>();
        Map<Character, Integer> contadorConsonantes = new HashMap<>();
        
        System.out.print("Ingrese el nombre del archivo de texto: ");
        String nombreArchivo = input.nextLine();
        
        try {
            File archivo = new File(nombreArchivo);
            Scanner fileScanner = new Scanner(archivo);
            
            int totalVocales = 0;
            int totalConsonantes = 0;
            int totalCaracteres = 0;
            
            while (fileScanner.hasNextLine()) {
                String linea = fileScanner.nextLine();
                
                for (char c : linea.toCharArray()) {
                    if (Character.isLetter(c)) {
                        totalCaracteres++;
                        char cLower = Character.toLowerCase(c);
                        
                        if (vocales.contains(c)) {
                            totalVocales++;
                            contadorVocales.put(cLower, contadorVocales.getOrDefault(cLower, 0) + 1);
                        } else {
                            totalConsonantes++;
                            contadorConsonantes.put(cLower, contadorConsonantes.getOrDefault(cLower, 0) + 1);
                        }
                    }
                }
            }
            
            fileScanner.close();
            
            // Mostrar resultados
            System.out.println("\n=== ANÁLISIS DE VOCALES Y CONSONANTES ===");
            System.out.println("Archivo analizado: " + nombreArchivo);
            System.out.println("Total de caracteres alfabéticos: " + totalCaracteres);
            System.out.println("Total de vocales: " + totalVocales);
            System.out.println("Total de consonantes: " + totalConsonantes);
            
            if (totalCaracteres > 0) {
                double porcentajeVocales = (totalVocales * 100.0) / totalCaracteres;
                double porcentajeConsonantes = (totalConsonantes * 100.0) / totalCaracteres;
                System.out.printf("Porcentaje de vocales: %.2f%%\n", porcentajeVocales);
                System.out.printf("Porcentaje de consonantes: %.2f%%\n", porcentajeConsonantes);
            }
            
            // Detalle de vocales
            System.out.println("\n--- DETALLE DE VOCALES ---");
            Map<Character, Integer> vocalesOrdenadas = new TreeMap<>(contadorVocales);
            for (Map.Entry<Character, Integer> entrada : vocalesOrdenadas.entrySet()) {
                System.out.println(entrada.getKey().toString().toUpperCase() + ": " + entrada.getValue());
            }
            
            // Detalle de consonantes (mostrar solo las 10 más frecuentes)
            System.out.println("\n--- CONSONANTES MÁS FRECUENTES ---");
            List<Map.Entry<Character, Integer>> consonantesLista = new ArrayList<>(contadorConsonantes.entrySet());
            consonantesLista.sort((a, b) -> b.getValue().compareTo(a.getValue()));
            
            int limite = Math.min(10, consonantesLista.size());
            for (int i = 0; i < limite; i++) {
                Map.Entry<Character, Integer> entrada = consonantesLista.get(i);
                System.out.println(entrada.getKey().toString().toUpperCase() + ": " + entrada.getValue());
            }
            
            if (consonantesLista.size() > 10) {
                System.out.println("... y " + (consonantesLista.size() - 10) + " consonantes más");
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("Error: No se pudo encontrar el archivo '" + nombreArchivo + "'");
            System.out.println("Asegúrate de que el archivo existe y el nombre está correcto.");
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
        
        input.close();
    }
}