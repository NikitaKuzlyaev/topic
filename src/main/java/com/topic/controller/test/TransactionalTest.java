package com.topic.controller.test;


import com.topic.dto.api.response.MessageResponse;
import com.topic.service.test.TestTransactionalService;
import com.topic.util.annotations.LoggingToSystemOut;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/t")
public class TransactionalTest {

    private final TestTransactionalService testTransactionalService;

    public TransactionalTest(
            TestTransactionalService testTransactionalService)
    {
        this.testTransactionalService = testTransactionalService;
    }

    @GetMapping("/req")
    @LoggingToSystemOut
    public MessageResponse transactionalRequest(){
        testTransactionalService.makeTransactional();
        return new MessageResponse("all right");
    }


}
