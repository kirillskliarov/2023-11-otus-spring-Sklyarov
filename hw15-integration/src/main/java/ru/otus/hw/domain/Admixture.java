package ru.otus.hw.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Admixture {
    private AdmixtureType type;

    private double concentration;
}
