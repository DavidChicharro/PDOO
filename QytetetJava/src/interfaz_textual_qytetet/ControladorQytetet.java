/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz_textual_qytetet;

import java.util.ArrayList;
import modeloqytetet.*;

/**
 *
 * @author david
 */
public class ControladorQytetet {
    private static Qytetet juego;
    private static Jugador jugador;
    private static Casilla casilla;
    private static VistaTextualQytetet vista = new VistaTextualQytetet();
    
        
    public static boolean bancarrota() {
        return jugador.getSaldo() < 0;
    }
    
    public static String estadoJugador() {
        return "\n\nJugador actual: " + jugador.getNombre() +
                ". Saldo: " + Integer.toString(jugador.getSaldo()) +
                "\nCasilla actual: " + jugador.getCasillaActual().toString();
    }
            
    public static Calle elegirPropiedad(ArrayList<Calle> propiedades){     
    //este metodo toma una lista de propiedades y genera una lista de strings, 
    //con el numero y nombre de las propiedades
    //luego llama a la vista para que el usuario pueda elegir.
    
        if(!propiedades.isEmpty()){
    
            vista.mostrar("\tCasilla\tTitulo");
            int seleccion;
            ArrayList<String> listaPropiedades = new ArrayList();
            for ( Calle miCasilla: propiedades) {
                listaPropiedades.add( "\t"+miCasilla.getNumeroCasilla()+"\t"+miCasilla.getTitulo().getNombre()); 
            }

            seleccion=vista.menuElegirPropiedad(listaPropiedades);  
            
            if(seleccion != 0)
                return propiedades.get(seleccion-1);
        }
        
        return null;
    }
    
    public static void inicializacionJuego() {
        juego = Qytetet.getInstance();
        ArrayList<String> nombres = vista.obtenerNombreJugadores();
        juego.inicializarJuego(nombres);
        
        jugador = juego.getJugadorActual();
        casilla = juego.getJugadorActual().getCasillaActual();
        
        vista.mostrar(juego.toString());
        vista.pausa();
    }   
    
    public static void desarrolloJuego() {
        do{            
            vista.mostrar(estadoJugador());
            vista.pausa();
            
            boolean libre = true;
            boolean encarcelado = jugador.getEncarcelado();
            if (encarcelado){
                MetodoSalirCarcel metodo = vista.menuSalirCarcel();
                libre = juego.intentarSalirCarcel(metodo);           
                
            }
            if (libre){
                boolean noTienePropietario = !(juego.jugar());
                casilla = juego.getJugadorActual().getCasillaActual();
                vista.mostrar(estadoJugador());
                if (!bancarrota()){
                    if (!encarcelado) {
                        if (casilla.getTipo() == TipoCasilla.CALLE) {
                            if (noTienePropietario) {
                                boolean elegirQuieroComprar = vista.elegirQuieroComprar();
                                if (elegirQuieroComprar) {
                                    boolean puedoComprarPropiedad = juego.comprarTituloPropiedad();
                                    if(!puedoComprarPropiedad)
                                        vista.mostrar("\n¡No puedes comprar la calle!");
                                }
                            }
                            //vista.mostrar(estadoJugador());
                        }
                        else if (casilla.getTipo() == TipoCasilla.SORPRESA) {
                            String carta = juego.getCartaActual().toString();
                            vista.mostrar(carta);                            
                            noTienePropietario = !(juego.aplicarSorpresa());
                            if (!encarcelado)
                                if(!bancarrota())
                                    if (casilla.getTipo() == TipoCasilla.CALLE)
                                        if (noTienePropietario) {
                                            boolean elegirQuieroComprar = vista.elegirQuieroComprar();
                                            if (elegirQuieroComprar) {
                                                boolean puedoComprarPropiedad = juego.comprarTituloPropiedad();
                                                if(!puedoComprarPropiedad)
                                                    vista.mostrar("\n¡No puedes comprar la calle!");
                                            }
                                        }
                            vista.mostrar(estadoJugador());
                        }
                        if (!encarcelado && !bancarrota() && jugador.tengoPropiedades()) {
                            int opcion;
                            do{
                                Calle opcionCasilla = null;
                                do{
                                    opcion = vista.menuGestionInmobiliaria();                                
                                    ArrayList<Calle> misPropiedades = new ArrayList();

                                    if (opcion==1 || opcion==2 || opcion==3 || opcion==4)
                                        misPropiedades = juego.propiedadesHipotecadasJugador(false);
                                    else if (opcion==5 )
                                        misPropiedades = juego.propiedadesHipotecadasJugador(true);

                                    if(opcion != 0)
                                        opcionCasilla = elegirPropiedad(misPropiedades);
                                    
                                }while (opcionCasilla == null && opcion!=0);
                                
                                switch (opcion) {
                                    case 1:
                                        boolean puedoEdificarCasa = juego.edificarCasa(opcionCasilla);
                                        if(puedoEdificarCasa)
                                            vista.mostrar("\nHas edificado una casa");
                                        else
                                            vista.mostrar("\n¡No puedes edificar ninguna casa!");
                                        break;
                                    case 2: 
                                        boolean puedoEdificarHotel = juego.edificarHotel(opcionCasilla);
                                        if(puedoEdificarHotel)
                                            vista.mostrar("\nHas edificado un hotel");
                                        else
                                            vista.mostrar("\n¡No puedes edificar ningún hotel!");
                                        break;
                                    case 3: 
                                        boolean puedoVenderPropiedad = juego.venderPropiedad(opcionCasilla);
                                        if(puedoVenderPropiedad)
                                            vista.mostrar("\nHas vendido la propiedad");
                                        else
                                            vista.mostrar("\n¡No puedes vender la propiedad!");
                                        break;
                                    case 4:
                                        boolean puedoHipotecarPropiedad = juego.hipotecarPropiedad(opcionCasilla);
                                        if(puedoHipotecarPropiedad)
                                            vista.mostrar("\nHas hipotecado la propiedad");
                                        else
                                            vista.mostrar("\n¡No puedes hipotecar la propiedad!");
                                        break;
                                    case 5:
                                        boolean puedoCancelarHipoteca = juego.cancelarHipoteca(opcionCasilla);
                                        if(puedoCancelarHipoteca)
                                            vista.mostrar("\nHas cancelado la hipoteca");
                                        else
                                            vista.mostrar("\n¡No cancelar la hipoteca!");
                                        break;
                                    default : 
                                        break;
                                }
                                vista.mostrar(estadoJugador());
                            } while (opcion != 0  && jugador.tengoPropiedades());
                        }
                    }
                }
            }
            
            if (!bancarrota()){
                juego.siguienteJugador();
                jugador = juego.getJugadorActual();
                casilla = juego.getJugadorActual().getCasillaActual();
            }
            
        } while (!bancarrota());
        
        vista.mostrar(juego.obtenerRanking());        
    }    
    
    public static void main(String[] args) {
        inicializacionJuego();
        desarrolloJuego();
    }
    
}
