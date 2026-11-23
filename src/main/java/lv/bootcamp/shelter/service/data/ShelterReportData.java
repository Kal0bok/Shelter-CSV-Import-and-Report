package lv.bootcamp.shelter.service.data;

import java.util.Map;

public record ShelterReportData(ImportResult importResult) {

    public CharSequence uniqueSpecies() {
        return null;
    }

    public Map<Object, Object> vaccinatedPerSpecies() {
        return Map.of();
    }

    public Map<Object, Object> oldestAnimalPerSpecies() {
        return Map.of();
    }

    public CharSequence animalsNeedingVetInput() {
        return null;
    }
}
