package dev.appkr;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.util.DefaultPayload;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RSocketTest {

    private static final int port = 10000;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Test
    public void testFireAndForget() {

        // Server

        final RSocket rSocketImpl = new RSocket() {
            @Override
            public Mono<Void> fireAndForget(Payload payload) {
                log.info(payload.getDataUtf8());
                return Mono.empty();
            }
        };

        final Disposable server = RSocketServer
                .create((setup, sendingSocket) -> Mono.just(rSocketImpl))
                .bind(TcpServerTransport.create("localhost", port))
                .subscribe();

        log.info("TCP server is listening port {}", port);

        // Client

        final RSocket socket = RSocketConnector
                .connectWith(TcpClientTransport.create("localhost", port))
                .block();

        log.info("TCP client started with a socket localhost:{}", port);

        assert socket != null;
        socket
                .fireAndForget(DefaultPayload.create("Hello RSocket!"))
                .subscribe(null, null, () -> server.dispose())
                .dispose();
    }

    @Test
    public void testRequestResponse() {

        // Server

        final RSocket rSocketImpl = new RSocket() {
            @Override
            public Mono<Payload> requestResponse(Payload payload) {
                log.info(payload.getDataUtf8());
                return Mono.just(DefaultPayload.create("Latte price is 1,200KRW"));
            }
        };

        final Disposable server = RSocketServer
            .create((setup, sendingSocket) -> Mono.just(rSocketImpl))
            .bind(TcpServerTransport.create("localhost", port))
            .subscribe();

        log.info("TCP server is listening port {}", port);

        // Client

        final RSocket socket = RSocketConnector
            .connectWith(TcpClientTransport.create("localhost", port))
            .block();

        log.info("TCP client started with a socket localhost:{}", port);

        assert socket != null;
        socket
            .requestResponse(DefaultPayload.create("How much do I pay for a cup of Latte?"))
            .map(Payload::getDataUtf8)
            .doOnNext(x -> log.info("{}", x))
            .block();

        socket.dispose();
        server.dispose();
    }

    @Test
    public void testRequestStream() throws InterruptedException {

        // Server

        final RSocket rSocketImpl = new RSocket() {
            @Override
            public Flux<Payload> requestStream(Payload payload) {
                log.info(payload.getDataUtf8());
                return Flux.range(1, 10)
                    .map(i -> DefaultPayload.create("onNext-" + i));
            }
        };

        final Disposable server = RSocketServer
            .create((setup, sendingSocket) -> Mono.just(rSocketImpl))
            .bind(TcpServerTransport.create("localhost", port))
            .subscribe();

        log.info("TCP server is listening port {}", port);

        // Client

        final RSocket socket = RSocketConnector
            .connectWith(TcpClientTransport.create("localhost", port))
            .block();

        log.info("TCP client started with a socket localhost:{}", port);

        assert socket != null;
        socket
            .requestStream(DefaultPayload.create("Hello Stream!"))
            .subscribe(
                payload -> log.info(payload.getDataUtf8()),
                error -> log.error(error.getMessage()),
                () -> log.info("Completed!")
            );

        Thread.sleep(1000);
    }
}
