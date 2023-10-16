package br.com.hiago640.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

@Component
public class Utils {

	private Utils() {

	}

	public static void copyNonNullProperties(Object source, Object destination) {
		BeanUtils.copyProperties(source, destination, getNullPropertyNames(source));
	}

	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);

		PropertyDescriptor[] propertyDescriptors = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<>();

		for (PropertyDescriptor property : propertyDescriptors) {
			Object propertyValue = src.getPropertyValue(property.getName());

			if (propertyValue == null)
				emptyNames.add(property.getName());
		}

		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);

	}

}
