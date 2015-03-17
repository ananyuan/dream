package com.dream.base.acl;

public class AuthorityHelper {

	/**
	 * 判断是否有权限
	 * 
	 * @param akey
	 *            aString中位置的索引值,也就是权限位
	 * @param aString
	 *            权限字段,比如 11010101011101
	 * @return
	 */
	public static boolean hasAuthority(int akey, String aString) {
		// return ConstanHelper.getAuthorityVaule(akey,rc);
		if (aString == null || "".equals(aString)) {
			return false;
		}

		char value = aString.charAt(akey);
		if (value == '1') {
			return true;
		}

		return false;

	}

}