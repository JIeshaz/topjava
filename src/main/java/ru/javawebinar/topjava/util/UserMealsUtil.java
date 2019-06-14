package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2017, Month.MAY, 1,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2017, Month.MAY, 1,11,0), "Обед", 700),
                new UserMeal(LocalDateTime.of(2017, Month.MAY, 1,19,0), "Ужин", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        for (UserMealWithExceed each : getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000)){
            System.out.println(each);
        }

//        .toLocalDate(); - приводим LocalDateTime к дате без времени
//        .toLocalTime(); - приводим LocalDateTime к времени без даты
    }

    public LocalDate toLocalDate(LocalDateTime localDateTime){
        return LocalDate.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth());
    }

    public LocalTime toLocalTime(LocalDateTime localDateTime){
        return LocalTime.of(localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond());
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        Map<LocalDate, Integer> calsPerDay = new HashMap<>(); // Собираю мапу дата-калории
        for (UserMeal each : mealList){ // Если дата уже в мапе - сложить калории, иначе создать новую с данными калориями
            if (calsPerDay.containsKey(each.getDateTime().toLocalDate())){
                calsPerDay.put(each.getDateTime().toLocalDate(), calsPerDay.get(each.getDateTime().toLocalDate())+each.getCalories());
            } else {
                calsPerDay.put(each.getDateTime().toLocalDate(), each.getCalories());
            }
        }
        List<UserMealWithExceed> exceedList = new ArrayList<>(); // Лист для вывода отфильтрованных записей
        for (Map.Entry<LocalDate, Integer> map : calsPerDay.entrySet()){
     //       System.out.println(map.getKey()+" "+map.getValue());
            if (map.getValue() > caloriesPerDay){
                for (UserMeal each : mealList){
                    if (each.getDateTime().toLocalDate().equals(map.getKey()) && each.getDateTime().toLocalTime().isAfter(startTime) && each.getDateTime().toLocalTime().isBefore(endTime)){
                        exceedList.add(new UserMealWithExceed(each.getDateTime(), each.getDescription(), each.getCalories(), true));
                    }
                }
            }
        }
        return exceedList;
    }

}
