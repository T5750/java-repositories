package evangel.util.hashmap;

import java.util.HashMap;

import evangel.util.hashmap.entity.Dog;
import evangel.util.hashmap.entity.Person;
import evangel.util.hashmap.entity.PersonKey;

/**
 * Key变了，能不能get出原来的value？<br/>
 * 答案是：有时可以，有时不可以<br/>
 * https://blog.csdn.net/raylee2007/article/details/50449781
 */
public class HashMapKeyTest {
	public static void main(String[] args) {
		HashMap<Person, Dog> map = new HashMap<Person, Dog>(2);
		Person person_1 = new Person();
		person_1.setHeight(180);
		person_1.setId(1);
		person_1.setName("person_1");
		Dog dog_1 = new Dog();
		dog_1.setId(1);
		dog_1.setName("dog_1");
		map.put(person_1, dog_1);
		person_1.setId(2);
		System.out.println(map.get(person_1));
		System.out.println("===============================");
		HashMap<PersonKey, Dog> mapKey = new HashMap<PersonKey, Dog>(2);
		PersonKey personKey_1 = new PersonKey();
		personKey_1.setHeight(180);
		personKey_1.setId(1);
		personKey_1.setName("person_1");
		mapKey.put(personKey_1, dog_1);
		personKey_1.setId(2);
		System.out.println(mapKey.get(personKey_1));
	}
}
