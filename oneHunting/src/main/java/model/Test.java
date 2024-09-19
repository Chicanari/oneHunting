package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) {
		
		//名前チェック 文字数チェック（2-12）
		String str = "";
		Pattern namePattern = Pattern.compile("{2,12}");
		Matcher nameMatcher = namePattern.matcher(str);
		System.out.println(nameMatcher.find());

	}

}
