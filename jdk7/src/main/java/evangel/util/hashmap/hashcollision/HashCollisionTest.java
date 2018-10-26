package evangel.util.hashmap.hashcollision;

import evangel.util.hashmap.entity.Dog;
import evangel.util.hashmap.entity.Person;
import evangel.util.hashmap.entity.PersonNew;

import java.util.HashMap;

/**
 * hash碰撞
 */
public class HashCollisionTest {
	public static void main(String[] args) {
		HashMap<Person, Dog> map = new HashMap<Person, Dog>(2);
		Person person_1 = new Person();
		person_1.setHeight(180);
		person_1.setId(1);
		person_1.setName("person_1");
		Person person_2 = new Person();
		person_2.setHeight(180);
		person_2.setId(2);
		person_2.setName("person_1");
		Dog dog_1 = new Dog();
		dog_1.setId(1);
		dog_1.setName("dog_1");
		Dog dog_2 = new Dog();
		dog_2.setId(2);
		dog_2.setName("dog_2");
		map.put(person_1, dog_1);
		map.put(person_2, dog_2);
		System.out.println("--" + map.get(person_1).getName());
		System.out.println("--" + map.get(person_2).getName());
		System.out.println("===============================");
		HashMap<PersonNew, Dog> mapNew = new HashMap<PersonNew, Dog>(2);
		PersonNew personNew_1 = new PersonNew();
		personNew_1.setHeight(180);
		personNew_1.setId(1);
		personNew_1.setName("person_1");
		PersonNew personNew_2 = new PersonNew();
		personNew_2.setHeight(180);
		personNew_2.setId(2);
		personNew_2.setName("person_2");
		mapNew.put(personNew_1, dog_1);
		// if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {//注意的地方
		mapNew.put(personNew_2, dog_2);
		System.out.println("--" + mapNew.get(personNew_1).getName());
		System.out.println("--" + mapNew.get(personNew_2).getName());
	}
}
