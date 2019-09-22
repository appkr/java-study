/**
 * 1. curl -s -X GET http://localhost:8080/integrate/Foo
 *      invokes a `@RestController`, `IntegrateController#getMessageFromIntegrationService()`
 * 2. Upon receiving the HTTP call, the controller sends the value obtained from `@PathVariable("name")`
 *      to the `IntegrationGateway`
 * 3. `@ServiceActivator` having a same inputChannel name as the `IntegrationGateway` will be invoked
 *      In this example `IntegrationService#anotherMessage()` is the one
 * 4. The `@ServiceActivator` returns back a message to the replyChannel
 * 5. The `@RestController` returns back the message to the HTTP client
 *      Which is "Welcome Foo to Spring Integration"
 */

package springstudy.springintegration.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springstudy.springintegration.messaging.IntegrationGateway;

@RestController
@RequestMapping("/integrate")
public class IntegrationController {

    private final IntegrationGateway integrationGateway;

    public IntegrationController(IntegrationGateway integrationGateway) {
        this.integrationGateway = integrationGateway;
    }

    @GetMapping("/{name}")
    public String getMessageFromIntegrationService(@PathVariable("name") String name) {
        return integrationGateway.sendMessage(name);
    }
}
