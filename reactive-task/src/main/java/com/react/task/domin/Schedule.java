package com.react.task.domin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {

    private Long Id;
    private Long taskId;
    private String description;

}
