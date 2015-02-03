package sample.hello.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import sample.pb.AddressBookProtos.AddressBook;
import sample.pb.AddressBookProtos.Person;

public class AddressBookStore {
	static AddressBook addressBook = null;
	static {
		try {
			addressBook =
			      AddressBook.parseFrom(new FileInputStream("D:/addressBooks.txt"));
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Person getPerson(String name) {
		  try {
              addressBook = AddressBook.parseFrom(new FileInputStream(
            		  "D:/addressBooks.txt"));
          } catch (FileNotFoundException e) {

              e.printStackTrace();
          } catch (IOException e) {
              e.printStackTrace();
          }
		for(Person person: addressBook.getPersonList()) {
			if(person.getName().equals(name)) {
				return person;
			}
		}
		return null;
	}
	
	public static void store(Person p) {
		 AddressBook.Builder addressBookBuilder = AddressBook.newBuilder();
		 try {
			String fileName = "D:/addressBooks.txt";
			File file = new File(fileName);
			if(!file.exists()) {
				file.createNewFile();
			}
			addressBookBuilder.mergeFrom(new FileInputStream(fileName));
			addressBookBuilder.addPerson(p);
			addressBookBuilder.build().writeTo(new FileOutputStream(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
	
}
