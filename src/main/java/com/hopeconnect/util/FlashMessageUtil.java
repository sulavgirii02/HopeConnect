package com.hopeconnect.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public final class FlashMessageUtil {
    private FlashMessageUtil() {}
    public static void success(HttpServletRequest request, String message) { set(request, "success", message); }
    public static void error(HttpServletRequest request, String message) { set(request, "error", message); }
    public static void warning(HttpServletRequest request, String message) { set(request, "warning", message); }
    public static void info(HttpServletRequest request, String message) { set(request, "info", message); }
    private static void set(HttpServletRequest request, String type, String message) {
        HttpSession session = request.getSession();
        session.setAttribute("flashType", type);
        session.setAttribute("flashMessage", message);
    }
}
