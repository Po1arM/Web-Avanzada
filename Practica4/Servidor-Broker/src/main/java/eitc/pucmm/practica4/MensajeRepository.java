package eitc.pucmm.practica4;

import eitc.pucmm.practica4.entidades.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensajeRepository extends JpaRepository<Mensaje, String> {
    List<Mensaje> findAllByIdDispositivo(int id);
}
