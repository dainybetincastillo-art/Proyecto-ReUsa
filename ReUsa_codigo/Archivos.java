/** Guarda los registros en archivos de texto dentro de la carpeta datos.
 * Tambien lee esos archivos para recuperar la informacion al iniciar.
 * Se utiliza desde las clases Usuarios, Materiales y Operaciones.
 */

import java.io.*;

public class Archivos {

    /*
     * Guarda en un archivo los registros que tiene un arreglo.
     * Recibe el nombre del archivo, el arreglo y la cantidad de registros.
     * Devuelve true si termina de guardar y false si ocurre un error.
     */
    static boolean guardar(String nombre, Object[] datos, int cantidad) {

        // Indica en que carpeta se guardaran los archivos.
        File carpeta = new File("datos");

        // Si la carpeta no existe, intenta crearla.
        // Si no puede crearla, avisa y no continua con el guardado.
        if (!carpeta.exists() && !carpeta.mkdir()) {
            System.out.println("No se pudo crear la carpeta datos.");
            return false;
        }

        // Abre el archivo para escribirlo desde el principio.
        // Si ya existia, reemplaza su contenido con los registros actuales.
        // Al salir de este bloque, el escritor se cierra automaticamente.
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter("datos/" + nombre))) {

            // Guarda solamente las posiciones ocupadas del arreglo.
            for (int i = 0; i < cantidad; i++) {

                // Usa toString() de cada registro para obtener el texto a guardar.
                escritor.write(datos[i].toString());

                // Deja cada registro en una linea diferente.
                escritor.newLine();
            }

            return true;

        } catch (IOException error) {

            // Muestra que archivo no se pudo guardar y el detalle del error.
            System.out.println("Error al guardar " + nombre + ": " + error.getMessage());
            return false;
        }
    }

    /*
     * Lee un archivo y devuelve sus lineas en un arreglo de hasta 100 posiciones.
     * Aqui solo se recupera el texto; cada clase se encarga de separar sus campos.
     */
    static String[] cargar(String nombre) {
        String[] lineas = new String[100];

        // Busca el archivo dentro de la carpeta datos.
        File archivo = new File("datos/" + nombre);

        // Si todavia no existe, devuelve el arreglo con sus posiciones en null.
        // Esto permite iniciar el programa sin registros guardados.
        if (!archivo.exists()) {
            return lineas;
        }

        // Abre el archivo para leerlo.
        // Al salir de este bloque, el lector se cierra automaticamente.
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {

            // Indica cuantas lineas se han guardado en el arreglo.
            int cantidad = 0;

            // Lee la primera linea. Si no hay ninguna, devuelve null.
            String linea = lector.readLine();

            // Continua mientras queden lineas por leer.
            while (linea != null) {

                // Evita intentar guardar mas lineas de las que caben.
                if (cantidad == lineas.length) {
                    throw new IOException("El archivo supera la capacidad de 100 registros.");
                }

                // Guarda la linea y avanza a la siguiente posicion libre.
                lineas[cantidad] = linea;
                cantidad++;

                // Lee la siguiente linea para continuar el recorrido.
                linea = lector.readLine();
            }

            return lineas;

        } catch (IOException error) {

            // Si no puede completar la lectura, detiene el inicio del programa.
            // Conserva el error original para poder revisar la causa.
            throw new IllegalStateException("No se pudo leer " + nombre, error);
        }
    }
}