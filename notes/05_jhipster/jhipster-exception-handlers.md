#### Jhipster Exception Handlers

```java
@ControllerAdvice class *.web.rest.errors.class ExceptionTranslator implements ProblemHandling {
    @Override
    public ResponseEntity<Problem> handleFoo(RuntimeException ex, NativeWebRequest req) {
        Problem problem = Problem.builder()....;
        return create(ex, problem, req);
    }
}
```
