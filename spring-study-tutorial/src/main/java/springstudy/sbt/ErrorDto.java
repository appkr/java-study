package springstudy.sbt;

public class ErrorDto {

    private int status;
    private String error;
    private String message;

    private ErrorDto(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public static ErrorDto of(int status, String error, String message) {
        return new ErrorDto(status, error, message);
    }

    public static ErrorDto of(int status, String error) {
        return new ErrorDto(status, error, null);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
