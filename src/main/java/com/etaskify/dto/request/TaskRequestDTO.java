package com.etaskify.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("model for task request")
public class TaskRequestDTO {

    @NotNull(message = "title of task cannot be null")
    private String title;

    @NotNull(message = "task description cannot be null")
    private String description;

    @ApiModelProperty("deadLine must be like this #example [ '2 month' , '2 days', '2 hours' ] this field 'll parsed by start words")
    @NotNull(message = "dedLine cannot be null")
    @Length(min = 3, message = "deadLine length must be 3 or greater than")
    private String deadLine;

    private Long organizationId;
}
