# Algoritmos y Estructuras de Datos - Conceptos Fundamentales

## Introducción

Este documento explora los conceptos fundamentales de **Algoritmos y Estructuras de Datos** aplicados en el sistema de cifrado/descifrado desarrollado, proporcionando una base teórica sólida para estudiantes de la Universidad Técnica Federico Santa María (UTA), Chile.

## 1. Estructuras de Datos Fundamentales

### 1.1 Arrays y Listas

#### Definición
Un **array** es una estructura de datos que almacena elementos del mismo tipo en posiciones contiguas de memoria, accesibles mediante índices numéricos.

#### Características Importantes
- **Acceso directo**: O(1) para acceso por índice
- **Tamaño fijo**: En Java, los arrays tienen tamaño inmutable
- **Localidad espacial**: Elementos consecutivos en memoria

#### Aplicación en el Proyecto
```java
char[] caracteres = mensaje.toCharArray();
```
- Conversión de String a array para procesamiento carácter por carácter
- Iteración eficiente sobre elementos secuenciales

### 1.2 Listas Dinámicas (ArrayList)

#### Definición
**ArrayList** es una implementación de lista redimensionable que mantiene un array interno que crece dinámicamente.

#### Características Clave
- **Redimensionamiento automático**: Crece según necesidad (factor de crecimiento ~1.5x)
- **Acceso aleatorio**: O(1) para get/set por índice
- **Inserción/eliminación**: O(n) en el peor caso (requiere desplazamiento)

#### Aplicación en el Proyecto
```java
List<String> tokens = new ArrayList<>();
```
- Almacenamiento dinámico de tokens durante descifrado
- Flexibilidad para mensajes de longitud variable

### 1.3 Mapas Hash (HashMap)

#### Definición Teórica
Un **HashMap** es una estructura de datos que implementa el concepto de tabla hash, proporcionando un mapeo entre claves y valores mediante una función hash.

#### Funcionamiento Interno

##### Función Hash
```java
hash = key.hashCode() % buckets.length
```
- Convierte la clave en un índice del array interno
- Distribución uniforme minimiza colisiones

##### Resolución de Colisiones
- **Chaining**: Lista enlazada en cada bucket (implementación Java 7)
- **Tree structure**: Árbol rojo-negro para buckets con muchas colisiones (Java 8+)

#### Complejidad Temporal
- **Acceso promedio**: O(1)
- **Peor caso**: O(n) con muchas colisiones
- **Amortizado**: O(1) con función hash bien distribuida

#### Aplicación Crítica en el Proyecto
```java
Map<Character, String> tablaCifrado = new HashMap<>();
Map<String, Character> tablaDescifrado = new HashMap<>();
```

**Justificación de Uso**:
1. **Búsquedas constantes**: Cifrado/descifrado requiere acceso frecuente
2. **Bidireccionalidad**: Mapas separados para cada dirección
3. **Eficiencia**: Superior a búsquedas lineales en arrays

## 2. Algoritmos de Búsqueda y Acceso

### 2.1 Búsqueda en Tablas Hash

#### Algoritmo
```java
public String get(Character key) {
    int hash = key.hashCode() % table.length;
    Node current = table[hash];
    while (current != null) {
        if (current.key.equals(key)) {
            return current.value;
        }
        current = current.next;
    }
    return null;
}
```

#### Análisis de Complejidad
- **Caso promedio**: O(1) - acceso directo
- **Peor caso**: O(n) - todas las claves en el mismo bucket
- **Factor de carga**: Ratio elementos/buckets, óptimo ≈ 0.75

### 2.2 Algoritmos de Procesamiento de Strings

#### Construcción Eficiente (StringBuilder)
```java
StringBuilder resultado = new StringBuilder();
for (char c : mensaje.toCharArray()) {
    resultado.append(procesar(c));
}
```

**Por qué StringBuilder vs String**:
- **String**: Inmutable, cada concatenación crea nuevo objeto
- **Complejidad String**: O(n²) para n concatenaciones
- **StringBuilder**: Buffer mutable, redimensionamiento automático
- **Complejidad StringBuilder**: O(n) amortizado

## 3. Análisis de Complejidad Algorítmica

### 3.1 Notación Big O

#### Definición
La **notación Big O** describe el comportamiento asintótico de un algoritmo en términos del tamaño de entrada.

#### Jerarquía de Complejidades (de mejor a peor)
1. **O(1)** - Constante: Acceso a HashMap
2. **O(log n)** - Logarítmica: Búsqueda binaria
3. **O(n)** - Lineal: Recorrido de array
4. **O(n log n)** - Linearítmica: Ordenamiento eficiente
5. **O(n²)** - Cuadrática: Algoritmos naive de ordenamiento
6. **O(2ⁿ)** - Exponencial: Problemas NP

### 3.2 Análisis del Algoritmo de Cifrado

