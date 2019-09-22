/**
 *
 * ```bash
 * $ curl -s -X GET http://localhost:8080/integrate/Foo
 * Welcome Foo to Spring Integration
 * ```
 *
 * Controller
 *      -> Gateway `IntegrationGateway.sendMessage()`
 *          -> Channel "integration.gateway.channel"
 *      -> ServiceActivator `IntegrationService.anotherMessage()`
 *          -> `replyChannel.send()`
 *      -> API response
 *
 * ---
 *
 * ```bash
 * $ curl -s -X POST http://localhost:8080/integrate -H "Content-type: application/json" -d '{"id": 1, "name": "Foo", "school": "Seoul JH"}'
 * Student{id='1', name='Foo', school='Seoul JH'}
 * ```
 *
 * Controller
 *      -> Gateway `IntegrationGateway.processStudentDetails()`
 *          -> Channel "integration.student.gateway.channel"
 *      -> Transformer `ObjectToJsonTransformer`
 *          -> Channel "integration.student.objectToJson.channel"
 *      -> ServiceActivator `StudentService.receiveMessage()`
 *          -> Channel "integration.student.jsonToObject.channel"
 *      -> Transformer `JsonToObjectTransformer`
 *          -> Channel "integration.student.jsonToObject.fromTransformer.channel"
 *      -> ServiceActivator `StudentService.processJsonToObject()`
 *          -> `replyChannel.send()`
 *      -> API Response
 *
 * Console output:
 * ---
 * GenericMessage [payload={"id":"1","name":"Foo","school":"Seoul JH"}, headers={replyChannel=org.springframework.messaging.core.GenericMessagingTemplate$TemporaryReplyChannel@6cc798f4, errorChannel=org.springframework.messaging.core.GenericMessagingTemplate$TemporaryReplyChannel@6cc798f4, id=1b27918f-bf79-364a-c35c-3f4d00ad8299, json__TypeId__=class springstudy.springintegration.model.Student, contentType=application/json, timestamp=1569156628005}]
 * ---
 * Object to Json: {"id":"1","name":"Foo","school":"Seoul JH"}
 * ---
 * Json to Object: Student{id='1', name='Foo', school='Seoul JH'}
 */
package springstudy.springintegration.controller;

import org.springframework.web.bind.annotation.*;
import springstudy.springintegration.messaging.IntegrationGateway;
import springstudy.springintegration.model.Student;

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

    @PostMapping
    public String processStudentDetails(@RequestBody Student student) {
        return integrationGateway.processStudentDetails(student);
    }
}
