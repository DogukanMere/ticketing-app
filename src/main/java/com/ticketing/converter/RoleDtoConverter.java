package com.ticketing.converter;

import com.ticketing.dto.RoleDTO;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
@ConfigurationPropertiesBinding
public class RoleDtoConverter implements Converter<String, RoleDTO> {


    @Override
    public RoleDTO convert(String source) {
        return null;
    }
}
