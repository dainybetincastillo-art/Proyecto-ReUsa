
/*======================================================
* Proyecto : ReUsa - Plataforma de materiales academicos
* Entidad  : Material
* Programado por : Elber Jose Arrieta Jimenez
* Fecha : 20/10/2026
* --------------------------------------------------
* HU-02 Como estudiante quiero publicar un material
* RF-04 Publicar material con materia, tipo, estado, hojas, punto
* RF-05 Rechazar si el numero de hojas esta vacio
* RF-06 Rechazar si el numero de hojas es cero o negativo
* RN-06 Solo se publica si hay hojas mayores que cero

* Criterios: CA-02.1 (bueno) CA-02.2 (falla) CA-02.3 (falla)
* Estructuras: Arreglo con contador . Pila propia
* ============================================================== */

/*
 * Representa un material academico y guarda sus datos.
 * Las operaciones como publicar, buscar o eliminar
 * se realizan en la clase Materiales.
 */
public class Material {

    // Identificador del material.
    String id_material;

    // ID del usuario que publico el material.
    String id_usuario;

    // Materia a la que pertenece el material.
    String materia;

    // Tipo de material, por ejemplo: guia, taller, apunte o libro.
    String tipo;

    // Condicion fisica del material, por ejemplo: bueno o regular.
    String estado_fisico;

    // Cantidad de hojas del material.
    int numero_hojas;

    // Lugar propuesto para entregar el material.
    String punto_entrega;

    // Indica si el material esta disponible o intercambiado.
    String estado;

    // Fecha en la que se publico.
    String fecha_publicacion;

    /*
     * Recibe los datos para crear un material.
     * this permite distinguir los datos del objeto
     * de los valores que recibe el constructor.
     */
    public Material(String id_material, String id_usuario, String materia, String tipo, String estado_fisico, int numero_hojas, String punto_entrega, String estado, String fecha_publicacion) {
        this.id_material = id_material;
        this.id_usuario = id_usuario;
        this.materia = materia;
        this.tipo = tipo;
        this.estado_fisico = estado_fisico;
        this.numero_hojas = numero_hojas;
        this.punto_entrega = punto_entrega;
        this.estado = estado;
        this.fecha_publicacion = fecha_publicacion;
    }

    /*
     * Devuelve los datos del material en una sola linea, separados por |.
     * Se usa para mostrar el material y guardarlo en el archivo.
     * Este mismo orden se utiliza al recuperar sus datos.
     */
    @Override
    public String toString() {
        return id_material + "|" + id_usuario + "|" + materia + "|" + tipo + "|" + estado_fisico + "|" + numero_hojas + "|" + punto_entrega + "|" + estado + "|" + fecha_publicacion;
    }
}