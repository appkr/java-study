package kr.appkr.dockerdemo

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class HelloController {

    @GetMapping("/hello")
    @ResponseBody
    fun helloWorld(): String {
        return "Hello World"
    }

    @GetMapping("/hello/{name}")
    fun helloName(@PathVariable name: String, model: Model): String {
        model.addAttribute("name", name);
        return "hello_name" // Thymeleaf 템플릿 이름
    }

}
