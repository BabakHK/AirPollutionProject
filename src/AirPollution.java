
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;

 
public class AirPollution extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel panel = new JPanel();
	JLabel lbl = new JLabel();
	// Persian date 
	ULocale locale = new ULocale("fa_IR@calendar=persian");
    Calendar calendar = Calendar.getInstance(locale);
    DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
    String IRDate = df.format(calendar);

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		Thread clock = new UpdateThread();
		clock.start();
		
		 		 
	}
	
	// Constructor
	public AirPollution(){
			
		// Set form
		this.setSize(420,85);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("وضعیت آلودگی");
		this.setVisible(true);
		this.add(panel);
		panel.add(lbl);
		// Set font
		Font myFont = new Font("B Titr", Font.BOLD, 15);
		lbl.setFont(myFont);
		
		// Get Dimension and set form location
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dm = tk.getScreenSize();
		int x = (dm.width ) - this.getWidth();
		int y = (dm.height) - this.getHeight() - 48;
		this.setLocation(x, y);
		
	}
	
	
	// Get information and update text 
	public void updateText(){
		try {
		    	URL url = new URL("http://31.24.238.89/home/OnlineAQI.aspx");
		    	InputStream inpStream = (InputStream) url.getContent();
		    	BufferedReader buffReader = new BufferedReader(new InputStreamReader(inpStream));
		    	String line ;
		    	StringBuffer strBuffer = new StringBuffer();
		    	while((line = buffReader.readLine()) != null){
		    		strBuffer.append(line);
		    	}
		    	String htmlContent = strBuffer.toString();
			 		    	
		    	// Find index of ContentPlaceHolder1_lblAQI
		    	int intIndex = htmlContent.indexOf("ContentPlaceHolder1_lblAQI");
			   	String htmlStr = htmlContent.substring(intIndex+47, intIndex+51);
		    	String digits = htmlStr.replaceAll("[^0-9]","");
		    	int finalNumber = Integer.parseInt(digits);
			 
			
		    	if (finalNumber <= 50){
						lbl.setText(IRDate + "میزان آلودگی " + "("+finalNumber+")" + " و وضعیت هوا (پاک) است ");
		    	}else if ( (50 < finalNumber) && (finalNumber) <= 100){
						lbl.setText(IRDate +"میزان آلودگی " + "("+finalNumber+")" + " و وضعیت هوا (سالم) است ");
		    	}else if ((100<finalNumber)&&(finalNumber <= 150)){
		    		lbl.setText(IRDate + "میزان آلودگی " + "("+finalNumber+")" + " و وضعیت هوا (ناسالم برای گروه حساس) است ");
			   	}else if ((150 < finalNumber) && (finalNumber <= 200)){
		    		lbl.setText(IRDate + "میزان آلودگی " + "("+finalNumber+")" + " و وضعیت هوا (ناسالم) است ");
		    	}else if ((200<finalNumber) && (finalNumber <= 300)){
		    		lbl.setText(IRDate + "میزان آلودگی " + "("+finalNumber+")" + " و وضعیت هوا (بسیار ناسالم) است ");
		    	}else if (finalNumber > 300){
		    		lbl.setText(IRDate + "میزان آلودگی " + "("+finalNumber+")" + " و وضعیت هوا (خطرناک) است ");
		    	}
			
			
			
		} catch (Exception e) {
				// TODO: handle exception
				System.out.println("خطا در دریافت اطلاعات");
		}
		
		}// End of updateText

	}
