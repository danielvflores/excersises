import java.util.*;

public class CifradorDescifrador {
    private Map<Character, String> tablaCifrado;
    private Map<String, Character> tablaDescifrado;
    private Map<String, String> digitoACodigo;
    
    public CifradorDescifrador() {
        inicializarTablas();
    }
    
    private void inicializarTablas() {
        tablaCifrado = new HashMap<>();
        tablaDescifrado = new HashMap<>();
        digitoACodigo = new HashMap<>();
        
        digitoACodigo.put("1", "abd");
        digitoACodigo.put("2", "def");
        digitoACodigo.put("3", "ghi");
        digitoACodigo.put("4", "jkl");
        digitoACodigo.put("5", "mnñ");
        digitoACodigo.put("6", "opq");
        digitoACodigo.put("7", "rst");
        digitoACodigo.put("8", "uvw");
        digitoACodigo.put("9", "wxy");
        digitoACodigo.put("0", "#?&");
        
        crearTablaCifradoCompleta();
        crearTablaDescifrado();
    }
    
    private void crearTablaCifradoCompleta() {
        for (int i = 0; i <= 127; i++) {
            char caracter = (char) i;
            String valorASCII = String.valueOf(i);
            String cifrado = "";
            
            for (char digito : valorASCII.toCharArray()) {
                cifrado += digitoACodigo.get(String.valueOf(digito));
            }
            cifrado += "%";
            
            tablaCifrado.put(caracter, cifrado);
        }
    }
    
    private void crearTablaDescifrado() {
        for (Map.Entry<Character, String> entrada : tablaCifrado.entrySet()) {
            tablaDescifrado.put(entrada.getValue(), entrada.getKey());
        }
    }
    
    public String cifrarMensaje(String mensaje) {
        StringBuilder mensajeCifrado = new StringBuilder();
        
        for (char caracter : mensaje.toCharArray()) {
            if (tablaCifrado.containsKey(caracter)) {
                mensajeCifrado.append(tablaCifrado.get(caracter));
            }
        }
        
        return mensajeCifrado.toString();
    }
    
    public String descifrarMensaje(String mensajeCifrado) {
        StringBuilder mensajeDescifrado = new StringBuilder();
        List<String> tokens = dividirTokens(mensajeCifrado);
        
        for (String token : tokens) {
            if (tablaDescifrado.containsKey(token)) {
                mensajeDescifrado.append(tablaDescifrado.get(token));
            }
        }
        
        return mensajeDescifrado.toString();
    }
    
    private List<String> dividirTokens(String mensajeCifrado) {
        List<String> tokens = new ArrayList<>();
        StringBuilder tokenActual = new StringBuilder();
        
        for (char caracter : mensajeCifrado.toCharArray()) {
            tokenActual.append(caracter);
            if (caracter == '%') {
                tokens.add(tokenActual.toString());
                tokenActual = new StringBuilder();
            }
        }
        
        return tokens;
    }
    
    public void mostrarTablaCifrado() {
        System.out.println("=== TABLA DE CIFRADO ===");
        System.out.printf("%-10s %-10s %-20s%n", "Caracter", "ASCII", "Cifrado");
        System.out.println("-".repeat(45));
        
        for (int i = 32; i <= 126; i++) {
            char caracter = (char) i;
            String cifrado = tablaCifrado.get(caracter);
            System.out.printf("%-10c %-10d %-20s%n", caracter, i, cifrado);
        }
    }
    
    public void mostrarTablaDescifrado() {
        System.out.println("\n=== TABLA DE DESCIFRADO ===");
        System.out.printf("%-20s %-10s %-10s%n", "Cifrado", "ASCII", "Caracter");
        System.out.println("-".repeat(45));
        
        for (int i = 32; i <= 126; i++) {
            char caracter = (char) i;
            String cifrado = tablaCifrado.get(caracter);
            System.out.printf("%-20s %-10d %-10c%n", cifrado, i, caracter);
        }
    }
    
    public void procesarArchivo(String nombreArchivoEntrada, String nombreArchivoSalida, boolean cifrar) {
        try {
            Scanner archivo = new Scanner(new java.io.File(nombreArchivoEntrada));
            java.io.PrintWriter salida = new java.io.PrintWriter(nombreArchivoSalida);
            
            while (archivo.hasNextLine()) {
                String linea = archivo.nextLine();
                String resultado;
                
                if (cifrar) {
                    resultado = cifrarMensaje(linea);
                } else {
                    resultado = descifrarMensaje(linea);
                }
                
                salida.println(resultado);
            }
            
            archivo.close();
            salida.close();
            
            System.out.println("Archivo procesado exitosamente: " + nombreArchivoSalida);
            
        } catch (java.io.FileNotFoundException e) {
            System.out.println("Error: Archivo no encontrado - " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        CifradorDescifrador cifrador = new CifradorDescifrador();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n=== SISTEMA DE CIFRADO/DESCIFRADO ===");
            System.out.println("1. Cifrar mensaje");
            System.out.println("2. Descifrar mensaje");
            System.out.println("3. Mostrar tabla de cifrado");
            System.out.println("4. Mostrar tabla de descifrado");
            System.out.println("5. Procesar archivo (cifrar)");
            System.out.println("6. Procesar archivo (descifrar)");
            System.out.println("7. Ejemplo de cifrado");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el mensaje a cifrar: ");
                    String mensajeOriginal = scanner.nextLine();
                    String mensajeCifrado = cifrador.cifrarMensaje(mensajeOriginal);
                    System.out.println("Mensaje cifrado: " + mensajeCifrado);
                    break;
                    
                case 2:
                    System.out.print("Ingrese el mensaje cifrado: ");
                    String mensajeCifradoInput = scanner.nextLine();
                    String mensajeDescifrado = cifrador.descifrarMensaje(mensajeCifradoInput);
                    System.out.println("Mensaje descifrado: " + mensajeDescifrado);
                    break;
                    
                case 3:
                    cifrador.mostrarTablaCifrado();
                    break;
                    
                case 4:
                    cifrador.mostrarTablaDescifrado();
                    break;
                    
                case 5:
                    System.out.print("Nombre del archivo de entrada: ");
                    String archivoEntrada = scanner.nextLine();
                    System.out.print("Nombre del archivo de salida: ");
                    String archivoSalida = scanner.nextLine();
                    cifrador.procesarArchivo(archivoEntrada, archivoSalida, true);
                    break;
                    
                case 6:
                    System.out.print("Nombre del archivo cifrado: ");
                    String archivoCifrado = scanner.nextLine();
                    System.out.print("Nombre del archivo de salida: ");
                    String archivoDescifrado = scanner.nextLine();
                    cifrador.procesarArchivo(archivoCifrado, archivoDescifrado, false);
                    break;
                    
                case 7:
                    System.out.println("\n=== EJEMPLO DE CIFRADO ===");
                    char ejemploChar = 'D';
                    int valorASCII = (int) ejemploChar;
                    String cifradoEjemplo = cifrador.cifrarMensaje(String.valueOf(ejemploChar));
                    
                    System.out.println("Caracter: " + ejemploChar);
                    System.out.println("Valor ASCII: " + valorASCII);
                    System.out.println("Cifrado: " + cifradoEjemplo);
                    
                    String descifradoEjemplo = cifrador.descifrarMensaje(cifradoEjemplo);
                    System.out.println("Descifrado: " + descifradoEjemplo);
                    break;
                    
                case 8:
                    System.out.println("¡Hasta luego!");
                    scanner.close();
                    return;
                    
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }
}