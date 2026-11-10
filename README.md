# ReUsa - Plataforma de Materiales Académicos Impresos

Prototipo académico desarrollado para la asignatura **Ingeniería de Software I** de la Corporación Universitaria Remington.

## Descripción del Proyecto
ReUsa es una plataforma diseñada para facilitar la donación e intercambio de materiales académicos impresos (guías, talleres, fotocopias y apuntes) entre los estudiantes de la universidad. El objetivo principal es promover la economía circular, reducir el desperdicio de papel y disminuir los gastos de impresión de los estudiantes.

## Tecnologías Utilizadas
*   **Lenguaje:** Java (JDK 8 o superior).
*   **Interfaz:** Menú por consola (Terminal).
*   **Estructuras de datos (programadas a mano, sin librerías):** 
    *   Arreglos estáticos con contador.
    *   Pilas (LIFO) para la función de "Deshacer".
*   **Persistencia:** Archivos de texto plano (`.txt`).

## Funcionalidades Implementadas
1.  Gestión de usuarios (Registro, Inicio de sesión, Actualización y Eliminación).
2.  Publicación de materiales (Materia, tipo, estado físico, hojas y punto de entrega).
3.  Búsqueda de materiales disponibles.
4.  Solicitud de materiales e intercambios.
5.  Sistema de deshacer para recuperar el último registro eliminado.
6.  Cálculo de totales y estimación de hojas ahorradas.
7.  Guardado y carga automática de datos al iniciar/cerrar el programa.

## Autores
*   **Elber José Arrieta Jiménez** - *Programación de la entidad Material*
*   **Dainy Betin Castillo** - *Programación de la entidad Usuario*

## ¿Cómo ejecutar el proyecto?
1. Clona este repositorio en tu máquina local o descarga los archivos.
2. Asegúrate de tener instalado el JDK de Java.
3. Abre una terminal en la carpeta raíz del proyecto.
4. Compila los archivos con el comando:
   ```bash
   javac *.java
