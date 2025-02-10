# Simulador de Gestión de Memoria MOSS con Implementación de NRU

## Resumen
Este repositorio contiene el Simulador de Gestión de Memoria MOSS, diseñado originalmente para demostrar técnicas de gestión de memoria como los algoritmos de reemplazo de páginas. El simulador ha sido mejorado con una implementación del algoritmo de reemplazo de páginas **NRU (Not Recently Used)**. NRU prioriza el reemplazo de páginas clasificándolas según sus bits de referencia (R) y modificación (M).

## Características
- **Algoritmo de reemplazo de páginas:** Implementación de NRU para escenarios de gestión de memoria más realistas.
- **Registro de trazas:** Registro detallado de las operaciones de memoria para su análisis en el archivo por defecto tracefile
- **Simulación configurable:** Personaliza el estado inicial de la memoria y los parámetros de simulación mediante `memory.conf`.
- **Escenarios de prueba:** Comandos predefinidos para probar y observar el comportamiento del reemplazo de páginas.
- **Vista Segmentada de Páginas:** La GUI ahora muestra las páginas divididas en cuatro segmentos de 4096 bytes cada uno.
- **Lectura de memoria Low y High:** Soporte para aceptar comandos con lectura o escritura (READ/WRITE) de memoria Low y High para sistemas numéricos hex/oct/bin en el archivo `commands` 

## Algoritmo NRU
El algoritmo NRU clasifica las páginas en cuatro categorías:
1. **Clase 0:** R = 0, M = 0 (No referenciada, no modificada - máxima prioridad para reemplazo).
2. **Clase 1:** R = 0, M = 1 (No referenciada, pero modificada).
3. **Clase 2:** R = 1, M = 0 (Referenciada, pero no modificada).
4. **Clase 3:** R = 1, M = 1 (Referenciada y modificada - menor prioridad para reemplazo).

Cuando ocurre un fallo de página, el algoritmo reemplaza una página de la clase con el número más bajo. Si existen múltiples candidatos en la misma clase, se reemplaza el primero encontrado.

## Estructura del Proyecto
- **`PageFault.java`:** Contiene la implementación del algoritmo de reemplazo de páginas NRU.
- **`memory.conf`:** Archivo de configuración para definir el estado inicial de la memoria virtual y física.
- **`commands`:** Archivo con operaciones de memoria predefinidas (READ/WRITE) para probar el simulador.
- **`tracefile`:** Archivo de registro que muestra el resultado de las operaciones de memoria durante la simulación.

## Ejecución del Simulador (Con Scripts)
Para poder ejecutar el simulador puede optar por la ejecución por medio de scripts 

### Para Sistemas Unix/Linux (`run.sh`):
```bash
./run.sh
```
*Nota:* Debe otorgar permisos de ejecución primero:
```bash
chmod +x run.sh
```

### Para Windows (`run.bat`):
Solo haga doble click en el archivo:
```bat
run.bat
```

Ambos scripts realizan:
1. Compilación automática de los archivos Java (con supresión de advertencias).
2. Ejecución del simulador con la configuración predeterminada (`commands` y `memory.conf`).
3. *(Solo `run.bat`)* Pausa la terminal al finalizar para visualizar la salida.

---

## Cómo Compilar (Manualmente)
Para compilar todos los archivos Java:
```bash
javac -nowarn *.java
```

---


## Cómo Ejecutar (Manualmente)
Para ejecutar el simulador:
```bash
java MemoryManagement commands memory.conf
```

### Ejemplo de Configuración (`memory.conf`)
- Las páginas 0 a 8 están marcadas como referenciadas y modificadas (R = 1, M = 1) para simular páginas ocupadas.
- La página 9 no está referenciada ni modificada (R = 0, M = 0) y será mapeada durante la simulación.
- Las páginas 23 no está referenciada pero sí modificada (R = 0, M = 1) y será mapeada durante la simulación.
- Lás páginas 24 y 31 están referenciadas pero no modificadas (R = 1, M = 0) y serán mapeadas durante la simulación.

```plaintext
// memset virtPage# physicalPage# R M inMemTime(ns) lastTouchTime(ns)
memset 0 0 1 1 0 0
memset 1 1 1 1 0 0
memset 2 2 1 1 0 0
...
memset 9 9 0 0 0 0
memset 23 23 0 1 0 0
memset 24 24 1 0 0 0
memset 31 31 1 0 0 0
```

### Ejemplo de Configuración (`commands`)
- Las páginas 0 a 3 se utilizan para probar la lectura de direcciones de memoria por segmentos (Para probar el funcionamiento procure borrar los símbolos "//")
- Las páginas 33 a 37 se utilizan para probar fallos de página.

```plaintext
// Enter READ/WRITE commands into this file
// READ <OPTIONAL  number  type:  bin/hex/oct> <virtual  memory  address  or  random>
// WRITE <OPTIONAL  number  type:  bin/hex/oct> <virtual  memory  address  or  random>

// Escritura y lectura de direcciones de memoria por segmento
// WRITE hex 3FFF 2000 // Página 00: Segmento 3, 4
// READ hex 7FFF 4000  // Página 01: Segmento 1, 4
// WRITE hex BFFF F000 // Página 02: Segmento 4; Pagina 3: Segmento 4

// Escribe en una pagina que no esta cargada para provocar fallos
READ hex 84FFF 84000 // Página 33: operación READ para generar un fallo de pagina
READ hex 88FFF 88000 // Página 34: operación READ para generar otro fallo de pagina
READ hex 8CFFF 8C000 // Página 35: operación READ para generar otro fallo de pagina
READ hex 90FFF 90000 // Página 36: operación READ para generar otro fallo de pagina
READ hex 94FFF 94000 // Página 37: operación READ ...
```

## Instrucciones de Prueba
1. Asegúrese de que `memory.conf` y `commands` estén configurados según sus necesidades.
2. Ejecute el simulador como se indica en la sección "Cómo Ejecutar".
3. Observe la salida en la terminal y el archivo `tracefile` para detalles sobre el reemplazo de páginas y los fallos..

## Ejemplo de Salida en `tracefile`
Después de ejecutar el simulador, puede esperar un archivo `tracefile` con registros como este:
```plaintext
READ 84fff ... page fault
READ 84000 ... okay
READ 88fff ... page fault
READ 88000 ... okay
READ 8cfff ... page fault
READ 8c000 ... okay
READ 90fff ... page fault
READ 90000 ... okay
READ 94fff ... page fault
READ 94000 ... okay
```

## Créditos
- Simulador MOSS original: [Alex Reeder](https://www.ontko.com/moss/)
- Implementación de NRU: Adaptado con fines educativos.
---

¡Siéntete libre de hacer un fork y extender este proyecto para explorar otros algoritmos de reemplazo de páginas o mejorar aún más el simulador!