#### Complejidad Temporal
```java
public String cifrarMensaje(String mensaje) {
    StringBuilder cifrado = new StringBuilder();          // O(1)
    for (char c : mensaje.toCharArray()) {               // O(n)
        String codigo = tablaCifrado.get(c);             // O(1) promedio
        cifrado.append(codigo);                          // O(1) amortizado
    }
    return cifrado.toString();                           // O(n)
}
```

**Análisis**:
- Loop principal: O(n)
- Acceso a HashMap: O(1) promedio
- StringBuilder.append: O(1) amortizado
- **Total**: O(n) donde n = longitud del mensaje

#### Complejidad Espacial
- Tabla de cifrado: O(k) donde k = 128 caracteres ASCII
- StringBuilder interno: O(m) donde m = longitud del mensaje cifrado
- **Total**: O(k + m)

## 4. Patrones de Diseño y Arquitectura

### 4.1 Patrón Initialization (Inicialización Eager)

#### Implementación
```java
public CifradorDescifrador() {
    inicializarTablas();  // Eager initialization
}
```

#### Ventajas
- **Consistencia**: Estado siempre válido después de construcción
- **Performance**: Evita verificaciones en cada operación
- **Simplicidad**: No requiere lazy loading complejo

#### Desventajas
- **Memoria**: Consume recursos inmediatamente
- **Tiempo de construcción**: Mayor latencia inicial

### 4.2 Principio de Responsabilidad Única (SRP)

#### Aplicación en el Proyecto
- **Cifrado**: Métodos dedicados solo a cifrar
- **Descifrado**: Métodos específicos para descifrar
- **Visualización**: Métodos separados para mostrar tablas
- **E/S de archivos**: Métodos especializados en procesamiento de archivos

### 4.3 Encapsulación y Abstracción

#### Métodos Privados
```java
private void crearTablaCifradoCompleta() { ... }
private List<String> dividirTokens(String mensaje) { ... }
```

**Beneficios**:
- **Ocultación de implementación**: Detalles internos no expuestos
- **Mantenibilidad**: Cambios internos no afectan interfaz pública
- **Modularidad**: Funciones específicas y reutilizables

## 5. Optimización y Performance

### 5.1 Técnicas de Optimización Implementadas

#### Precalculated Tables (Tablas Precalculadas)
```java
// Constructor calcula todas las correspondencias una sola vez
private void crearTablaCifradoCompleta() {
    for (int i = 0; i <= 127; i++) {
        // Precálculo de todas las transformaciones
    }
}
```

**Ventaja**: Intercambia memoria por velocidad (space-time tradeoff)

#### String Interning y Reutilización
```java
// Reutilización de códigos constantes
digitoACodigo.put("1", "abd");  // String literal reutilizada
```

### 5.2 Análisis de Memory Usage

#### Estimación de Memoria
- **HashMap overhead**: ~32 bytes por entrada
- **String overhead**: ~24 bytes + contenido
- **Tabla completa**: ≈ 128 × (32 + 24 + longitud_promedio_codigo)

#### Optimizaciones Posibles
1. **Primitive collections**: TCharObjectHashMap para reducir boxing
2. **String pooling**: Reutilización de códigos comunes
3. **Lazy loading**: Cargar solo caracteres utilizados

## 6. Estructuras de Datos Avanzadas

### 6.1 Trie (Prefix Tree) - Alternativa Conceptual

#### Definición
Un **Trie** es un árbol de búsqueda donde cada nodo representa un prefijo común de las claves almacenadas.

#### Aplicación Potencial
```java
// Para descifrado eficiente de códigos variables
class TrieNode {
    Map<Character, TrieNode> children;
    Character decodedChar;  // null si no es final
}
```

#### Ventajas sobre HashMap para este problema
- **Prefijos comunes**: Códigos como "abc", "abd" comparten "ab"
- **Menos memoria**: Almacenamiento compartido de prefijos
- **Búsqueda incremental**: Procesamiento carácter por carácter

### 6.2 Finite State Automaton (Autómata Finito)

#### Concepto
Un **autómata finito** puede modelar el proceso de descifrado como una máquina de estados.

#### Estados del Sistema de Descifrado
1. **INITIAL**: Esperando inicio de token
2. **READING**: Leyendo código de caracteres
3. **TERMINAL**: Encontrado '%', token completo

#### Implementación Conceptual
```java
enum State { INITIAL, READING, TERMINAL }
State current = INITIAL;
StringBuilder token = new StringBuilder();

for (char c : input) {
    switch (current) {
        case INITIAL:
        case READING:
            if (c == '%') {
                current = TERMINAL;
                processToken(token.toString());
                token.setLength(0);
                current = INITIAL;
            } else {
                token.append(c);
                current = READING;
            }
            break;
    }
}
```

## 7. Teoría de la Información y Criptografía

