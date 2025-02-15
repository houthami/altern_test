package com.altern.test.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component // Makes this a Spring bean
public class GenericMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Converts any object to a DTO/Entity of the specified target class.
     * Example: genericMapper.convert(entity, MyDTO.class)
     */
    public <T> T convert(Object source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }

    /**
     * Convert a list of objects to a list of target class type.
     * Handles null/empty input gracefully.
     */
    public <S, T> List<T> convertList(List<S> sourceList, Class<T> targetClass) {
        if (sourceList == null || sourceList.isEmpty()) {
            return Collections.emptyList();
        }
        return sourceList.stream()
                .map(source -> convert(source, targetClass))
                .collect(Collectors.toList());
    }

}