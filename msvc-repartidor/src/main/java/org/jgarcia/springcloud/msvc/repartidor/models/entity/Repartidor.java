package org.jgarcia.springcloud.msvc.repartidor.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "repartidor")
public class Repartidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long repartidorId;
    @Column(unique = true)
    @NotEmpty
    @Pattern(regexp = "\\w{6}", message = "La placa debe tener exactamente 6 caracteres")
    private String placa;
    @NotEmpty
    private String nombres;
    @Column(unique = true)
    @NotEmpty
    @Pattern(regexp = "\\w{9}", message = "El teléfono debe tener exactamente 9 caracteres")
    private String telefono;


    public long getRepartidorId() {
        return repartidorId;
    }

    public void setRepartidorId(long repartidorId) {
        this.repartidorId = repartidorId;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
