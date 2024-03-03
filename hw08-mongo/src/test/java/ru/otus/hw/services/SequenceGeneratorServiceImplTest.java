package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw.helpers.DatabaseSequence;
import ru.otus.hw.repositories.DatabaseSequenceRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Import({SequenceGeneratorServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisplayName("Should execute test for SequenceGeneratorServiceImpl")
public class SequenceGeneratorServiceImplTest {

    @MockBean
    public DatabaseSequenceRepository databaseSequenceRepository;

    @Autowired
    @InjectMocks
    public SequenceGeneratorServiceImpl sequenceGeneratorService;

    @Test
    @DisplayName("should return new sequence")
    void shouldReturnNewSequence() {
        // Arrange
        var sequenceName = "testSequence";
        Optional<DatabaseSequence> databaseSequence = Optional.empty();
        Mockito.when(databaseSequenceRepository.findById(sequenceName)).thenReturn(databaseSequence);

        // Act
        var sequence = sequenceGeneratorService.getNext(sequenceName);

        // Assert
        assertThat(sequence).isEqualTo(1);
    }

    @Test
    @DisplayName("should return existed sequence")
    void shouldReturnExistedSequence() {
        // Arrange
        var sequenceName = "testSequence";
        Optional<DatabaseSequence> databaseSequence = Optional.of(new DatabaseSequence(sequenceName, 1));
        Mockito.when(databaseSequenceRepository.findById(sequenceName)).thenReturn(databaseSequence);

        // Act
        var sequence = sequenceGeneratorService.getNext(sequenceName);

        // Assert
        assertThat(sequence).isEqualTo(2);
    }

}
