package se.omegapoint.academy.opmarketplace.marketplace.mitigation;

import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.ItemObtained;
import se.omegapoint.academy.opmarketplace.marketplace.domain.services.ItemRepository;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Id;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.events.ItemCreatedEntity;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories.ItemCreatedJPARepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FakeItemAnalyzer {

    private final double SIMILARITY_THRESHOLD = 0.85;

    public List<Id> getAllItemIds(ItemCreatedJPARepository itemRepository) {
        return itemRepository.findAll()
                .stream()
        .map(itemCreatedEntity -> itemCreatedEntity.domainObject().itemId())
                .collect(Collectors.toList());
    }

    public Map<Id, Map<String, Integer>> wordFrequencies(List<Id> itemIds, ItemRepository repository) {
        Map<Id, Map<String, Integer>> wordFrequencies = new HashMap<>();

        for (Id itemId: itemIds) {
            Item item = ((ItemObtained) repository.item(itemId)).item();
            wordFrequencies.put(itemId, wordFrequency(item.description().text()));
        }

        return wordFrequencies;
    }

    private Map<String, Integer> wordFrequency(String description) {
        Map<String, Integer> wordFrequency = new HashMap<>();
        for (String word : description.split("[\\s.,()]+")) {
            word = word.toLowerCase();
            int currentValue = wordFrequency.getOrDefault(word, 0);
            wordFrequency.put(word, currentValue + 1);
        }
        return wordFrequency;
    }

    public List<Id> fakeItems(Map<Id, Map<String, Integer>> wordFrequencies, ItemCreatedJPARepository repository) {
        return wordFrequencies.keySet().stream()
                .filter(id -> isDuplicate(id, wordFrequencies, repository))
                .collect(Collectors.toList());
    }

    private boolean isDuplicate(Id possibleDuplicate,
                                Map<Id, Map<String, Integer>> wordFrequencies,
                                ItemCreatedJPARepository repository) {
//        System.out.println("DUPLICATES FOR " + possibleDuplicate);
        return wordFrequencies.keySet().stream()
                .filter(id -> {
                    double similarity = similarity(wordFrequencies.get(possibleDuplicate), wordFrequencies.get(id));
                    LocalDateTime time1 = repository.findById(possibleDuplicate.toString()).get(0).domainObject().timestamp().toLocalDateTime();
                    LocalDateTime time2 = repository.findById(id.toString()).get(0).domainObject().timestamp().toLocalDateTime();
                    boolean isOriginal = time1.isBefore(time2) || time1.isEqual(time2);
                    if (similarity > SIMILARITY_THRESHOLD && !isOriginal) {
                        System.out.println(similarity + " | " + getTitle(possibleDuplicate, repository) + " == " + getTitle(id, repository));
                    }
                    return similarity > SIMILARITY_THRESHOLD && !isOriginal && !getEmail(possibleDuplicate, repository).equals("legit@email.com");
                }).findAny().isPresent();
    }

    private double similarity(Map<String, Integer> freq1, Map<String, Integer> freq2) {
        int totalSize = freq1.values().stream().mapToInt(value -> value).sum() +
                freq2.values().stream().mapToInt(value -> value).sum();
        int deviation = 0;

        for (String word: freq1.keySet()) {
            deviation += Math.abs(freq1.getOrDefault(word, 0) - freq2.getOrDefault(word, 0));
        }

        for (String word: freq2.keySet()) {
            deviation += Math.abs(freq1.getOrDefault(word, 0) - freq2.getOrDefault(word, 0));
        }

        return 1 - (double) deviation / totalSize;
    }

    private String getTitle(Id id, ItemCreatedJPARepository repository) {
        return repository.findById(id.toString()).get(0).domainObject().item().title().text();
    }

    private String getEmail(Id id, ItemCreatedJPARepository repository) {
        return repository.findById(id.toString()).get(0).domainObject().item().seller().address();
    }
}
