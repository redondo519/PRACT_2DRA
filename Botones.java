import com.sun.jna.Native;
import com.sun.jna.plataform.win32.User32;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/*
Redondo
* Alonso
* David
*/
public class Botones {






    //Niveles de juego (hay 9) van en funcion de los golpes (3,6,9,10,12,15,18,20,27)
    //Inicialmente comienza en nivel "normal" = 15 golpes.
    public static int seleccionarNivel(int selecUsuario) {

        Scanner sc = new Scanner(System.in);
        switch (selecUsuario) {
            case 1 -> { //Nivel 1: más facil "fácil"
                return 3;
            }
            case 2 -> { //Nivel 2: "iniciación"
                return 6;

            }
            case 3 -> { //Nivel 3: "principiante"
                return 9;
            }
            case 4 -> { //Nivel 4: "dominando"
                return 10;
            }
            case 5 -> { //Nivel 5: "avanzado"
                return 12;
            }
            case 6 -> { //Nivel normal: "normal"
                return 15;
            }
            case 7 -> { //nivel 7  "avanzado2"
                return 18;
            }
            case 8 -> { //nivel 8 "extremo"
                return 20;
            }
            case 9 -> { //nivel 9 mas dificil "casi imposible"
                return 27;
            }
            default -> {
                return 15; //Por defecto nivel normal
            }

        }

    }


    //funcion crear tablero, se le pasa el numero de golpes
    public static int[][] crearTablero(int numGolpesInicio) {
        int numGolpes;
        Random r = new Random();


        //arrray tablero 8 por 8 a CEROS
        int[][] tablero = new int[8][8];
        for (int i = 1; i <= tablero.length - 1; i++) {
            for (int j = 0; j <= tablero.length - 1; j++) {
                tablero[i][j] = 0;
            }
        }
        //suma en posiciones i,j random
        for (int i = 0; i < numGolpesInicio; i++) {
            //realiza tantas veces como golpes de nivel
            int p1 = r.nextInt(1, 7);
            int p2 = r.nextInt(1, 7);
            tablero[p1][p2]++; //Golpe positivo en coordenada generada

        }
        return tablero; //Devuelve tablero para mostrarTablero()


    }

    //Copia del tablero para poder reiniciarlo al pulsar R
    /*
    public static void copiaTablero(int[][] tablero){

    }*/


    //funcion mostrar tablero
    public static void mostrarTablero(int[][] tablero) {

        System.out.println("________________________\n");
        System.out.print("------------------------");
        for (int i = 1; i <= 6; i++) {
            System.out.println();
            for (int j = 1; j <= 6; j++) {
                System.out.print(tablero[i][j] + " | ");
            }
            System.out.print("\n------------------------");
        }
        System.out.println("\n________________________");
    }


    //funcion al pulsar  L  Cambio de nivel
    public static int cambioNivel(char l) {
        int nivel = 15;
        if (l == 'l') {
            Scanner sc2 = new Scanner(System.in);
            System.out.println("Introduce el nivel que quieres jugar (1-9)");
            nivel = sc2.nextInt();

        }
        return nivel;
    }









    public class Main{
        //FINAL int
        public static final  int LETRA_L = 0x4c; //Letra L
        public static final  int LETRA_R = 0x52; //Letra R
        public static final  int LETRA_N = 0x4e; //Letra N

        //PRESSED
        private static boolean lPressed = false;
        private static boolean rPressed = false;
        private static boolean nPressed = false;

        //WINDOWS
        public interface  Kernel32 extends com.sun.jna.plataform.win32.Kernel32 {
            Kernel32 INSTANCE = (Kernel32) Native.load("user32", User32.class);

            //Definir la funcion de windows que lee un caracter e la consola
            boolean GetAsyncKeyState(int vKey);

        }

    }



    //desarrollo del programa principal
    public static void main(String[] args) {

        //Bucle hasta pulsar S
        //Añadir while(true)

        //INSTRUCCIONES A
        System.out.println("Nuevo ( N ) - Recomenzar ( R ) - Deshacer ( U ) - Salir ( S ) \n");

        //Por defecto el nivel es el 6 - Normal de 15 golpes
        int nivel = 6; // nivel se cambia con funcion cambioNivel

        //LLamada a la funcion mostrarTablero (creatablero (num golpes nivel))
        mostrarTablero(crearTablero(seleccionarNivel(nivel)));

        //INSTRUCCIONES B
        System.out.printf("\n Nivel de juego ( L ) : %d\n ", seleccionarNivel(nivel));      //DEVUELVA TAMBIEN "normal"
        System.out.print("Instrucciones:\n" +
                "\tMueva el cursor a un botón del tablero (con las flechas).\n" +
                "\tPulse 'return'\n" +
                "\tpara decrementar el valor de ese botón en 1,\n" +
                "\ty también los valores de sus 4 vecinos.\n" +
                "Objetivo:\n" +
                "\tDejar todos los botones en '0'.");


            /*
            Scanner sc = new Scanner(System.in);
            System.out.print("\n");
            char letra = sc.next().toLowerCase().charAt(0);


            if (letra == 'l'){
                cambioNivel(letra);  // Guardar el nuevo nivel
                mostrarTablero(crearTablero(seleccionarNivel(nivel))); // Actualizar el tablero con el nuevo nivel
            } else if (letra == 'n') {
                mostrarTablero(crearTablero(seleccionarNivel(6)));
            }else if (letra == 's'){
                break;
            }
             */


    }
}
