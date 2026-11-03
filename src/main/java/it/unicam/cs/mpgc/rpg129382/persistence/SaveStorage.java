package it.unicam.cs.mpgc.rpg129382.persistence;

import java.util.Optional;

public interface SaveStorage {
    void save(SaveData data);

    Optional<SaveData> load();
}
