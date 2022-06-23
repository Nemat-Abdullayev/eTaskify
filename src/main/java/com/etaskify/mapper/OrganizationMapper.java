package com.etaskify.mapper;


import com.etaskify.dto.request.OrganizationRequestDTO;
import com.etaskify.dto.request.UserDTO;
import com.etaskify.enums.RoleName;
import com.etaskify.model.Organization;
import com.etaskify.model.Role;
import com.etaskify.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class OrganizationMapper {

    public static OrganizationMapper INSTANCE = Mappers.getMapper(OrganizationMapper.class);


    @Mappings({
            @Mapping(target = "name", source = "requestDTO.orgName"),
            @Mapping(target = "users", expression = "java(mapUser(requestDTO.getUser()))")
    })
    public abstract Organization mapDtoToEntity(OrganizationRequestDTO requestDTO);

    public List<User> mapUser(UserDTO userDTO) {
        Role role = new Role();
        role.setId(1L);

        return List.of(
                User.builder()
                        .name(userDTO.getName())
                        .surname(userDTO.getSurname())
                        .role(role)
                        .email(userDTO.getEmail())
                        .password(new BCryptPasswordEncoder().encode(userDTO.getPassword()))
                        .build());
    }
}
