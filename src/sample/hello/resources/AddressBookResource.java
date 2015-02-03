package sample.hello.resources;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import sample.hello.util.AddressBookStore;
import sample.pb.AddressBookProtos.Person;



@Path("/addressbook")
public class AddressBookResource {
	@PUT
	public Response putPerson(Person person) {		
		AddressBookStore.store(person);
		return Response.ok().build();
	}
	
	@GET
	@Path("/{name}")
	public Response getPerson(@PathParam("name") String name) {		
		Person p = AddressBookStore.getPerson(name);
		System.out.println(name);
		return Response.ok(p, "application/x-protobuf").build();
	}
}
