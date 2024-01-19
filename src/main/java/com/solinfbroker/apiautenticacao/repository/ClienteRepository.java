package com.solinfbroker.apiautenticacao.repository;

import com.solinfbroker.apiautenticacao.model.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Long>{
    ClienteModel findByEmail(String email);
}
