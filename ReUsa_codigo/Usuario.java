/*======================================================
* Proyecto : ReUsa - Plataforma de materiales academicos
* Entidad  : Usuario
* Programado por : Dainy Betin Castillo
* Fecha : 20/10/2026
--------------------------------------------------
* HU-01 Como estudiante quiero registrarme en la plataforma
*        para poder publicar y solicitar materiales.
* RF-01 Registrar usuario con nombre, correo y contrasena
* RF-02 Rechazar si el correo ya esta registrado
* RF-03 Rechazar si la contrasena esta vacia
* RN-01 Un usuario se identifica por un correo unico
 
* Criterios: CA-01.1 (bueno) CA-01.2 (falla) CA-01.3 (falla)
* Estructuras: Arreglo con contador . Pila propia
* ============================================================== */

/* Representa a un usuario y guarda los datos de su cuenta.
 * Las operaciones como registrar, buscar o eliminar
 * se realizan en la clase Usuarios.
 */
public class Usuario {

    // Identificador del usuario.
    String id_usuario;

    // Nombre del usuario.
    String nombre;

    // Correo que utiliza para iniciar sesion.
    String correo;

    // Contrasena de la cuenta.
    String contrasena;

    // Fecha en la que se registro.
    String fecha_registro;

    /*
     * Recibe los datos para crear un usuario.
     * this permite distinguir los datos del objeto
     * de los valores que recibe el constructor.
     */
    public Usuario(String id_usuario, String nombre, String correo, String contrasena, String fecha_registro) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.fecha_registro = fecha_registro;
    }

    /*
     * Devuelve los datos del usuario en una sola linea, separados por |.
     * Este orden se utiliza al guardar y recuperar el usuario del archivo.
     */
    @Override
    public String toString() {
        return id_usuario + "|" + nombre + "|" + correo + "|" + contrasena + "|" + fecha_registro;
    }
}