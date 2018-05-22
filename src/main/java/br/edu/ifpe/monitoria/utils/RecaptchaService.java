package br.edu.ifpe.monitoria.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

import javax.faces.context.FacesContext;
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;

import javax.servlet.ServletRequest;

import com.google.gson.Gson;

public class RecaptchaService {

	public boolean checkRecaptcha(FacesContext facesContext){
		String recap = ((ServletRequest) facesContext.getExternalContext().getRequest()).getParameter("g-recaptcha-response");

		try{
			String urlGoogle = "https://www.google.com/recaptcha/api/siteverify";
			String secret = facesContext.getExternalContext().getInitParameter("PRIVATE_CAPTCHA_KEY");
			
			URL obj = new URL(urlGoogle);
			HttpsURLConnection conn = (HttpsURLConnection) obj.openConnection();
			
			conn.setRequestMethod("POST");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0");
			conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String postParams = "secret=" + secret + "&response="
					+ recap;

			// Send post request
			conn.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes(postParams);
			wr.flush();
			wr.close();
			
			String line, outputString = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((line = reader.readLine()) != null) {
				outputString += line;
			}
			// Convert response into Object
			CaptchaResponse capRes = new Gson().fromJson(outputString, CaptchaResponse.class);

			// Verify whether the input from Human or Robot
			if (capRes.isSuccess()) {
				// Input by Human
				return true;
			} else {
				// Input by Robot
				return false;
			}

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	private class CaptchaResponse {
		private boolean success;
		private String[] errorCodes;

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

		public String[] getErrorCodes() {
			return errorCodes;
		}

		public void setErrorCodes(String[] errorCodes) {
			this.errorCodes = errorCodes;
		}

	}   
}
