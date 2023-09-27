
package civitas;

public class JugadorEspeculador extends Jugador {
    private static int FactorEspeculador = 2;

    protected JugadorEspeculador(Jugador aConvertir) {
        super(aConvertir);
        CasasMax = CasasMax * FactorEspeculador;
        HotelesMax = HotelesMax * FactorEspeculador;
        actualizaPropiedadesPorConversion();
    }

    private void actualizaPropiedadesPorConversion() {
        for (Casilla prop : propiedades)
            ((CasillaCalle)prop).actualizaPropietarioPorConversion(this);
    }

    boolean paga (float cantidad) {
        return modificaSaldo(cantidad*(-1)/FactorEspeculador);
        // Posiblemente hay que modificar el método puedoGastar
    }
    
    public boolean esEspeculador() {
        return true;
    }

    public Jugador convertir() {
        // Puede que no funcione, posible solución hacer constructor de jugador
        // que reciba como único parámetro un JugadorEspeculador
        Jugador nuevo = new Jugador(this);

        return nuevo;
    }

    public String toString() {
        return "Especulador\n" + super.toString();
    }
}
