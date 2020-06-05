package the.nss.boys.blog.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;


/**
 * Interceptor for UserController
 */
public class TopicLoggerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(TopicLoggerInterceptor.class);

    /**
     * Logs Before "method name" call and after "method name" call
     * @param context method
     * @return
     * @throws Exception
     */
    @AroundInvoke
    public Object log(InvocationContext context) throws Exception {
        LOG.debug("Before method " + context.getMethod().getName() + " call");
        Object result = context.proceed();
        LOG.debug("After method " + context.getMethod().getName() + " call");
        return result;
    }

}
