package chirp.service.resources;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import chirp.model.User;
import chirp.model.UserRepository;

@Path("/users")
public class UserResource {

	private final UserRepository userRepository = UserRepository.getInstance();

	@POST
	public Response createUser(final @FormParam("username") String username,
			final @FormParam("realname") String realname) {

		userRepository.createUser(username, realname);

		// http://localhost:8080/users</username>
		final URI location = UriBuilder.fromResource(this.getClass())
				.path(username).build();

		// should return a 201 status code with the URL to the user created
		// in the location header.
		return Response.created(location).build();
	}

	
	@GET
	@Path("/{username}")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public User getUser(final @PathParam("username") String username) {
		return userRepository.getUser(username);
	}
	
	
	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Collection<User> getAllUsers() {
		
		// add and delete protection, references to data could modify data.
		return Collections.unmodifiableCollection(userRepository.getUsers());
	}


}