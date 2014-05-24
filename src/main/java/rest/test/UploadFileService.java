package rest.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
//import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/test")
public class UploadFileService {

	@GET
	@Path("/prova")
	public Response getResponse() {
		String msg = "OK";
		return Response.status(200).entity(msg).build();
	}

	@GET
	@Path("/users/test")
	public Response getTest() {
		String msg = "OK!";

		return Response.status(200).entity(msg).build();
	}

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	// @POST
	// @Consumes(MediaType.MULTIPART_FORM_DATA_TYPE)
	// public String postForm(
	// @DefaultValue("true") @FormDataParam("enabled") boolean enabled,
	// @FormDataParam("data") FileData bean,
	// @FormDataParam("file") InputStream file,
	// @FormDataParam("file") FormDataContentDisposition fileDisposition) {
	//
	// // ...
	// }
	//
	public Response uploadFile(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {

		String uploadedFileLocation = "d://uploaded/"
				+ fileDetail.getFileName();

		// save it
		writeToFile(uploadedInputStream, uploadedFileLocation);

		String output = "File uploaded to : " + uploadedFileLocation;

		return Response.status(200).entity(output).build();

	}

	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {

		try {
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
