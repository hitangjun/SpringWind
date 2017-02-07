/**
 * Copyright (c) 2011-2014, liuchangng@qq.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.framework.common;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.baomidou.framework.exception.SpringWindException;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * <p>
 * Pinyin4j 简繁体转拼音辅助类
 * </p>
 * 
 * @author liuchangng@qq.com
 * @Date 2016-04-12
 */
public class Pinyin4jHelper {
	
	/**
	 * <p>
	 * 拼音首字母，获取第一个结果。 （北京市长:bjsz,bjsc 返回 bjsz）
	 * </p>
	 * 
	 * @param chines
	 *            	汉字
	 * @return 拼音首字母
	 */
	public static String converterToFirstSpell( String chines ) {
		String pinyin = converterToAllFirstSpell(chines);
		if ( pinyin != null && pinyin.contains(",") ) {
			return pinyin.split(",")[0];
		}
		return pinyin;
	}

	/**
	 * <p>
	 * 汉字转换位汉语拼音首字母，英文字符不变，特殊字符丢失 支持多音字。 （北京市长:bjsz,bjsc）
	 * </p>
	 * 
	 * @param chines
	 *            	汉字
	 * @return 拼音首字母
	 */
	public static String converterToAllFirstSpell( String chines ) {
		StringBuffer pinyinName = new StringBuffer();
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for ( int i = 0 ; i < nameChar.length ; i++ ) {
			if ( nameChar[i] > 128 ) {
				try {
					/* 取得当前汉字的所有全拼 */
					String[] strs = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat);
					if ( strs != null ) {
						for ( int j = 0 ; j < strs.length ; j++ ) {
							// 取首字母
							pinyinName.append(strs[j].charAt(0));
							if ( j != strs.length - 1 ) {
								pinyinName.append(",");
							}
						}
					}
				} catch ( BadHanyuPinyinOutputFormatCombination e ) {
					throw new SpringWindException(e);
				}
			} else {
				pinyinName.append(nameChar[i]);
			}
			pinyinName.append(" ");
		}
		return parseTheChineseByObject(discountTheChinese(pinyinName.toString()));
	}

	/**
	 * <p>
	 * 汉语全拼，获取第一个结果。（北京市长:beijingshizhang,beijingshichang 返回 beijingshizhang）
	 * </p>
	 *
	 * @param chines
	 *            	汉字
	 * @return 拼音
	 */
	public static String converterToSpell( String chines ) {
		String pinyin = converterToAllSpell(chines);
		if ( pinyin != null && pinyin.contains(",") ) {
			return pinyin.split(",")[0];
		}
		return pinyin;
	}

	/**
	 * <p>
	 * 汉字转换位汉语全拼，英文字符不变，特殊字符丢失
	 * 支持多音字，生成方式如（北京市长:beijingshizhang,beijingshichang）
	 * </p>
	 *
	 * @param chines
	 *            	汉字
	 * @return 拼音
	 */
	public static String converterToAllSpell( String chines ) {
		StringBuffer pinyinName = new StringBuffer();
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for ( int i = 0 ; i < nameChar.length ; i++ ) {
			if ( nameChar[i] > 128 ) {
				try {
					/* 取得当前汉字的所有全拼 */
					String[] strs = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat);
					if ( strs != null ) {
						for ( int j = 0 ; j < strs.length ; j++ ) {
							pinyinName.append(strs[j]);
							if ( j != strs.length - 1 ) {
								pinyinName.append(",");
							}
						}
					}
				} catch ( BadHanyuPinyinOutputFormatCombination e ) {
					e.printStackTrace();
				}
			} else {
				pinyinName.append(nameChar[i]);
			}
			pinyinName.append(" ");
		}
		return parseTheChineseByObject(discountTheChinese(pinyinName.toString()));
	}


	/**
	 * <p>
	 * 去除多音字重复数据
	 * </p>
	 *
	 * @param theStr
	 * 				多音字
	 * @return
	 */
	protected static List<Map<String, Integer>> discountTheChinese( String theStr ) {
		/* 去除重复拼音后的拼音列表 */
		List<Map<String, Integer>> mapList = new ArrayList<Map<String, Integer>>();
		Map<String, Integer> onlyOne = null;
		String[] firsts = theStr.split(" ");
		/*
		 * 读出每个汉字的拼音，多音字处理
		 */
		for ( String str : firsts ) {
			onlyOne = new Hashtable<String, Integer>();
			String[] china = str.split(",");
			for ( String s : china ) {
				Integer count = onlyOne.get(s);
				if ( count == null ) {
					onlyOne.put(s, new Integer(1));
				} else {
					onlyOne.remove(s);
					count++;
					onlyOne.put(s, count);
				}
			}
			mapList.add(onlyOne);
		}
		return mapList;
	}


	/**
	 * 解析并组合拼音，对象合并方案(推荐使用)
	 *
	 * @return
	 */
	protected static String parseTheChineseByObject( List<Map<String, Integer>> list ) {
		Map<String, Integer> first = null; // 用于统计每一次,集合组合数据
		// 遍历每一组集合
		for ( int i = 0 ; i < list.size() ; i++ ) {
			// 每一组集合与上一次组合的Map
			Map<String, Integer> temp = new Hashtable<String, Integer>();
			// 第一次循环，first为空
			if ( first != null ) {
				// 取出上次组合与此次集合的字符，并保存
				for ( String s : first.keySet() ) {
					for ( String s1 : list.get(i).keySet() ) {
						String str = s + s1;
						temp.put(str, 1);
					}
				}
				// 清理上一次组合数据
				if ( temp.size() > 0 ) {
					first.clear();
				}
			} else {
				for ( String s : list.get(i).keySet() ) {
					String str = s;
					temp.put(str, 1);
				}
			}
			// 保存组合数据以便下次循环使用
			if ( temp.size() > 0 ) {
				first = temp;
			}
		}
		String returnStr = "";
		if ( first != null ) {
			// 遍历取出组合字符串
			for ( String str : first.keySet() ) {
				returnStr += (str + ",");
			}
		}
		if ( returnStr.length() > 0 ) {
			returnStr = returnStr.substring(0, returnStr.length() - 1);
		}
		return returnStr;
	}

}
