#### Jhipster generated Utilities

```java
class *.config.DefaultProfileUtil
+ public static String[] getActiveProfiles(Environment env);

// @see
public static void main(String[] args) {
    SpringApplication app = new SpringApplication(MainApp.class);
    DefaultProfileUtil.addDefaultProfile(app);
    Environment env = app.run(args).getEnvironment();
}
```
```java
class *.security.SecurityUtils
+ public static Optional<String> getCurrentUserLogin();
+ public static boolean isAuthenticated();
+ public static boolean isCurrentUserInRole(String authority);
```
```java
class *.web.rest.util.HeaderUtil
+ public static HttpHeaders createAlert(String message, String param);
+ public static HttpHeaders createEntityCreationAlert(String entityName, String param);
+ public static HttpHeaders createEntityUpdateAlert(String entityName, String param);
+ public static HttpHeaders createEntityDeletionAlert(String entityName, String param);
+ public static HttpHeaders createFailureAlert(String entityName, String errorKey, String defaultMessage);
```
```java
class *.web.rest.util.PaginationUtil
+ public static <T> HttpHeaders generatePaginationHttpHeaders(Page<T> page, String baseUrl);

// Custom
public static PageMetadata getPageMetadata(Page page) {
    return new PageMetadata()
        .size(page.getSize())
        .number(page.getNumber() + 1)
        .totalElements(page.getTotalElements())
        .totalPages(page.getTotalPages());
}

public static PageRequest getPageRequest(int size, int page) {
    return PageRequest.of(getPage(page), getSize(size));
}

private static int getPage(final int page) {
    return page <= 0 ? DEFAULT_PAGE : page - 1;
}

private static int getSize(final int size) {
    return size < 0 ? DEFAULT_SIZE : size;
}
```
