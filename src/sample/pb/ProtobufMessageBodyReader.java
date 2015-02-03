package sample.pb;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import sample.pb.AddressBookProtos.Person;


@Provider
@Consumes("application/x-protobuf")
public class ProtobufMessageBodyReader implements MessageBodyReader<Object> {
	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations,
			MediaType mediaType) {
		return mediaType.toString().equals("application/x-protobuf");
	}

	@Override
	public Object readFrom(Class<Object> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream inputStream)
			throws IOException, WebApplicationException {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	byte[] buffer = new byte[4096];
    	int read;
    	long total = 0L;
    	do {
    		read = inputStream.read(buffer, 0, buffer.length);
    		if (read > 0) {
    			baos.write(buffer, 0, read);
    			total += read;
    		}
    	} while (read > 0);
 
		return Person.parseFrom(baos.toByteArray());
	}
}
