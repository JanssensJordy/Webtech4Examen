package edu.ap.spring.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BlockChainModel {
    @Id
	@GeneratedValue
    private int id;

    @Column(length=10000)
    private String BlockChainJson;

    public BlockChainModel(String json) {
        this.BlockChainJson = json;
    }

    public String getJson(){
        return this.BlockChainJson;
    }
}