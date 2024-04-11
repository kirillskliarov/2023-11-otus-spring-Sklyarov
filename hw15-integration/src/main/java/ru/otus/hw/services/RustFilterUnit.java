package ru.otus.hw.services;

import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Admixture;
import ru.otus.hw.domain.AdmixtureType;
import ru.otus.hw.domain.Water;

import java.util.List;
import java.util.Objects;

@Service
public class RustFilterUnit implements FilterUnit {
    public Water clean(Water water) {
        List<Admixture> admixtures = water.getAdmixtures()
                .stream()
                .filter(admixture -> !Objects.equals(admixture.getType(), AdmixtureType.RUST))
                .toList();

        return new Water(water.getVolume(), admixtures);
    }
}
