/*======================================================
* Proyecto : ReUsa - Plataforma de materiales academicos
* Entidad  : Pila de Usuarios (para deshacer eliminacion)
* Programado por : Dainy Betin Castillo
* Fecha : 20/10/2026
* --------------------------------------------------
* HU-09 Como estudiante quiero deshacer la ultima eliminacion
* RF-21 Deshacer la ultima eliminacion de un usuario
* RF-22 Avisar si no hay nada que deshacer

* Criterios: CA-09.1 (bueno) CA-09.2 (falla)
* Estructuras: Arreglo con entero que marca la cima (sin java.util.Stack)
* ============================================================== */

/* Guarda usuarios en una pila de capacidad fija.
 * El ultimo usuario que se apila es el primero que se desapila.
 */
public class PilaUsuario {

    // Arreglo que almacena los usuarios de la pila.
    private Usuario[] datos;

    // Cantidad de usuarios que hay actualmente en la pila.
    private int cantidad = 0;

    /*
     * Crea la pila con la capacidad recibida.
     */
    public PilaUsuario(int capacidad) {
        datos = new Usuario[capacidad];
    }

    /*
     * Agrega un usuario en la cima de la pila.
     * Devuelve true si lo agrega y false si la pila esta llena.
     */
    public boolean apilar(Usuario registro) {

        // Si todas las posiciones estan ocupadas, no agrega el usuario.
        if (cantidad == datos.length) {
            return false;
        }

        // Guarda el usuario en la siguiente posicion libre.
        datos[cantidad] = registro;
        cantidad++;
        return true;
    }

    /*
     * Retira y devuelve el usuario de la cima.
     * Si la pila esta vacia, devuelve null.
     */
    public Usuario desapilar() {
        if (estaVacia()) {
            return null;
        }

        // Retrocede hasta la posicion del ultimo usuario apilado.
        cantidad--;

        // Conserva el usuario para devolverlo y libera su posicion.
        Usuario registro = datos[cantidad];
        datos[cantidad] = null;
        return registro;
    }

    /*
     * Devuelve el usuario de la cima sin retirarlo.
     * Si la pila esta vacia, devuelve null.
     */
    public Usuario cima() {
        if (estaVacia()) {
            return null;
        }

        // La cima esta en la ultima posicion ocupada.
        return datos[cantidad - 1];
    }

    /*
     * Devuelve true si la pila no tiene usuarios.
     */
    public boolean estaVacia() {
        return cantidad == 0;
    }

    /*
     * Devuelve la cantidad actual de usuarios en la pila.
     */
    public int tamano() {
        return cantidad;
    }
}