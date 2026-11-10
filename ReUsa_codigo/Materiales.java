
 
 
 //Administra los materiales publicados y permite buscarlos,
 // consultarlos, actualizarlos, eliminarlos y recuperarlos.
 //Tambien calcula el total de hojas de los materiales registrados
 //y pide el guardado y la carga de sus datos.
 
public class Materiales {

    // Guarda hasta 100 materiales.
    static Material[] datos = new Material[100];

    // Indica cuantos materiales hay guardados en el arreglo.
    static int cantidad = 0;

    // Guarda los materiales eliminados para poder recuperarlos.
    static PilaMaterial eliminados = new PilaMaterial(100);

    // Muestra las opciones de materiales y realiza la que se elija.
    static void menu() {
        System.out.println("1. Publicar  2. Buscar  3. Listar  4. Eliminar  5. Deshacer  6. Consultar ID  7. Actualizar  0. Volver");

        switch (Main.numero("Opcion: ")) {
            case 1: publicar(); break;
            case 2: buscarTexto(); break;
            case 3: listar(); break;
            case 4: eliminar(); break;
            case 5: deshacer(); break;
            case 6: consultar(); break;
            case 7: actualizar(); break;
            case 0: break;
            default: System.out.println("Opcion invalida.");
        }
    }

    
     //Busca un material por su ID y devuelve su posicion.
     //Si no lo encuentra, devuelve -1.
     
    static int buscar(String id) {

        // Revisa solamente las posiciones que tienen materiales.
        for (int i = 0; i < cantidad; i++) {

            // Compara los ID sin distinguir mayusculas y minusculas.
            if (datos[i].id_material.equalsIgnoreCase(id)) {
                return i;
            }
        }

        return -1;
    }

    // Publica un material a nombre del usuario que tiene la sesion iniciada.
    static void publicar() {

        // Para publicar, primero se debe iniciar sesion.
        if (!Usuarios.haySesion()) {
            return;
        }

        // No permite agregar materiales si el arreglo esta lleno.
        if (cantidad == datos.length) {
            System.out.println("No hay espacio.");
            return;
        }

        String id = Main.texto("ID material: ");

        // Evita registrar otro material con el mismo ID.
        if (buscar(id) != -1) {
            System.out.println("El ID ya existe.");
            return;
        }

        String materia = Main.texto("Materia: ");
        String tipo = Main.texto("Tipo: ");
        String estadoFisico = Main.texto("Estado fisico: ");
        int hojas = Main.numero("Numero de hojas: ");

        // Rechaza cantidades menores que uno o entradas que no sean enteros.
        if (hojas <= 0) {
            System.out.println("Las hojas deben ser un entero mayor que cero.");
            return;
        }

        String punto = Main.texto("Punto de entrega: ");
        String fecha = Main.texto("Fecha de publicacion: ");

        // Crea el material con el ID de su propietario y estado disponible.
        // Lo agrega en la siguiente posicion libre del arreglo.
        datos[cantidad] = new Material(id, Usuarios.actual, materia, tipo,
                estadoFisico, hojas, punto, "disponible", fecha);

        cantidad++;

        System.out.println("Material publicado.");
    }

