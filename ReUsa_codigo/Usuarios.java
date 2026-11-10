
 // Administra los usuarios registrados y permite iniciar sesion,
 // consultar, actualizar, eliminar y recuperar sus cuentas.
 // Tambien se encarga de pedir el guardado y la carga de sus datos.
 
public class Usuarios {

    // Guarda hasta 100 usuarios.
    static Usuario[] datos = new Usuario[100];

    // Indica cuantos usuarios hay guardados en el arreglo.
    static int cantidad = 0;

    // Guarda el ID de quien inicio sesion. Si es null, no hay sesion activa.
    static String actual = null;

    // Guarda los usuarios eliminados para poder recuperarlos.
    static PilaUsuario eliminados = new PilaUsuario(100);

    // Muestra las opciones de usuarios y realiza la que se elija.
    static void menu() {
        System.out.println("1. Registrar  2. Iniciar sesion  3. Listar  4. Consultar ID\n5. Buscar nombre  6. Actualizar mis datos  7. Eliminarme  8. Deshacer  0. Volver");

        switch (Main.numero("Opcion: ")) {
            case 1: registrar(); break;
            case 2: iniciarSesion(); break;
            case 3: listar(); break;
            case 4: consultar(); break;
            case 5: buscarTexto(); break;
            case 6: actualizar(); break;
            case 7: eliminar(); break;
            case 8: deshacer(); break;
            case 0: break;
            default: System.out.println("Opcion invalida.");
        }
    }

    
     //Busca un usuario por su ID y devuelve su posicion.
     //Si no lo encuentra, devuelve -1.
     
    static int buscar(String id) {

        // Revisa solamente las posiciones que tienen usuarios.
        for (int i = 0; i < cantidad; i++) {

            // Compara los ID sin distinguir mayusculas y minusculas.
            if (datos[i].id_usuario.equalsIgnoreCase(id)) {
                return i;
            }
        }

        return -1;
    }

    // Registra un usuario si hay espacio y su ID y correo no estan repetidos.
    static void registrar() {

        // No permite agregar mas usuarios si el arreglo esta lleno.
        if (cantidad == datos.length) {
            System.out.println("No hay espacio.");
            return;
        }

        String id = Main.texto("ID: ");

        // Si la busqueda devuelve una posicion, el ID ya esta registrado.
        if (buscar(id) != -1) {
            System.out.println("El ID ya existe.");
            return;
        }

        String nombre = Main.texto("Nombre: ");
        String correo = Main.texto("Correo: ");

        // Comprueba que otra cuenta no tenga el mismo correo.
        for (int i = 0; i < cantidad; i++) {
            if (datos[i].correo.equalsIgnoreCase(correo)) {
                System.out.println("El correo ya existe.");
                return;
            }
        }

        String clave = Main.texto("Contrasena: ");
        String fecha = Main.texto("Fecha de registro: ");

        // Crea el usuario y lo guarda en la siguiente posicion libre.
        datos[cantidad] = new Usuario(id, nombre, correo, clave, fecha);

        // Aumenta el total porque se agrego un usuario.
        cantidad++;

        System.out.println("Usuario registrado.");
    }

    // Revisa el correo y la contrasena para iniciar sesion.
    static void iniciarSesion() {
        String correo = Main.texto("Correo: ");
        String clave = Main.texto("Contrasena: ");

        for (int i = 0; i < cantidad; i++) {

            // El correo y la contrasena deben corresponder a la misma cuenta.
            if (datos[i].correo.equalsIgnoreCase(correo) && datos[i].contrasena.equals(clave)) {

                // Guarda el ID para identificar quien esta usando el programa.
                actual = datos[i].id_usuario;

                System.out.println("Bienvenido, " + datos[i].nombre);
                return;
            }
        }

        // Se llega aqui si ninguna cuenta coincide con los datos escritos.
        System.out.println("Datos incorrectos.");
    }

    
     //Comprueba si alguien inicio sesion.
     // Devuelve true si hay sesion y false si no la hay.
     
    static boolean haySesion() {
        if (actual == null) {
            System.out.println("Primero inicie sesion.");
            return false;
        }

        return true;
    }

    // Muestra los usuarios registrados y su cantidad, sin mostrar contrasenas.
    static void listar() {
        for (int i = 0; i < cantidad; i++) {
            System.out.println(datos[i].id_usuario + " | " + datos[i].nombre + " | " + datos[i].correo);
        }

        System.out.println("Total de usuarios: " + cantidad);
    }

    // Pide un ID y muestra el usuario que le corresponde.
    static void consultar() {
        int posicion = buscar(Main.texto("ID usuario: "));

        if (posicion == -1) {
            System.out.println("El usuario no existe.");
        } else {
            System.out.println(datos[posicion].id_usuario + " | " + datos[posicion].nombre);
        }
    }

