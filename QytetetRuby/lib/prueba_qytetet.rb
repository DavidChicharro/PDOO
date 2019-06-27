# encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "sorpresa"
require_relative "tipo_sorpresa"
require_relative "tipo_casilla"
require_relative "titulo_propiedad"
require_relative "casilla"
require_relative "tablero"
require_relative "qytetet"

module ModeloQytetet

  class PruebaQytetet
    @@mazo = Array.new
    
    def self.inicializar_sorpresas
      @@mazo<< Sorpresa.new("Regaligo para tu pecho, por lo bien que lo has hecho.", 1700, TipoSorpresa::PAGARCOBRAR)
      @@mazo<< Sorpresa.new("¡IMPUESTO! Hay que pagar para financiar nuestros servicios públicos", -1300, TipoSorpresa::PAGARCOBRAR)
      @@mazo<< Sorpresa.new("Aquí no existe la presunción de inocencia, así que te vas derecho a la trena.", 6, TipoSorpresa::IRACASILLA) #tablero.carcel.numero_casilla
      @@mazo<< Sorpresa.new("Sabemos que te gusta viajar, así que te has ganado un vuelo directo al comienzo del juego.", 2, TipoSorpresa::IRACASILLA)
      @@mazo<< Sorpresa.new("Vuelo directo con destino a... ¿París? ¿Londres? No. Me temo que a un callejón de la ciudad.", 17, TipoSorpresa::IRACASILLA)
      @@mazo<< Sorpresa.new("¡Hora de cobrar el alquiler de los inquilinos!", 300, TipoSorpresa::PORCASAHOTEL)
      @@mazo<< Sorpresa.new("Es una pena, pero tienes que pagar por cada una de tus propiedades.", -300, TipoSorpresa::PORCASAHOTEL)
      @@mazo<< Sorpresa.new("Aquí reina el socialismo, por eso tus queridos vecinos estarán encantados de darte algo de limosna.", 400, TipoSorpresa::PORJUGADOR)
      @@mazo<< Sorpresa.new("¿Recuerdas aquella vez que twiteaste que estarías encantado de compartir todo lo que tienes con los demás? ¡Tus desesos son órdenes!", -200, TipoSorpresa::PORJUGADOR)
      @@mazo<< Sorpresa.new("Aquella noche de marcha con el fiscal general ha servido de algo, así que ha decidido que no entres en prisión", 0, TipoSorpresa::SALIRCARCEL)
    end

    
    
    def self.print_sorpresa_positiva
      positivas = Array.new
      for mazo_cartas in @@mazo
        if mazo_cartas.valor > 0
          positivas<< mazo_cartas
        end
      end
      return positivas
    end
    
    def self.print_sorpresa_ir_a_casilla
      ir_a_casilla = Array.new
      for mazo_cartas in @@mazo
        if mazo_cartas.tipo == TipoSorpresa::IRACASILLA
          ir_a_casilla<< mazo_cartas
        end
      end
      return ir_a_casilla
    end
    
    def self.print_sorpresa_tipo(sorpresa)
      tipo = Array.new
      for mazo_cartas in @@mazo
        if mazo_cartas.tipo == sorpresa
          tipo<< mazo_cartas
        end
      end
      return tipo
    end
    
    def self.main
      self.inicializar_sorpresas
      
#      puts "Mostrar positivas: "
#      positivas = self.print_sorpresa_positiva
#      for elemento1 in positivas
#        puts elemento1.to_s
#      end
#      
#      puts "\nMostrar elementos 'Ir a casilla': "
#      ir_a_casilla = self.print_sorpresa_ir_a_casilla
#      for elemento2 in ir_a_casilla
#        puts elemento2.to_s
#      end
#      
#      puts "\nMostrar elementos 'Por casa hotel': "
#      sorpresa_tipo = self.print_sorpresa_tipo(TipoSorpresa::PORCASAHOTEL)
#      for elemento3 in sorpresa_tipo
#        puts elemento3.to_s
#     end
      
      
      q = Qytetet.instance
      nombres = []
      nombres<< "Ana"
      nombres<< "Pepe"
      
      q.inicializar_juego(nombres)
      puts q.to_s
      
      
    end
   
  end
  PruebaQytetet.main
end