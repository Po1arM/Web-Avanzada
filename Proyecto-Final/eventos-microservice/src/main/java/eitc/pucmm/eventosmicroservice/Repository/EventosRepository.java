package eitc.pucmm.eventosmicroservice.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eitc.pucmm.eventosmicroservice.entidades.Compra;

public interface EventosRepository extends JpaRepository<Compra,Long>{
    List<Compra> findAllByIdUsuario(long idUsuario);
}
