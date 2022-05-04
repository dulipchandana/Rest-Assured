package com.react.task.domin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    private Long taskId;
    private String daskDescription;
    private Integer  scheduleOrder;
}
