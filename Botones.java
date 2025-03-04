import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/*
Redondo
* Alonso
* David
*/
public class Botones {


    //Declaracion de variables
    private static int numGolpes = 0;
    private static int numCeros = 0;
    private static int golpesPrevistos = 6;


    //Declaracion de integraciones de usuario
    public static final int LETRA_L = 0x4c; //Letra L
    public static final int LETRA_R = 0x52; //Letra R
    public static final int LETRA_N = 0x4e; //Letra N
    public static final int LETRA_S = 0x53; //Letra s
    public static final int LETRA_U = 0x55; //Letra U

    public static final int VK_UP = 0x26; //Tecla flecha arriba
    public static final int VK_DOWN = 0x28; //Tecla flecha abajo
    public static final int VK_LEFT = 0x25; //Tecla flecha izquierda
    public static final int VK_RIGHT = 0x27; //Tecla flecha derecha
    public static final int VK_RETURN = 0x0D;//Tecla enter

    public static final int num1 = 0x31;
    public static final int num2 = 0x32;
    public static final int num3 = 0x33;
    public static final int num4 = 0x34;
    public static final int num5 = 0x35;
    public static final int num6 = 0x36;
    public static final int num7 = 0x37;
    public static final int num8 = 0x38;
    public static final int num9 = 0x39;


    //PRESSED  Incialmente FALSE porque no se han pulsado
    private static boolean lPressed = false;
    private static boolean rPressed = false;
    private static boolean nPressed = false;
    private static boolean sPressed = false;
    private static boolean uPressed = false;

    private static boolean upPressed = false;
    private static boolean downPressed = false;
    private static boolean leftPressed = false;
    private static boolean rightPressed = false;
    private static boolean enterPressed = false;

    private static boolean num1Pressed = false;
    private static boolean num2Pressed = false;
    private static boolean num3Pressed = false;
    private static boolean num4Pressed = false;
    private static boolean num5Pressed = false;
    private static boolean num6Pressed = false;
    private static boolean num7Pressed = false;
    private static boolean num8Pressed = false;
    private static boolean num9Pressed = false;



    //WINDOWS
    public interface Kernel32 extends com.sun.jna.platform.win32.Kernel32 {
        Kernel32 INSTANCE = (Kernel32) Native.load("user32", User32.class);

        //Definir la funcion de windows que lee un caracter e la consola
        boolean GetAsyncKeyState(int vKey);

    }


    //Comprueba entrada usuario
    public static void compruebaTeclas() {
        while (true) {

            String opcion;

            //Verifica si S esta presionada y aun no ha sido regristada
            if ((User32.INSTANCE.GetAsyncKeyState(LETRA_S) & 0x8000) != 0) {
                //Si presionamos S fin del programa
                if (!sPressed) {
                    System.out.println("\nGracias por jugar\nFIN DEL PROGRAMA");
                    opcion = "S";
                    sPressed = true; //Ya se ha presionado
                    break;
                }
            } else {
                sPressed = false;  //Restablecemos al soltar la tecla
            }

            //Verifica si L esta presionada y aun no ha sido registrada
            if ((User32.INSTANCE.GetAsyncKeyState(LETRA_L) & 0x8000) != 0) {
                //Si presionamos L llamada a funcion cambio cambioNivel
                if (!lPressed) {
                    //seleccionarNivel(2);
                    opcion = "L";
                    lPressed = true; //Ya se ha presionado
                    break;
                }
            } else {
                lPressed = false;  //Restablecemos al soltar la tecla
            }

            //Tecla 1
            if ((User32.INSTANCE.GetAsyncKeyState(num1) & 0x8000) != 0) {
                //Si presionamos 1 lleva a el nivel 1
                if (!num1Pressed) {
                    //seleccionarNivel(1);
                    int nivel = 1;
                    num1Pressed = true; //Ya se ha presionado

                }
            } else {
                num1Pressed = false;  //Restablecemos al soltar la tecla
            }
            break;

        }
    }


