package com.project.restaurant.base.utils;

import com.project.restaurant.components.orders.dtos.OrderCreateRequest;

import javax.servlet.http.HttpServletRequest;

public class ControllerUtil {
    private ControllerUtil() {
    }

    public static OrderCreateRequest createRequestFromServlet(HttpServletRequest httpServletRequest,
                                                              Long uid) {
        return new OrderCreateRequest(
                Long.parseLong(httpServletRequest.getParameter("itemId")),
                uid
        );
    }
}
