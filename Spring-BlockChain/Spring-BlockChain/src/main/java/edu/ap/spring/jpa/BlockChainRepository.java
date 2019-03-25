package edu.ap.spring.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.ap.spring.service.BlockChain;

@Repository
public interface BlockChainRepository extends CrudRepository<BlockChainModel, Long> { 
    public BlockChain findFirstByOrderByIdDesc();
}