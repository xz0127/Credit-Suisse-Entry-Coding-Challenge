import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;


public class Main {
    public static void main(String[] args) {
        JSONParser jsonParser = new JSONParser();
        try{
            Object obj = jsonParser.parse(new FileReader("src/test/java/test.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject shift =  (JSONObject) jsonObject.get("shift");
            JSONObject roboRate =  (JSONObject) jsonObject.get("roboRate");
            LocalDateTime startTime = LocalDateTime.parse((String) shift.get("start"));
            LocalDateTime endTime   = LocalDateTime.parse((String) shift.get("end"));
            Robot robot = new Robot(roboRate);
            Shift robotShift = new Shift(startTime, endTime);
            RateCalculator rateCalculator = new RateCalculator(robot, robotShift);
            JSONObject value = new JSONObject();
            value.put("value", rateCalculator.calculateValue(robotShift));
            System.out.println(value.toJSONString());
        } catch (ParseException | IOException ex) {
            ex.printStackTrace();
        }
    }
}
