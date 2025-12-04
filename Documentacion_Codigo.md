# Documentación del Código - Sistema de Cifrado/Descifrado

## Descripción General

El sistema implementado en `CifradorDescifrador.java` es una aplicación completa de cifrado y descifrado de mensajes que utiliza un algoritmo personalizado basado en la conversión de caracteres a valores ASCII y su posterior transformación mediante un esquema de sustitución específico.

## Estructura de Clases y Métodos

### Clase Principal: `CifradorDescifrador`

#### Atributos
- **`Map<Character, String> tablaCifrado`**: Almacena la correspondencia entre cada carácter y su versión cifrada
- **`Map<String, Character> tablaDescifrado`**: Almacena la correspondencia inversa para el proceso de descifrado
- **`Map<String, String> digitoACodigo`**: Contiene la tabla de sustitución básica (0-9 → códigos específicos)

#### Constructor
```java
public CifradorDescifrador()
```
- Inicializa todas las estructuras de datos necesarias
- Llama al método `inicializarTablas()` para poblar las tablas de cifrado

#### Métodos Privados

##### `inicializarTablas()`
- **Propósito**: Configura todas las tablas de cifrado y descifrado
- **Funcionamiento**: 
  - Inicializa el mapa `digitoACodigo` con las correspondencias básicas
  - Llama a métodos auxiliares para crear las tablas completas

##### `crearTablaCifradoCompleta()`
- **Propósito**: Genera la tabla de cifrado para todos los caracteres ASCII (0-127)
- **Algoritmo**:
  1. Itera por cada valor ASCII posible
  2. Convierte el valor ASCII a string
  3. Reemplaza cada dígito según la tabla `digitoACodigo`
  4. Agrega el carácter '%' al final
  5. Almacena en `tablaCifrado`

##### `crearTablaDescifrado()`
- **Propósito**: Crea la tabla inversa para descifrado
- **Funcionamiento**: Invierte las entradas de `tablaCifrado` para crear `tablaDescifrado`

##### `dividirTokens(String mensajeCifrado)`
- **Propósito**: Separa un mensaje cifrado en tokens individuales
- **Algoritmo**: Utiliza el carácter '%' como delimitador para identificar cada carácter cifrado

#### Métodos Públicos

##### `cifrarMensaje(String mensaje)`
- **Entrada**: Mensaje en texto plano
- **Salida**: Mensaje cifrado completo
- **Proceso**:
  1. Itera por cada carácter del mensaje
  2. Busca el carácter en `tablaCifrado`
  3. Concatena todas las representaciones cifradas

##### `descifrarMensaje(String mensajeCifrado)`
- **Entrada**: Mensaje cifrado
- **Salida**: Mensaje original descifrado
- **Proceso**:
  1. Divide el mensaje en tokens usando `dividirTokens()`
  2. Busca cada token en `tablaDescifrado`
  3. Reconstruye el mensaje original

##### `mostrarTablaCifrado()` y `mostrarTablaDescifrado()`
- **Propósito**: Visualización de las tablas para debugging y verificación
- **Formato**: Tablas organizadas mostrando carácter, valor ASCII y versión cifrada

##### `procesarArchivo()`
- **Propósito**: Procesa archivos completos para cifrado/descifrado masivo
- **Parámetros**:
  - `nombreArchivoEntrada`: Archivo fuente
  - `nombreArchivoSalida`: Archivo destino
  - `cifrar`: Boolean que determina la operación (cifrar/descifrar)

##### `main(String[] args)`
- **Propósito**: Interfaz de usuario interactiva
- **Funcionalidades**:
  - Menú con 8 opciones diferentes
  - Procesamiento de mensajes individuales
  - Procesamiento de archivos
  - Visualización de tablas
  - Ejemplo demostrativo

## Algoritmo de Cifrado Detallado

### Paso 1: Conversión ASCII
Cada carácter se convierte a su valor ASCII decimal.

**Ejemplo**: 'D' → 68

### Paso 2: Descomposición en Dígitos
El valor ASCII se descompone en dígitos individuales.

**Ejemplo**: 68 → ['6', '8']

### Paso 3: Sustitución por Códigos
Cada dígito se reemplaza según la tabla:
- 0 → "#/&$"
- 1 → "abd"
- 2 → "def"
- 3 → "ghi"
- 4 → "jkl"
- 5 → "mno"
- 6 → "pqr"
- 7 → "rst"
- 8 → "tuv"
- 9 → "wxy"

**Ejemplo**: ['6', '8'] → ["pqr", "tuv"]

### Paso 4: Concatenación y Terminador
Se concatenan todos los códigos y se agrega '%' al final.

**Ejemplo**: "pqr" + "tuv" + "%" → "pqrtuv%"

## Estructuras de Datos Utilizadas

### HashMap
- **Uso**: Implementación de las tablas de cifrado y descifrado
- **Ventaja**: Acceso O(1) promedio para búsquedas
- **Justificación**: Ideal para correspondencias uno-a-uno entre caracteres y códigos

### ArrayList
- **Uso**: Almacenamiento temporal de tokens durante el descifrado
- **Ventaja**: Redimensionamiento dinámico y acceso secuencial eficiente

### StringBuilder
- **Uso**: Construcción eficiente de strings largos durante cifrado/descifrado
- **Ventaja**: Evita la creación múltiple de objetos String inmutables

## Complejidad Algorítmica

### Cifrado
- **Tiempo**: O(n × m) donde n = longitud del mensaje, m = promedio de dígitos por ASCII
- **Espacio**: O(k) donde k = tamaño del conjunto de caracteres ASCII

### Descifrado
- **Tiempo**: O(n) donde n = longitud del mensaje cifrado
- **Espacio**: O(n) para almacenar tokens temporales

### Inicialización
- **Tiempo**: O(128 × log(128)) para crear tablas ASCII completas
- **Espacio**: O(128 × 2) para ambas tablas de cifrado y descifrado

## Manejo de Errores

### Archivos
- Captura `FileNotFoundException` para archivos inexistentes
- Mensajes informativos al usuario
- Cierre seguro de recursos con try-catch

### Entrada de Usuario
- Validación de opciones de menú
- Manejo de entradas inválidas con mensajes apropiados

## Casos de Prueba Recomendados

### Casos Básicos
1. **Carácter simple**: 'A' → "pqrabd%"
2. **Número**: '5' → "mno%"
3. **Símbolo**: '!' → "ghiabd%"

### Casos Complejos
1. **Mensaje completo**: "HOLA" → cifrado concatenado
2. **Números múltiples**: "123" → cifrado de cada dígito
3. **Caracteres especiales**: "@#$" → manejo de símbolos

### Casos Límite
1. **String vacío**: "" → ""
2. **Caracteres de control**: \\n, \\t, etc.
3. **Caracteres ASCII extendidos**: > 127

## Optimizaciones Implementadas

### Precálculo de Tablas
- Las tablas se generan una sola vez en el constructor
- Evita recálculos durante operaciones repetitivas

### Uso de StringBuilder
- Construcción eficiente de strings largos
- Previene fragmentación de memoria por concatenación

### HashMap para Búsquedas
- Acceso constante a correspondencias
- Superior a búsquedas lineales en arrays

## Extensibilidad del Código

### Nuevos Algoritmos de Cifrado
- Fácil modificación del método `crearTablaCifradoCompleta()`
- Intercambio de `digitoACodigo` por nuevas reglas

### Soporte de Archivos
- Extensión simple para diferentes formatos
- Posibilidad de cifrado por bloques para archivos grandes

### Interfaz Gráfica
- Separación clara entre lógica y presentación
- Métodos públicos listos para integración GUI