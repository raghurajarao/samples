package com.example.resource;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.jsonb.JsonBindingFeature;
import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.example.Main;
import com.example.bean.User;

public class MultipartResourceTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newBuilder().register(MultiPartFeature.class).register(JsonBindingFeature.class).build();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testMultiPartCreateUser() {
    	User newUser = new User("demoUser", "demo", "user"); 
    	assertNull(newUser.getId());
    	MultiPart requestBody = new MultiPart().bodyPart(new BodyPart(newUser, MediaType.APPLICATION_JSON_TYPE));
        Response response = target.path("users").request().post(Entity.entity(requestBody, requestBody.getMediaType()));
        User responseUser = response.readEntity(MultiPart.class).getBodyParts().get(0).getEntityAs(User.class);
        assertEquals("User creation Failed", "1111111", responseUser.getId());
    }
}
