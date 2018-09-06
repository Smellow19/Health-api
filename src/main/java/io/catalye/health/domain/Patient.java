package io.catalye.health.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection ="patients")
public class Patient {
		@Id
		String id;
		String firstname;
		String lastname;
		String ssn;
		Integer age;
		String gender;
		Integer height;
		Integer weight;
		String insurance;
		Address address;
		public String getId() {
			return id;
		}
		public String getFirstname() {
			return firstname;
		}
		public String getLastname() {
			return lastname;
		}
		public String getSsn() {
			return ssn;
		}
		
		public Integer getAge() {
			return age;
		}
		public String getStringAge() {
			return Integer.toBinaryString(age);
		}
		
		public String getGender() {
			return gender;
		}
		public Integer getHeight() {
			return height;
		}
		
		public String getStringHeight() {
			return Integer.toBinaryString(height);
		}
		
		public Integer getWeight() {
			return weight;
		}
		
		public String getStringWeight() {
			return Integer.toBinaryString(weight);
		}
		public String getInsurance() {
			return insurance;
		}
		public Address getAddress() {
			return address;
		}
		public void setId(String id) {
			this.id = id;
		}
		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}
		public void setLastname(String lastname) {
			this.lastname = lastname;
		}
		public void setSsn(String ssn) {
			this.ssn = ssn;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		public void setHeight(int height) {
			this.height = height;
		}
		public void setWeight(int weight) {
			this.weight = weight;
		}
		public void setInsurance(String insurance) {
			this.insurance = insurance;
		}
		public void setAddress(Address address) {
			this.address = address;
		}
		@Override
		public String toString() {
			return "Patient [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", ssn=" + ssn
					+ ", age=" + age + ", gender=" + gender + ", height=" + height + ", weight=" + weight
					+ ", insurance=" + insurance + ", address=" + address + "]";
		}
		
		
		
}