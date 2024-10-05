package santiagoczarny.customers.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import santiagoczarny.customers.classes.OrderDto;

import java.util.List;

@FeignClient(name = "orders", url = "http://localhost:8082/order")
public interface OrderClient {

    @GetMapping("/get-id={id}")
    OrderDto getOrderById(@PathVariable Long id);

    @GetMapping("/all-by-customer{id}")
    List<OrderDto> getOrdersByCustomer(@PathVariable Long id);

}
