package zipkinex;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.Client;

@Client("http://127.0.0.1:8080")
public interface HubClient {

    @Get("/hubs/stats")
    public String getStats();

}
