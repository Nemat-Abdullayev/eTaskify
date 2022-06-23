package com.etaskify.mapper;

import com.etaskify.dto.request.UserDTO;
import com.etaskify.dto.request.UserRequestDTO;
import com.etaskify.dto.response.UserResponseDTO;
import com.etaskify.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {

    public static UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    public abstract User mapDtoToEntity(UserRequestDTO requestDTO);


    @Mappings({
            @Mapping(target = "roleName",source = "user.role.name")
    })
    public abstract UserResponseDTO maoEntityToDto(User user);
}