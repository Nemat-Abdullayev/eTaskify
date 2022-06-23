package com.etaskify.model;

import com.etaskify.model.base.AbstractBaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskAssign extends AbstractBaseEntity {

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<User> users;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Task task;
}
