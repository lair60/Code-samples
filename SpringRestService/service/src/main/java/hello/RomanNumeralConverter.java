package hello;

public class RomanNumeralConverter {
	
	
	private final static int million = 1000000;
	private final static int thousand = 1000;
	private final static int hundred = 100;
	private final static int ten = 10;
	private final static int unit = 1;
	private String romanNumber;
	private String millionRoman = "";
	private String thousandRoman = "";
	private boolean millionRomanUnderline = false;
	private boolean millionRomanOverline = false;
	private boolean thousandRomanOverline = false;
	private String hundredRoman = "";
	private String tenRoman = "";
	private String unitRoman = "";
	private String errorMessage = "No error detected";
	//Constructors
	public RomanNumeralConverter(){
	}
	public RomanNumeralConverter(int n){
		romanNumber = convertRomanNumber(n);
	}
	//Given an arabic number, this function converts to a roman number.
	public String convertRomanNumber(int n){
		millionRoman = "";
		thousandRoman = "";
		hundredRoman = "";
		tenRoman = "";
		unitRoman = "";
		return f2(n,million);//
	}
	//Gets
	public String getRomanNumber(){
		return romanNumber;
	}
	public String getMillionRoman(){
		return millionRoman;
	}
	public boolean getMillionRomanUnderline(){
		return millionRomanUnderline;
	}
	public boolean getMillionRomanOverline(){
		return millionRomanOverline;
	}
	public String getThousandRoman(){
		return thousandRoman;
	}
	public boolean getThousandRomanOverline(){
		return thousandRomanOverline;
	}
	public String getHundredRoman(){
		return hundredRoman;
	}
	public String getTenRoman(){
		return tenRoman;
	}	
	public String getUnitRoman(){
		return unitRoman;
	}
	public String getErrorMessage(){
		return errorMessage;
	}
	//Set an error Message
	public void setErrorMessage(String s){
		errorMessage=s;
	}
	private String f2(int n,int i){
		switch (i) {
			case million:{// According with http://en.wikipedia.org/wiki/Roman_numerals, we can use an overline for values > 4 000 and an underline for values > 4 000 000.
				if (n/(4*million)>0){//If more than 4 000 000 we use an underline(multiply the number concerned by 1 000 000)
					millionRoman = f2(n/million,thousand);
					millionRomanUnderline = true;// We need to use underline
					return millionRoman + f2(n%million,thousand);
				}
				else{// else we can use an overline(multiply the number concerned by 1000 ) or normal notation. [1 000 000..3 999 9999]
					millionRoman = new String(new char[(int) (n/million)]).replace("\0", "M");// (n/million) times M's
					millionRomanOverline = (millionRoman.length() != 0) ;//To check if we need to use overline
					return millionRoman + f2(n % million,thousand);
				}
			}
			case thousand:{//[1000..999 999]
				if (n/(4*thousand)>0){//If more than 4 000 we use an overline(multiply the number concerned by 1000)
					thousandRoman = f2(n/thousand,hundred);
					thousandRomanOverline = true ;// We need to use overline
					return thousandRoman + f2(n%thousand,hundred);
				}
				else{//else we dont need another extra notation for [1000..3999]
					thousandRoman = new String(new char[(int) (n/thousand)]).replace("\0", "M");// (n/thousand) times M's
					return thousandRoman + f2(n%thousand,hundred);
				}
			}
			case hundred:{//[100..999]
				int num= (n/hundred);
				if(num<=3){//[100..399]
					hundredRoman=  new String(new char[(int) (n/hundred)]).replace("\0", "C"); // (n/hundred) times C's
					return hundredRoman + f2(n%hundred,ten);
				}
				else
					if(num==4){//[400..499]
						hundredRoman = "CD";
						return hundredRoman + f2(n%hundred,ten);
					}
					else
						if (num==9){//[900..999]
							hundredRoman = "CM";
							return hundredRoman + f2(n%hundred,ten);
						}
						else{//[500..899]
							hundredRoman = "D"+ new String(new char[(int) ((n/hundred)-5)]).replace("\0", "C");//D + the rest of C's 
							return hundredRoman + f2(n%hundred,ten);
						}
			}
			case ten:{//[10..99]
				int num= (n/ten);
				if(num<=3){//[10..39]
					tenRoman= new String(new char[(int) (n/ten)]).replace("\0", "X");
					return tenRoman + f2(n%ten,unit);
				}
				else
					if(num==4){//[40..49]
						tenRoman = "XL"; 
						return tenRoman + f2(n%ten,unit);
					}
					else
						if (num==9){//[90..99]
							tenRoman = "XC";
							return tenRoman + f2(n%ten,unit);
						}
						else{//[50..89]	
							tenRoman = "L" +  new String(new char[(int) ((n/ten)-5)]).replace("\0", "X");
							return tenRoman + f2(n%ten,unit);
						}
			}
			case unit:{//[1..9]
				int num= (n/unit);
				if(num<=3){//[1..3]
					unitRoman = new String(new char[(int) (n/unit)]).replace("\0", "I");
					return unitRoman;
				}
				else
					if(num==4){//[4]
						unitRoman = "IV";
						return unitRoman;
					}
					else
						if (num==9){//[9]
							unitRoman = "IX"; 
							return unitRoman;
						}
						else{//[5..8]
							unitRoman = "V" + new String(new char[(int) (n-5)]).replace("\0", "I");
							return unitRoman;
							
						}
			}
	
			default:
				return "";
		}
	}
}
