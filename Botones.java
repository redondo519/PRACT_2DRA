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
    private static int numGolpes = 0;                   //Golpes que se han realizado
    private static int numCeros = 0;                    //Cantidad de 0s del tablero mostrado
    private static int golpesPrevistos = 15;            //Golpes previstos para el nivel predeterminado (modifica en selecionarNivel())
    private static int nivel = 6;                       //Nivel por defecto
    private static int corcheteX = 1;                   //Posicion del corchete en el eje X (columnas)
    private static int corcheteY = 1;                   //Posicion del corchete en el eje Y (filas)
    private static int[] golpesX = new int[0];         //array almacena posiciones donde hemos golpeado eje X
    private static int[] golpesY = new int[0];         //array almacena posiciones donde hemos golpeado eje Y
    private static int[][] tablero = new int[8][8];    //tablero inicial
    private static int[][] tableroCopia;                //tablero para copiar
    private static Random r = new Random();             //Se utiliza en la creación del tablero.


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

    public static final int NUM1 = 0x31;
    public static final int NUM2 = 0x32;
    public static final int NUM3 = 0x33;
    public static final int NUM4 = 0x34;
    public static final int NUM5 = 0x35;
    public static final int NUM6 = 0x36;
    public static final int NUM7 = 0x37;
    public static final int NUM8 = 0x38;
    public static final int NUM9 = 0x39;


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


    //WINDOWS integración usuario
    public interface Kernel32 extends com.sun.jna.platform.win32.Kernel32 {
        Kernel32 INSTANCE = (Kernel32) Native.load("user32", User32.class);

        //Definir la funcion de windows que lee un caracter e la consola
        boolean GetAsyncKeyState(int vKey);

    }


    //desarrollo del programa principal
    public static void main(String[] args) {

        String opcion = "A";
        crearTableroCorchetes(golpesPrevistos);
        do {
            //Limpiar pantalla al inicio
            borrarPantalla();

            //INSTRUCCIONES MENÚ
            System.out.println("Nuevo ( N ) - Recomenzar ( R ) - Deshacer ( U ) - Salir ( S ) \n");

            //mostrarTablero(crearTablero(golpesPrevistos));
            mostrarTableroCorchete(corcheteX, corcheteY);

            //INSTRUCCIONES
            System.out.printf("\n Nivel de juego ( L ) : %d\n ", seleccionarNivel(nivel));      //DEVUELVA TAMBIEN "normal"
            System.out.print("Instrucciones:\n" +
                    "\tMueva el cursor a un botón del tablero (con las flechas).\n" +
                    "\tPulse 'return'\n" +
                    "\tpara decrementar el valor de ese botón en 1,\n" +
                    "\ty también los valores de sus 4 vecinos.\n" +
                    "Objetivo:\n" +
                    "\tDejar todos los botones en '0'.");

            //Comprueba si el tablero esta a 0s
            if (!enJuego(numCeros)) {
                //Si esta acabado, lanzo mensajes de  fin de juego
                finJuego();
            } else { //No esta a 0s aun /Continuación juego

                while (true) {

                    //TECLAS

                    //flecha dercha
                    if ((User32.INSTANCE.GetAsyncKeyState(VK_RIGHT) & 0x8000) != 0) {
                        if (!rightPressed) { //Si esta pulsada
                            corcheteX = moverRight(corcheteX); //llamamos a su funcion.
                            rightPressed = true;
                            break;
                        }
                    } else {
                        rightPressed = false;  //Restablecemos al soltar la tecla
                    }

                    //flecha izquierda
                    if ((User32.INSTANCE.GetAsyncKeyState(VK_LEFT) & 0x8000) != 0) {
                        if (!leftPressed) {
                            corcheteX = moverLeft(corcheteX); //llamamos a su funcion.
                            leftPressed = true;
                            break;
                        }
                    } else {
                        leftPressed = false;  //Restablecemos al soltar la tecla
                    }

                    //flecha arriba
                    if ((User32.INSTANCE.GetAsyncKeyState(VK_UP) & 0x8000) != 0) {
                        if (!upPressed) {
                            corcheteY = moverUP(corcheteY); //llamamos a su funcion.
                            upPressed = true;
                            break;
                        }
                    } else {
                        upPressed = false;
                    }

                    //flecha abajo
                    if ((User32.INSTANCE.GetAsyncKeyState(VK_DOWN) & 0x8000) != 0) {
                        if (!downPressed) {
                            corcheteY = moverDown(corcheteY); //llamamos a su funcion
                            downPressed = true;
                            break;
                        }
                    } else {
                        downPressed = false;
                    }

                    //Enter
                    if ((User32.INSTANCE.GetAsyncKeyState(VK_RETURN) & 0x8000) != 0) {
                        if (!enterPressed) {
                            golpear(corcheteY, corcheteX, numGolpes); //llamamos a su funcion
                            enterPressed = true;
                            break;
                        }
                    } else {
                        enterPressed = false;
                    }
                    //letra S
                    if ((User32.INSTANCE.GetAsyncKeyState(LETRA_S) & 0x8000) != 0) {
                        if (!sPressed) {
                            System.out.println("\nGracias por jugar\nFIN DEL PROGRAMA");
                            opcion = "S";
                            sPressed = true;
                            break;
                        }
                    } else {
                        sPressed = false;
                    }
                    //letra R
                    if ((User32.INSTANCE.GetAsyncKeyState(LETRA_R) & 0x8000) != 0) {
                        if (!rPressed) {
                            copiarTableroCorchete();
                            rPressed = true;
                            break;
                        }
                    } else {
                        rPressed = false;
                    }
                    //letra L
                    if ((User32.INSTANCE.GetAsyncKeyState(LETRA_L) & 0x8000) != 0) {
                        if (!lPressed) {
                            cambioNivel();

                            while (true) {
                                //Numero 1
                                if ((User32.INSTANCE.GetAsyncKeyState(NUM1) & 0x8000) != 0) {
                                    if (!num1Pressed) {
                                        nivel = 1;
                                        seleccionarNivel(nivel);
                                        num1Pressed = true;
                                        break;
                                    }
                                } else {
                                    num1Pressed = false;
                                }
                                //Numero 2
                                if ((User32.INSTANCE.GetAsyncKeyState(NUM2) & 0x8000) != 0) {
                                    if (!num2Pressed) {
                                        nivel = 2;
                                        seleccionarNivel(nivel);
                                        num2Pressed = true;
                                        break;
                                    }
                                } else {
                                    num2Pressed = false;
                                }
                                //Numero 3
                                if ((User32.INSTANCE.GetAsyncKeyState(NUM3) & 0x8000) != 0) {
                                    if (!num3Pressed) {
                                        nivel = 3;
                                        seleccionarNivel(nivel);
                                        num3Pressed = true;
                                        break;
                                    }
                                } else {
                                    num3Pressed = false;
                                }
                                //Numero 4
                                if ((User32.INSTANCE.GetAsyncKeyState(NUM4) & 0x8000) != 0) {
                                    if (!num4Pressed) {
                                        nivel = 4;
                                        seleccionarNivel(nivel);
                                        num4Pressed = true;
                                        break;
                                    }
                                } else {
                                    num4Pressed = false;
                                }
                                //Numero 5
                                if ((User32.INSTANCE.GetAsyncKeyState(NUM5) & 0x8000) != 0) {
                                    if (!num5Pressed) {
                                        nivel = 5;
                                        seleccionarNivel(nivel);
                                        num5Pressed = true;
                                        break;
                                    }
                                } else {
                                    num5Pressed = false;
                                }
                                if ((User32.INSTANCE.GetAsyncKeyState(NUM6) & 0x8000) != 0) {
                                    if (!num6Pressed) {
                                        nivel = 6;
                                        seleccionarNivel(nivel);
                                        num6Pressed = true;
                                        break;
                                    }
                                } else {
                                    num6Pressed = false;
                                }
                                //Numero 7
                                if ((User32.INSTANCE.GetAsyncKeyState(NUM7) & 0x8000) != 0) {
                                    if (!num7Pressed) {
                                        nivel = 7;
                                        seleccionarNivel(nivel);
                                        num7Pressed = true;
                                        break;
                                    }
                                } else {
                                    num7Pressed = false;
                                }
                                //Numero 8
                                if ((User32.INSTANCE.GetAsyncKeyState(NUM8) & 0x8000) != 0) {
                                    if (!num8Pressed) {
                                        nivel = 8;
                                        seleccionarNivel(nivel);
                                        num8Pressed = true;
                                        break;
                                    }
                                } else {
                                    num8Pressed = false;
                                }
                                //Numero 9
                                if ((User32.INSTANCE.GetAsyncKeyState(NUM9) & 0x8000) != 0) {
                                    if (!num9Pressed) {
                                        nivel = 9;
                                        seleccionarNivel(nivel);
                                        num8Pressed = true;
                                        break;
                                    }
                                } else {
                                    num9Pressed = false;
                                }

                            }//Fin while numeros
                        }
                    } else {
                        sPressed = false;
                    }
                    //letra N
                    if ((User32.INSTANCE.GetAsyncKeyState(LETRA_N) & 0x8000) != 0) {
                        if (!nPressed) {
                            crearTableroCorchetes(golpesPrevistos);
                            nPressed = true;
                            break;
                        }
                    } else {
                        nPressed = false;
                    }
                    //Letra U
                    if ((User32.INSTANCE.GetAsyncKeyState(LETRA_U) & 0x8000) != 0) {
                        if (!uPressed) {

                            if (numGolpes > 0) {
                                deshacerGolpe();
                                numGolpes--;

                                if (numGolpes == 0) {
                                    //Si es 0, no hay golpes, por lo que vacio los arrays
                                    golpesX = new int[0];
                                    golpesY = new int[0];
                                } else {
                                    //Creo array con un espacio menos
                                    int[] nuevogolpesX = new int[numGolpes];
                                    int[] nuevogolpesY = new int[numGolpes];

                                    for (int i = 0; i < numGolpes; i++) {
                                        nuevogolpesX[i] = golpesX[i];
                                        nuevogolpesY[i] = golpesY[i];
                                    }

                                    //Reemplazo por los nuevos arrays
                                    golpesX = nuevogolpesX;
                                    golpesY = nuevogolpesY;
                                }
                            } else {
                                System.out.println("No existen retrocesos posibles");
                            }
                            uPressed = true;
                            break;
                        }
                    } else {
                        uPressed = false;
                    }


                }//FIN while(true)

            }//FIN else


        } while (!opcion.equals("S")); //Bucle hasta presionar S

    }//FIN main


    //GOLPES
    //Metodo golpear
    public static void golpear(int x, int y, int golpe) {

        //Creamos dos arrays nuevos con una posicion mas
        int[] nuevoGolpesX = new int[golpe + 1];
        int[] nuevoGolpesY = new int[golpe + 1];


        for (int i = 0; i < golpe; i++) {
            nuevoGolpesX[i] = golpesX[i];
            nuevoGolpesY[i] = golpesY[i];
        }

        //Nuevo golpe añadido
        nuevoGolpesX[golpe] = x;
        nuevoGolpesY[golpe] = y;

        //Arrays reemplazados
        golpesX = nuevoGolpesX;
        golpesY = nuevoGolpesY;

        //Aumentamos la varible contadora de golpes
        numGolpes++;

        //Dar golpe en la posicion marcada con el cursor
        tablero[corcheteX][corcheteY]--;

        //Sumar  a las casillas vecinas
        tablero[corcheteX][corcheteY - 1]++;
        tablero[corcheteX][corcheteY + 1]++;
        tablero[corcheteX - 1][corcheteY]++;
        tablero[corcheteX + 1][corcheteY]++;

        //Comprobacion suma y resta correcta. Comprobación numero de 0s
        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 6; j++) {
                if (tablero[i][j] == -1) {
                    tablero[i][j] = 3;
                } else if (tablero[i][j] == 4) {
                    tablero[i][j] = 0;
                } else if (tablero[i][j] == 0) {
                    numCeros++;
                }
            }
        }


    }

    //Metodo copia del tablero para poder reiniciarlo
    public static void copiarTableroCorchete() {
        for (int i = 1; i < 6; i++) {
            for (int j = 1; j < 6; j++) {

                tablero[i][j] = tableroCopia[i][j];
            }

        }
    }


    //metodo deshacer a movimiento anterior
    public static void deshacerGolpe() {

        if (numGolpes > 0) {
            //Buscamos en el array lel ultimo golpe dado
            int fila = golpesY[numGolpes - 1];
            int columna = golpesX[numGolpes - 1];

            //Golpe a la inversa
            //Dar golpe en la posicion marcada con el cursor
            tablero[fila][columna]++;

            //Sumar  a las casillas vecinas
            tablero[fila][columna - 1]--;
            tablero[fila][columna + 1]--;
            tablero[fila - 1][columna]--;
            tablero[fila + 1][columna]--;

        }
    }


    //METODOS FLECHAS

    //mover arriba
    public static int moverLeft(int fila) {
        fila--; //Restamos en eje Y (subimos)

        //Si supera la cima del tablero : vuelve a la zona mas baja
        if (fila < 1) {
            fila = 6; //ultima posicion
        }
        return fila;

    }

    //mover abajo
    public static int moverRight(int fila) {
        fila++; //Sumamos en eje Y (bajamos )
        //Si superamos el final del tablero volvemos a la cima
        if (fila > 6) {
            fila = 1; //primera posicion
        }
        return fila;
    }

    //mover derecha
    public static int moverDown(int columna) {
        columna++;
        if (columna > 6) {
            columna = 1;
        }
        return columna;
    }

    //mover izquierda
    public static int moverUP(int fila) {
        fila--;
        if (fila < 1) {
            fila = 6;
        }
        return fila;
    }


    //Metodo borrar pantalla
    public static void borrarPantalla() {
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


    public static void crearTableroCorchetes(int golpesPrevistos) {
        numGolpes = 0;

        //arrray tablero 8 por 8 a CEROS
        int[][] tablero = new int[8][8];
        for (int i = 1; i <= tablero.length - 1; i++) {
            for (int j = 0; j <= tablero.length - 1; j++) {
                tablero[i][j] = 0;
            }
        }
        //suma en posiciones i,j random
        for (int i = 0; i < golpesPrevistos; i++) {
            //realiza tantas veces como golpes de nivel
            int p1 = r.nextInt(1, 7);
            int p2 = r.nextInt(1, 7);
            tablero[p1][p2]++; //Golpe positivo en coordenada generada

        }

    }


    //funcion mostrar tablero
    public static void mostrarTableroCorchete(int fila, int columna) {

        System.out.println("________________________\n");
        System.out.print("------------------------");
        for (int i = 1; i <= 6; i++) {
            System.out.println();
            for (int j = 1; j <= 6; j++) {
                if (i == fila && j == columna) {
                    System.out.print("[" + tablero[i][j] + "]" + " | ");
                } else {
                    System.out.print(tablero[i][j] + " | ");
                }

            }
            System.out.print("\n------------------------");
        }
        System.out.println("\n________________________");
    }


    //funcion al pulsar  L  Cambio de nivel
    public static void cambioNivel() {

        Scanner sc2 = new Scanner(System.in);
        System.out.println("\nIntroduce el nivel que quieres jugar (1-9)");

    }


    //Metodo contador ceros
    public static int contadorCeros(int[][] tablero) {
        return numCeros;
    }

    //Metodo Contador golpes
    public static int contadorGolpes() {
        return numGolpes;
    }


    //Comprobar seguimiento de partida.  Recibe cantidadceros de contadorCeros()
    public static boolean enJuego(int ceros) {
        boolean juego = true;

        if (ceros >= 36) {
            juego = false;
        }

        return juego;
    }

    //Mensajes de salida al terminar
    public static void finJuego() { //numero de golpes por entrada

        if (numGolpes == golpesPrevistos) {
            System.out.printf("\nPerfecto. Hecho en %d golpes.\n", numGolpes);
        } else if (numGolpes > golpesPrevistos) {
            System.out.printf("\nHecho en %d golpes.\n", numGolpes);
        } else {
            System.out.printf("\nExtraordinariamente bien: Hecho en %d golpes.\n", numGolpes);
        }

    }


}
