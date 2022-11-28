package com.module.entity;


import com.module.dto.HabitDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@DynamicInsert
public class Habit extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "habit_id", nullable = false)
    private Long habitID;

    @Column(name = "habit_name", nullable = false)
    private String habitName;

    @Column(name = "habit_amount", nullable = false)
    private Long habitAmount;//단축정량유닛 윗몸일으키기 300회

    @Column(name = "habit_period", nullable = false)
    private Long habitPeriod;//단축기간 유닛(밀리초) 1일 3일 7일 30일

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "habit_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")//디폴트 현재시간
    private Timestamp habitDate;//습관 시간  습관시작날짜
    // 습관 달성 실패시 habitDate를 오늘 날짜로

    @Column(name = "is_checked", columnDefinition = "BIT(1) DEFAULT 0")//디폴트 0
    private Boolean isChecked;//재확인하기
    //하면 true 안하면 false
    // habitDate에서 habitPeriod만큼 지난 시점에서 true면 false로 바꿔야지
    // habitDate에서 habitPeriod만큼 지난 시점에서 false면 habitDate를 현재 시간으로 수정

    @Column(name = "habit_count", columnDefinition = "BIGINT DEFAULT 0", insertable = false)
    private Long habitCount;//check 횟수

    @Column(name = "picture_location")
    private String pictureLocation;//사진경로

    @ManyToOne
    @JoinColumn
    private HabitCategory habitCategory;

    @ManyToOne
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "habit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    public void updateHabit(HabitCategory habitCategory, User user){
        this.habitCategory = habitCategory;
        this.user = user;
    }
    public void updateHabitBody(HabitDTO habitDTO){
        this.habitName = habitDTO.getName();
        this.habitAmount = habitDTO.getAmount();
        this.habitPeriod = habitDTO.getPeriod();
        this.isChecked = this.getIsChecked();
        this.habitDate = this.getHabitDate();
        this.pictureLocation = habitDTO.getLocation();
    }

    public void updateHabitDate(){
        this.habitDate = new Timestamp(System.currentTimeMillis());
        this.habitCount = Long.valueOf(0);
    }

    public void updateCheck(){
        this.isChecked = true;
        this.habitCount += 1;
    }

    public Long getTotalAmount(){
       Long totalAmount = this.habitAmount * this.habitCount;
       return totalAmount;
    }

    public Long getTotalPeriod(){
        Long totalPeriod = this.habitPeriod * this.habitCount/1000;
        return totalPeriod;
    }

    public void returnCheck(){
        this.isChecked = false;
    }



}
