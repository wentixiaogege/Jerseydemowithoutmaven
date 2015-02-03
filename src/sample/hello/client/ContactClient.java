package sample.hello.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import sample.pb.AddressBookProtos.Person;
import sample.pb.AddressBookProtos.Person.PhoneNumber;

public class ContactClient {
	
	public static void main(String[] args) throws IOException {
		String url1 = "http://localhost:8080/jerseydemo/rest/addressbook/";
		putContacts(url1, "jack");
		String url2 = "http://localhost:8080/jerseydemo/rest/addressbook/jack";
		getContacts(url2);
	}
	public static void putContacts(String url, String name) throws IOException {
		File file = new File("D:/"+name+".per");
		Person p = Person.parseFrom(new FileInputStream(file));
		byte[] content = p.toByteArray();
		
		URL target = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) target.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/x-protobuf");
		conn.setRequestProperty("Accept", "application/x-protobuf;q=0.5");
		
		conn.setRequestProperty("Content-Length", Integer.toString(content.length));
			// set stream mode to decrease memory usage
		conn.setFixedLengthStreamingMode(content.length);
		OutputStream out = conn.getOutputStream();
		out.write(content);
		out.flush();
		out.close();
		conn.connect();
		// check response code
		int code = conn.getResponseCode();
		boolean success = (code >= 200) && (code < 300);
		System.out.println("put person:"+name+" "+(success?"successful!":"failed!"));
	}
	public static void getContacts(String url) throws IOException {
		URL target = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) target.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/x-protobuf");
		conn.setRequestProperty("Accept", "application/x-protobuf");
		conn.connect();
		// check response code
		int code = conn.getResponseCode();
		boolean success = (code >= 200) && (code < 300);
		InputStream in = success ? conn.getInputStream() : conn.getErrorStream();
				
		int size = conn.getContentLength();
		
		byte[] response = new byte[size];
		int curr = 0, read = 0;
		
		while (curr < size) {
			read = in.read(response, curr, size - curr);
			if (read <= 0) break;
			curr += read;
		}
		
		Person p = Person.parseFrom(response);
		System.out.println("id:"+p.getId());
		System.out.println("name:"+p.getName());
		System.out.println("email:"+p.getEmail());
		List<PhoneNumber> pl = p.getPhoneList();
		for(PhoneNumber pn : pl) {
			System.out.println("number:"+pn.getNumber()+" type:"+pn.getType().name());
		}
	}
}
