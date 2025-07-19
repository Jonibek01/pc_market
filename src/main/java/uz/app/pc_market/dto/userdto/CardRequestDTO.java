package uz.app.pc_market.dto.userdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import uz.app.pc_market.entity.enums.PaymentStatus;

@Getter
@Setter

public class CardRequestDTO {
    @NotNull(message = "Card number is required")
    @Size(min = 16, max = 16, message = "Card number must be 16 digits")
    private String number;
    @NotNull(message = "Password is required")
    private String password;
    @NotNull(message = "Amount is required")
    @PositiveOrZero(message = "Amount must be non-negative")
    private Double amount;
    @NotNull(message = "Card status is required")
    private PaymentStatus cardStatus;
}