### 7.1 Entropía y Compresión

#### Definición de Entropía
$$H(X) = -\\sum_{i} p(x_i) \\log_2 p(x_i)$$

Donde p(x_i) es la probabilidad del símbolo x_i.

#### Análisis del Algoritmo de Cifrado
- **Expansión**: Cada carácter se convierte en múltiples caracteres
- **Ratio de expansión**: Promedio ~8-12 caracteres por carácter original
- **Seguridad vs Eficiencia**: Mayor longitud reduce eficiencia pero puede aumentar confusión

### 7.2 Propiedades Criptográficas

#### Confusión
- **Definición**: Oscurecer la relación entre texto plano y cifrado
- **En nuestro algoritmo**: ASCII → dígitos → códigos alfabéticos

#### Difusión
- **Definición**: Dispersar la influencia de cada bit de entrada
- **Limitación actual**: Cambios locales (un carácter afecta solo su cifrado)

## 8. Complejidad Computacional y Teoría de Algoritmos

### 8.1 Clases de Complejidad

#### P vs NP
- **Clase P**: Problemas resolubles en tiempo polinomial
- **Nuestro algoritmo**: Claramente en P (O(n) lineal)
- **Descifrado**: También en P con tabla de lookup

#### Problemas de Decisión
- **¿Es descifrable?**: Verificable en O(n) revisando formato
- **¿Es válido el cifrado?**: Cada token debe terminar en '%'

### 8.2 Algoritmos de Aproximación

#### Concepto
Algoritmos que encuentran soluciones cercanas al óptimo para problemas NP-hard.

#### No aplicable directamente, pero conceptualmente:
- **Compresión óptima**: Encontrar la representación más corta
- **Búsqueda aproximada**: Encontrar coincidencias parciales en descifrado corrupto

## 9. Aplicaciones Prácticas y Extensiones

### 9.1 Sistemas de Cifrado en el Mundo Real

#### Caesar Cipher (Cifrado César)
- **Simplicidad**: Desplazamiento fijo del alfabeto
- **Debilidad**: Fácilmente rompible por análisis de frecuencia

#### Comparación con Nuestro Algoritmo
- **Más complejo**: Mapeo no uniforme de caracteres
- **Expansión**: Aumenta longitud del mensaje
- **Determinístico**: Mismo input → mismo output

### 9.2 Optimizaciones Industriales

#### Vectorización SIMD
```java
// Procesamiento paralelo de múltiples caracteres
// (No directamente aplicable en Java puro, pero conceptualmente importante)
```

#### Cache-Friendly Algorithms
- **Localidad temporal**: Reutilizar datos recientemente accedidos
- **Localidad espacial**: Acceder datos en posiciones cercanas
- **En nuestro código**: HashMap aprovecha localidad en buckets

## 10. Métricas y Evaluación de Rendimiento

### 10.1 Benchmarking

#### Metodología
```java
long startTime = System.nanoTime();
String resultado = cifrarMensaje(input);
long endTime = System.nanoTime();
double duration = (endTime - startTime) / 1_000_000.0;  // ms
```

#### Factores que Afectan Performance
1. **Longitud del mensaje**: Relación lineal
2. **Distribución de caracteres**: HashMap performance
3. **Garbage collection**: Creación de objetos String
4. **JIT compilation**: Warming up de métodos frecuentes

### 10.2 Profiling y Optimización

#### Herramientas Java
- **JProfiler**: Análisis detallado de memoria y CPU
- **VisualVM**: Profiler incluido en JDK
- **JMH**: Framework de microbenchmarks

#### Puntos de Optimización Identificados
1. **StringBuilder inicial capacity**: Evitar redimensionamientos
2. **HashMap load factor**: Balancear memoria vs colisiones
3. **String pooling**: Reducir duplicación de códigos

## Conclusión

Este ejercicio integra múltiples conceptos fundamentales de **Algoritmos y Estructuras de Datos**:

- **Estructuras básicas**: Arrays, listas, mapas hash
- **Análisis de complejidad**: Big O notation, space-time tradeoffs
- **Algoritmos de búsqueda**: Hash tables, lookup tables
- **Optimización**: Precálculo, construcción eficiente de strings
- **Patrones de diseño**: Encapsulación, responsabilidad única
- **Teoría computacional**: Clases de complejidad, análisis algorítmico

La implementación demuestra cómo aplicar estos conceptos teóricos en un problema práctico, equilibrando eficiencia, legibilidad y mantenibilidad del código.

### Temas Avanzados para Profundización
1. **Criptografía moderna**: AES, RSA, criptografía de clave pública
2. **Estructuras probabilísticas**: Bloom filters, skip lists
3. **Algoritmos paralelos**: Fork-join, streams paralelos
4. **Estructuras persistentes**: Immutable data structures
5. **Algoritmos aproximados**: Sketching, sampling