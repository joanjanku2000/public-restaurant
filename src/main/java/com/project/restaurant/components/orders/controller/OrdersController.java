package com.project.restaurant.components.orders.controller;


import com.project.restaurant.base.utils.ControllerUtil;
import com.project.restaurant.base.utils.LoggedUser;
import com.project.restaurant.base.utils.SearchCriteria;
import com.project.restaurant.components.orders.converter.OrderConverter;
import com.project.restaurant.components.orders.dtos.OrdersDto;
import com.project.restaurant.components.orders.entities.Orders;
import com.project.restaurant.components.orders.service.OrderService;
import com.project.restaurant.components.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {

    private final OrderService orderService;
    private final LoggedUser loggedUser;
    private final OrderConverter orderConverter;

    @Autowired
    public OrdersController(OrderService orderService, UserService userServiceImpl,
                            OrderConverter orderConverter
    ) {
        this.orderService = orderService;
        loggedUser = new LoggedUser(userServiceImpl);
        this.orderConverter = orderConverter;
    }

    @PostMapping("/place")
    @ResponseStatus(HttpStatus.OK)
    public void save(HttpServletRequest httpServletRequest) {
        orderService.saveOrder(ControllerUtil.createRequestFromServlet(httpServletRequest,
                loggedUser.getLoggedUser().getId()));
    }

    @GetMapping
    public ResponseEntity<OrdersDto> findById(@RequestParam Long id) {
        Orders orders = orderService.getOrder(id);
        return ResponseEntity.ok(
                orderConverter
                        .toDto(orders)
        );
    }

    @PutMapping("{id}/feedeback/{feedback}")
    @ResponseStatus(HttpStatus.OK)
    public void feedback(@PathVariable Long id, @PathVariable String feedback) {
        orderService.provideFeedback(id, feedback);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<OrdersDto>> filterOrder(@RequestBody SearchCriteria searchCriteria) {
        return ResponseEntity.ok(
                orderConverter.toDtoList(orderService.ordersFilter(searchCriteria)));

    }

    @PutMapping("/deliver/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deliver(@PathVariable Long id) {
        orderService.deliverOrder(id);
    }

    @GetMapping("/my-orders")
    public ResponseEntity<List<OrdersDto>> findOrdersOfUser() {
        return ResponseEntity.ok(
                orderConverter.toDtoList(orderService.findOrdersOfUser(loggedUser.getLoggedUser().getId()))
        );
    }
}
