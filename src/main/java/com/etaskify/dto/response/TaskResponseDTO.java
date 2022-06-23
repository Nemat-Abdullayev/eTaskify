package com.etaskify.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("model for task response")
public class TaskResponseDTO {

    private Long id;
    private String title;
    private String description;
    private String status;
    private String deadLine;
    private String created;
    private String endDate;

}
