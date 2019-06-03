package springstudy.cloudstream2.stream;

import springstudy.cloudstream2.data.Order;

import java.util.UUID;

public class Event {

    private UUID id;
    private Order subject;
    private String type;
    private String originator;

    public Event() {
    }

    public Event(Order subject, String type, String originator) {
        this.subject = subject;
        this.type = type;
        this.originator = originator;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Order getSubject() {
        return subject;
    }

    public void setSubject(Order subject) {
        this.subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOriginator() {
        return originator;
    }

    public void setOriginator(String originator) {
        this.originator = originator;
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + id +
            ", subject=" + subject +
            ", type='" + type + '\'' +
            ", originator='" + originator + '\'' +
            '}';
    }
}
