package com.module.controller;

import com.module.dto.*;
import com.module.service.HabitService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class HabitController {

    private HabitService habitService;
    private static final Logger logger = LoggerFactory
            .getLogger(HabitController.class);

    public HabitController(HabitService habitService) {this.habitService = habitService;}

    @PostMapping("/categories/{categoryId}/members/{memberId}/habits")
    public ResponseEntity<HabitDTO> createHabit(@PathVariable(value = "categoryId") long habitCategoryId,
                                                @PathVariable(value = "memberId") long memberId,
                                                @Valid @RequestBody HabitDTO habitDTO){

        return new ResponseEntity<>(habitService.createHabit(habitCategoryId, memberId, habitDTO), HttpStatus.CREATED);
    }

    @GetMapping("/categories/{categoryId}/habits")
    public List<HabitDTO> getHabitsByHabitCategoryId(@PathVariable(value = "categoryId") Long habitCategoryId){
        return habitService.getHabitsByCategoryId(habitCategoryId);
    }

    @GetMapping("/members/{memberId}/habits")
    public List<HabitDTO> getHabitsByHabitMemberId(@PathVariable(value = "memberId") long memberId){
        return habitService.getHabitsByMemberId(memberId);
    }

    @GetMapping("/habits/{habitId}")
    public ResponseEntity<HabitDTO> getHabitById(@PathVariable(value = "habitId") Long habitId){

        HabitDTO habitDTO = habitService.getHabitById(habitId);

        return new ResponseEntity<>(habitDTO, HttpStatus.OK);
    }

    @GetMapping("categories/{categoryId}/total_amount")
    public ResponseEntity<AmountResponse> getTotalAmounts(@PathVariable(value = "categoryId") Long habitCategoryId){
        AmountResponse totalAmount = habitService.getTotalAmounts(habitCategoryId);
        logger.info("total amount: " + totalAmount);
        return new ResponseEntity<>(totalAmount, HttpStatus.OK);
    }

    @GetMapping("categories/{categoryId}/total_period")
    public ResponseEntity<PeriodResponse> getTotalPeriods(@PathVariable(value = "categoryId") Long habitCategoryId){
        PeriodResponse totalPeriod = habitService.getTotalPeriods(habitCategoryId);
        logger.info("total period: " + totalPeriod);
        return new ResponseEntity<>(totalPeriod, HttpStatus.OK);
    }

    @GetMapping("/habits/{habitID}/my_amount")
    public ResponseEntity<MyAmountResponse> getMyAmount(@PathVariable(value = "habitId") long habitId){
        MyAmountResponse myAmount = habitService.getMyAmount(habitId);
        return new ResponseEntity<>(myAmount, HttpStatus.OK);
    }

    @GetMapping("/habits/{habitID}/my_period")
    public ResponseEntity<MyPeriodResponse> getMyPeriod(@PathVariable(value = "habitId") long habitId){
        MyPeriodResponse myPeriod = habitService.getMyPeriod(habitId);
        return new ResponseEntity<>(myPeriod, HttpStatus.OK);
    }

    @PutMapping("/habits/{habitId}")
    public ResponseEntity<HabitDTO> updateHabit(@PathVariable(value = "habitId") Long habitId, @Valid @RequestBody HabitDTO habitDTO){
        HabitDTO updatedHabit = habitService.updateHabit(habitId, habitDTO);
        return new ResponseEntity<>(updatedHabit, HttpStatus.OK);
    }

    @PutMapping("check/success/habits/{habitId}")//is_checked를 1로
    public ResponseEntity<HabitDTO> updateCheck(@PathVariable(value = "habitId") Long habitId){
        HabitDTO updatedHabit = habitService.updateHabitCheck(habitId);
        return new ResponseEntity<>(updatedHabit, HttpStatus.OK);
    }

    @PutMapping("check/judge/habits/{habitId}")//check 성공 -> check를 false로 check 실패 -> date를 0로
    public ResponseEntity<HabitDTO> judgeHabitCheck(@PathVariable(value = "habitId") Long habitId){
        HabitDTO updatedHabit = habitService.judgeHabitCheck(habitId);
        return new ResponseEntity<>(updatedHabit, HttpStatus.OK);
    }

    @DeleteMapping("/habits/{habitId}")
    public ResponseEntity<String> deleteHabit(@PathVariable(value = "habitId") Long habitId){
        habitService.deleteHabit(habitId);
        return new ResponseEntity<>("Habit deleted successfully", HttpStatus.OK);
    }
}
