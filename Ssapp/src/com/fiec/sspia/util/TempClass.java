package com.fiec.sspia.util;

import java.util.Calendar;

public class TempClass {
	protected int min;
	protected int max;

	public TempClass(int min, int max) {
		this.min = min;
		this.max = max;

	}
	
	public int Temper()
	{
		Calendar c = Calendar.getInstance(); 
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int temp = 0;
		
		if(hour>=22||hour<=4)
			temp = min;
		else
			if(hour>4&&hour<12)
				temp = min + ((max-min)/8)*(hour-4);
			else
				if(hour>=12||hour<=15)
					temp = max;
				else
					if(hour>15&&hour<22)
						temp = max + ((max-min)/7)*(hour-15);
		

		return temp;
	}

}
