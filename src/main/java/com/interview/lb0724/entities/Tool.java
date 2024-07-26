package com.interview.lb0724.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Tool {

    @Id
    private String toolCode;
    private String toolType;
    private String brand;


    public Tool() {

    }
}