    // Busca usuarios escribiendo el nombre completo o una parte.
    static void buscarTexto() {

        // Pasa el texto a minusculas para hacer la comparacion.
        String texto = Main.texto("Nombre o parte del nombre: ").toLowerCase();

        // Permite saber si se encontro al menos un usuario.
        boolean encontrado = false;

        for (int i = 0; i < cantidad; i++) {

            // Tambien pasa el nombre a minusculas y revisa si contiene el texto.
            if (datos[i].nombre.toLowerCase().contains(texto)) {
                System.out.println(datos[i].id_usuario + " | " + datos[i].nombre);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No hay coincidencias.");
        }
    }

    // Permite cambiar los datos de quien tiene la sesion iniciada.
    static void actualizar() {
        if (!haySesion()) {
            return;
        }

        // Localiza la cuenta que se va a actualizar.
        int posicion = buscar(actual);

        String nombre = Main.texto("Nuevo nombre: ");
        String correo = Main.texto("Nuevo correo: ");

        // Revisa las otras cuentas, sin contar la que se esta actualizando.
        for (int i = 0; i < cantidad; i++) {
            if (i != posicion && datos[i].correo.equalsIgnoreCase(correo)) {
                System.out.println("El correo ya existe.");
                return;
            }
        }

        // Cambia los datos, pero conserva el ID y la fecha de registro.
        datos[posicion].nombre = nombre;
        datos[posicion].correo = correo;
        datos[posicion].contrasena = Main.texto("Nueva contrasena: ");

        System.out.println("Datos actualizados.");
    }

    
     // Elimina la cuenta de quien inicio sesion.
     // No permite eliminarla si tiene materiales o solicitudes asociados.
     
    static void eliminar() {
        if (!haySesion()) {
            return;
        }

        // Comprueba si el usuario tiene materiales registrados.
        for (int i = 0; i < Materiales.cantidad; i++) {
            if (Materiales.datos[i].id_usuario.equals(actual)) {
                System.out.println("No se elimina: tiene materiales asociados.");
                return;
            }
        }

        // Comprueba si el usuario ha registrado solicitudes.
        for (int i = 0; i < Operaciones.cantidadSolicitudes; i++) {
            if (Operaciones.solicitudes[i].id_solicitante.equals(actual)) {
                System.out.println("No se elimina: tiene solicitudes asociadas.");
                return;
            }
        }

        int posicion = buscar(actual);

        // Primero guarda el usuario en la pila para poder recuperarlo.
        // Si la pila esta llena, deja la cuenta en el arreglo.
        if (!eliminados.apilar(datos[posicion])) {
            System.out.println("La pila esta llena. No se elimina.");
            return;
        }

        // Mueve los siguientes usuarios una posicion a la izquierda.
        // Asi no queda un espacio vacio entre los registros.
        for (int i = posicion; i < cantidad - 1; i++) {
            datos[i] = datos[i + 1];
        }

        // Reduce el total y limpia la posicion que ya no se usa.
        cantidad--;
        datos[cantidad] = null;

        // Cierra la sesion porque la cuenta acaba de eliminarse.
        actual = null;

        System.out.println("Usuario eliminado. Sesion cerrada.");
    }

    
     //Recupera el ultimo usuario eliminado.
     // Pide su correo y contrasena antes de devolverlo al arreglo.
     
    static void deshacer() {

        // Para restaurar debe haber un usuario en la pila y espacio en el arreglo.
        if (eliminados.estaVacia() || cantidad == datos.length) {
            System.out.println("No hay usuario para restaurar o no hay espacio.");
            return;
        }

        // Consulta el ultimo eliminado sin sacarlo todavia de la pila.
        Usuario usuario = eliminados.cima();

        String correo = Main.texto("Correo del ultimo usuario eliminado: ");
        String clave = Main.texto("Contrasena: ");

        // Si los datos no coinciden, el usuario permanece en la pila.
        if (!usuario.correo.equalsIgnoreCase(correo) || !usuario.contrasena.equals(clave)) {
            System.out.println("Datos incorrectos.");
            return;
        }

        // Evita restaurarlo si otra cuenta ya esta usando su ID.
        if (buscar(usuario.id_usuario) != -1) {
            System.out.println("El ID ya esta en uso.");
            return;
        }

        // Tambien comprueba que su correo no haya sido usado por otra cuenta.
        for (int i = 0; i < cantidad; i++) {
            if (datos[i].correo.equalsIgnoreCase(usuario.correo)) {
                System.out.println("El correo ya esta en uso.");
                return;
            }
        }

        // Ahora si lo retira de la pila y lo agrega al final del arreglo.
        datos[cantidad] = eliminados.desapilar();
        cantidad++;

        System.out.println("Usuario restaurado. Inicie sesion.");
    }

    // Pide a Archivos que guarde los usuarios y devuelve el resultado.
    static boolean guardar() {
        return Archivos.guardar("usuarios.txt", datos, cantidad);
    }

    // Lee el archivo y vuelve a crear los usuarios al iniciar el programa.
    static void cargar() {
        String[] lineas = Archivos.cargar("usuarios.txt");

        // Recorre las lineas leidas hasta encontrar una posicion sin informacion.
        for (int i = 0; i < lineas.length && lineas[i] != null; i++) {

            // Separa los datos por | y conserva los campos vacios, si los hay.
            String[] campos = lineas[i].split("\\|", -1);

            // Usa los cinco campos para crear el usuario y agregarlo al arreglo.
            datos[cantidad] = new Usuario(campos[0], campos[1], campos[2], campos[3], campos[4]);
            cantidad++;
        }
    }
}