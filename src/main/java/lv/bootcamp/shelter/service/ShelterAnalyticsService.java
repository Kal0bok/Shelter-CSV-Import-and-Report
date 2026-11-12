package lv.bootcamp.shelter.service;

import lv.bootcamp.shelter.model.Animal;
import lv.bootcamp.shelter.service.data.ImportResult;
import lv.bootcamp.shelter.service.data.ShelterReportData;
import java.util.stream.Collectors;
import java.util.*;


public class ShelterAnalyticsService {

    public Map<String, Long> countVaccinatedPerSpecies(List<Animal> allAnimals){
        return allAnimals.stream()
                .filter(Animal::isVaccinated)
                .collect(Collectors.groupingBy(Animal::getSpecies,Collectors.counting()));
    }

    public ShelterReportData buildReportData(ImportResult importResult) {
        List<Animal> allAnimals = importResult.allAnimals();

        Set<String> uniqueSpecies = new TreeSet<>();
        Map<String, List<Animal>> animalsBySpecies = new HashMap<>();
        List<String> animalsNeedingVetInput = new ArrayList<>();

        for(Animal animal : allAnimals){
            uniqueSpecies.add(animal.getSpecies());

            animalsBySpecies.computeIfAbsent(animal.getSpecies(), k -> new ArrayList<>()).add(animal);
        if(animal.getAge() == null){
            animalsNeedingVetInput.add(animal.getName() + "(" + animal.getSpecies() + ")");
        }
        }
        return new ShelterReportData(importResult);
    }
}
