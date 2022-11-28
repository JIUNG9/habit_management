package com.module.service;

import com.module.dto.*;
import com.module.dto.UserHabitDto;

import java.util.List;

public interface HabitService {
    HabitDTO createHabit(long habitCategoryId, long memberId, HabitDTO habitDto);//습관 만들기

    UserHabitDto getUserHabit(String categoryType, long userId);

    List<HabitDTO> getHabitsByCategoryId(long habitCategoryId);//카테고리 하위 습관 다불러오기

    List<HabitDTO> getHabitsByMemberId(long memberId);//멤버의 습관 다 불러오기

    PeriodResponse getTotalPeriods(long habitCategoryId);

    AmountResponse getTotalAmounts(long habitCategoryId);

    MyPeriodResponse getMyPeriod(long habitID);
    MyAmountResponse getMyAmount(long habitId);

    HabitDTO getHabitById(Long habitId);// 습관카테고리넣고 습관 아이디도 넣은거

    HabitDTO updateHabit(long habitId, HabitDTO habitRequest);//습관 수정하기

    HabitDTO updateHabitCheck(Long habitId);//습관 체크 true로 업데이트

    HabitDTO judgeHabitCheck(Long habitId);

    void deleteHabit(Long habitId);// 습관 삭제


}