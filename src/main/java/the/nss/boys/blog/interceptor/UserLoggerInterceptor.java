package the.nss.boys.blog.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;



public class UserLoggerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(UserLoggerInterceptor.class);

    @AroundInvoke
    public Object log(InvocationContext context) throws Exception {
        LOG.debug("Before method " + context.getMethod().getName() + " call");
        Object result = context.proceed();
        LOG.debug("After method " + context.getMethod().getName() + " call");
        return result;
    }

}
