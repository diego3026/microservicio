package com.customerservice.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.lang.reflect.Field;
import java.util.UUID;

@Entity
@Table(name = "customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String full_name;

    public void updateWith(Customer customer){
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                Object value = field.get(customer);
                if (value != null) {
                    field.set(this, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
