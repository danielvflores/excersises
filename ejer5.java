import java.io.*;
import java.util.*;

/**
 * Ejercicio 5: Algoritmo de descifrado Hydra
 * 
 * La codificación se realiza de la siguiente manera:
 * 1. Primero se convierte el carácter a su valor en la tabla ASCII y se le multiplica por 37
 * 2. Segundo se calcula un complemento en base al valor del carácter:
 *    - Si num <= 78: complemento = 79 + carácter - 32
 *    - Si num > 78: complemento = 32 + carácter - 79
 * 3. Luego a cada dígito obtenido en el punto uno se lo eleva al cuadrado y se le suma el
 *    complemento obtenido en el punto anterior y se transforma a carácter
 * 4. Por último se juntan los cuatro caracteres y se le agrega al final el carácter
 *    correspondiente al complemento
 * 
 * Ejemplo: R = 82, 82 * 37 = 3034, complemento = 32 + 82 - 79 = 35 = "#"
 * 3² + 35 = 44 = ",", 0² + 35 = 35 = "#", 3² + 35 = 44 = ",", 4² + 35 = 51 = "3"
 * El resultado final son estos cinco caracteres: ",#,3#"
 */
public class ejer5 {
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        // Crear las tablas de cifrado y descifrado
        Map<Character, String> tablaEncriptar = crearTablaEncriptar();
        Map<String, Character> tablaDesencriptar = crearTablaDesencriptar();
        
        while (true) {
            System.out.println("\n=== SISTEMA HYDRA DE CIFRADO/DESCIFRADO ===");
            System.out.println("1. Cifrar mensaje");
            System.out.println("2. Descifrar mensaje");
            System.out.println("3. Mostrar tabla de cifrado");
            System.out.println("4. Procesar archivo de cifrado");
            System.out.println("5. Procesar archivo de descifrado");
            System.out.println("6. Ejemplo detallado (carácter R)");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            
            int opcion = input.nextInt();
            input.nextLine(); // Consumir el salto de línea
            
            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el mensaje a cifrar: ");
                    String mensaje = input.nextLine();
                    String mensajeCifrado = cifrarMensaje(mensaje, tablaEncriptar);
                    System.out.println("Mensaje cifrado: " + mensajeCifrado);
                    break;
                    
                case 2:
                    System.out.print("Ingrese el mensaje cifrado: ");
                    String mensajeEncriptado = input.nextLine();
                    String mensajeDescifrado = descifrarMensaje(mensajeEncriptado, tablaDesencriptar);
                    System.out.println("Mensaje descifrado: " + mensajeDescifrado);
                    break;
                    
                case 3:
                    mostrarTablaCifrado(tablaEncriptar);
                    break;
                    
                case 4:
                    System.out.print("Archivo de entrada: ");
                    String archivoEntrada = input.nextLine();
                    procesarArchivo(archivoEntrada, "encriptado.txt", tablaEncriptar, true);
                    break;
                    
                case 5:
                    System.out.print("Archivo cifrado: ");
                    String archivoCifrado = input.nextLine();
                    procesarArchivo(archivoCifrado, "textollano.txt", tablaDesencriptar, false);
                    break;
                    
                case 6:
                    mostrarEjemploDetallado();
                    break;
                    
                case 7:
                    System.out.println("¡Hasta luego!");
                    input.close();
                    return;
                    
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }
    
    /**
     * Crea la tabla de descifrado según el algoritmo Hydra
     */
    public static Map<String, Character> crearTablaDesencriptar() {
        Map<String, Character> tabla = new HashMap<>();
        
        // Para cada carácter ASCII imprimible (32-126)
        for (int i = 32; i < 127; i++) {
            char caracter = (char) i;
            
            // Paso 1: Multiplicar ASCII por 37
            int num = i * 37;
            
            // Paso 2: Calcular complemento
            int complemento;
            if (num <= 78) {
                complemento = 79 + i - 32;
            } else {
                complemento = 32 + i - 79;
            }
            
            // Paso 3: Extraer dígitos y procesarlos
            int d0 = num % 10;
            int d1 = (num / 10) % 10;
            int d2 = (num / 100) % 10;
            int d3 = (num / 1000) % 10;
            
            // Paso 4: Crear los 5 caracteres del código Hydra
            char h0 = (char) (d3 * d3 + complemento);
            char h1 = (char) (d2 * d2 + complemento);
            char h2 = (char) (d1 * d1 + complemento);
            char h3 = (char) (d0 * d0 + complemento);
            char h4 = (char) complemento;
            
            String hydra = "" + h0 + h1 + h2 + h3 + h4;
            tabla.put(hydra, caracter);
        }
        
        return tabla;
    }
    
    /**
     * Crea la tabla de cifrado invirtiendo la tabla de descifrado
     */
    public static Map<Character, String> crearTablaEncriptar() {
        Map<String, Character> tablaDesc = crearTablaDesencriptar();
        Map<Character, String> tablaEnc = new HashMap<>();
        
        for (Map.Entry<String, Character> entrada : tablaDesc.entrySet()) {
            tablaEnc.put(entrada.getValue(), entrada.getKey());
        }
        
        return tablaEnc;
    }
    
    /**
     * Cifra un mensaje completo
     */
    public static String cifrarMensaje(String mensaje, Map<Character, String> tabla) {
        StringBuilder resultado = new StringBuilder();
        
        for (char c : mensaje.toCharArray()) {
            if (tabla.containsKey(c)) {
                resultado.append(tabla.get(c));
            } else {
                System.out.println("Advertencia: Carácter no soportado: " + c);
            }
        }
        
        return resultado.toString();
    }
    
