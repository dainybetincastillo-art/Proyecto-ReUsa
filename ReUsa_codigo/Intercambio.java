/*======================================================
* Proyecto : ReUsa - Plataforma de materiales academicos
* Entidad  : Intercambio (auxiliar)
* Programado por : Elber Jose Arrieta Jimenez
* Fecha : 20/10/2026
* --------------------------------------------------
* HU-06 Como sistema quiero registrar un intercambio
*        cuando el material pasa de disponible a intercambiado
*        para llevar la cuenta de las hojas ahorradas.
* RF-16 Registrar intercambio al aceptar solicitud
* RF-17 Rechazar registro si el material ya fue intercambiado

* Criterios: CA-06.1, CA-06.2
* Estructuras: Arreglo con contador
* ============================================================== */


/* Representa un intercambio y guarda sus datos.
 * Relaciona el intercambio con una solicitud y un material,
 * e incluye su fecha y la cantidad de hojas ahorradas.
 */
public class Intercambio {

    // Identificador del intercambio.
    String id_intercambio;

    // ID de la solicitud relacionada con el intercambio.
    String id_solicitud;

    // ID del material intercambiado.
    String id_material;

    // Fecha en la que se realizo el intercambio.
    String fecha_intercambio;

    // Cantidad de hojas ahorradas mediante el intercambio.
    int hojas_ahorradas;

    /*
     * Recibe los datos para crear un intercambio.
     * this permite distinguir los datos del objeto
     * de los valores que recibe el constructor.
     */
    public Intercambio(String id_intercambio, String id_solicitud, String id_material, String fecha_intercambio, int hojas_ahorradas) {
        this.id_intercambio = id_intercambio;
        this.id_solicitud = id_solicitud;
        this.id_material = id_material;
        this.fecha_intercambio = fecha_intercambio;
        this.hojas_ahorradas = hojas_ahorradas;
    }

    /*
     * Devuelve los datos del intercambio en una sola linea,
     * separados por |, para guardarlos en un archivo.
     * El orden es: intercambio, solicitud, material,
     * fecha y hojas ahorradas.
     */
    @Override
    public String toString() {
        return id_intercambio + "|" + id_solicitud + "|" + id_material + "|" + fecha_intercambio + "|" + hojas_ahorradas;
    }
}