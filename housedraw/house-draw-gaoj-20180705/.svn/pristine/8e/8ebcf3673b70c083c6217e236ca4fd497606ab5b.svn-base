package com.sandu.test.utils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.sandu.util.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.<p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn <p>
 * 2018年3月23日
 */

@Slf4j
@SpringBootTest
public class ArrayTests {
	
	@Test
	public void test1() throws ParseException {
		log.debug("{}", Utils.random(6));
	}
	
//	@Test
	public void test() {
		
		List<Entity> list = new ArrayList<>();
		list.add(new Entity(1, "zhangsan"));
		list.add(new Entity(2, "maliu"));
		list.add(new Entity(3, "wangwu"));
		list.add(new Entity(1, "zhangsan"));
		list.add(new Entity(4, "lisi"));
		list.add(new Entity(4, "lisi"));
		list.add(new Entity(4, "lisi"));
		list.add(new Entity(4, "lisi"));
		list.add(new Entity(4, "lisi"));
		list.add(new Entity(2, "maliu"));
		list.add(new Entity(2, "maliu"));
		list.add(new Entity(2, "maliu"));
		list.add(new Entity(2, "mafliu"));
		
		
//		Map<String, List<Entity>> map = list.stream().collect(Collectors.groupingBy(Entity::getName));
		
		
//		Entity reduce = list.stream().reduce(list.get(1), (e, f) -> {
//			log.debug("e => {}", e);
//			log.debug("f => {}", f);
//			return e.getId() - f.getId() > 0 ? e : f;
//		});
		
//		log.debug("reduce => {}", reduce);
		
//		List<String> collect = list.stream().map(e -> e.getName().toUpperCase()).collect(Collectors.toList());
		
//		list.stream().distinct().forEach(System.out::println);
		
//		list.removeIf(e -> e == null || e.getId() == 2);
//		
		for (java.util.Iterator<Entity> it = list.iterator(); it.hasNext();) {
			log.debug("{}", it.next());
		}
		
//		String collect2 = list.stream().map(Entity::getName).collect(Collectors.joining(","));
		
//		log.debug(collect2);
		
		LocalDate now = LocalDate.now();
		LocalDate of = LocalDate.of(1, 2, 2);
		LocalTime now2 = LocalTime.now();
		
		LocalDateTime now3 = LocalDateTime.now();
		
		log.debug("{}", now3);
		log.debug("{}", of);
		
	}
	
	@Data
	@AllArgsConstructor
	class Entity {
		int id;
		String name;
		
		public Entity() {}
	}
	
	public interface Test1<T> {
		T get(T t1, T t2);
	}
}
