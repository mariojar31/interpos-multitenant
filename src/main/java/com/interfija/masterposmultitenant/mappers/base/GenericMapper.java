package com.interfija.masterposmultitenant.mappers.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface GenericMapper<E, D> {

    D toDto(E entity);

    E toEntity(D dto);

    /**
     * Convierte una lista de entidades a lista de DTOs
     */
    default List<D> toDtoList(List<E> entities) {
        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        }

        return entities.stream()
                .map(this::toDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una lista de DTOs a lista de entidades
     */
    default List<E> toEntityList(List<D> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return new ArrayList<>();
        }

        return dtos.stream()
                .map(this::toEntity)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