    // Busca materiales disponibles por el nombre completo o parcial de la materia.
    static void buscarTexto() {

        // Pasa la busqueda a minusculas para compararla con las materias.
        String texto = Main.texto("Materia o parte del nombre: ").toLowerCase();

        // Permite saber si se encontro al menos un resultado.
        boolean encontrado = false;

        for (int i = 0; i < cantidad; i++) {

            // Muestra el material si esta disponible y la materia contiene el texto.
            if (datos[i].estado.equals("disponible") && datos[i].materia.toLowerCase().contains(texto)) {
                System.out.println(datos[i]);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No hay materiales disponibles para esa busqueda.");
        }
    }

    // Muestra todos los materiales registrados y suma sus hojas.
    static void listar() {
        int hojas = 0;

        for (int i = 0; i < cantidad; i++) {
            System.out.println(datos[i]);

            // Acumula las hojas de cada material, sin importar su estado.
            hojas += datos[i].numero_hojas;
        }

        System.out.println("Materiales: " + cantidad + " | Hojas registradas: " + hojas);
    }

    
     //Elimina un material del usuario que tiene la sesion iniciada.
    // Si tiene solicitudes asociadas, no permite eliminarlo.
     
    static void eliminar() {
        if (!Usuarios.haySesion()) {
            return;
        }

        int posicion = buscar(Main.texto("ID material: "));

        // Detiene la operacion si el material no esta registrado.
        if (posicion == -1) {
            System.out.println("El material no existe.");
            return;
        }

        // Solo permite que el propietario elimine su material.
        if (!datos[posicion].id_usuario.equals(Usuarios.actual)) {
            System.out.println("El material no le pertenece.");
            return;
        }

        // Revisa si existe alguna solicitud relacionada, sin importar su estado.
        for (int i = 0; i < Operaciones.cantidadSolicitudes; i++) {
            if (Operaciones.solicitudes[i].id_material.equals(datos[posicion].id_material)) {
                System.out.println("No se elimina: tiene solicitudes asociadas.");
                return;
            }
        }

        // Primero guarda el material en la pila para poder recuperarlo.
        // Si la pila esta llena, deja el material en el arreglo.
        if (!eliminados.apilar(datos[posicion])) {
            System.out.println("La pila esta llena. No se elimina.");
            return;
        }

        // Mueve los siguientes materiales una posicion a la izquierda.
        // Asi no queda un espacio vacio entre los registros.
        for (int i = posicion; i < cantidad - 1; i++) {
            datos[i] = datos[i + 1];
        }

        // Reduce el total y limpia la posicion que ya no se usa.
        cantidad--;
        datos[cantidad] = null;

        System.out.println("Material eliminado.");
    }

    // Recupera el ultimo material eliminado si pertenece al usuario actual.
    static void deshacer() {
        if (!Usuarios.haySesion()) {
            return;
        }

        // Debe haber un material en la pila y espacio para devolverlo al arreglo.
        if (eliminados.estaVacia() || cantidad == datos.length) {
            System.out.println("No hay material para restaurar o no hay espacio.");
            return;
        }

        // Consulta el ultimo eliminado sin retirarlo todavia de la pila.
        Material material = eliminados.cima();

        // No permite restaurar el material de otra persona.
        if (!material.id_usuario.equals(Usuarios.actual)) {
            System.out.println("El ultimo eliminado pertenece a otro usuario.");
            return;
        }

        // Evita restaurarlo si su ID ya esta siendo usado por otro material.
        if (buscar(material.id_material) != -1) {
            System.out.println("El ID ya esta en uso.");
            return;
        }

        // Lo retira de la pila y lo agrega al final de los materiales registrados.
        datos[cantidad] = eliminados.desapilar();
        cantidad++;

        System.out.println("Material restaurado.");
    }

    // Pide un ID y muestra el material que le corresponde, cualquiera sea su estado.
    static void consultar() {
        int posicion = buscar(Main.texto("ID material: "));

        if (posicion == -1) {
            System.out.println("El material no existe.");
        } else {
            System.out.println(datos[posicion]);
        }
    }

    // Permite al propietario cambiar los datos de un material disponible.
    static void actualizar() {
        if (!Usuarios.haySesion()) {
            return;
        }

        int posicion = buscar(Main.texto("ID material: "));

        if (posicion == -1) {
            System.out.println("El material no existe.");
            return;
        }

        // Permite trabajar con el material encontrado sin repetir datos[posicion].
        // Los cambios hechos mediante esta variable afectan al mismo material.
        Material material = datos[posicion];

        // Debe pertenecer al usuario actual y seguir disponible.
        if (!material.id_usuario.equals(Usuarios.actual) || !material.estado.equals("disponible")) {
            System.out.println("No puede actualizar ese material.");
            return;
        }

        int hojas = Main.numero("Nuevo numero de hojas: ");

        // Comprueba la cantidad antes de modificar los datos.
        if (hojas <= 0) {
            System.out.println("Cantidad invalida.");
            return;
        }

        // Actualiza los campos permitidos.
        // Conserva el ID, el propietario, el estado y la fecha de publicacion.
        material.numero_hojas = hojas;
        material.materia = Main.texto("Nueva materia: ");
        material.tipo = Main.texto("Nuevo tipo: ");
        material.estado_fisico = Main.texto("Nuevo estado fisico: ");
        material.punto_entrega = Main.texto("Nuevo punto de entrega: ");

        System.out.println("Material actualizado.");
    }

    // Pide a Archivos que guarde los materiales y devuelve el resultado.
    static boolean guardar() {
        return Archivos.guardar("materiales.txt", datos, cantidad);
    }

    // Lee el archivo y vuelve a crear los materiales al iniciar el programa.
    static void cargar() {
        String[] lineas = Archivos.cargar("materiales.txt");

        // Recorre las lineas leidas hasta encontrar una posicion sin informacion.
        for (int i = 0; i < lineas.length && lineas[i] != null; i++) {

            // Separa los datos por | y conserva los campos vacios, si los hay.
            String[] campos = lineas[i].split("\\|", -1);

            // Crea el material con los nueve campos guardados.
            // El numero de hojas se convierte de texto a entero.
            datos[cantidad] = new Material(campos[0], campos[1], campos[2], campos[3],
                    campos[4], Integer.parseInt(campos[5]), campos[6], campos[7], campos[8]);

            cantidad++;
        }
    }
}