    /**
     * Descifra un mensaje cifrado
     */
    public static String descifrarMensaje(String mensajeCifrado, Map<String, Character> tabla) {
        StringBuilder resultado = new StringBuilder();
        
        // Procesar de 5 en 5 caracteres
        for (int i = 0; i < mensajeCifrado.length(); i += 5) {
            if (i + 4 < mensajeCifrado.length()) {
                String codigo = mensajeCifrado.substring(i, i + 5);
                if (tabla.containsKey(codigo)) {
                    resultado.append(tabla.get(codigo));
                } else {
                    System.out.println("Advertencia: Código no reconocido: " + codigo);
                }
            }
        }
        
        return resultado.toString();
    }
    
    /**
     * Procesa archivos para cifrado/descifrado
     */
    public static void procesarArchivo(String archivoEntrada, String archivoSalida, 
                                     Map<?, ?> tabla, boolean cifrar) {
        try {
            File entrada = new File(archivoEntrada);
            Scanner scanner = new Scanner(entrada);
            
            File salida = new File(archivoSalida);
            PrintWriter writer = new PrintWriter(salida);
            
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String resultado;
                
                if (cifrar) {
                    @SuppressWarnings("unchecked")
                    Map<Character, String> tablaEnc = (Map<Character, String>) tabla;
                    resultado = cifrarMensaje(linea, tablaEnc);
                } else {
                    @SuppressWarnings("unchecked")
                    Map<String, Character> tablaDesc = (Map<String, Character>) tabla;
                    resultado = descifrarMensaje(linea, tablaDesc);
                }
                
                writer.println(resultado);
                System.out.println("Procesado: " + linea + " -> " + resultado);
            }
            
            scanner.close();
            writer.close();
            
            System.out.println("Archivo procesado exitosamente: " + archivoSalida);
            
        } catch (FileNotFoundException e) {
            System.out.println("Error: No se pudo encontrar el archivo " + archivoEntrada);
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }
    
    /**
     * Muestra la tabla de cifrado para caracteres comunes
     */
    public static void mostrarTablaCifrado(Map<Character, String> tabla) {
        System.out.println("\n=== TABLA DE CIFRADO HYDRA (MUESTRA) ===");
        System.out.printf("%-10s %-10s %-15s %-40s%n", "Carácter", "ASCII", "ASCII*37", "Código Hydra");
        System.out.println("-".repeat(80));
        
        // Mostrar algunos caracteres importantes
        char[] muestras = {' ', 'A', 'B', 'R', 'a', 'b', '0', '1', '!', '?'};
        
        for (char c : muestras) {
            if (tabla.containsKey(c)) {
                int ascii = (int) c;
                int producto = ascii * 37;
                String codigo = tabla.get(c);
                System.out.printf("%-10c %-10d %-15d %-40s%n", c, ascii, producto, codigo);
            }
        }
    }
    
    /**
     * Muestra el ejemplo detallado del carácter R
     */
    public static void mostrarEjemploDetallado() {
        System.out.println("\n=== EJEMPLO DETALLADO: CARÁCTER 'R' ===");
        
        char caracter = 'R';
        int ascii = (int) caracter;
        
        System.out.println("Carácter: " + caracter);
        System.out.println("Valor ASCII: " + ascii);
        
        // Paso 1
        int num = ascii * 37;
        System.out.println("Paso 1 - Multiplicar por 37: " + ascii + " * 37 = " + num);
        
        // Paso 2
        int complemento;
        if (num <= 78) {
            complemento = 79 + ascii - 32;
            System.out.println("Paso 2 - Complemento (num <= 78): 79 + " + ascii + " - 32 = " + complemento);
        } else {
            complemento = 32 + ascii - 79;
            System.out.println("Paso 2 - Complemento (num > 78): 32 + " + ascii + " - 79 = " + complemento);
        }
        
        System.out.println("Carácter del complemento: '" + (char) complemento + "'");
        
        // Paso 3
        int d0 = num % 10;
        int d1 = (num / 10) % 10;
        int d2 = (num / 100) % 10;
        int d3 = (num / 1000) % 10;
        
        System.out.println("Paso 3 - Extraer dígitos de " + num + ":");
        System.out.println("  d3 (miles): " + d3);
        System.out.println("  d2 (centenas): " + d2);
        System.out.println("  d1 (decenas): " + d1);
        System.out.println("  d0 (unidades): " + d0);
        
        // Paso 4
        char h0 = (char) (d3 * d3 + complemento);
        char h1 = (char) (d2 * d2 + complemento);
        char h2 = (char) (d1 * d1 + complemento);
        char h3 = (char) (d0 * d0 + complemento);
        char h4 = (char) complemento;
        
        System.out.println("Paso 4 - Procesar dígitos:");
        System.out.println("  " + d3 + "² + " + complemento + " = " + (d3*d3 + complemento) + " = '" + h0 + "'");
        System.out.println("  " + d2 + "² + " + complemento + " = " + (d2*d2 + complemento) + " = '" + h1 + "'");
        System.out.println("  " + d1 + "² + " + complemento + " = " + (d1*d1 + complemento) + " = '" + h2 + "'");
        System.out.println("  " + d0 + "² + " + complemento + " = " + (d0*d0 + complemento) + " = '" + h3 + "'");
        System.out.println("  Complemento: " + complemento + " = '" + h4 + "'");
        
        String resultado = "" + h0 + h1 + h2 + h3 + h4;
        System.out.println("\nResultado final: \"" + resultado + "\"");
    }
}