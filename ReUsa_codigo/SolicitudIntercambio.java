
/*======================================================
* Proyecto : ReUsa - Plataforma de materiales academicos
* Entidad  : SolicitudIntercambio (auxiliar)
* Programado por : Dainy Betin Castillo
* Fecha : 20/10/2026
* --------------------------------------------------
* HU-04 Como estudiante quiero solicitar un material
*        publicado para obtener el material fisico que necesito.
* HU-05 Como dueno quiero aceptar o rechazar una solicitud
*        para decidir a quien entregar mi material.
* RF-10 Solicitar un material disponible
* RF-11 Rechazar solicitud si el material ya fue intercambiado
* RF-12 Rechazar solicitud duplicada para el mismo material
* RF-13 Aceptar o rechazar una solicitud pendiente
* RF-14 Rechazar accion si la solicitud ya fue procesada
* RF-15 Avisar si no hay solicitudes pendientes

* Criterios: CA-04.1, CA-04.2, CA-04.3, CA-05.1, CA-05.2, CA-05.3
* Estructuras: Arreglo con contador
* ============================================================== */

/* Representa una solicitud de intercambio de un material.
 * Guarda el material solicitado, el usuario que lo solicita,
 * la fecha y el estado de la solicitud.
 */
public class SolicitudIntercambio {

    // Identificador de la solicitud.
    String id_solicitud;

    // ID del material que se solicita.
    String id_material;

    // ID del usuario que realiza la solicitud.
    String id_solicitante;

    // Fecha en la que se realizo la solicitud.
    String fecha_solicitud;

    // Estado en el que se encuentra la solicitud.
    String estado_solicitud;

    /*
     * Recibe los datos para crear una solicitud de intercambio.
     * this permite distinguir los datos del objeto
     * de los valores que recibe el constructor.
     */
    public SolicitudIntercambio(String id_solicitud, String id_material, String id_solicitante, String fecha_solicitud, String estado_solicitud) {
        this.id_solicitud = id_solicitud;
        this.id_material = id_material;
        this.id_solicitante = id_solicitante;
        this.fecha_solicitud = fecha_solicitud;
        this.estado_solicitud = estado_solicitud;
    }

    /*
     * Devuelve los datos de la solicitud en una sola linea,
     * separados por |, para guardarlos en un archivo.
     * El orden es: solicitud, material, solicitante, fecha y estado.
     */
    @Override
    public String toString() {
        return id_solicitud + "|" + id_material + "|" + id_solicitante + "|" + fecha_solicitud + "|" + estado_solicitud;
    }
}