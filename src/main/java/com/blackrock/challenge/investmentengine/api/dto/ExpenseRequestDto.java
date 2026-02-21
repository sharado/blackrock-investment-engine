package com.blackrock.challenge.investmentengine.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseRequestDto {

    /*@NotNull
    @Pattern(
            regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}",
            message = "timestamp must be in format YYYY-MM-DD HH:mm:ss"
    )*/
    private String date;

//    @NotNull
    private Double amount;
}

