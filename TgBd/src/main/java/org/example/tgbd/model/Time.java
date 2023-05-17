package org.example.tgbd.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Time {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String unitOfTime;
    private short amount;
    @ManyToOne
    @JoinColumn(name = "messageId")
    private Memorization memorization;

    public Time(String unitOfTime, short amount) {
        this.unitOfTime = unitOfTime;
        this.amount = amount;
    }

}
