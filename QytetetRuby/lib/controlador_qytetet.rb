#encoding: utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.


require_relative "qytetet"
require_relative "jugador"
require_relative "especulador"
require_relative "casilla"
require_relative "calle"
require_relative "tablero"
require_relative "sorpresa"
require_relative "tipo_sorpresa"
require_relative "titulo_propiedad"
require_relative "tipo_casilla"
require_relative "dado"
require_relative "metodo_salir_carcel"
require_relative "vista_textual_qytetet"

module InterfazTextualQytetet
  class ControladorQytetet
    include ModeloQytetet
    @@juego = nil
    @@jugador = nil
    @@casilla = nil
    @@vista = VistaTextualQytetet.new
    
    
    def self.bancarrota
      return @@jugador.saldo < 0
    end
    
    def self.estado_jugador
      return "\n\nJugador actual: #{@@jugador.to_s}
        \nCasilla actual: #{@@jugador.casilla_actual.to_s}"
    end
    
    def self.elegir_propiedad( propiedades ) # lista de propiedades a elegir
      if !propiedades.empty?      
        @@vista.mostrar("    Casilla\tTitulo");
      
        lista_propiedades = Array.new
        for prop in propiedades  # crea una lista de strings con numeros y nombres de propiedades
          prop_string = prop.numero_casilla.to_s + '    ' + prop.titulo.nombre;
          lista_propiedades<< prop_string
        end
        seleccion=@@vista.menu_elegir_propiedad(lista_propiedades)  # elige de esa lista del menu
        if(seleccion != 0)
          return propiedades.at(seleccion-1)
        end
      end
      return nil
    end

    
    def self.inicializacion_juego
      @@juego = Qytetet.instance
      nombres = @@vista.obtener_nombre_jugadores
      @@juego.inicializar_juego(nombres)
      
      @@jugador = @@juego.jugador_actual
      @@casilla = @@juego.jugador_actual.casilla_actual
      
      @@vista.mostrar(@@juego.to_s)
      @@vista.pausa
    end
    
    def self.desarrollo_juego
      begin
        @@vista.mostrar(self.estado_jugador)
        @@vista.pausa        
        
        libre=true
        encarcelado=@@jugador.encarcelado
        if encarcelado
          metodo=@@vista.menu_salir_carcel
          libre=@@juego.intentar_salir_carcel(metodo)
          
          if libre
            @@vista.mostrar("Saliste de la cárcel")
          else
            @@vista.mostrar("Permaneces en la cárcel")
          end
        end
        if libre
          no_tiene_propietario = !@@juego.jugar
          @@casilla = @@juego.jugador_actual.casilla_actual
          @@vista.mostrar(self.estado_jugador)
          if !self.bancarrota
            if !encarcelado
              if @@casilla.tipo == TipoCasilla::CALLE
                if no_tiene_propietario
                  elegir_quiero_comprar = @@vista.elegir_quiero_comprar
                  if elegir_quiero_comprar
                    @@juego.comprar_titulo_propiedad
                    @@vista.mostrar(self.estado_jugador)
                  end
                end
              elsif @@casilla.tipo == TipoCasilla::SORPRESA
                carta = @@juego.carta_actual.to_s
                @@vista.mostrar(carta)
                no_tiene_propietario = !@@juego.aplicar_sorpresa
                if !encarcelado
                  if !self.bancarrota
                    if @@casilla.tipo == TipoCasilla::CALLE
                      if !no_tiene_propietario
                        elegir_quiero_comprar = @@vista.elegir_quiero_comprar
                        if elegir_quiero_comprar
                          @@juego.comprar_titulo_propiedad
                        end
                      end
                    end
                  end
                end
                @@vista.mostrar(self.estado_jugador)
              end
              if (!encarcelado && !self.bancarrota && @@jugador.tengo_propiedades)
                begin
                  opcion_casilla = nil
                  begin
                    opcion = @@vista.menu_gestion_inmobiliaria
                    mis_propiedades = []
                    if(opcion==1 || opcion==2 || opcion==3 || opcion==4)
                      mis_propiedades = @@juego.propiedades_hipotecadas_jugador(false)
                    elsif (opcion==5)
                      mis_propiedades = @@juego.propiedades_hipotecadas_jugador(true)
                    end
                    if opcion!=0
                      opcion_casilla = self.elegir_propiedad(mis_propiedades)
                    end
                  end while(opcion_casilla==nil && opcion!=0)
                  
                  case opcion
                    when 1
                      puedo_edificar_casa = @@juego.edificar_casa(opcion_casilla)
                      if !puedo_edificar_casa
                        @@vista.mostrar("\n¡No puedes edificar más casas!")
                      end
                    when 2
                      puedo_edificar_hotel = @@juego.edificar_hotel(opcion_casilla)
                      if !puedo_edificar_hotel
                        @@vista.mostrar("\n¡No puedes edificar un hotel!")
                      end
                    when 3
                      puedo_vender_propiedad = @@juego.vender_propiedad(opcion_casilla)
                      if !puedo_vender_propiedad
                        @@vista.mostrar("\n¡No puedes vender la propiedad!")
                      end
                    when 4
                      puedo_hipotecar_propiedad = @@juego.hipotecar_propiedad(opcion_casilla)
                      if !puedo_hipotecar_propiedad
                        @@vista.mostrar("\n¡No puedes hipotecar la propiedad!")
                      end
                    when 5
                      puedo_cancelar_hipoteca = @@juego.cancelar_hipoteca(opcion_casilla)
                      if !puedo_cancelar_hipoteca
                        @@vista.mostrar("\n¡No puedes cancelar la hipoteca!")
                      end
                  end
                  @@vista.mostrar(self.estado_jugador)
                end while(opcion!=0 && @@jugador.tengo_propiedades)
              end
            end
          end
        end
        if !self.bancarrota
          @@juego.siguiente_jugador
          @@jugador = @@juego.jugador_actual
          @@casilla = @@juego.jugador_actual.casilla_actual
        end
      end while(!self.bancarrota)
      
      @@vista.mostrar(@@juego.obtener_ranking)
      
    end
    
    def self.main
      self.inicializacion_juego
      self.desarrollo_juego
    end
    
  end
  ControladorQytetet.main
end
