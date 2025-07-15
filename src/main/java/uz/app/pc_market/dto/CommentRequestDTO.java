package uz.app.pc_market.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDTO {
    private String description;
    private Double rating;
}
