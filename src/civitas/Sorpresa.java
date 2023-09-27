
package civitas;

import java.util.ArrayList;

public abstract class Sorpresa {
    
    private String texto;
    protected int valor;
    private MazoSorpresas mazo;

    Sorpresa (String text, int value){
      texto = text;
      valor = value;
    }

    abstract void aplicarAJugador(int actual,  ArrayList<Jugador> todos);

    protected void informe(int actual,  ArrayList<Jugador> todos){
      Diario.getInstance().ocurreEvento("Sorpresa " + this + " al jugador " + todos.get(actual));
    }

    public String toString(){
      return texto;
    }
}
