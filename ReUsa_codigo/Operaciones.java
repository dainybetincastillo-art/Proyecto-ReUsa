/*
 * Permite solicitar materiales, revisar las solicitudes recibidas
 * y registrar intercambios cuando el propietario acepta una solicitud.
 * Tambien muestra los totales y pide guardar o cargar estos registros.
 */
public class Operaciones {

    // Guarda hasta 100 solicitudes.
    static SolicitudIntercambio[] solicitudes = new SolicitudIntercambio[100];

    // Indica cuantas solicitudes hay registradas.
    static int cantidadSolicitudes = 0;

    // Guarda hasta 100 intercambios.
    static Intercambio[] intercambios = new Intercambio[100];

    // Indica cuantos intercambios hay registrados.
    static int cantidadIntercambios = 0;

    // Muestra las opciones de solicitudes e intercambios.
    static void menu() {
        System.out.println("1. Solicitar material  2. Ver solicitudes pendientes  3. Ver totales  0. Volver");

        switch (Main.numero("Opcion: ")) {
            case 1: solicitar(); break;
            case 2: verSolicitudes(); break;
            case 3: totales(); break;
            case 0: break;
            default: System.out.println("Opcion invalida.");
        }
    }

    // Registra una solicitud para un material disponible de otro usuario.
    static void solicitar() {

        // Para solicitar un material, primero se debe iniciar sesion.
        if (!Usuarios.haySesion()) {
            return;
        }

        // Comprueba que haya espacio para guardar otra solicitud.
        if (cantidadSolicitudes == solicitudes.length) {
            System.out.println("No hay espacio.");
            return;
        }

        // Busca el material usando el ID que se escribe.
        int posicion = Materiales.buscar(Main.texto("ID material: "));

        if (posicion == -1) {
            System.out.println("El material no existe.");
            return;
        }

        // Permite trabajar con el material encontrado sin repetir su posicion.
        Material material = Materiales.datos[posicion];

        // No permite solicitar un material propio ni uno que no este disponible.
        if (!material.estado.equals("disponible") || material.id_usuario.equals(Usuarios.actual)) {
            System.out.println("No puede solicitar ese material.");
            return;
        }

        // Evita que la misma persona tenga dos solicitudes pendientes
        // para el mismo material.
        for (int i = 0; i < cantidadSolicitudes; i++) {
            if (solicitudes[i].id_material.equals(material.id_material)
                    && solicitudes[i].id_solicitante.equals(Usuarios.actual)
                    && solicitudes[i].estado_solicitud.equals("pendiente")) {
                System.out.println("Ya tiene una solicitud pendiente.");
                return;
            }
        }

        String id = Main.texto("ID solicitud: ");

        // Comprueba que el ID de la solicitud no este registrado.
        for (int i = 0; i < cantidadSolicitudes; i++) {
            if (solicitudes[i].id_solicitud.equalsIgnoreCase(id)) {
                System.out.println("El ID de solicitud ya existe.");
                return;
            }
        }

        String fecha = Main.texto("Fecha de solicitud: ");

        // Crea la solicitud con el material, quien lo solicita y la fecha.
        // Queda pendiente hasta que el propietario tome una decision.
        solicitudes[cantidadSolicitudes] = new SolicitudIntercambio(
                id, material.id_material, Usuarios.actual, fecha, "pendiente");

        cantidadSolicitudes++;

        System.out.println("Solicitud registrada.");
    }

    /*
     * Muestra las solicitudes pendientes para los materiales del usuario actual.
     * Permite aceptarlas, rechazarlas o dejarlas pendientes.
     */
    static void verSolicitudes() {
        if (!Usuarios.haySesion()) {
            return;
        }

        // Permite saber si se encontro alguna solicitud para revisar.
        boolean hay = false;

        for (int i = 0; i < cantidadSolicitudes; i++) {

            // Busca el material relacionado con cada solicitud.
            int posicion = Materiales.buscar(solicitudes[i].id_material);

            // Solo muestra solicitudes pendientes de materiales que existen
            // y que pertenecen a quien tiene la sesion iniciada.
            if (posicion != -1 && Materiales.datos[posicion].id_usuario.equals(Usuarios.actual)
                    && solicitudes[i].estado_solicitud.equals("pendiente")) {

                hay = true;
                System.out.println(solicitudes[i]);

                String accion = Main.texto("Aceptar (a), rechazar (r) o dejar pendiente (p): ");

                if (accion.equalsIgnoreCase("a")) {

                    // Envia las posiciones de la solicitud y del material
                    // al metodo que registra el intercambio.
                    aceptar(i, posicion);

                } else if (accion.equalsIgnoreCase("r")) {

                    // Cambia el estado sin registrar un intercambio.
                    solicitudes[i].estado_solicitud = "rechazada";

                } else if (!accion.equalsIgnoreCase("p")) {

                    // Si no se escribe a, r o p, avisa y no cambia el estado.
                    System.out.println("Opcion invalida. Sigue pendiente.");
                }

                // Si se escribe p, la solicitud se conserva sin cambios.
            }
        }

        if (!hay) {
            System.out.println("No tiene solicitudes pendientes.");
        }
    }

