package springstudy.events;

import org.springframework.context.ApplicationEvent;

public class GenericApplicationEvent<T> extends ApplicationEvent {

    private T what;
    private boolean success;

    public GenericApplicationEvent(T what, boolean success) {
        super(what);
        this.what = what;
        this.success = success;
    }

    public T getWhat() {
        return what;
    }

    public boolean isSuccess() {
        return success;
    }
}
