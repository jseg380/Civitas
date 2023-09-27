
package civitas;

import java.util.ArrayList;

public class Tablero {
   private ArrayList<Casilla> casillas;
   private boolean porSalida;
   
   Tablero () {
       casillas = new ArrayList<Casilla>();
       casillas.add(new Casilla("SALIDA"));
       porSalida = false;
   }
   
   private boolean correcto (int numCasilla) {
       return (numCasilla >= 0 && numCasilla < casillas.size());
   }
   
   boolean computarPasoPorSalida () {
       boolean porSalidaTmp = porSalida;
       
       porSalida = false;
       
       return porSalidaTmp;
   }
   
   void añadeCasilla (Casilla casilla) {
       casillas.add(casilla);
   }
   
   public Casilla getCasilla (int numCasilla) {
       Casilla aDevolver = null;
       
       if (correcto(numCasilla))
           aDevolver = casillas.get(numCasilla);
       
       return aDevolver;
   }
   
   public ArrayList<Casilla> getCasillas() {
       return casillas;
   }
   
   int nuevaPosicion (int actual, int tirada) {
       int posicion = actual;
       
       posicion = (actual + tirada) % casillas.size();
       
       if (posicion != (actual + tirada))
           porSalida = true;
       
       return posicion;
   }
}
