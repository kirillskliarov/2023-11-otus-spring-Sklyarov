package ru.otus.hw.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class Water {
    private long volume;

    private List<Admixture> admixtures;

    @Override
    public String toString() {
        String admixturesData = admixtures.stream()
                .map(admixture -> String.format(
                        "{ type: %s, concentration: %f }",
                        admixture.getType().toString(),
                        admixture.getConcentration())
                )
                .collect(Collectors.joining(", "));

        return String.format("{ volume: %d, admixtures: [%s] }", volume, admixturesData);
    }
}
