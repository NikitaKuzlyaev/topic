package com.topic.repository.test;

import com.topic.entity.test.Dummy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestTransactionalRepository extends JpaRepository<Dummy, Long> {

}
