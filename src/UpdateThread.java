
public class UpdateThread extends Thread  {

	AirPollution air = new AirPollution();
	
	public void run (){
		
		while(true){
			air.updateText();
			try{
				// Delay for check site every 30 minutes
				Thread.sleep(1000*60*30);
				
			}catch(InterruptedException e){
				
			}
		}// end of while
		
		
		
	}//end of run method
}
