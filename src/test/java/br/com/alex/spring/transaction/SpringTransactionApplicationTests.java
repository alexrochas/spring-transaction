package br.com.alex.spring.transaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import br.com.alex.spring.transaction.entity.DogModel;
import br.com.alex.spring.transaction.repository.DogRepository;
import br.com.alex.spring.transaction.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringTransactionApplicationTests {

  @Autowired
  private TransactionService transactionService;
  @Autowired
  private DogRepository repository;

  @Test
  public void shouldAddCorrectlyToDatabaseWithoutErrors() {
    transactionService.success("Success Dog");
    DogModel successDog = repository.findByName("Success Dog");

    assertEquals(successDog.getName(), "Success Dog");
  }

  @Test
  public void shouldAddEvenWithException() {
    try {
      transactionService.throwExceptionNotTransactional("Dog 1", "Dog 2", "Dog 3");
    } catch (Exception e) {
      // don't care
    }
    DogModel dog3 = repository.findByName("Dog 3");

    assertEquals(dog3.getName(), "Dog 3");
  }

  @Test
  public void shouldRollbackBecauseOfException() {
    try {
      transactionService.throwExceptionTransactional("Dog 4", "Dog 5", "Dog 6");
    } catch (Exception e) {
      // still don't care
    }

    DogModel dog6 = repository.findByName("Dog 6");

    assertNull(dog6);
  }

  @Test
  public void shouldNotRollbackBecauseOfExpectedException() {
    try {
      transactionService.throwExpectedExceptionTransactional("Dog 7", "Dog 8", "Dog 9");
    } catch (Exception e) {
      // still don't care
    }

    DogModel dog9 = repository.findByName("Dog 9");

    assertNotNull(dog9);
  }

  @Test
  public void shouldRollbackBecauseOfExpectedException() {
    try {
      transactionService.throwExpectedExceptionWaitingForItTransactional("Dog 10", "Dog 11", "Dog 12");
    } catch (Exception e) {
      // still don't care
    }

    DogModel dog12 = repository.findByName("Dog 12");

    assertNull(dog12);
  }
}
