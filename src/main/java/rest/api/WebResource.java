package rest.api;

import hibernate.manager.UserManager;
import hibernate.model.Photo;
import hibernate.model.User;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.HibernateException;

import rest.model.UserBean;
import rest.model.CodeBean;

@Path("/web")
public class WebResource {

    public UserManager mgmtUser;
    Integer OK = 200;
    Integer UNAUTHORIZED = 401;
    Integer BAD_REQUEST = 400;
    Integer INTERNAL_ERROR = 500;

    @GET
    @Path("/users/test")
    public Response getTest() {
        return Response.status(200).entity("OK!").build();
    }

    @GET
    @Path("/users/list")
    private List<User> getUsersTest() {
        List<User> list = new ArrayList<User>();
        list = mgmtUser.loadAllUsers();
        return list;
    }

    @POST
    @Path("/users/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CodeBean registerResource(UserBean user) {
        if (user == null)
            return new CodeBean(BAD_REQUEST, "Please, insert an user");
        else {
            Queries query = new Queries();
            try {
            	if(query.checkUserExist(user.getNif()) == true)
            		return new CodeBean(BAD_REQUEST, "Someone already has that nif. Try another?");
                query.registerUser(user);
                return new CodeBean(OK, "User registered correctly");
            } catch (HibernateException e) {
                System.err.println(e.getMessage());
                return new CodeBean(BAD_REQUEST, "Please check parameters");
            } catch (NoSuchAlgorithmException e) {
                System.err.println(e.getMessage());
                return new CodeBean(INTERNAL_ERROR, "Ups, something was wrong");
            }
        }
    }

    @POST
    @Path("/users/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginResource(User user) {
        if (user == null)
            return Response.status(400).entity("KO!").build();
        return Response.status(200).entity("OK!").build();
    }

    @GET
    @Path("/users/{user-code}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserBean getUserResource(@PathParam("user-code") String userNif) {
        UserManager userManager = new UserManager();

        UserBean user = new UserBean(userManager.findByUserNif(userNif));
        System.out.println("The user with NIF:" + userNif + " is "
                + user.getName() + " " + user.getSurname());
        return user;

    }

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public HashSet<User> getUsersResource() {
        HashSet<User> users = null;
        return users;
    }

    @POST
    @Path("/photos/upload")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadPhotoResource(Photo image,
            @DefaultValue("null") @QueryParam("user") String userId) {
        if (image == null || userId.isEmpty())
            return Response.status(400).entity("KO!").build();
        return Response.status(200).entity("OK!").build();
    }

    @GET
    @Path("/photos")
    @Produces(MediaType.APPLICATION_JSON)
    public HashSet<Photo> getPhotosToVoteResource(
            @DefaultValue("null") @QueryParam("user") String userId) {
        HashSet<Photo> photos = null;
        return photos;
    }

}
