package zipkinex;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/")
public class ExampleController {

    private final HubClient hubClient;

    ExampleController(HubClient hubClient){
        this.hubClient = hubClient;
    }

    @Get("/hello")
    public String hello() {
        return "Hello World";
    }

    @Get("/other")
    public String other() {
        return hubClient.getStats();
    }
}
