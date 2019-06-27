# encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.


module ModeloQytetet
  class Tablero
    attr_reader :casilla
    attr_accessor :carcel
    
    def initialize
      @casilla = Array.new
      self.inicializar
      @carcel = @casilla[5]
    end
    
    def es_casilla_carcel(numero_casilla)
      return @casilla[numero_casilla] == @carcel
    end
    
    def obtener_casilla_numero(numero_casilla)
      return @casilla[numero_casilla]
    end
    
    def obtener_nueva_casilla(casilla, desplazamiento)
      numero_casilla = casilla.numero_casilla + desplazamiento
      if numero_casilla > 19
        numero_casilla -= 20
      end
      return @casilla[numero_casilla]
    end
    
    def to_s
      devolver = ""      
      for mi_casilla in @casilla
        devolver += "\n"+mi_casilla.to_s
      end
      
      return devolver
    end
    
    def inicializar
      @casilla<< Casilla.new(0, 0, TipoCasilla::SALIDA)
      @casilla<< Calle.new(1, 300, TituloPropiedad.new("Casería del Cerro", 250, -20.0, 50, 150))
      @casilla<< Calle.new(2, 350, TituloPropiedad.new("Tete Montoliú", 290, -17.5, 50, 200))
      @casilla<< Casilla.new(3, 0, TipoCasilla::SORPRESA)
      @casilla<< Calle.new(4, 400, TituloPropiedad.new("Joaquina Eguaras", 330, -15.0, 55, 280))
      @casilla<< Casilla.new(5, 0, TipoCasilla::CARCEL)
      @casilla<< Calle.new(6, 600, TituloPropiedad.new("Paseo del Violón", 400, -12.0, 58, 375))
      @casilla<< Calle.new(7, 750, TituloPropiedad.new("Avenida de Cádiz", 425, -10.0, 60, 425))
      @casilla<< Casilla.new(8, 250, TipoCasilla::IMPUESTO)
      @casilla<< Calle.new(9, 900, TituloPropiedad.new("Carretera de la Sierra", 475, 10.0, 65, 500))
      @casilla<< Casilla.new(10, 0, TipoCasilla::PARKING)
      @casilla<< Calle.new(11, 1000, TituloPropiedad.new("Avenida de Madrid", 500, 12.5, 75, 700))
      @casilla<< Casilla.new(12, 0, TipoCasilla::SORPRESA)
      @casilla<< Calle.new(13, 1200, TituloPropiedad.new("Ribera del Beiro", 550, -10.5, 70, 750))
      @casilla<< Calle.new(14, 1500, TituloPropiedad.new("Doctor Olóriz", 590, 12.0, 77, 850))
      @casilla<< Casilla.new(15, 0, TipoCasilla::JUEZ)
      @casilla<< Calle.new(16, 1750, TituloPropiedad.new("Pavaneras", 625, 15.0, 90, 900))
      @casilla<< Calle.new(17, 2000, TituloPropiedad.new("Carrera del Darro", 700, 18.0, 95, 990))
      @casilla<< Casilla.new(18, 0, TipoCasilla::SORPRESA)
      @casilla<< Calle.new(19, 2200, TituloPropiedad.new("Gran Vía de Colón", 750, 20.0, 100, 1000))
    end
    
  end
end