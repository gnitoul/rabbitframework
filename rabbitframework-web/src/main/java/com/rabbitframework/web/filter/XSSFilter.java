package com.rabbitframework.web.filter;

import org.apache.commons.collections.CollectionUtils;
import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@PreMatching
public class XSSFilter implements ContainerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(XSSFilter.class);

    /**
     * @see ContainerRequestFilter#filter(ContainerRequestContext)
     */
    @Override
    public void filter(ContainerRequestContext request) {
        logger.debug("XSSFilter=>filter");
        cleanQueryParams(request);
        //cleanHeaders(request.getHeaders());
    }

    /**
     * Replace the existing query parameters with ones stripped of XSS vulnerabilities
     *
     * @param request
     */
    private void cleanQueryParams(ContainerRequestContext request) {
        UriBuilder builder = request.getUriInfo().getRequestUriBuilder();
        MultivaluedMap<String, String> queries = request.getUriInfo().getQueryParameters();

        for (Map.Entry<String, List<String>> query : queries.entrySet()) {
            String key = query.getKey();
            List<String> values = query.getValue();

            List<String> xssValues = new ArrayList<String>();
            for (String value : values) {
                xssValues.add(stripXSS(value));
            }

            Integer size = CollectionUtils.size(xssValues);
            builder.replaceQueryParam(key);

            if (size == 1) {
                builder.replaceQueryParam(key, xssValues.get(0));
            } else if (size > 1) {
                builder.replaceQueryParam(key, xssValues.toArray());
            }
        }

        request.setRequestUri(builder.build());
    }


    /**
     * Replace the existing headers with ones stripped of XSS vulnerabilities
     *
     * @param headers
     */
    private void cleanHeaders(MultivaluedMap<String, String> headers) {
        for (Map.Entry<String, List<String>> header : headers.entrySet()) {
            String key = header.getKey();
            List<String> values = header.getValue();

            List<String> cleanValues = new ArrayList<String>();
            for (String value : values) {
                cleanValues.add(stripXSS(value));
            }

            headers.put(key, cleanValues);
        }
    }

    /**
     * Strips any potential XSS threats out of the value
     *
     * @param value
     * @return
     */
    public String stripXSS(String value) {
        if (value == null)
            return null;
        value = ESAPI.encoder().encodeForHTML(value);


        // Use the ESAPI library to avoid encoded attacks.
//        value = ESAPI.encoder().canonicalize(value);
//
//        // Avoid null characters
//        value = value.replaceAll("\0", "");
//
//        // Clean out HTML
//        Document.OutputSettings outputSettings = new Document.OutputSettings();
//        outputSettings.escapeMode(EscapeMode.xhtml);
//        outputSettings.prettyPrint(false);
//        value = Jsoup.clean(value, "", Whitelist.none(), outputSettings);
        return value;
    }
}