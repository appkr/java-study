package dev.appkr.debuggerex;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("canIDrive/{name}/{age}")
    @ResponseBody
    public String hello(@PathVariable String name, @PathVariable Integer age) {
        return notifyUserOfDriverStatus(name, age);
    }

    private String notifyUserOfDriverStatus(String name, Integer age) {
        String message = isOfDrivingAge(age) ? "You may drive" : "You man not drive";
        return name + ": " + message;
    }

    private Boolean isOfDrivingAge(Integer age) {
        return age >= 16;
    }

}
