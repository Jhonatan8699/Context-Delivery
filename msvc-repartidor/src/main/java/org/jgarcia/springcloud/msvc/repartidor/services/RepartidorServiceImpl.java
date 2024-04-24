package org.jgarcia.springcloud.msvc.repartidor.services;

import org.jgarcia.springcloud.msvc.repartidor.models.entity.Repartidor;
import org.jgarcia.springcloud.msvc.repartidor.repositories.RepartidorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//Es una clase marcada que desempeña el papel de un "servicio. Encapsula la lógica de negocio"
@Service
public class RepartidorServiceImpl implements RepartidorService{

    //Anotación de inyección de dependencias en Spring
    @Autowired
    private RepartidorRepository repository;

    //Anotación que se utiliza para definir la transaccionalidad de los métodos anotados.
    //Metodo que devuelve la información de solo lectura
    @Transactional(readOnly = true)
    @Override
    public List<Repartidor> listar() {
        return (List<Repartidor>) repository.findAll();
    }
    @Transactional(readOnly = true)
    @Override
    public Optional<Repartidor> porId(Long id) {
        return repository.findById(id);
    }
    //Metodo que no será solo de lectura, sino que será utilizado para ser guardado
    @Transactional
    @Override
    public Repartidor guardar(Repartidor repartidor) {
        return repository.save(repartidor);
    }
    @Transactional
    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Repartidor> porPlaca(String placa) {
        return repository.findByPlaca(placa);
    }

    @Override
    public Optional<Repartidor> porTelefono(String telefono) {
        return repository.findByTelefono(telefono);
    }
}