    /*
     * Registra el intercambio de una solicitud que se esta aceptando.
     * Recibe la posicion de la solicitud y la del material en sus arreglos.
     */
    static void aceptar(int posicionSolicitud, int posicionMaterial) {
        Material material = Materiales.datos[posicionMaterial];

        // Antes de continuar, revisa la disponibilidad y el espacio.
        if (!material.estado.equals("disponible") || cantidadIntercambios == intercambios.length) {
            System.out.println("Material no disponible o sin espacio para el intercambio.");
            return;
        }

        String id = Main.texto("ID intercambio: ");

        // Evita registrar dos intercambios con el mismo ID.
        for (int i = 0; i < cantidadIntercambios; i++) {
            if (intercambios[i].id_intercambio.equalsIgnoreCase(id)) {
                System.out.println("El ID de intercambio ya existe.");
                return;
            }
        }

        String fecha = Main.texto("Fecha de intercambio: ");

        // Guarda el intercambio con la solicitud, el material y sus hojas.
        intercambios[cantidadIntercambios] = new Intercambio(id,
                solicitudes[posicionSolicitud].id_solicitud, material.id_material,
                fecha, material.numero_hojas);

        cantidadIntercambios++;

        // La solicitud queda aceptada y el material deja de estar disponible.
        solicitudes[posicionSolicitud].estado_solicitud = "aceptada";
        material.estado = "intercambiado";

        // Rechaza las otras solicitudes pendientes para ese mismo material.
        // La aceptada no cambia porque ya no tiene estado pendiente.
        for (int i = 0; i < cantidadSolicitudes; i++) {
            if (solicitudes[i].id_material.equals(material.id_material)
                    && solicitudes[i].estado_solicitud.equals("pendiente")) {
                solicitudes[i].estado_solicitud = "rechazada";
            }
        }

        System.out.println("Intercambio registrado.");
    }

    // Muestra las cantidades registradas y suma las hojas de los intercambios.
    static void totales() {
        int hojas = 0;

        // Aunque el campo se llama hojas_ahorradas, aqui se usa para contar
        // hojas asociadas a registros, no para confirmar un ahorro real.
        for (int i = 0; i < cantidadIntercambios; i++) {
            hojas += intercambios[i].hojas_ahorradas;
        }

        System.out.println("Materiales: " + Materiales.cantidad);
        System.out.println("Solicitudes: " + cantidadSolicitudes);
        System.out.println("Intercambios: " + cantidadIntercambios);
        System.out.println("Hojas asociadas a intercambios registrados: " + hojas);
    }

    // Pide guardar las solicitudes y los intercambios en archivos separados.
    static boolean guardar() {
        boolean solicitudesGuardadas = Archivos.guardar("solicitudes.txt", solicitudes, cantidadSolicitudes);
        boolean intercambiosGuardados = Archivos.guardar("intercambios.txt", intercambios, cantidadIntercambios);

        // Solo devuelve true cuando los dos guardados se completan.
        return solicitudesGuardadas && intercambiosGuardados;
    }

    // Recupera las solicitudes y los intercambios al iniciar el programa.
    static void cargar() {

        // Primero lee las solicitudes guardadas.
        String[] lineas = Archivos.cargar("solicitudes.txt");

        for (int i = 0; i < lineas.length && lineas[i] != null; i++) {

            // Separa los cinco campos de cada solicitud.
            String[] campos = lineas[i].split("\\|", -1);

            solicitudes[cantidadSolicitudes] = new SolicitudIntercambio(
                    campos[0], campos[1], campos[2], campos[3], campos[4]);

            cantidadSolicitudes++;
        }

        // Reutiliza la variable para leer ahora el archivo de intercambios.
        lineas = Archivos.cargar("intercambios.txt");

        for (int i = 0; i < lineas.length && lineas[i] != null; i++) {
            String[] campos = lineas[i].split("\\|", -1);

            // Crea cada intercambio y convierte las hojas de texto a entero.
            intercambios[cantidadIntercambios] = new Intercambio(
                    campos[0], campos[1], campos[2], campos[3], Integer.parseInt(campos[4]));

            cantidadIntercambios++;
        }
    }
}