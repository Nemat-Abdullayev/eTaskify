package com.etaskify.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("model for task-assign request")
public class TaskAssignRequestDTO {


    @NotEmpty(message = "user id list cannot be empty")
    @NotNull(message = "user id list cannot be null")
    private List<Long> userIdList;

    @NotNull(message = "task id cannot be null")
    private Long taskId;
}
