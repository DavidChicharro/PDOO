/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

/**
 *
 * @author david
 */
public class Especulador extends Jugador {
//    static int factorEspeculador = 2;
    private int fianza;
    
    protected Especulador(Jugador jugador, int fianza) {
        super(jugador);
        this.fianza = fianza;
        this.factorEspeculador = 2;
    }
    
    @Override
    protected void pagarImpuestos(int cantidad) {
        modificarSaldo(-cantidad/2);
    }
    
    @Override
    protected void irACarcel(Casilla casilla) {
        if(!pagarFianza(fianza)) {
            setCasillaActual(casilla);
            setEncarcelado(true);
        }
    }
    
    @Override
    protected Especulador convertirme(int fianza) {
        return this;
    }
    
    private boolean pagarFianza(int cantidad) {
        boolean puedePagarFianza = false;
        
        if(getSaldo() > cantidad) {
            modificarSaldo(-cantidad);
            puedePagarFianza = true;
        }            
        
        return puedePagarFianza;
    }
    
}
