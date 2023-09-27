
package civitas;

import java.util.ArrayList;

public class SorpresaConvertirme extends Sorpresa {
    SorpresaConvertirme(String texto) {
        super(texto, 0);
    }

    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        todos.get(actual).convertir();
    }
}
