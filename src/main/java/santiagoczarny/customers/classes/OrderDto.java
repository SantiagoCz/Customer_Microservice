package santiagoczarny.customers.classes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;
    private LocalDate creationDate;
    private String processingStatus;
    private String itemDescription;
    private Long idCustomer;

}
