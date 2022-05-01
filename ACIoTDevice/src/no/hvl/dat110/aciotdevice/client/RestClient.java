package no.hvl.dat110.aciotdevice.client;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;


public class RestClient {


	public RestClient() {
		// TODO Auto-generated constructor stub

	}

	private static String logpath = "/accessdevice/log";
	private static String host = Configuration.host;


	
	public void doPostAccessEntry(String message) {

		OkHttpClient okClient = new OkHttpClient();
		Gson gson = new Gson();
		AccessMessage msg = new AccessMessage(message);

		MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		RequestBody reqBody = RequestBody.create(JSON, gson.toJson(msg));

		Request request = new Request.Builder()
				.url("http://localhost:8080/accessdevice/log")
						.post(reqBody)
						.build();

		try (Response response = okClient.newCall(request).execute()) {
			System.out.println(response.body().string());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static String codepath = "/accessdevice/code";

	public AccessCode doGetAccessCode() {

		AccessCode code = null;
		
		// TODO: implement a HTTP GET on the service to get current access code
		OkHttpClient client = new OkHttpClient();

		Gson gson = new Gson();

		Request request = new Request.Builder()
				.url("http://localhost:8080/accessdevice/code")
						.get()
						.build();

		System.out.println(request);

		try (Response response = client.newCall(request).execute()) {
			String body = response.body().string();
			System.out.println(body);
			code = gson.fromJson(body, AccessCode.class);
		} catch (JsonSyntaxException | IOException e) {
			e.printStackTrace();
		}
		
		return code;
	}
}
