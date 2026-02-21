package com.blackrock.challenge.investmentengine.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QPeriodDto {

    @NotNull
    @Min(0)
    private Double fixed;

    @NotNull
    private String start;

    @NotNull
    private String end;
}

