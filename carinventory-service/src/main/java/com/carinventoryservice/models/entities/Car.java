package com.carinventoryservice.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.lang.reflect.Field;
import java.util.UUID;

@Entity
@Table(name = "cars")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String model;
    private String maker;
    private Boolean available;

    public void updateWith(Car car){
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                Object value = field.get(car);
                if (value != null) {
                    field.set(this, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
