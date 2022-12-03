package com.module.service.impl;

import com.module.dto.*;
import com.module.entity.Habit;
import com.module.entity.HabitCategory;
import com.module.entity.User;
import com.module.exception.ResourceNotFoundException;
import com.module.repository.HabitCategoryRepository;
import com.module.repository.HabitRepository;
import com.module.repository.UserRepository;
import com.module.service.HabitService;
import com.module.dto.UserHabitDto;
import com.module.utils.lambda.BindParameterSupplier;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@EnableAsync
@Service
public class HabitServiceImpl implements HabitService {
    private HabitRepository habitRepository;
    private HabitCategoryRepository habitCategoryRepository;
    private UserRepository userRepository;


    public HabitServiceImpl(HabitRepository habitRepository, HabitCategoryRepository habitCategoryRepository, UserRepository userRepository){
        this.habitRepository = habitRepository;
        this.habitCategoryRepository = habitCategoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public HabitDTO createHabit(long habitCategoryID, long memberId, HabitDTO habitDTO) {
        Habit habit = mapToEntity(habitDTO);

        //retrieve Habit Category entity by id
        HabitCategory habitCategory = habitCategoryRepository.findById(habitCategoryID).orElseThrow(() -> new ResourceNotFoundException("HabitCategory", "habit category id", habitCategoryID));

        //retrieve Member entity by id
        User user = Optional.ofNullable(userRepository.getUserById(memberId)).orElseThrow(() -> new ResourceNotFoundException("Member", "member Id", memberId));

        //set member, habitCategory to habit entity
        habit.updateHabit(habitCategory, user);

        //habit entity to DB
        Habit newHabit = habitRepository.save(habit);

        return mapToDTO(newHabit);
    }

    @Override
    public UserHabitDto getUserHabit(String categoryType, long userId) {

        HabitCategory habitCategory =
                Optional.ofNullable(
                        habitCategoryRepository.findByCategoryName(
                        categoryType)).
                        orElseThrow(() -> new ResourceNotFoundException("Habit Category", "Habit Category Id", categoryType));

        User user = Optional.ofNullable(userRepository.getUserById(userId)).orElseThrow(BindParameterSupplier.bind(EntityExistsException::new, "there is no user with that user_id"));

        Habit habit =
                Optional.
                        ofNullable(
                                habitRepository.findByUserAndHabitCategory(user, habitCategory)).
                                    orElseThrow(BindParameterSupplier.bind(EntityNotFoundException::new,"there is no habit with that user"));

        int totalAmount = (int) (habit.getTotalAmount()/habit.getTotalPeriod());
       return  UserHabitDto.builder().categoryName(categoryType).startTime(habit.getHabitDate()).totalAmount(totalAmount).userName(habit.getUser().getUsername()).build();

    }

    @Override
    public List<HabitDTO> getHabitsByCategoryId(long habitCategoryId) {
        //retrieve habits by category ID
        List<Habit> habits = habitRepository.findByHabitCategoryId(habitCategoryId);

        //convert list of habit entities to list of habit dto
        return habits.stream().map(habit -> mapToDTO(habit)).collect(Collectors.toList());
    }

    @Override
    public List<HabitDTO> getHabitsByMemberId(long memberId) {
        //retrieve habits by member Id
        List<Habit> habits = habitRepository.findByUserId(memberId);

        //convert list of habit entities to list of habit dto
        return habits.stream().map(habit -> mapToDTO(habit)).collect(Collectors.toList());
    }

    @Override
    public PeriodResponse getTotalPeriods(long habitCategoryId) {
        //retrieve habits by category ID
        List<Habit> habits = habitRepository.findByHabitCategoryId(habitCategoryId);
        HabitCategory habitCategory = habitCategoryRepository.findById(habitCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Habit Category", "HabitCategory Id", habitCategoryId));

        String categoryName = habitCategory.getHabitCategoryName();
        List<Long> totalPeriods = habits.stream().map(habit -> habit.getTotalPeriod()).collect(Collectors.toList());

        List<Long> totalPeriodCount = getTotalPeriodCount(totalPeriods);

        PeriodResponse periodResponse = PeriodResponse.builder()
                .categoryName(categoryName)
                .totalPeriodCount(totalPeriodCount)
                .build();

        return periodResponse;
    }

    @Override
    public AmountResponse getTotalAmounts(long habitCategoryId) {
        //retrieve habits by category ID
        List<Habit> habits = habitRepository.findByHabitCategoryId(habitCategoryId);
        HabitCategory habitCategory = habitCategoryRepository.findById(habitCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Habit Category", "HabitCategory Id", habitCategoryId));

        String categoryName = habitCategory.getHabitCategoryName();
        List<Long> totalAmounts = habits.stream().map(habit -> habit.getTotalAmount()).collect(Collectors.toList());

        List<Long> totalAmountCount = getTotalAmountCount(totalAmounts);

        AmountResponse amountResponse = AmountResponse.builder()
                .categoryName(categoryName)
                .totalAmountCount(totalAmountCount)
                .max(Collections.max(totalAmounts))
                .build();

        return amountResponse;
    }

    @Override
    public MyPeriodResponse getMyPeriod(long habitId) {
        Habit habit = habitRepository.findById(habitId).orElseThrow(() -> new ResourceNotFoundException("Habit", "habit id", habitId));

        MyPeriodResponse myPeriodResponse = MyPeriodResponse.builder()
                .myTotalPeriod(habit.getTotalPeriod())
                .memberName(habit.getUser().getName())
                .categoryName(habit.getHabitCategory().getHabitCategoryName())
                .build();

        return myPeriodResponse;//초단위로 보내는거임
    }

    @Override
    public MyAmountResponse getMyAmount(long habitId) {
        Habit habit = habitRepository.findById(habitId).orElseThrow(() -> new ResourceNotFoundException("Habit", "habit id", habitId));

        MyAmountResponse myAmountResponse = MyAmountResponse.builder()
                .myTotalAmount(habit.getTotalAmount())
                .memberName(habit.getUser().getName())
                .categoryName(habit.getHabitCategory().getHabitCategoryName())
                .build();

        return myAmountResponse;
    }


    @Override
    public HabitDTO getHabitById(Long habitId) {

        //retrieve habit by id
        Habit habit = habitRepository.findById(habitId).orElseThrow(() -> new ResourceNotFoundException("Habit", "id", habitId));

        return mapToDTO(habit);
    }

    @Override
    public HabitDTO updateHabit( long habitId, HabitDTO habitRequest) {

        //retrieve habit by id
        Habit habit = habitRepository.findById(habitId).orElseThrow(() -> new ResourceNotFoundException("Habit", "id", habitId));

        habit.updateHabitBody(habitRequest);

        Habit updatedHabit = habitRepository.save(habit);
        return mapToDTO(updatedHabit);
    }

    @Override
    public HabitDTO updateHabitCheck(Long habitId) {
        //retrieve habit entity by id
        Habit habit = habitRepository.findById(habitId).orElseThrow(() -> new ResourceNotFoundException("Habit", "id", habitId));

        habit.updateCheck(); // 습관 체크하기 true로

        Habit updatedHabit = habitRepository.save(habit);
        return mapToDTO(updatedHabit);
    }

    @Override
    public HabitDTO judgeHabitCheck(Long habitId) {//check값을 기반으로 계산
        //retrieve habit entity by id
        Habit habit = habitRepository.findById(habitId).orElseThrow(() -> new ResourceNotFoundException("Habit", "id", habitId));

        if(!habit.getIsChecked()){
            habit.updateHabitDate();
            //habitdate 초기화 및 카운트를 0로 만든다
        }
        else {
            habit.returnCheck();
            //check를 false로 만든다
        }

        Habit updatedHabit = habitRepository.save(habit);
        return mapToDTO(updatedHabit);

    }


    @Override
    public void deleteHabit(Long habitId) {

        //retrieve habit entity by id
        Habit habit = habitRepository.findById(habitId).orElseThrow(() -> new ResourceNotFoundException("Habit", "id", habitId));

        habitRepository.delete(habit);
    }

    public List<Long> getTotalPeriodCount(List<Long> totalPeriods){//totalPeriod 초단위임
        long count1 = 0; long count2 = 0; long count3 = 0; long count4 = 0;
        long count5 = 0; long count6 = 0; long count7 = 0; long count8 = 0;
        long count9 = 0; long count10 = 0; long count11= 0; long count12 = 0;
        List<Long> totalPeriodsMonth = totalPeriods.stream().map(i -> TimeUnit.SECONDS.toDays(i)/30).collect(Collectors.toList());
        for(long totalPeriod : totalPeriodsMonth){
            if(totalPeriod>=0 && totalPeriod < 1){
                count1 += 1;
            }
            else if(totalPeriod>=1 && totalPeriod < 2){
                count2 += 1;
            }
            else if(totalPeriod>=2 && totalPeriod < 3){
                count3 += 1;
            }
            else if(totalPeriod>=3 && totalPeriod < 4){
                count4 += 1;
            }
            else if(totalPeriod>=4 && totalPeriod < 5){
                count5 += 1;
            }
            else if(totalPeriod>=5 && totalPeriod < 6){
                count6 += 1;
            }
            else if(totalPeriod>=6 && totalPeriod < 7){
                count7 += 1;
            }
            else if(totalPeriod>=7 && totalPeriod < 8){
                count8 += 1;
            }
            else if(totalPeriod>=8 && totalPeriod < 9){
                count9 += 1;
            }
            else if(totalPeriod>=9 && totalPeriod < 10){
                count10 += 1;
            }
            else if(totalPeriod>=10 && totalPeriod < 11){
                count11 += 1;
            }
            else if(totalPeriod>=11 && totalPeriod < 12){
                count12 += 1;
            }
        }

        List<Long> totalPeriodCount = Arrays.asList(count1, count2, count3, count4,
                                                    count5, count6, count7, count8,
                                                    count9, count10, count11, count12);

        System.out.println(totalPeriodCount.toString());
        return totalPeriodCount;
    }

    public List<Long> getTotalAmountCount(List<Long> totalAmounts){
        long count1 = 0; long count2 = 0; long count3 = 0; long count4 = 0;
        long count5 = 0; long count6 = 0; long count7 = 0; long count8 = 0;
        long count9 = 0; long count10 = 0;
        long max = Collections.max(totalAmounts);
        double gap = max/10;

        for(long totalAmount : totalAmounts){
            if(totalAmount>=0 && totalAmount< gap){
                count1 += 1;
            }
            else if(totalAmount>=gap && totalAmount < 2*gap){
                count2 += 1;
            }
            else if(totalAmount>=2*gap && totalAmount < 3*gap){
                count3 += 1;
            }
            else if(totalAmount>=3*gap && totalAmount < 4*gap){
                count4 += 1;
            }
            else if(totalAmount>=4*gap && totalAmount < 5*gap){
                count5 += 1;
            }
            else if(totalAmount>=5*gap && totalAmount< 6*gap){
                count6 += 1;
            }
            else if(totalAmount>=6*gap && totalAmount < 7*gap){
                count7 += 1;
            }
            else if(totalAmount>=7*gap && totalAmount < 8*gap){
                count8 += 1;
            }
            else if(totalAmount>=8*gap && totalAmount < 9*gap){
                count9 += 1;
            }
            else if(totalAmount>=9*gap && totalAmount <= 10*gap){
                count10 += 1;
            }
        }

        List<Long> totalAmountCount = Arrays.asList(count1, count2, count3, count4,
                count5, count6, count7, count8,
                count9, count10);

        System.out.println(totalAmountCount.toString());
        return totalAmountCount;
    }

    private HabitDTO mapToDTO(Habit habit){
        HabitDTO habitDTO = HabitDTO.builder()
                .id(habit.getHabitID())
                .name(habit.getHabitName())
                .check(habit.getIsChecked())
                .categoryName(habit.getHabitCategory().getHabitCategoryName())
                .amount(habit.getHabitAmount())
                .period(habit.getHabitPeriod())
                .count(habit.getHabitCount())
                .date(habit.getHabitDate())
                .location(habit.getPictureLocation())
                .build();
        return habitDTO;
    }

    private Habit mapToEntity(HabitDTO habitDTO){
        Habit habit = Habit.builder()
                .habitID(habitDTO.getId())
                .habitName(habitDTO.getName())
                .isChecked(habitDTO.getCheck())
                .habitAmount(habitDTO.getAmount())
                .habitPeriod(habitDTO.getPeriod())
                .habitCount(habitDTO.getCount())
                .habitDate(habitDTO.getDate())
                .pictureLocation(habitDTO.getLocation())
                .build();
        return habit;
    }
}
