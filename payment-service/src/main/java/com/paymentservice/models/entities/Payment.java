package com.paymentservice.models.entities;

import com.paymentservice.models.entities.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.lang.reflect.Field;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID booking_id;
    private int credit_card;
    private int amount;
    private PaymentStatus status;
    private UUID transaction_id;

    public void updateWith(Payment payment){
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                Object value = field.get(payment);
                if (value != null) {
                    field.set(this, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
