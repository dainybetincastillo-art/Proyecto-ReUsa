/*======================================================
* Proyecto : ReUsa - Plataforma de materiales academicos
* Entidad  : Pila de Materiales (para deshacer eliminacion)
* Programado por : Elber Jose Arrieta Jimenez
* Fecha : 20/10/2026
* --------------------------------------------------
* HU-09 Como estudiante quiero deshacer la ultima eliminacion
* RF-21 Deshacer la ultima eliminacion de un material
* RF-22 Avisar si no hay nada que deshacer

* Criterios: CA-09.1 (bueno) CA-09.2 (falla)
* Estructuras: Arreglo con entero que marca la cima (sin java.util.Stack)
* ============================================================== */

/* Guarda materiales en una pila de capacidad fija.
 * El ultimo material que se apila es el primero que se desapila.
 */
public class PilaMaterial {

    // Arreglo que almacena los materiales de la pila.
    private Material[] datos;

    // Cantidad de materiales que hay actualmente en la pila.
    private int cantidad = 0;

    /*
     * Crea la pila con la capacidad recibida.
     */
    public PilaMaterial(int capacidad) {
        datos = new Material[capacidad];
    }

    /*
     * Agrega un material en la cima de la pila.
     * Devuelve true si lo agrega y false si la pila esta llena.
     */
    public boolean apilar(Material registro) {

        // Si todas las posiciones estan ocupadas, no agrega el material.
        if (cantidad == datos.length) {
            return false;
        }

        // Guarda el material en la siguiente posicion libre.
        datos[cantidad] = registro;
        cantidad++;
        return true;
    }

    /*
     * Retira y devuelve el material de la cima.
     * Si la pila esta vacia, devuelve null.
     */
    public Material desapilar() {
        if (estaVacia()) {
            return null;
        }

        // Retrocede hasta la posicion del ultimo material apilado.
        cantidad--;

        // Conserva el material para devolverlo y libera su posicion.
        Material registro = datos[cantidad];
        datos[cantidad] = null;
        return registro;
    }

    /*
     * Devuelve el material de la cima sin retirarlo.
     * Si la pila esta vacia, devuelve null.
     */
    public Material cima() {
        if (estaVacia()) {
            return null;
        }

        // La cima esta en la ultima posicion ocupada.
        return datos[cantidad - 1];
    }

    /*
     * Devuelve true si la pila no tiene materiales.
     */
    public boolean estaVacia() {
        return cantidad == 0;
    }

    /*
     * Devuelve la cantidad actual de materiales en la pila.
     */
    public int tamano() {
        return cantidad;
    }
}