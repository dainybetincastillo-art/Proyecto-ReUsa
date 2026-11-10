/*======================================================
* Proyecto : ReUsa - Plataforma de materiales academicos
* Clase    : Main (menu principal)
* Programado por : Elber Jose Arrieta Jimenez y Dainy Betin Castillo
* Fecha : 20/10/2026
* --------------------------------------------------
* HU-01 a HU-09
* RF-01 a RF-22
* RN-01 a RN-06

* Estructuras: Arreglo con contador . Pila propia . Archivo de texto
* ============================================================== */

import java.util.Scanner;

// metodos que usamos para pedir textos y numeros.
public class Main {

    // Usamos este Scanner para leer lo que se escribe en la terminal.
    static Scanner teclado = new Scanner(System.in);

    // Inicia el programa y permite elegir las opciones del menu.
    public static void main(String[] args) {

        // Carga la informacion que se guardo la ultima vez.
        Usuarios.cargar();
        Materiales.cargar();
        Operaciones.cargar();

        // Guarda la opcion que el usuario elige.
        int opcion;

        // El menu se repite hasta que se elige guardar y salir.
        do {
            System.out.println("\nREUSA");
            System.out.println("1. Usuarios");
            System.out.println("2. Materiales");
            System.out.println("3. Solicitudes e intercambios");
            System.out.println("4. Guardar y salir");

            // Pide el numero de la opcion.
            opcion = numero("Opcion: ");

            // Abre la parte del programa que se haya elegido.
            switch (opcion) {

                // Muestra las opciones para trabajar con usuarios.
                case 1: Usuarios.menu(); break;

                // Muestra las opciones para trabajar con materiales.
                case 2: Materiales.menu(); break;

                // Muestra las opciones de solicitudes e intercambios.
                case 3: Operaciones.menu(); break;

                case 4:
                    // Intenta guardar toda la informacion.
                    // Cada variable indica si esa parte se guardo correctamente.
                    boolean usuariosGuardados = Usuarios.guardar();
                    boolean materialesGuardados = Materiales.guardar();
                    boolean operacionesGuardadas = Operaciones.guardar();

                    // Si alguna parte no se guarda, avisa y no cierra el programa.
                    if (!usuariosGuardados || !materialesGuardados || !operacionesGuardadas) {
                        System.out.println("No se guardo todo. Revise el error e intente otra vez.");

                        // Cambia la opcion para que el menu vuelva a aparecer.
                        opcion = 0;
                    }
                    break;

                // Avisa si se escribe una opcion que no esta en el menu.
                default: System.out.println("Opcion invalida.");
            }

        // Solo termina cuando la opcion queda en 4.
        } while (opcion != 4);
    }

    // Pide un texto y lo devuelve.
    //Si esta vacio o contiene |, lo vuelve a pedir.
     
    static String texto(String mensaje) {
        String valor;

        do {
            // Muestra lo que se le esta pidiendo al usuario.
            System.out.print(mensaje);

            // Lee la respuesta y quita los espacios del principio y del final.
            valor = teclado.nextLine().trim();

        // Repite si la respuesta no cumple las condiciones.
        } while (valor.isEmpty() || valor.contains("|"));

        return valor;
    }

    //Pide un numero entero.
    //Si lo escrito no se puede convertir a entero, devuelve -1.

    static int numero(String mensaje) {
        System.out.print(mensaje);

        try {
            // Lee la respuesta e intenta convertirla a numero entero.
            return Integer.parseInt(teclado.nextLine().trim());

        } catch (NumberFormatException error) {

            // Devuelve -1 para que el programa pueda manejar la entrada incorrecta.
            return -1;
        }
    }
}