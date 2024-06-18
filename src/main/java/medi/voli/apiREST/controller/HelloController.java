package medi.voli.apiREST.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@Tag(name = "Hello", description = "Teste para verificar funcionamento da api")
public class HelloController {

    @GetMapping
    public String helloWorld(){
        return "Hello World!";
    }
}
