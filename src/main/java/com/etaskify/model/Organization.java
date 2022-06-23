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
@Table(name = "organizations")
public class Organization extends AbstractBaseEntity {

    private String name;
    private String phoneNumber;
    private String address;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "organization")
    private List<User> users;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "organization")
    private List<Task> tasks;


}
