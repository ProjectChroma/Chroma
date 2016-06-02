package io.github.projectchroma.chroma.util;

public class MathUtils{
	public static boolean sameSign(float a, float b){
		if(a > 0) return b > 0;
		else if(a < 0) return b < 0;
		else return b == 0;
	}
}
