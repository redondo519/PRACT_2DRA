/*
Redondo
* Alonso
* David
*/
public class botones {



    //funcion mostrar instrucciones + tablero
    public static void mostrarTablero(int [][] tablero){
        System.out.println("INSTRUCCIONES");
        System.out.println();
        for(int i = 1; i <=6;i++){
            System.out.println();
            for(int j = 1; j <=6;j++){
                System.out.print(tablero[i][j]+" ");


            }
        }


    }





    public static void main(String[] args) {

        //desarrollo del programa principal


        //arrray tablero de prueba 8 por 8 (Meter esto en una funcion crear tablero)
        int [][] tablero = new int[8][8];
        for(int i = 1; i <=6;i++){
            for(int j = 1; j <=6;j++){
                tablero[i][j] = 1;

            }
        }
        mostrarTablero(tablero);


    }
}
