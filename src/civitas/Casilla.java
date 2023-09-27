
package civitas;

import java.util.ArrayList;

public class Casilla {

    protected String nombre;

    Casilla (String nombre){
        this.nombre = nombre;
    }

    void informe (int iactual, ArrayList<Jugador> todos) {
        Diario.getInstance().ocurreEvento("Jugador " + todos.get(iactual).getNombre()
                + " ha caido en la casilla " + toString());
    }

    void recibeJugador (int iactual, ArrayList<Jugador> todos) {
        informe(iactual, todos);
    }

    public String getNombre(){
        return nombre;
    }
    
    public String infoCasilla() {
        return toString();
    }

    public String toString() {
        String cad = "";
        cad = nombre + "\n";
        return cad;
    }
    
    public String getTipo() {
        return "Descanso";
    }

}
