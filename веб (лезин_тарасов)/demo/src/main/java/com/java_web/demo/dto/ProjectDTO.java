package com.java_web.demo.dto;
import lombok.*;

import java.util.Date;

@Data
@Builder
public class ProjectDTO {
     
    private Long id;
    private String name;
    private String description;
    @Builder.Default
    private Date startDate = new Date();
    private Date endDate;

   
}
