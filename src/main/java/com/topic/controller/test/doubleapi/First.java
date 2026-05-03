package com.topic.controller.test.doubleapi;


import com.topic.dto.api.response.MessageResponse;
import com.topic.util.annotations.LoggingToSystemOut;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/double")
public class First {


    @GetMapping("/same")
    @LoggingToSystemOut
    public MessageResponse testController() {
        return new MessageResponse("all right");
    }


}
