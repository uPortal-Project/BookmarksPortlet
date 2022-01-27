package edu.wisc.my.portlets.bookmarks.web.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.portlet.PortletRequest;
import java.util.Collections;

public class LoggerUtils {

    private static final Logger logger = LoggerFactory.getLogger(LoggerUtils.class);

    public static void logRequest(final String label, final PortletRequest request) {
        if (!logger.isInfoEnabled()) {
            return;
        }

        logger.info("{} req params: ", label);
        request.getParameterMap().forEach((k,v) -> logger.info("param: {} -> {}", k, v));
        logger.info("{} req props: ", label);
        Collections.list(request.getPropertyNames()).forEach(p -> logger.info("prop: {} -> {}", p, request.getProperty(p)));
        logger.info("{} req attrs: ", label);
        Collections.list(request.getAttributeNames()).forEach(a -> logger.info("prop: {} -> {}", a, request.getAttribute(a)));
    }
}
