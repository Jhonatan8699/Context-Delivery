package org.jgarcia.springcloud.msvc.repartidor.controllers;


import jakarta.validation.Valid;
import org.jgarcia.springcloud.msvc.repartidor.models.entity.Repartidor;
import org.jgarcia.springcloud.msvc.repartidor.services.RepartidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//Define a la clase como un controlador de Spring MVC que maneja las solicitudes y resultados HTTP
@RestController
//Establece la raíz de la URL para todos los métodos dentro de esa clase.
@RequestMapping("/api/postgarciahuaccha")
public class RepartidorController {

    @Autowired
    private RepartidorService service;

    @GetMapping
    public List<Repartidor> listar() {
        return service.listar();

    }

    @GetMapping ("/{id}")
    public ResponseEntity<?> detalle (@PathVariable Long id){

        //Optional es un contenedor que puede o no contener un valor.
        Optional<Repartidor> repartidorOptional = service.porId(id);

        //Buscamos el objeto
        if (repartidorOptional.isPresent()){
            return ResponseEntity.ok(repartidorOptional.get());
        }
        //Si no se encuentra
            return ResponseEntity.notFound().build();
        }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Repartidor repartidor, BindingResult result){

        //Para validar el metodo personalizado. Compara 2 objetos.
        if (service.porPlaca(repartidor.getPlaca()).isPresent()){

            return ResponseEntity.badRequest().body(
                    //Creando el mapa para guardar el error
                    Collections.singletonMap("Mensaje", " Ya existe un repartidor con esa placa")
            );
        }

        if (service.porTelefono(repartidor.getTelefono()).isPresent()){

            return ResponseEntity.badRequest().body(
                    //Creando el mapa para guardar el error
                    Collections.singletonMap("Mensaje", " Ya existe un repartidor con ese telefono")
            );
        }

        //result es una colección para recoger los errores de la validación
        if (result.hasErrors()){
            return validando(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(repartidor));

    }

    @PutMapping ("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Repartidor repartidor, BindingResult result,@PathVariable Long id) {

        if (result.hasErrors()) {
            return validando(result);
        }
        Optional<Repartidor> pos = service.porId(id);

        if (pos.isPresent()){
            Repartidor repartAux = pos.get();

            if(!repartidor.getPlaca().equalsIgnoreCase(repartAux.getPlaca())
                    && service.porPlaca(repartidor.getPlaca()).isPresent()){

                return ResponseEntity.badRequest().body(
                        //Creando el mapa para guardar el error
                        Collections.singletonMap("Mensaje", " Ya existe un repartidor con esa placa")
                );
            }

            if(!repartidor.getTelefono().equalsIgnoreCase(repartAux.getTelefono())
                    && service.porTelefono(repartidor.getTelefono()).isPresent()){

                return ResponseEntity.badRequest().body(
                        //Creando el mapa para guardar el error
                        Collections.singletonMap("Mensaje", " Ya existe un repartidor con ese telefono")
                );
            }

            repartAux.setPlaca(repartidor.getPlaca());
            repartAux.setNombres(repartidor.getNombres());
            repartAux.setTelefono(repartidor.getTelefono());


            // para guardar el cambio del valor
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(repartAux));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar (@PathVariable Long id){

        Optional<Repartidor> pos = service.porId(id);

        if (pos.isPresent()){
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> validando(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

}
