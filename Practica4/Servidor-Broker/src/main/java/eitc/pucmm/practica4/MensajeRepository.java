package eitc.pucmm.practica4;

import eitc.pucmm.practica4.entidades.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MensajeRepository extends JpaRepository<Mensaje, String> {
    List<Mensaje> findAllByIdDispositivo(int id);
}
