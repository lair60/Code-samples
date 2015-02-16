package hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RomanNumeralConverterController {

    @RequestMapping("/convert")
    public @ResponseBody RomanNumeralConverter convertion(@RequestParam(value="arabicNumber", required=false, defaultValue="1") String arabicNumber) {
    	int value;
    	try {
    	    value = Integer.parseInt(arabicNumber);
    	  } catch (NumberFormatException e) {
    		  RomanNumeralConverter num = new RomanNumeralConverter();
    		  num.setErrorMessage("Wrong arabic number format!");
    		  return num;
    	  }
    	int maxvalue = 2147483647;
    	if ((value > 0) && (value <= maxvalue)){
    		return new RomanNumeralConverter(Integer.parseInt(arabicNumber));//We convert the string into int
    	}
    	else{
    		RomanNumeralConverter num = new RomanNumeralConverter();
  		  	num.setErrorMessage("Invalid Arabic number. It should be between 1..2147483647");
  		  	return num;
    	}
    }
}
