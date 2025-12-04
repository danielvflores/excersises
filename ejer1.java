import java.io.*;
import java.util.*;

/**
 * Ejercicio 1: Mostrar palabras no duplicadas en orden ascendente
 * Escribe un programa que lea palabras de un archivo de texto y muestra 
 * todas las palabras no duplicadas en orden ascendente.
 */
public class ejer1 {
    public static void main(String[] args) {
        try {
            // Usar TreeSet para automáticamente ordenar y eliminar duplicados
            Set<String> palabrasUnicas = new TreeSet<>();
            
            // Leer archivo de texto
            File archivo = new File("datos.txt");
            Scanner scanner = new Scanner(archivo);
            
            // Procesar cada línea del archivo
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                // Dividir línea en palabras y convertir a minúsculas
                String[] palabras = linea.toLowerCase().split("\\s+");
                
                for (String palabra : palabras) {
                    // Limpiar la palabra de signos de puntuación
                    palabra = palabra.replaceAll("[^a-záéíóúñü]", "");
                    if (!palabra.isEmpty()) {
                        palabrasUnicas.add(palabra);
                    }
                }
            }
            
            scanner.close();
            
            // Mostrar palabras no duplicadas en orden ascendente
            System.out.println("=== PALABRAS NO DUPLICADAS EN ORDEN ASCENDENTE ===");
            int contador = 1;
            for (String palabra : palabrasUnicas) {
                System.out.println(contador + ". " + palabra);
                contador++;
            }
            
            System.out.println("\nTotal de palabras únicas: " + palabrasUnicas.size());
            
        } catch (FileNotFoundException e) {
            System.out.println("Error: No se pudo encontrar el archivo 'datos.txt'");
            System.out.println("Asegúrate de que el archivo existe en el directorio actual.");
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }
}