package org.novi.languagelearner.utils;

import jakarta.servlet.http.HttpServletRequest;

import java.net.URI;

public class UrlHelper {
    public static String getCurrentUrlString(HttpServletRequest request) {
        return request.getRequestURL().toString();
    }

    public static String getCurrentUrlString(HttpServletRequest request, Long id) {
        return request.getRequestURL().toString() + "/" + id.toString();
    }

    public static URI getCurrentUrl(HttpServletRequest request) {
        return convertToURI(getCurrentUrlString(request));
    }

    public static URI getCurrentUrlWithId(HttpServletRequest request, Long id) {
        return convertToURI(getCurrentUrlString(request, id));
    }

    private static URI convertToURI(String uri) {
        return URI.create(uri);
    }
}
