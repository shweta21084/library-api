package com.api.library.util;

public class LibraryAPIUtils {
	
	public static boolean doesStringValueExists(String value) {
		if (value != null && value.trim().length() > 0) {
			return true;
		}
		return false;
	}

}
