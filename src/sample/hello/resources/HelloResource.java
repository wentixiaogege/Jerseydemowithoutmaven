package sample.hello.resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.QueryParam;
@Path("/hello")
public class HelloResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello(@Context HttpServletRequest req, @QueryParam("UserName") String userName)
	{
		return "hello "+userName+"!";
	}
}
