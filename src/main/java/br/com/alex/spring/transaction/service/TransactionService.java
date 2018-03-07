package br.com.alex.spring.transaction.service;

import br.com.alex.spring.transaction.entity.DogModel;
import br.com.alex.spring.transaction.repository.DogRepository;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionService {

  @Autowired
  private DogRepository dogRepository;

  public void success(String name) {
    dogRepository.save(new DogModel(name));
  }

  @Transactional
  public void throwExceptionTransactional(String... names) {
    Arrays.stream(names)
            .forEach((name) -> {
              dogRepository.save(new DogModel(name));
            });

    throw new RuntimeException();
  }

  public void throwExceptionNotTransactional(String... names) {
    Arrays.stream(names)
            .forEach((name) -> {
              dogRepository.save(new DogModel(name));
            });

    throw new RuntimeException();
  }
}