    //Metodo borrar pantalla
    public static void borrarPantalla(){
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    //Niveles de juego (hay 9) van en funcion de los golpes (3,6,9,10,12,15,18,20,27)
    //Inicialmente comienza en nivel "normal" = 15 golpes.
    public static int seleccionarNivel(int selecUsuario) {


        Scanner sc = new Scanner(System.in);
        switch (selecUsuario) {
            case 1 -> { //Nivel 1: más facil "fácil"
                golpesPrevistos = 3;
                return 3;
            }
            case 2 -> { //Nivel 2: "iniciación"
                golpesPrevistos = 6;
                return 6;
            }
            case 3 -> { //Nivel 3: "principiante"
                golpesPrevistos = 9;
                return 9;
            }
            case 4 -> { //Nivel 4: "dominando"
                golpesPrevistos = 10;
                return 10;
            }
            case 5 -> { //Nivel 5: "avanzado"
                golpesPrevistos = 12;
                return 12;
            }
            case 6 -> { //Nivel normal: "normal"
                golpesPrevistos = 15;
                return 15;
            }
            case 7 -> { //nivel 7  "avanzado2"
                golpesPrevistos = 18;
                return 18;
            }
            case 8 -> { //nivel 8 "extremo"
                golpesPrevistos = 20;
                return 20;
            }
            case 9 -> { //nivel 9 mas dificil "casi imposible"
                golpesPrevistos = 27;
                return 27;
            }
            default -> {
                golpesPrevistos = 15;
                return 15; //Por defecto nivel normal
            }

        }

    }


    //funcion crear tablero, se le pasa el numero de golpes
    public static int[][] crearTablero(int numGolpesInicio) {

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
        return tablero; //Devuelve tablero

    }

    //Copia del tablero para poder reiniciarlo al pulsar R
    public static int[][] copiaTablero(int[][] tablero){

        int[][] tableroR;
        tableroR = tablero;

        mostrarTablero(tableroR);
        return tableroR;
    }





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



    //funcion letras
    public static void llamarFuncionesLetras(String letra){
        switch (letra) {
            case "L"->{
                //cambioNivel();
            }
            case "N"->{
                //crearTablero(seleccionarNivel(6));
            }
            case "R"->{
                //llamar al tablero copia sin modificar y mostrarle;
            }
            case "S"->{
                //llamar a funcion salir
            }
            case "U"->{
                //llamar a funcion deshacer
            }
        }

    }


    //funcion al pulsar  L  Cambio de nivel
    public static void cambioNivel() {

        int nivel;

        Scanner sc2 = new Scanner(System.in);
        System.out.println("\nIntroduce el nivel que quieres jugar (1-9)");
        compruebaTeclas();
        if (num1Pressed) {
            seleccionarNivel(1);
        } else {
            seleccionarNivel(6);
        }

    }

    //funcion deshacer movimiento. Vuelve al tablero antes de su ultimo golpe
    public static int[][] deshacer(int[][] tablero, int p1, int p2) {
        //Dar un golpe positivo en la posicion dada.
        tablero [p1][p2] ++;

        //Restar a las casillas vecinas
        tablero [p1][p2-1] --;
        tablero [p1][p2+1] --;
        tablero [p1-1][p2] --;
        tablero [p1+1][p2] --;

        //Comprobacion suma y resta correcta. Comprobación numero de 0s
        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 6; j++) {
                if(tablero[i][j] == -1){
                    tablero[i][j] = 3;
                } else if (tablero[i][j] == 4) {
                    tablero[i][j] = 0;
                }
            }
        }
        mostrarTablero(tablero);

        return tablero;

    }



    //Metodo dar golpe
    public static int[][] golpear(int[][] tablero, int p1, int p2) {

        numGolpes ++;
        //Dar golpe en la posicion marcada con el cursor
        tablero [p1][p2] --;

        //Sumar  a las casillas vecinas
        tablero [p1][p2-1] ++;
        tablero [p1][p2+1] ++;
        tablero [p1-1][p2] ++;
        tablero [p1+1][p2] ++;

        //Comprobacion suma y resta correcta. Comprobación numero de 0s
        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 6; j++) {
                if(tablero[i][j] == -1){
                    tablero[i][j] = 3;
                } else if (tablero[i][j] == 4) {
                    tablero[i][j] = 0;
                }else if (tablero[i][j] == 0) {
                    numCeros++;
                }
                }
            }

        mostrarTablero(tablero);

        return tablero;
    }

    //Metodo contador ceros
    public static int contadorCeros(int [][] tablero) {
        return numCeros;
    }

    //Metodo Contador golpes
    public static int contadorGolpes() {
        return numGolpes;
    }




    //Comprobar seguimiento de partida.  Recibe cantidadceros de contadorCeros()
    public static boolean enJuego(int ceros){
        boolean juego = true;

        if(ceros >= 36){
            juego = false;
        }

        return juego;
    }

    //Mensajes de salida al terminar
    public static void finJuego(boolean juego ){ //numero de golpes por entrada

        if(!juego){
            //SI hizo tantos golpes
            if (numGolpes == golpesPrevistos ){
                System.out.printf("\nPerfecto. Hecho en %d golpes.\n" , numGolpes);
            }else if(numGolpes > golpesPrevistos){
                System.out.printf("\nHecho en %d golpes.\n" , numGolpes);
            }else{
                System.out.printf("\nExtraordinariamente bien: Hecho en %d golpes.\n" , numGolpes);
            }

        }

    }


    //desarrollo del programa principal
    public static void main(String[] args) {


        //INSTRUCCIONES A
        System.out.println("Nuevo ( N ) - Recomenzar ( R ) - Deshacer ( U ) - Salir ( S ) \n");


        //Por defecto el nivel es el 6 - Normal de 15 golpes
        int nivel = 6; // nivel se cambia con funcion cambioNivel

        //LLamada a la funcion mostrarTablero (creatablero (num golpes nivel))
        int[][] tablero = crearTablero(seleccionarNivel(nivel));
        int [][] tableroCopia = copiaTablero(tablero);
        mostrarTablero(tablero);
        System.out.println(contadorCeros(tablero));
        //mostrarTablero(tableroCopia);
        //System.out.println(contadorCeros(tableroCopia));

        //LLamar a golpear
        int pos1 = 4;
        int pos2 = 3;
        golpear(tablero, pos1, pos2);
        borrarPantalla(); // en cmd funciona
        //mostrarTablero(tablero);
        System.out.println(contadorCeros(tablero));
        System.out.println();


        //INSTRUCCIONES B
        System.out.printf("\n Nivel de juego ( L ) : %d\n ", seleccionarNivel(nivel));      //DEVUELVA TAMBIEN "normal"
        System.out.print("Instrucciones:\n" +
                "\tMueva el cursor a un botón del tablero (con las flechas).\n" +
                "\tPulse 'return'\n" +
                "\tpara decrementar el valor de ese botón en 1,\n" +
                "\ty también los valores de sus 4 vecinos.\n" +
                "Objetivo:\n" +
                "\tDejar todos los botones en '0'.");


        //compruebaTeclas();









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
