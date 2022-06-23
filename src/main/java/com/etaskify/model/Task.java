package com.etaskify.model;

import com.etaskify.enums.Status;
import com.etaskify.model.base.AbstractBaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task")
public class Task extends AbstractBaseEntity {

    private String title;
    private String description;
    private String deadLine;
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(fetch = FetchType.LAZY)
    private List<User> users;

    @ManyToOne(fetch = FetchType.LAZY)
    private Organization organization;

}
