package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reservation {
    @Id
    private int id;
    private String name;
    @Column(name = "fromDate", columnDefinition = "DATE")
    private LocalDate fromDate;
    @Column(name = "toDate", columnDefinition = "DATE")
    private LocalDate toDate;
    @OneToOne
    private Room room;
}
