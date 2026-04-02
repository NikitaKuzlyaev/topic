package com.topic.service.test;

import com.topic.entity.test.Dummy;
import com.topic.repository.test.TestTransactionalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class TestTransactionalService {

    private final TestTransactionalRepository testTransactionalRepository;

    public TestTransactionalService(TestTransactionalRepository testTransactionalRepository) {
        this.testTransactionalRepository = testTransactionalRepository;
    }

    @Transactional // все проверил, все работает. если убрать будет херня
    public boolean makeTransactional() {
        someServiceA.doSmth(testTransactionalRepository);
        Util.initException(0.1d);
        return true;
    }

}

@Service
class someServiceA {

    protected static boolean doSmth(TestTransactionalRepository repository) {
        Dummy d = new Dummy();
        repository.save(d);
        Util.initException(0.1d);
        someServiceB.doSmth(repository);
        return true;
    }

}

@Service
class someServiceB {

    protected static boolean doSmth(TestTransactionalRepository repository) {
        Dummy d = new Dummy();
        repository.save(d);
        Util.initException(0.1d);
        someServiceC.doSmth(repository);
        return true;
    }

}

@Service
class someServiceC {

    protected static boolean doSmth(TestTransactionalRepository repository) {
        Dummy d = new Dummy();
        repository.save(d);
        Util.initException(0.1d);
        return true;
    }

}

class Util{
    public static void initException(double rate) {
        if (ThreadLocalRandom.current().nextDouble() < rate) {
            throw new RuntimeException();
        }
    }
}