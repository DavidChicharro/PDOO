# encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module ModeloQytetet
  class Calle < Casilla
    attr_accessor :num_casas, :num_hoteles, :titulo
    
    def initialize(numero_casilla, coste, titulo)
      super(numero_casilla, coste, TipoCasilla::CALLE)
      @num_casas = 0
      @num_hoteles = 0
      @titulo = titulo      
    end


    def asignar_propietario(jugador)
      @titulo.propietario = jugador
      return @titulo
    end
    
    def calcular_valor_hipoteca
      return @titulo.hipoteca_base*(1+(@num_casas*0.5)+@num_hoteles)
    end
    
    def cancelar_hipoteca
      @titulo.hipotecada(false)
      return calcular_valor_hipoteca*1.10
    end
    
    def cobrar_alquiler
      coste_alquiler_base = @titulo.alquiler_base
      coste_alquiler = coste_alquiler_base + (@num_casas*0.5 + @num_hoteles*2)
      @titulo.cobrar_alquiler(coste_alquiler)
      return coste_alquiler
    end
    
    def edificar_casa
      @num_casas += 1
      coste_edificar_casa = precio_edificar
      return coste_edificar_casa
    end
    
    def edificar_hotel
      @num_hoteles += 1
      @num_casas = 0
      coste_edificar_hotel = precio_edificar
      return coste_edificar_hotel
    end
    
    def esta_hipotecada
      return @titulo.hipotecada
    end
    
    def precio_edificar
      return @titulo.precio_edificar
    end
    
    def hipotecar
      @titulo.hipotecada = true
      cantidad_recibida = calcular_valor_hipoteca
      return cantidad_recibida
    end
    
    def propietario_encarcelado
      return @titulo.propietario_encarcelado
    end
    
    def se_puede_edificar_casa
      factor_especulador = @titulo.propietario.factor_especulador
      return (@tipo == TipoCasilla::CALLE) && (@num_casas < 4*factor_especulador)
    end
    
    def se_puede_edificar_hotel
      factor_especulador = @titulo.propietario.factor_especulador
      return (@tipo == TipoCasilla::CALLE) && 
        (@num_hoteles < 4*factor_especulador) && (@num_casas == 4*factor_especulador);
    end
    
    def soy_edificable
      return true
    end
    
    def tengo_propietario
      return @titulo.tengo_propietario
    end
    
    def vender_titulo
      precio_compra = @coste + (@num_casas+@num_hoteles)*precio_edificar
      precio_venta = precio_compra*(1+0.01*(@titulo.factor_revalorizacion))
      @titulo.propietario = nil
      @num_hoteles = 0
      @num_casas = 0
      return precio_venta
    end
    
    
    
    def to_s
      salida = "\nCasilla \nNumero casilla: #{@numero_casilla}, Tipo de casilla: Calle, 
      Coste= #{@coste}, Número hoteles: #{@num_hoteles}, Número casas: #{@num_casas}"
      
      if(@titulo != nil)
        salida += "\nTítulo de propiedad: #{@titulo.to_s}"
      end
        
      return salida
    end
  end
end
