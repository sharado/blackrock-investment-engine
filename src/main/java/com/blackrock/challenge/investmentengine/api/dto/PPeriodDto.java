package com.blackrock.challenge.investmentengine.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PPeriodDto {

    @NotNull
    @Min(0)
    private Double extra;

    @NotNull
    private String start;

    @NotNull
    private String end;

}

