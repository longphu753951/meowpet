package com.phutl.meowpet.repository;

public interface CustomCharacterRepository {
    boolean softDelete(Long id);
    boolean hardDelete(Long id);
}
