package springstudy.cloudstream;

public class LogMessage {
    private String message;

    public LogMessage(String message) {
        this.message = message;
    }

    public LogMessage() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "LogMessage{" +
            "message='" + message + '\'' +
            '}';
    }
}
