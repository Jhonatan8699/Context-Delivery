package org.jgarcia.springcloud.msvc.repartidor.repositories;

import org.jgarcia.springcloud.msvc.repartidor.models.entity.Repartidor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RepartidorRepository extends CrudRepository<Repartidor,Long> {

    // Carga un objeto nulo si no hay valor
    Optional<Repartidor> findByTelefono (String telefono);
    Optional<Repartidor> findByPlaca (String placa);

}
