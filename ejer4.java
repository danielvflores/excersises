import java.io.*;
import java.util.*;

/**
 * Ejercicio 4: Cuenta las ocurrencias de los números ingresados
 * Escribe un programa que lea un número específico de entrada y encuentre 
 * el que tiene más ocurrencias. La entrada finaliza cuando la entrada es 0.
 * Por ejemplo, si ingresas 2 3 40 3 5 4 -3 3 3 2 0, el número 3 ocurre 
 * más veces (cuatro). Si tiene más de uno sino número con mayor ocurrencia, 
 * todas ellas deben ser reportadas.
 */
public class ejer4 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Map<Integer, Integer> contadorNumeros = new HashMap<>();
        
        System.out.println("=== CONTADOR DE OCURRENCIAS DE NÚMEROS ===");
        System.out.println("Ingrese números enteros (termine con 0):");
        System.out.print("Números: ");
        
        // Leer números hasta que se ingrese 0
        int numero;
        int totalNumeros = 0;
        
        while ((numero = input.nextInt()) != 0) {
            contadorNumeros.put(numero, contadorNumeros.getOrDefault(numero, 0) + 1);
            totalNumeros++;
        }
        
        if (contadorNumeros.isEmpty()) {
            System.out.println("No se ingresaron números válidos.");
            input.close();
            return;
        }
        
        // Encontrar la máxima ocurrencia
        int maxOcurrencias = Collections.max(contadorNumeros.values());
        
        // Encontrar todos los números con máxima ocurrencia
        List<Integer> numerosMasFrequentes = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entrada : contadorNumeros.entrySet()) {
            if (entrada.getValue() == maxOcurrencias) {
                numerosMasFrequentes.add(entrada.getKey());
            }
        }
        
        // Ordenar los números más frecuentes
        Collections.sort(numerosMasFrequentes);
        
        // Mostrar resultados
        System.out.println("\n=== RESULTADOS ===");
        System.out.println("Total de números ingresados: " + totalNumeros);
        System.out.println("Números únicos diferentes: " + contadorNumeros.size());
        System.out.println("Máximo número de ocurrencias: " + maxOcurrencias);
        
        if (numerosMasFrequentes.size() == 1) {
            System.out.println("El número con más ocurrencias es: " + numerosMasFrequentes.get(0));
        } else {
            System.out.println("Los números con más ocurrencias son: " + numerosMasFrequentes);
        }
        
        // Mostrar tabla de frecuencias completa
        System.out.println("\n--- TABLA DE FRECUENCIAS ---");
        System.out.printf("%-10s %-12s %-15s%n", "Número", "Ocurrencias", "Porcentaje");
        System.out.println("-".repeat(40));
        
        // Ordenar por número para mejor visualización
        Map<Integer, Integer> numerosOrdenados = new TreeMap<>(contadorNumeros);
        
        for (Map.Entry<Integer, Integer> entrada : numerosOrdenados.entrySet()) {
            double porcentaje = (entrada.getValue() * 100.0) / totalNumeros;
            String marca = entrada.getValue() == maxOcurrencias ? " *" : "";
            System.out.printf("%-10d %-12d %-15.2f%%%s%n", 
                entrada.getKey(), entrada.getValue(), porcentaje, marca);
        }
        
        System.out.println("\n* Indica el/los número(s) con mayor frecuencia");
        
        // Estadísticas adicionales
        System.out.println("\n--- ESTADÍSTICAS ADICIONALES ---");
        
        // Número más alto y más bajo
        int minNum = Collections.min(contadorNumeros.keySet());
        int maxNum = Collections.max(contadorNumeros.keySet());
        System.out.println("Número más bajo ingresado: " + minNum);
        System.out.println("Número más alto ingresado: " + maxNum);
        System.out.println("Rango de números: " + (maxNum - minNum));
        
        // Números que aparecen solo una vez
        List<Integer> numerosUnicos = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entrada : contadorNumeros.entrySet()) {
            if (entrada.getValue() == 1) {
                numerosUnicos.add(entrada.getKey());
            }
        }
        
        if (!numerosUnicos.isEmpty()) {
            Collections.sort(numerosUnicos);
            System.out.println("Números que aparecen solo una vez: " + numerosUnicos);
        }
        
        input.close();
    }
}