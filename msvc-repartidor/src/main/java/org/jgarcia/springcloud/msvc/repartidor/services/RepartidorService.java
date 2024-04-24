package org.jgarcia.springcloud.msvc.repartidor.services;

import org.jgarcia.springcloud.msvc.repartidor.models.entity.Repartidor;

import java.util.List;
import java.util.Optional;

public interface RepartidorService {

    //Creación de metodos
    List<Repartidor> listar();
    Optional<Repartidor> porId(Long id);
    Repartidor guardar (Repartidor repartidor);
    void eliminar (Long id);

    //Metodo personalizado
    Optional<Repartidor> porPlaca (String placa);
    Optional<Repartidor> porTelefono (String telefono);

}
