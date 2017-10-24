package com.example.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.MultiPart;

import com.example.bean.User;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("users")
public class MultipartResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @POST
    @Produces("multipart/mixed")
    @Consumes("multipart/mixed")
    public MultiPart createUser(MultiPart requestBody) {
    	User newUser = requestBody.getBodyParts().get(0).getEntityAs(User.class);
    	if (newUser.getName().equals("demoUser")) {
    		newUser.setId("1111111");
    	}
        MultiPart responseBody = new MultiPart();
        responseBody.bodyPart(new BodyPart(newUser, MediaType.APPLICATION_JSON_TYPE));
        return responseBody;
    }
}
