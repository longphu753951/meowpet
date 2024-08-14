package com.phutl.meowpet.modules.character;

public interface CustomCharacterRepository {
    boolean softDelete(Long id);
    boolean hardDelete(Long id);
}
