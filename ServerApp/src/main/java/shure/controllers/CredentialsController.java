package shure.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import shure.utilities.CredentialsAuthenticator;

@Controller
@CrossOrigin
public class CredentialsController {

	private CredentialsAuthenticator authenticator = new CredentialsAuthenticator();

	@PostMapping("api/v1/verifyCredentials")
	public ResponseEntity<?> verify(@RequestBody String verificationRequestString) {
		JSONObject verificationRequest = new JSONObject(verificationRequestString);
		try {
			String hashedCredentials = verificationRequest.getString("hash");
			if (authenticator.verifyCredentials(hashedCredentials)) {
				return ResponseEntity.ok("{\"message\": \"Success\"}");
			} else {
				return ResponseEntity.badRequest().body("{\"message\": \"Invalid credentials\"}");
			}
		} catch (JSONException je) {
			return ResponseEntity.badRequest().body("{\"message\": \"Bad request format\"}");
		}

	}

}
