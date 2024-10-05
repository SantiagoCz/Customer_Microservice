package santiagoczarny.customers.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 8, max = 8, message = "The ID number must be exactly 8 digits long")
    @Pattern(regexp = "\\d{8}", message = "The ID number must contain only digits")
    @Column(unique = true, nullable = false, length = 8)
    private String idNumber;

    @Column(name = "first_name")
    @Pattern(regexp = "^[A-Za-zñÑáéíóúÁÉÍÓÚ\\s]+$", message = "The first name can only contain letters and spaces")
    private String firstName;

    @Column(name = "last_name")
    @Pattern(regexp = "^[A-Za-zñÑáéíóúÁÉÍÓÚ\\s]+$", message = "The last name can only contain letters and spaces")
    private String lastName;

    @Column(name = "phone_number")
    @Pattern(regexp = "\\d{9,12}", message = "The phone number must be between 9 and 12 digits long")
    private String phoneNumber;

    @Column(name = "address")
    @NotBlank(message = "The address cannot be blank")
    private String address;


}
