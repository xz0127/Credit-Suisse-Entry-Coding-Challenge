import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * This represents a robot
 */
public class Robot {
    private Map<Shift, Long> rate;
    private Map<String, Shift> mapping;

    public Robot(JSONObject roboRate) {
        this.rate = new HashMap<>();
        this.mapping = new HashMap<>();
        JSONObject standardDay =  (JSONObject) roboRate.get("standardDay");
        LocalDateTime standardDayStart = LocalTime.parse((String) standardDay.get("start")).atDate(LocalDate.MAX);
        LocalDateTime standardDayEnd = LocalTime.parse((String) standardDay.get("end")).atDate(LocalDate.MAX);
        Long standardDayPrice = (long) standardDay.get("value");
        Shift standardDayShift = new Shift(standardDayStart, standardDayEnd);
        rate.put(standardDayShift, standardDayPrice);
        mapping.put("standardDay", standardDayShift);

        JSONObject standardNight =  (JSONObject) roboRate.get("standardNight");
        LocalDateTime standardNightStart = LocalTime.parse((String) standardNight.get("start")).atDate(LocalDate.MAX);
        LocalDateTime standardNightEnd = LocalTime.parse((String) standardNight.get("end")).atDate(LocalDate.MAX);
        Long standardNightPrice = (long) standardNight.get("value");
        Shift standardNightShift = new Shift(standardNightStart, standardNightEnd);
        rate.put(standardNightShift, standardNightPrice);
        mapping.put("standardNight", standardNightShift);

        JSONObject extraDay =  (JSONObject) roboRate.get("extraDay");
        LocalDateTime extraDayStart = LocalTime.parse((String) extraDay.get("start")).atDate(LocalDate.MAX);
        LocalDateTime extraDayEnd = LocalTime.parse((String) extraDay.get("end")).atDate(LocalDate.MAX);
        Long extraDayPrice = (long) extraDay.get("value");
        Shift extraDayShift = new Shift(extraDayStart, extraDayEnd);
        rate.put(extraDayShift, extraDayPrice);
        mapping.put("extraDay", extraDayShift);

        JSONObject extraNight =  (JSONObject) roboRate.get("extraNight");
        LocalDateTime extraNightStart = LocalTime.parse((String) extraNight.get("start")).atDate(LocalDate.MAX);
        LocalDateTime extraNightEnd = LocalTime.parse((String) extraNight.get("end")).atDate(LocalDate.MAX);
        Long extraNightPrice = (long) extraNight.get("value");
        Shift extraNightShift = new Shift(extraNightStart, extraNightEnd);
        rate.put(extraNightShift, extraNightPrice);
        mapping.put("extraNight", extraNightShift);
    }

    public Map<Shift, Long> getRate() {
        return this.getRate();
    }

    public Map<Shift, Long> getMapping() {
        return this.getMapping();
    }

    public Shift getShift(String str) {
        return this.mapping.get(str);
    }

    public int getPrice(String str) {
        return this.rate.get(this.mapping.get(str)).intValue();
    }
}
