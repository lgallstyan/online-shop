package grid.onlineshop.sweetland.util;

import grid.onlineshop.sweetland.util.enums.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class RoleSetConverter implements AttributeConverter<Set<Role>, String> {

    @Override
    public String convertToDatabaseColumn(Set<Role> roles) {
        return roles.stream()
                .map(Role::name)
                .collect(Collectors.joining(","));
    }

    @Override
    public Set<Role> convertToEntityAttribute(String rolesString) {
        return Arrays.stream(rolesString.split(","))
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }
}
