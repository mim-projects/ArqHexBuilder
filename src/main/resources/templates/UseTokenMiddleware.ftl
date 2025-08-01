package ${package};

<#if useJakarta>
import jakarta.ws.rs.NameBinding;
</#if>
<#if !useJakarta>
import javax.ws.rs.NameBinding;
</#if>

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@NameBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface UseTokenMiddleware {
}