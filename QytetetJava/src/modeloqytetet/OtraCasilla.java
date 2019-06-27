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
public class OtraCasilla extends Casilla {
    
    public OtraCasilla(int numeroCasilla, int coste, TipoCasilla tipo) {                   
        super(numeroCasilla, coste, tipo);
    }
    
    @Override
    boolean soyEdificable() {
        return false;            
    }
    
    @Override
    public String toString () {
        return "Número casilla: " + Integer.toString(numeroCasilla) + 
               "\nTipo de casilla: " + tipo;
    }
    
}
