
package civitas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MazoSorpresas {
    private ArrayList<Sorpresa> sorpresas;
    private boolean barajada;
    private int usadas;
    private boolean debug;
    
    MazoSorpresas (boolean debug) {
        this.debug = debug;
        init();
        if (debug)
            Diario.getInstance().ocurreEvento("Mazo sorpresas con debug activado");
    }
    
    MazoSorpresas () {
        init();
        debug = false;
    }
    
    private void init() {
        sorpresas = new ArrayList<Sorpresa>();
        barajada = false;
        usadas = 0;
    }
    
    void alMazo (Sorpresa s) {
        if (!barajada)
            sorpresas.add(s);
    }
    
    Sorpresa siguiente() {
        if ((!barajada || usadas == sorpresas.size())) {
            if (!debug) {
                Collections.shuffle(sorpresas, new Random());
            }
            usadas = 0;
            barajada = true;
        }
        
        usadas++;
        Sorpresa aDevolver = sorpresas.get(0);
        sorpresas.remove(0);
        sorpresas.add(aDevolver);
        
        return aDevolver;
    }
}
