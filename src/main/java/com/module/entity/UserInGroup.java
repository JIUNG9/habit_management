package com.module.entity;


import com.module.type.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "user_in_group",uniqueConstraints= {@UniqueConstraint(columnNames = {"nickName"})})
public class UserInGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    //there should not be the same name in the group
    @NotNull
    String nickName;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    Role role;

    @Column(name = "warn_count", columnDefinition = "int default 0")
    int warnCount;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private Group group;

    @Override
    public String toString(){
        return "the group name is: " + group.getName() + " user nick name is : " + this.getNickName() +" Role is: "+ this.getRole();
    }

}
