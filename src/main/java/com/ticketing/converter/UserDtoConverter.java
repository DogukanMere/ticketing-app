package com.ticketing.converter;

import com.ticketing.dto.UserDTO;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class UserDtoConverter implements Converter<String, UserDTO> {


    @Override
    public UserDTO convert(String source) {
        return null;
    }
}
