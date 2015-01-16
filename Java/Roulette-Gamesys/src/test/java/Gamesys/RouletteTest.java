package Gamesys;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RouletteTest 
   
{
	
	Roulette r= new Roulette("Input.txt");
	@Test
	   public void TestValidedBets() {
		     assertTrue(r.validBet("Barbara 36 100").equals("Processed bet."));
		     assertTrue(r.validBet("Tiki_Monkey 1 0.1").equals("Processed bet."));
		     assertTrue(r.validBet("Barbara 14 10.1").equals("Processed bet."));
		     assertTrue(r.validBet("Tiki_Monkey even 99.9").equals("Processed bet."));
		     assertTrue(r.validBet("Barbara odd 1").equals("Processed bet."));
	   }
	@Test
	   public void TestInvalidUser() {
		     assertTrue(r.validBet("Paul_monkey3311 even 10").equals("Invalid User: Max length 12 characters"));
		     assertTrue(r.validBet("Tiki_monkey7777 5 0.1").equals("Invalid User: Max length 12 characters"));
	   }
	@Test
	   public void TestInvalidFormat() {
		     assertTrue(r.validBet("Pauleven 10").equals("Invalid bet's format"));
		     assertTrue(r.validBet("Barbara even0.1").equals("Invalid bet's format"));
	   }
	@Test
	   public void TestInvalidBet() {
		     assertTrue(r.validBet("Barbara even -0.1").equals("Invalid bet"));
		     assertTrue(r.validBet("Tiki_Monkey 0 1").equals("Invalid bet"));
		     assertTrue(r.validBet("Tiki_Monkey 37 10").equals("Invalid bet"));
	   }
	@Test
	   public void TestRouletteResults() {
		  	String result=r.spinRoulette();
		  	r.validBet("Barbara odd 1");
		    assertTrue(!r.spinRoulette().equals(result));//the result always will be modified after a bet.
		     
	   }
}
