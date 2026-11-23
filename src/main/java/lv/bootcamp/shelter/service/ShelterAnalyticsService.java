package lv.bootcamp.shelter.service;

import lv.bootcamp.shelter.model.Animal;
import lv.bootcamp.shelter.service.data.ImportResult;
import lv.bootcamp.shelter.service.data.ShelterReportData;
import java.util.stream.Collectors;
import java.util.*;

public class ShelterAnalyticsService {

    public Map<String, Long> countVaccinatedPerSpecies(List<Animal> allAnimals) {
        return allAnimals.stream()
                .filter(Animal::isVaccinated)
                .collect(Collectors.groupingBy(Animal::getSpecies, Collectors.counting()));
    }

    public Map<String, Animal> findOldestAnimalPerSpecies(List<Animal> allAnimals) {
        return allAnimals.stream()
                .filter(a -> a.getAge() != null)
                .collect(Collectors.toMap(
                        Animal::getSpecies,
                        animal -> animal,
                        (existing, replacement) -> existing.getAge() > replacement.getAge() ? existing : replacement
                ));
    }

    public ShelterReportData buildReportData(ImportResult importResult) {
        List<Animal> allAnimals = importResult.allAnimals();

        // 1. Collect unique species
        Set<String> uniqueSpecies = allAnimals.stream()
                .map(Animal::getSpecies)
                .collect(Collectors.toCollection(TreeSet::new));

        // 2. Find animals needing vet input (age is null)
        List<String> animalsNeedingVetInput = allAnimals.stream()
                .filter(a -> a.getAge() == null)
                .map(a -> a.getName() + "(" + a.getSpecies() + ")")
                .toList();

        // 3. Invoke analytics methods mentioned by the reviewer
        Map<String, Long> vaccinatedPerSpecies = countVaccinatedPerSpecies(allAnimals);
        Map<String, Animal> oldestAnimalPerSpecies = findOldestAnimalPerSpecies(allAnimals);

        // 4. Return populated report data object with all metrics
        return new ShelterReportData(
                importResult,
                uniqueSpecies,
                vaccinatedPerSpecies,
                oldestAnimalPerSpecies,
                animalsNeedingVetInput
        );
    }
}
