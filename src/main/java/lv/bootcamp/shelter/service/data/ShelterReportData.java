package lv.bootcamp.shelter.service.data;

import lv.bootcamp.shelter.model.Animal;

import java.util.List;
import java.util.Map;
import java.util.Set;

public record ShelterReportData(
        ImportResult importResult,
        Set<String> uniqueSpecies,
        Map<String, Long> vaccinatedPerSpecies,
        Map<String, Animal> oldestAnimalPerSpecies,
        List<String> animalsNeedingVetInput
) {}
