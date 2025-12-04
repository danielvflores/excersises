import java.io.*;
import java.util.*;

/**
 * Ejercicio 2: Contar palabras clave en el código fuente de Java
 * Si una palabra clave está en un comentario o en una cadena, no la cuente.
 * Suponga que el código fuente de Java es correcto (sin comentarios de párrafo no superpuestos).
 */
public class ejer2 {
    public static void main(String[] args) {
        // Conjunto de palabras clave de Java
        Set<String> palabrasClave = new HashSet<>();
        String[] claves = {
            "abstract", "continue", "for", "new", "switch", "assert", "default", 
            "goto", "package", "synchronized", "boolean", "do", "if", "private", 
            "this", "break", "double", "implements", "protected", "throw", "byte", 
            "else", "import", "public", "throws", "case", "enum", "instanceof", 
            "return", "transient", "catch", "extends", "int", "short", "try", 
            "char", "final", "interface", "static", "void", "class", "finally", 
            "long", "volatile", "const", "float", "native", "super", "while"
        };
        
        for (String clave : claves) {
            palabrasClave.add(clave);
        }
        
        // Mapa para contar frecuencias
        Map<String, Integer> contadorPalabrasClave = new HashMap<>();
        
        try {
            File archivo = new File("codigo.txt");
            Scanner scanner = new Scanner(archivo);
            
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine().trim();
                
                // Procesar la línea eliminando comentarios y cadenas
                String lineaLimpia = eliminarComentariosYCadenas(linea);
                
                if (!lineaLimpia.isEmpty()) {
                    // Extraer palabras válidas (solo letras)
                    String[] palabras = lineaLimpia.split("[^a-zA-Z]+");
                    
                    for (String palabra : palabras) {
                        if (!palabra.isEmpty() && palabrasClave.contains(palabra)) {
                            contadorPalabrasClave.put(palabra, 
                                contadorPalabrasClave.getOrDefault(palabra, 0) + 1);
                        }
                    }
                }
            }
            
            scanner.close();
            
            // Mostrar resultados ordenados alfabéticamente
            System.out.println("=== FRECUENCIA DE PALABRAS CLAVE DE JAVA ===");
            Map<String, Integer> resultadoOrdenado = new TreeMap<>(contadorPalabrasClave);
            
            if (resultadoOrdenado.isEmpty()) {
                System.out.println("No se encontraron palabras clave en el código.");
            } else {
                for (Map.Entry<String, Integer> entrada : resultadoOrdenado.entrySet()) {
                    System.out.println(entrada.getKey() + ": " + entrada.getValue());
                }
                
                int totalOcurrencias = resultadoOrdenado.values().stream()
                    .mapToInt(Integer::intValue).sum();
                System.out.println("\nTotal de ocurrencias: " + totalOcurrencias);
                System.out.println("Palabras clave diferentes encontradas: " + resultadoOrdenado.size());
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("Error: No se pudo encontrar el archivo 'codigo.txt'");
            System.out.println("Asegúrate de que el archivo existe en el directorio actual.");
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }
    
    /**
     * Elimina comentarios de línea (//) y cadenas de caracteres ("texto") de una línea de código
     * @param linea La línea de código a procesar
     * @return La línea sin comentarios ni cadenas
     */
    private static String eliminarComentariosYCadenas(String linea) {
        StringBuilder resultado = new StringBuilder();
        boolean enCadena = false;
        boolean enCaracter = false;
        
        for (int i = 0; i < linea.length(); i++) {
            char c = linea.charAt(i);
            char siguiente = (i + 1 < linea.length()) ? linea.charAt(i + 1) : '\0';
            
            if (!enCadena && !enCaracter) {
                // Detectar inicio de comentario de línea
                if (c == '/' && siguiente == '/') {
                    break; // Resto de la línea es comentario
                }
                // Detectar inicio de cadena
                else if (c == '"') {
                    enCadena = true;
                }
                // Detectar inicio de carácter literal
                else if (c == '\'') {
                    enCaracter = true;
                }
                else {
                    resultado.append(c);
                }
            }
            else if (enCadena) {
                // Detectar final de cadena (considerando escape)
                if (c == '"' && (i == 0 || linea.charAt(i-1) != '\\')) {
                    enCadena = false;
                }
            }
            else if (enCaracter) {
                // Detectar final de carácter literal (considerando escape)
                if (c == '\'' && (i == 0 || linea.charAt(i-1) != '\\')) {
                    enCaracter = false;
                }
            }
        }
        
        return resultado.toString();
    }
}