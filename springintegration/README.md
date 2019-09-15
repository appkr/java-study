## Spring Integration

- `Message`: the unit of data transfer within a Spring Integration context.
- `Channel`: the pipe by which messages are relayed from one system to another.
- `Bridge`: is used to connect two message channels or adapters if for any reason they can't connect directly.
- `Service Activator`: allows us to execute any method on our POJO when a message is received from an inbound channel, and it allows us to write messages to an outward channel.
- `Adapter`: literally an adapter as we know it from plugging into a wall socket or electronic device.
    - Inbound adapters: `@InboundChannelAdapter` bring in messages from the external system
    - Outbound adapters: are used to send messages outwards