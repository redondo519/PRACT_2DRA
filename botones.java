import java.util.Random;
import java.util.Scanner;

/*
Redondo
* Alonso
* David
*/
public class botones {


    //Niveles de juego (hay 9) van en funcion de los golpes (3,6,9,10,12,15,18,20,27)
    //Inicialmente comienza en nivel "normal" = 15 golpes.

    public static int seleccionarNivel(int selecUsuario){

        Scanner sc = new Scanner(System.in);
        switch(selecUsuario){
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
    public static int [][] crearTablero(int numGolpesInicio){
        int  numGolpes;
        Random r = new Random();


        //arrray tablero 8 por 8 a CEROS
        int [][] tablero = new int[8][8];
        for(int i = 1; i  <= tablero.length -1; i++){
            for(int j = 0;  j<= tablero.length -1; j++){
                tablero[i][j] = 0;
            }
        }
        //suma en posiciones i,j random
        for(int i = 0; i < numGolpesInicio; i++){
            int p1 = r.nextInt(1,7);
            int p2 = r.nextInt(1,7);
            tablero[p1][p2] ++;

        }
        return tablero;

    }


    //funcion mostrar tablero
    public static void mostrarTablero(int [][] tablero){

        System.out.print("------------");
        for(int i = 1; i <=6;i++){
            System.out.println();
            for(int j = 1; j <=6;j++){
                System.out.print(tablero[i][j]+" ");

            }
        }
        System.out.println("\n------------");
    }





    public static void main(String[] args) {

        //desarrollo del programa principal

        //INSTRUCIONES
        System.out.println("Nuevo ( N ) - Recomenzar ( R ) - Deshacer ( U ) - Salir ( S ) \n");
        //LLamada a la funcion mostrarTablero (creatablero (num golpes nivel))

        mostrarTablero(crearTablero(seleccionarNivel(3)));







    }
}
