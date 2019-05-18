package module9;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Module9 {

    // The @EnableCaching annotation triggers a post processor that inspects
    // every Spring bean for the presence of caching annotations on public
    // methods. If such an annotation is found, a proxy is automatically created
    // to intercept the method call and handle the caching behavior accordingly.
    //
    // The annotations that are managed by this post processor are Cacheable,
    // CachePut and CacheEvict. You can refer to the javadocs and the
    // documentation for more details.
    //
    // Spring Boot automatically configures a suitable CacheManager to serve as
    // a provider for the relevant cache. See the Spring Boot documentation for
    // more details. https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-caching.html
    //
    // Our sample does not use a specific caching library so our cache store is
    // the simple fallback that uses ConcurrentHashMap. The caching abstraction
    // supports a wide range of cache library and is fully compliant with
    // JSR-107 (JCache).

    public static void main(String[] args) {
        SpringApplication.run(Module9.class, args);
    }

}
