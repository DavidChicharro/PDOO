# encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.


module ModeloQytetet
  
  class Especulador < Jugador
    attr_writer :fianza
    
    def initialize(jugador, fianza)
      copia_jugador(jugador)
      @fianza = fianza
      @factor_especulador = 2
    end
    
    def pagar_impuestos(cantidad)
      modificar_saldo(-cantidad/2)
    end
    
    def ir_a_carcel(casilla)
      if !pagar(@fianza)
        @casilla_actual = casilla
        @encarcelado = true
      end
    end
    
    def pagar_fianza(cantidad)
      puede_pagar_fianza = false
      
      if @saldo > cantidad
        modificar_saldo(-cantidad)
        puede_pagar_fianza = true
      end
      
      return puede_pagar_fianza
    end
    
    def convertirme(fianza)
      return self
    end
    
    private :pagar_fianza
    
  end
  
end