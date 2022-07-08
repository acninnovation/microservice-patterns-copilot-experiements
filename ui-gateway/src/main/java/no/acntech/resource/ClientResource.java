package no.acntech.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RequestMapping(path = "/api/client")
@RestController
public class ClientResource {

    @GetMapping(path = "config")
    public Map<String, Object> getConfig() {
        return new LinkedHashMap<>();
    }
}
