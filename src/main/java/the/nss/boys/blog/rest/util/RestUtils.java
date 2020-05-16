
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the.nss.boys.blog.rest.util;

import java.net.URI;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class RestUtils {

    /**
     * Creates HTTP headers object with a location header with the specified path appended to the current request URI.
     * <p>
     * The {@code uriVariableValues} are used to fill in possible variables specified in the {@code path}.
     *
     * @param path              Path to add to the current request URI in order to construct a resource location
     * @param uriVariableValues Values used to replace possible variables in the path
     * @return HttpHeaders with location headers set
     */
    public static HttpHeaders createLocationHeaderFromCurrentUri(String path, Object... uriVariableValues) {
        assert path != null;

        final URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path(path).buildAndExpand(
                uriVariableValues).toUri();
        final HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.LOCATION, location.toASCIIString());
        return headers;
    }
}
