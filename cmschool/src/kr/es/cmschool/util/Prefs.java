package kr.es.cmschool.util;

import java.io.IOException;
import java.util.ArrayList;

import kr.es.cmschool.MainActivity;
import kr.es.cmschool.R;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class Prefs extends PreferenceActivity {

	private static final String OPT_UID = "uid";
	private static final String OPT_PWD = "pwd";
	private static final String OPT_LOGIN = "login";
	private static final String OPT_AUTOLOGIN = "autologin";

	private EditTextPreference uid;
	private EditTextPreference pwd;
	private Preference login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.settings);

		uid = (EditTextPreference) findPreference(OPT_UID);
		pwd = (EditTextPreference) findPreference(OPT_PWD);
		login = (Preference) findPreference(OPT_LOGIN);

		login.setOnPreferenceClickListener(new myOnPreferenceClickListener());

	}

	public class myOnPreferenceClickListener implements OnPreferenceClickListener {
		public boolean onPreferenceClick(Preference preference) {
			// CheckBoxPreference cpf = (CheckBoxPreference) preference;
			Preference pf = (Preference) preference;

			if (OPT_LOGIN.equals(pf.getKey())) {
				new LoginTask().execute("http://cmschool.es.kr/");
			}

			return false;
		}
	}

	private class LoginTask extends AsyncTask<String, Void, Void> {
		private String Content;
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(Prefs.this);

		protected void onPreExecute() {
			Dialog.setMessage("Login..");
			Dialog.show();
		}

		protected Void doInBackground(String... urls) {
			try {

				// HttpGet httpget = new HttpGet(urls[0]);
				ResponseHandler<String> responseHandler = new BasicResponseHandler();

				// ������ ������ �Ķ���� ����
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("_page", "10001"));
				nameValuePairs.add(new BasicNameValuePair("_action", "login"));
				nameValuePairs.add(new BasicNameValuePair("userid", getUid(getBaseContext())));
				nameValuePairs.add(new BasicNameValuePair("userpw", getPwd(getBaseContext())));

				// ����ð��� 5�ʰ� ������ timeout ó���Ϸ��� �Ʒ� �ڵ��� Ŀ��Ʈ�� Ǯ�� �����Ѵ�.
				// HttpParams params = http.getParams();
				// HttpConnectionParams.setConnectionTimeout(params, 5000);
				// HttpConnectionParams.setSoTimeout(params, 5000);

				// HTTP�� ���� ������ ��û�� �����Ѵ�.
				// ��û�� ���Ѱ���� responseHandler�� handleResponse()�޼��尡 ȣ��Ǿ� ó���Ѵ�.
				// ������ ���޵Ǵ� �Ķ���Ͱ��� ���ڵ��ϱ����� UrlEncodedFormEntity() �޼��带 ����Ѵ�.
				HttpPost httpPost = new HttpPost(urls[0]);
				UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
				httpPost.setEntity(entityRequest);
				Content = MainActivity.Client.execute(httpPost, responseHandler);

				// Content = Client.execute(httpget, responseHandler);

			} catch (ClientProtocolException e) {
				Error = e.getMessage();
				cancel(true);
			} catch (IOException e) {
				Error = e.getMessage();
				cancel(true);
			}

			return null;
		}

		protected void onPostExecute(Void unused) {
			Dialog.dismiss();
			if (Error != null) {
				Toast.makeText(Prefs.this, Error, Toast.LENGTH_SHORT).show();
			} else {

				if (Content.indexOf("��!!") > 0) {

					setAutoLogin(getBaseContext(), true);

					Toast.makeText(getBaseContext(), "�α��� ������ Ȯ�� �Ǿ����ϴ�.", Toast.LENGTH_LONG).show();

					close();

				} else {
					Toast.makeText(getBaseContext(), "�α����� �� �����ϴ�.", Toast.LENGTH_LONG).show();

				}
			}
		}
	}

	public static String getUid(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString(OPT_UID, "");
	}

	public void close() {
		// TODO Auto-generated method stub

		this.close();

	}

	public static void setUid(Context context, String value) {
		PreferenceManager.getDefaultSharedPreferences(context).edit().putString(OPT_UID, value).commit();
	}

	public static String getPwd(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString(OPT_PWD, "");
	}

	public static void setPwd(Context context, String value) {
		PreferenceManager.getDefaultSharedPreferences(context).edit().putString(OPT_PWD, value).commit();
	}

	// ///////////
	public static boolean isAutoLogin(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(OPT_AUTOLOGIN, false);
	}

	public static void setAutoLogin(Context context, boolean value) {
		PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(OPT_AUTOLOGIN, value).commit();
	}

}
