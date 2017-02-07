package com.baomidou.framework.common.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

/**
 * <p>
 * 集合相关工具类，包括数组
 * </p>
 * 
 * @author hubin
 * @Date 2016-04-16
 */
public class CollectionUtil {

	/**
	 * <p>
	 * 以 conjunction 为分隔符将集合转换为字符串
	 * </p>
	 * 
	 * @param <T>
	 *            被处理的集合
	 * 
	 * @param collection
	 *            集合
	 * 
	 * @param conjunction
	 *            分隔符
	 * 
	 * @return 连接后的字符串
	 * 
	 */
	public static <T> String join(Iterable<T> collection, String conjunction) {
		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		for (T item : collection) {
			if (isFirst) {
				isFirst = false;
			} else {
				sb.append(conjunction);
			}
			sb.append(item);
		}
		return sb.toString();
	}

	/**
	 * <p>
	 * 以 conjunction 为分隔符将数组转换为字符串
	 * </p>
	 * 
	 * @param <T>
	 *            被处理的集合
	 * 
	 * @param array
	 *            数组
	 * 
	 * @param conjunction
	 *            分隔符
	 * 
	 * @return 连接后的字符串
	 * 
	 */
	public static <T> String join(T[] array, String conjunction) {
		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		for (T item : array) {
			if (isFirst) {
				isFirst = false;
			} else {
				sb.append(conjunction);
			}
			sb.append(item);
		}
		return sb.toString();
	}

	/**
	 * <p>
	 * 将Set排序（根据Entry的值）
	 * </p>
	 * 
	 * @param set
	 *            被排序的Set
	 * 
	 * @return 排序后的Set
	 * 
	 */
	public static List<Entry<Long, Long>> sortEntrySetToList(Set<Entry<Long, Long>> set) {
		List<Entry<Long, Long>> list = new LinkedList<Map.Entry<Long, Long>>(set);
		Collections.sort(list, new Comparator<Entry<Long, Long>>() {

			@Override
			public int compare(Entry<Long, Long> o1, Entry<Long, Long> o2) {
				if (o1.getValue() > o2.getValue())
					return 1;
				if (o1.getValue() < o2.getValue())
					return -1;
				return 0;
			}
		});
		return list;
	}

	/**
	 * <p>
	 * 切取部分数据
	 * </p>
	 * 
	 * @param <T>
	 *            集合元素类型
	 * 
	 * @param surplusAlaDatas
	 *            原数据
	 * 
	 * @param partSize
	 *            每部分数据的长度
	 * 
	 * @return 切取出的数据或null
	 * 
	 */
	public static <T> List<T> popPart(Stack<T> surplusAlaDatas, int partSize) {
		if (surplusAlaDatas == null || surplusAlaDatas.size() <= 0)
			return null;

		List<T> currentAlaDatas = new ArrayList<T>();
		int size = surplusAlaDatas.size();
		// 切割

		if (size > partSize) {
			for (int i = 0; i < partSize; i++) {
				currentAlaDatas.add(surplusAlaDatas.pop());
			}
		} else {
			for (int i = 0; i < size; i++) {
				currentAlaDatas.add(surplusAlaDatas.pop());
			}
		}
		return currentAlaDatas;
	}

	/**
	 * <p>
	 * 新建一个HashMap
	 * </p>
	 * 
	 * @return HashMap对象
	 */
	public static <T, K> HashMap<T, K> newHashMap() {
		return new HashMap<T, K>();
	}

	/**
	 * <p>
	 * 新建一个HashSet
	 * </p>
	 * 
	 * @return HashSet对象
	 */
	public static <T> HashSet<T> newHashSet() {
		return new HashSet<T>();
	}

	/**
	 * <p>
	 * 新建一个ArrayList
	 * </p>
	 * 
	 * @return ArrayList对象
	 * 
	 */
	public static <T> ArrayList<T> newArrayList() {
		return new ArrayList<T>();
	}

	/**
	 * <p>
	 * 将新元素添加到已有数组中<br>
	 * 添加新元素会生成一个新的数组，不影响原数组
	 * </p>
	 * 
	 * @param buffer
	 *            已有数组
	 * 
	 * @param newElement
	 *            新元素
	 * 
	 * @return 新数组
	 * 
	 */
	public static <T> T[] append(T[] buffer, T newElement) {
		T[] t = resize(buffer, buffer.length + 1, newElement.getClass());
		t[buffer.length] = newElement;
		return t;
	}

	/**
	 * <p>
	 * 生成一个新的重新设置大小的数组
	 * </p>
	 * 
	 * @param buffer
	 *            原数组
	 * 
	 * @param newSize
	 *            新的数组大小
	 * 
	 * @param componentType
	 *            数组元素类型
	 * 
	 * @return 调整后的新数组
	 * 
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T> T[] resize(T[] buffer, int newSize, Class<?> componentType) {
		// 给定类型为空时，使用原数组的类型

		if (componentType == null) {
			componentType = buffer.getClass().getComponentType();
		}
		T[] newArray = (T[]) Array.newInstance(componentType, newSize);
		System.arraycopy(buffer, 0, newArray, 0, buffer.length >= newSize ? newSize : buffer.length);
		return newArray;
	}

	/**
	 * <p>
	 * 生成一个新的重新设置大小的数组<br>
	 * 新数组的类型为原数组的类型
	 * </p>
	 * 
	 * @param buffer
	 *            原数组
	 * 
	 * @param newSize
	 *            新的数组大小
	 * 
	 * @return 调整后的新数组
	 */
	public static <T> T[] resize(T[] buffer, int newSize) {
		return resize(buffer, newSize, null);
	}
}
