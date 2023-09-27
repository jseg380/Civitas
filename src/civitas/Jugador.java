
package civitas;

import java.util.ArrayList;

public class Jugador implements Comparable<Jugador> {

    protected static int CasasMax = 4;
    protected static int CasasPorHotel = 4;
    protected static int HotelesMax = 4;
    protected static float PasoPorSalida = 1000;
    private static float SaldoInicial = 7500;
    private String nombre;
    private float saldo;
    private int casillaActual;
    private boolean puedeComprar;
    protected ArrayList<CasillaCalle> propiedades;

    Jugador (String nom){
        nombre = nom;
        saldo = SaldoInicial;
        casillaActual = 0;
        puedeComprar = true;
        propiedades = new ArrayList<CasillaCalle>();
    }

    protected Jugador (Jugador otro){
        nombre = otro.nombre;
        saldo = otro.saldo;
        casillaActual = otro.casillaActual;
        puedeComprar = otro.puedeComprar;
        propiedades = otro.propiedades;
    }

    int cantidadCasasHoteles(){
        // Devuelve la suma del número de casas y hoteles que tiene el jugador
        // a través de todas sus propiedades
        int cantidadTotal = 0;
        for (CasillaCalle propiedad : propiedades) {
            cantidadTotal += propiedad.cantidadCasasHoteles();
        }

        return cantidadTotal;
    }

    public int compareTo (Jugador otro){
        return Float.compare(saldo, otro.saldo);
    }

    boolean comprar (CasillaCalle titulo){
        boolean result = false;
        if (puedeComprar){
            float precio = titulo.getPrecioCompra();

            if (puedoGastar(precio)){

                result = titulo.comprar(this);
                propiedades.add (titulo);
                Diario.getInstance().ocurreEvento("El jugador " + getNombre()
                                      + " compra la propiedad " + titulo );
                puedeComprar = false;
            } else
                Diario.getInstance().ocurreEvento("El jugador " + getNombre()
                 + " no tiene saldo para comprar la propiedad " + titulo);
        }
        return result;
    }

    boolean construirCasa (int ip){
        boolean result = false;
        boolean existe = existeLaPropiedad(ip);

        if (existe){
            CasillaCalle propiedad = propiedades.get(ip);
            boolean puedoEdificar = puedoEdificarCasa(propiedad);

            if (puedoEdificar){
                result = propiedad.construirCasa(this);
                Diario.getInstance().ocurreEvento("El jugador " + nombre
                            + " contruye casa en la propiedad " + ip);
            }
        }

        return result;
    }

    boolean construirHotel (int ip){
        boolean result = false;

        if (existeLaPropiedad(ip)) {
            CasillaCalle propiedad = propiedades.get(ip);

            boolean puedoEdificarHotel = puedoEdificarHotel(propiedad);

            if (puedoEdificarHotel) {
                result = propiedad.construirHotel(this);

                propiedad.derruirCasas(CasasPorHotel, this);

                Diario.getInstance().ocurreEvento("El jugador " + nombre
                    + " construye hotel en la propiedad " + ip);
            }
        }

        return result;
    }

    boolean enBancarrota (){
        return saldo < 0;
    }

    private boolean existeLaPropiedad (int ip){
        return (ip >= 0 && ip < propiedades.size());
    }

    private static int getCasasMax(){
        return CasasMax;
    }

    static int getCasasPorHotel(){
        return CasasPorHotel;
    }

    public int getCasillaActual(){
        return casillaActual;
    }

    private static int getHotelesMax(){
        return HotelesMax;
    }

    public String getNombre(){
        return nombre;
    }

    private static float getPremioPasoSalida(){
        return PasoPorSalida;
    }

    public ArrayList<CasillaCalle> getPropiedades (){
        return propiedades;
    }

    boolean getPuedeComprar(){
        return puedeComprar;
    }

    public float getSaldo(){
        return saldo;
    }

    boolean modificaSaldo (float cantidad){
        saldo += cantidad;
        Diario.getInstance().ocurreEvento("Se le añade al jugador " + nombre +
                                " la siguiente cantidad de saldo: " + cantidad);
        return true;
    }

    boolean moverACasilla(int numCasilla){
        casillaActual = numCasilla;
        puedeComprar = false;
        Diario.getInstance().ocurreEvento("El jugador " + nombre +
                                " se desplaza a la casilla nº" + numCasilla);
        return true;
    }

    boolean paga (float cantidad){
        return modificaSaldo(cantidad*(-1));
    }

    boolean pagaAlquiler (float cantidad) {
        return paga (cantidad);
    }

    boolean pasaPorSalida(){
        recibe (PasoPorSalida);
        Diario.getInstance().ocurreEvento("El jugador " + nombre +
                        "ha pasado por salida y ha recibido " + PasoPorSalida);
        return true;
    }

    boolean puedeComprarCasilla(){
        puedeComprar = true;
        return puedeComprar;
    }

    private boolean puedoEdificarHotel (CasillaCalle prop){
        return (puedoGastar(prop.getPrecioEdificar())
                && prop.getNumHoteles() < getHotelesMax()
                && prop.getNumCasas() >= getCasasPorHotel());
    }

    private boolean puedoEdificarCasa (CasillaCalle prop){
        /*return (puedoGastar(prop.getPrecioEdificar())
                && prop.getNumCasas() < getCasasMax());*/
        boolean puedoEdificar = false;

        float precioEdificar = prop.getPrecioEdificar();
        if (puedoGastar(precioEdificar) && prop.getNumCasas() < getCasasMax())
            puedoEdificar = true;

        return puedoEdificar;
    }

    private boolean puedoGastar (float precio){
        return (saldo >= precio);
    }

    boolean recibe (float cantidad){
        return modificaSaldo(cantidad);
    }

    boolean tieneAlgoQueGestionar(){
        return (!propiedades.isEmpty());
    }
    
    public boolean esEspeculador() {
        return false;
    }

    public Jugador convertir() {
        JugadorEspeculador nuevo = new JugadorEspeculador(this);
        return nuevo; // Se puede poner como return new JugadorEspeculador(this)
    }

    public String toString(){
        return nombre + "\nSaldo " + saldo + "\nCasilla nº" + casillaActual
                + "\nPropiedades " + propiedades;
    }
}
