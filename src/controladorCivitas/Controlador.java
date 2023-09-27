
package controladorCivitas;

import civitas.CivitasJuego;
import civitas.OperacionJuego;
import civitas.OperacionInmobiliaria;
import vistaTextualCivitas.Vista;

public class Controlador {
    private Vista vista;
    private CivitasJuego juegoModel;

    public Controlador (CivitasJuego juego, Vista vista) {
        this.juegoModel = juego;
        this.vista = vista;
    }

    public void juega() {
        boolean finalJuego = false;

        while (!finalJuego) {
            // Actualizar vista
            vista.actualiza();

            // Pausar para que el jugador interaccione
            vista.pausa();
            
            // Escoger qué hacer
            OperacionJuego opJuego = juegoModel.siguientePaso();

            vista.mostrarSiguienteOperacion(opJuego);

            // Si no se escoge pasar de turno mostrar eventos del diario
            if (opJuego != OperacionJuego.PASAR_TURNO) {
                vista.mostrarEventos();
            }

            // Comprobar si el juego ha finalizado
            finalJuego = juegoModel.finalDelJuego();

            // (Una forma de hacer los siguientes ifs sin !finalJuego)
            /* if (finalJuego) {
                break;
            } */

            if (!finalJuego && (opJuego == OperacionJuego.COMPRAR)) {
                Respuesta resComprar = vista.comprar();

                if (resComprar == Respuesta.SI) 
                    juegoModel.comprar();
                    
                juegoModel.siguientePasoCompletado(opJuego);
            }
            else if (!finalJuego && (opJuego == OperacionJuego.GESTIONAR)) {
                OperacionInmobiliaria opInmobiliaria = vista.elegirOperacion();

                if (opInmobiliaria != OperacionInmobiliaria.TERMINAR) {
                    int propiedad = vista.elegirPropiedad();

                    if (opInmobiliaria == OperacionInmobiliaria.CONSTRUIR_CASA) {
                        juegoModel.construirCasa(propiedad);
                    }
                    else {
                        juegoModel.construirHotel(propiedad);
                    }
                }
                else {
                    juegoModel.siguientePasoCompletado(opJuego);
                }
            }
        } // while (!finalJuego)

        // Calcular ranking de los jugadores
        juegoModel.ranking();

        // Mostrar los jugadores ordenados según el ranking
        vista.actualiza();
    }
}