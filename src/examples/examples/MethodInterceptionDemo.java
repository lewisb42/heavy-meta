package examples;

import mockit.Mock;
import mockit.MockUp;

public class MethodInterceptionDemo {
	
	public static class Student {
		private String name;
		public Student(String name) {
			this.name = name;
		}
		public String getName() {
			return this.name;
		}
	}

	public static void main(String[] args) {
		Student rey = new Student("Rey");
		System.out.println(rey.getName());
		
		new MockUp<Student>() {
			@Mock
			public String getName() {
				return "Finn";
			}
		};
		System.out.println(rey.getName());
	}
}
