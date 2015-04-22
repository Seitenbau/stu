package model;

import java.util.Random;

public class RandomTest {
	public static void main(String[] args) throws Exception {
		Integer[] list = new Integer[]{0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0};
		
		for (Integer i = 0; i < 2; i++) {
			Random random = new Random(i);
			//System.out.println(i.toString() + ": ");
			
			for(Integer j = 0; j < 10; j++){
				Integer value = random.nextInt(2);
				list[value] += 1;
			}
		}
		
		for(Integer value: list){
			System.out.println(value);
		}
	}
}
