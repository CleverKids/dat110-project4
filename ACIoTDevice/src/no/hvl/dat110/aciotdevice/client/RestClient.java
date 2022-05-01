package no.hvl.dat110.aciotdevice.client;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import okhttp3.*;

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

		// implements a HTTP GET on the service to get current access code
		OkHttpClient okClient = new OkHttpClient();
		Gson gson = new Gson();

		Request request = new Request.Builder()
				.url("http://localhost:8080/accessdevice/code")
						.get()
						.build();

		System.out.println("doGetAccessCode request: ");
		System.out.println(request);

		try (Response response = okClient.newCall(request).execute()) {
			System.out.println("response data: ");
			System.out.println(response.body());
			String responseBody = response.body().string();
			System.out.println("ResponseBody: ");
			System.out.println(responseBody);

			// JsonObject responseAsJSON = JsonParser.parseString(responseBody).getAsJsonObject(); // Testing...
			code = gson.fromJson(JsonParser.parseString(responseBody), AccessCode.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return code;
	}
}
