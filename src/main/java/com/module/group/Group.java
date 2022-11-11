package com.module.group;

import com.module.chat.Room;
import com.module.user.Role;
import com.module.user.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "user_group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @NotNull
    private String name;

    //admin should be user email
    @NotNull
    private String admin;

    @OneToMany(mappedBy = "group", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<UserGroup> userGroup;

    @OneToOne(mappedBy = "group", cascade = CascadeType.REMOVE)
    private Room room;

    @Override
    public String toString(){
        return "Group name : " + this.name +"'\n" + "admin: "+ this.admin;
    }
}
