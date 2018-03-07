package br.com.alex.spring.transaction.repository;

import br.com.alex.spring.transaction.entity.DogModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogRepository extends CrudRepository<DogModel, Long> {

  DogModel findByName(String name);

}
