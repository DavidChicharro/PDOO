/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;


import java.util.ArrayList;

/**
 *
 * @author david
 */
public class Tablero {
    private ArrayList<Casilla> casilla;
    private Casilla carcel;
    
    private void inicializar() {
        casilla = new ArrayList();
        
        casilla.add(new OtraCasilla(0, 0, TipoCasilla.SALIDA));
        casilla.add(new Calle(1, 300, new TituloPropiedad("Casería del Cerro", 250, (float) -20.0, 50, 150)));
        casilla.add(new Calle(2, 350, new TituloPropiedad("Tete Montoliú", 290, (float) -17.5, 50, 200)));
        casilla.add(new OtraCasilla(3, 0, TipoCasilla.SORPRESA));
        casilla.add(new Calle(4, 400, new TituloPropiedad("Joaquina Eguaras", 330, (float) -15.0, 55, 280)));
        casilla.add(new OtraCasilla(5, 0, TipoCasilla.CARCEL));
        casilla.add(new Calle(6, 600, new TituloPropiedad("Paseo del Violón", 400, (float) -12.0, 58, 375)));
        casilla.add(new Calle(7, 750, new TituloPropiedad("Avenida de Cádiz", 425, (float) -10.0, 60, 425)));
        casilla.add(new OtraCasilla(8, 250, TipoCasilla.IMPUESTO));
        casilla.add(new Calle(9, 900, new TituloPropiedad("Carretera de la Sierra", 475, (float) 10.0, 65, 500)));
        casilla.add(new OtraCasilla(10, 0, TipoCasilla.PARKING));
        casilla.add(new Calle(11, 1000, new TituloPropiedad("Avenida de Madrid", 500, (float) 12.5, 75, 700)));
        casilla.add(new OtraCasilla(12, 0, TipoCasilla.SORPRESA));
        casilla.add(new Calle(13, 1200, new TituloPropiedad("Ribera del Beiro", 550, (float) -10.5, 70, 750)));
        casilla.add(new Calle(14, 1500, new TituloPropiedad("Doctor Olóriz", 590, (float) 12.0, 77, 850)));
        casilla.add(new OtraCasilla(15, 0, TipoCasilla.JUEZ));
        casilla.add(new Calle(16, 1750, new TituloPropiedad("Pavaneras", 625, (float) 15.0, 90, 900)));
        casilla.add(new Calle(17, 2000, new TituloPropiedad("Carrera del Darro", 700, (float) 18.0, 95, 990)));
        casilla.add(new OtraCasilla(18, 0, TipoCasilla.SORPRESA));
        casilla.add(new Calle(19, 2200, new TituloPropiedad("Gran Vía de Colón", 750, (float) 20.0, 100, 1000)));        
    }
    
    public Tablero() {
        this.inicializar();
        carcel = casilla.get(5);
    }
    
    boolean esCasillaCarcel(int numeroCasilla) {
        return casilla.get(numeroCasilla) == carcel;
    }
    
    Casilla getCarcel() {
        return carcel;
    }    
    
    Casilla obtenerCasillaNumero(int numeroCasilla) {
        return casilla.get(numeroCasilla);
    }
    
    Casilla obtenerNuevaCasilla (Casilla casilla, int desplazamiento) {
        int numeroCasilla = casilla.getNumeroCasilla() + desplazamiento;
        
        if (numeroCasilla > 19)
            numeroCasilla -= 20;
        
        return this.casilla.get(numeroCasilla);
    }
    
    
    @Override
    public String toString() {
        String st = "";
        
        for (Casilla miCasilla : casilla)
            st += "\n" + miCasilla.toString();
        
        return st;
    }
}
