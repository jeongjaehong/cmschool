package kr.es.cmschool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.es.cmschool.util.Prefs;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class MainActivity extends Activity {

	private WebView webview = null;

	private ListView listview = null;
	private ListView listview2 = null;

	public final static HttpClient Client = new DefaultHttpClient();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		listview2 = (ListView) findViewById(R.id.listView2);
		listview = (ListView) findViewById(R.id.listView1);

		listview2.setOnItemClickListener(new fileOnItemClickListener());
		listview.setOnItemClickListener(new listOnItemClickListener());

		webview = (WebView) findViewById(R.id.webView1);
		// textview = (TextView) findViewById(R.id.textView1);

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		new LoginTask().execute("http://cmschool.es.kr/");

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (Prefs.isAutoLogin(getBaseContext())) {
			new LoginTask().execute("http://cmschool.es.kr/");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.notice: {
			new LoadBlog().execute("http://cmschool.es.kr/blog/?blogCode=2014010105&m=01&bbsCode=271");// 공지사항
			return true;
		}
		case R.id.alarm: {
			new LoadBlog().execute("http://cmschool.es.kr/blog/?blogCode=2014010105&m=01&bbsCode=296");// 알림장
			return true;
		}
		case R.id.week: {

			new LoadBlog().execute("http://cmschool.es.kr/blog/?blogCode=2014010105&m=01&bbsCode=305");//
			return true;
		}
		case R.id.letter: {

			new LoadBlog().execute("http://cmschool.es.kr/blog/?blogCode=2014010105&m=01&bbsCode=389");//

			return true;
		}
		case R.id.album: {

			new LoadBlog().execute("http://cmschool.es.kr/blog/?blogCode=2014010105&m=01&bbsCode=297");//

			return true;
		}
		case R.id.login: {

			startActivity(new Intent(this, Prefs.class));

			new LoginTask().execute("http://cmschool.es.kr/");
			return true;
		}
		}
		return super.onOptionsItemSelected(item);
	}

	public class listOnItemClickListener implements OnItemClickListener {

		@SuppressWarnings("unchecked")
		public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

			if (listview.getCount() <= 0 || pos < 0 || listview.getCount() <= pos) {
				return;
			}

			HashMap<String, String> map = (HashMap<String, String>) listview.getItemAtPosition(pos);

			Log.e("cmschool", "index=" + map.get("index"));
			Log.e("cmschool", "urls=" + map.get("urls"));
			Log.e("cmschool", "title=" + map.get("title"));

			// new
			// LoadBlog().execute("http://cmschool.es.kr/blog/?blogCode=2014010105&m=01&bbsCode=271&m=01&mode=view&idx=8104&page=1");
			new viewBoard().execute(map.get("urls"));

		}
	}

	public class fileOnItemClickListener implements OnItemClickListener {

		@SuppressWarnings("unchecked")
		public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

			if (listview2.getCount() <= 0 || pos < 0 || listview2.getCount() <= pos) {
				return;
			}

			HashMap<String, String> map = (HashMap<String, String>) listview2.getItemAtPosition(pos);

			Log.e("cmschool", "index=" + map.get("index"));
			Log.e("cmschool", "urls=" + map.get("urls"));
			Log.e("cmschool", "title=" + map.get("title"));

			// new
			// LoadBlog().execute("http://cmschool.es.kr/blog/?blogCode=2014010105&m=01&bbsCode=271&m=01&mode=view&idx=8104&page=1");
			new FileDown().execute(map.get("urls"), map.get("title"));

		}
	}

	private class LoginTask extends AsyncTask<String, Void, Void> {
		private String Content;
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(MainActivity.this);

		protected void onPreExecute() {
			Dialog.setMessage("Login..");
			Dialog.show();
		}

		protected Void doInBackground(String... urls) {
			try {

				// HttpGet httpget = new HttpGet(urls[0]);
				ResponseHandler<String> responseHandler = new BasicResponseHandler();

				// 서버에 전달할 파라메터 세팅
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("_page", "10001"));
				nameValuePairs.add(new BasicNameValuePair("_action", "login"));
				nameValuePairs.add(new BasicNameValuePair("userid", Prefs.getUid(getBaseContext())));
				nameValuePairs.add(new BasicNameValuePair("userpw", Prefs.getPwd(getBaseContext())));

				// 응답시간이 5초가 넘으면 timeout 처리하려면 아래 코드의 커맨트를 풀고 실행한다.
				// HttpParams params = http.getParams();
				// HttpConnectionParams.setConnectionTimeout(params, 5000);
				// HttpConnectionParams.setSoTimeout(params, 5000);

				// HTTP를 통해 서버에 요청을 전달한다.
				// 요청에 대한결과는 responseHandler의 handleResponse()메서드가 호출되어 처리한다.
				// 서버에 전달되는 파라메터값을 인코딩하기위해 UrlEncodedFormEntity() 메서드를 사용한다.
				HttpPost httpPost = new HttpPost(urls[0]);
				UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
				httpPost.setEntity(entityRequest);
				Content = Client.execute(httpPost, responseHandler);

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
				Toast.makeText(MainActivity.this, Error, Toast.LENGTH_SHORT).show();
			} else {

				if (Content.indexOf("님!!") > 0) {

					new LoadBlog().execute("http://cmschool.es.kr/blog/?blogCode=2014010105&m=01&bbsCode=271");
				}
			}
		}
	}

	private class LoadBlog extends AsyncTask<String, Void, Void> {
		private String Content;
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(MainActivity.this);

		protected void onPreExecute() {
			Dialog.setMessage("Loading Board List..");
			Dialog.show();
		}

		protected Void doInBackground(String... urls) {
			try {

				HttpGet httpget = new HttpGet(urls[0]);
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				Content = Client.execute(httpget, responseHandler);

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
				Toast.makeText(MainActivity.this, Error, Toast.LENGTH_SHORT).show();
			} else {

				try {
					Content = Content.substring(Content.indexOf("<div class=\"board_list\">"), Content.indexOf("<!-- //content -->"));
					Content = Content.substring(Content.indexOf("<tbody>"), Content.indexOf("</tbody>"));
				} catch (Exception e) {

				}
				try {
					// Content = Content.replace("<a href=",
					// "<a target='_self' href=");
					// Content =
					// "<!DOCTYPE html><html><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1'><body>"
					// + Content;
					// Content += "</body></html>";

					List<String> link = new ArrayList<String>();
					String opentag = "<a href=";
					String closetag = ">";
					String closetag2 = "</a>";
					String linkurl = "";
					int sidx, eidx, idx = 0;

					ArrayList<HashMap<String, String>> mList = new ArrayList<HashMap<String, String>>();
					while (Content.indexOf(opentag) >= 0) {
						sidx = Content.indexOf(opentag);
						eidx = Content.indexOf(closetag, Content.indexOf(opentag));

						linkurl = Content.substring(sidx + opentag.length() + 1, eidx - 1);
						linkurl = linkurl.replace("&amp;", "&");
						link.add("http://cmschool.es.kr" + linkurl);
						Content = Content.substring(eidx);

						HashMap<String, String> item = new HashMap<String, String>();

						item.put("index", ++idx + "");
						item.put("urls", "http://cmschool.es.kr" + linkurl);
						item.put("title", Content.substring(0, Content.indexOf(closetag2)));

						mList.add(item);

						Log.d("cmschool", link.get(link.size() - 1));
						Log.d("cmschool", item.toString());

					}

					/* html 태그 제거. */
					/*
					 * Matcher matcher =
					 * Pattern.compile("<(\"[^\"]*\"|'[^']*'|[^'\">])*>"
					 * ).matcher(""); StringBuffer result = new StringBuffer();
					 * matcher.reset(Content); while (matcher.find()) {
					 * matcher.appendReplacement(result, ""); }
					 * matcher.appendTail(result); Content = result.toString();
					 */

					SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), mList, R.layout.list_item, new String[] { "index", "title" }, new int[] { R.id.index, R.id.title });

					listview.setAdapter(adapter);

					new viewBoard().execute((String) link.get(0));

					// textview.setText(link.toString());
					// webview.loadDataWithBaseURL("http://cmschool.es.kr/",
					// link.toString(), "text/html", "UTF-8", "");
				} catch (Exception e) {

				}
			}
		}
	}

	private class viewBoard extends AsyncTask<String, Void, Void> {
		private String Content;
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(MainActivity.this);

		protected void onPreExecute() {
			Dialog.setMessage("View Board Contents..");
			Dialog.show();
		}

		protected Void doInBackground(String... urls) {
			try {
				Log.d("cmschool", "open url is " + urls[0]);

				HttpGet httpget = new HttpGet(urls[0]);
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				Content = Client.execute(httpget, responseHandler);

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
				Toast.makeText(MainActivity.this, Error, Toast.LENGTH_SHORT).show();
			} else {
				String attach = "";

				try {

					Content = Content.substring(Content.indexOf("<div class=\"board_view\">"), Content.indexOf("<!-- //content -->", Content.indexOf("<div class=\"board_view\">")));

					try {
						if (Content.indexOf("<div class=\"file_li\">") >= 0 && Content.indexOf(">0 Files <") < 0) {
							// Attach file exists

							attach = Content.substring(Content.indexOf("<div class=\"file_li\">"), Content.indexOf("</div>", Content.indexOf("<div class=\"file_li\">")));
							attach = attach.replace("=\"/", "=\"http://cmschool.es.kr/");

							ArrayList<HashMap<String, String>> mList = new ArrayList<HashMap<String, String>>();
							int idx = 0;
							String linkurl = "";
							while (attach.indexOf("<li") >= 0) {
								Log.d("cmschool", "attach is " + attach);
								linkurl = attach.substring(attach.indexOf("<li"), attach.indexOf("</li>", attach.indexOf("<li")));

								Log.d("cmschool", "linkurl is " + linkurl);

								if (linkurl.indexOf("<a href=") < 0) {

									attach = attach.substring(attach.indexOf("</li>", attach.indexOf("<li")) + 5);
									Log.d("cmschool", "skip attach is " + attach);
								} else {
									HashMap<String, String> item = new HashMap<String, String>();

									item.put("index", ++idx + "");

									item.put("urls", linkurl.substring(linkurl.indexOf("<a href=") + 9, linkurl.indexOf("\">", linkurl.indexOf("<a href="))));

									item.put("title", linkurl.substring(linkurl.indexOf("\">", linkurl.indexOf("<a href=")) + 2, linkurl.indexOf("</a>")));

									if (!"".equals((String) item.get("title"))) {
										mList.add(item);
									}

									Log.d("cmschool", "item is " + item);

									attach = attach.substring(attach.indexOf("</li>", attach.indexOf("<li")) + 5);

									Log.d("cmschool", "next attach is " + attach);
								}
							}

							if (mList.size() > 0) {
								SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), mList, R.layout.list_item, new String[] { "index", "title" }, new int[] { R.id.index, R.id.title });

								listview2.setAdapter(adapter);
								listview2.setVisibility(View.VISIBLE);
							} else {
								listview2.setVisibility(View.GONE);
							}
						} else {
							listview2.setVisibility(View.GONE);
						}
					} catch (Exception e) {
						e.printStackTrace();

						attach = "";
					}
					Content = Content.substring(Content.indexOf("<div class=\"substance\">"), Content.indexOf("<div class=\"borad_sel_bot2\">", Content.indexOf("<div class=\"substance\">")));

					Log.d("cmschool", "Content is " + Content);

					// Content =
					// "<!DOCTYPE html><html><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1'><body>"
					// + attach + "<br>" + Content;
					Content = "<!DOCTYPE html><html><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1'><body>" + Content;

					Log.d("cmschool", "Content is " + Content);

					Content += "</body></html>";

					Log.d("cmschool", "Content is " + Content);

					webview.loadDataWithBaseURL("http://cmschool.es.kr/", Content, "text/html", "UTF-8", "");

				} catch (Exception e) {

					e.printStackTrace();
				}

			}
		}
	}

	private class FileDown extends AsyncTask<String, String, Void> {
		private ProgressDialog Dialog = new ProgressDialog(MainActivity.this);
		private String filename = "noname.hwp";

		protected void onPreExecute() {
			Dialog.setMessage("다운로드 중입니다.");
			Dialog.setCancelable(false);
			Dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			Dialog.show();
		}

		protected Void doInBackground(String... args) {

			URL myFileUrl = null;
			try {

				String urls = args[0];

				try {
					// urls = urls.substring(0, urls.indexOf("?") + 1) +
					// URLEncoder.encode(urls.substring(urls.indexOf("?") + 1),
					// "utf-8");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				myFileUrl = new URL(urls.trim());
				filename = args[1];
			} catch (MalformedURLException e) {
				Log.e(Constants.TAG, e.getMessage());
				e.printStackTrace();
			}
			try {
				HttpResponse response = Client.execute(new HttpGet(args[0]));
				HttpEntity entity = response.getEntity();
				long max = entity.getContentLength();
				InputStream is = entity.getContent();
				// How do I write it?

				// HttpURLConnection conn = (HttpURLConnection)
				// myFileUrl.openConnection();
				// /conn.setDoInput(true);
				// conn.connect();
				// InputStream is = conn.getInputStream();

				// int max = conn.getContentLength();
				Dialog.setMax((int) max);

				// 다운 받는 파일의 경로는 sdcard/ 아래 이다.
				// 단, sdcard에 접근하려면 uses-permission에
				// android.permission.WRITE_EXTERNAL_STORAGE을 추가해야한다.
				String mPath = Environment.getExternalStorageDirectory() + "/" + filename;
				FileOutputStream fos;
				File f = new File(mPath);

				if (f.exists()) {
					Log.d(Constants.TAG, "file Exists!!!");
					if (f.delete()) {
						Log.d(Constants.TAG, "file delete success!!!");
					} else {
						Log.d(Constants.TAG, "file delete fail.");
					}
					f = new File(mPath);
				}

				Log.d(Constants.TAG, "file path = " + f.getPath());

				if (f.createNewFile()) {
					fos = new FileOutputStream(mPath);

					int read;
					byte data[] = new byte[1024];
					int readSize = 0;
					while ((read = is.read(data)) != -1) {
						readSize += read;
						Dialog.setProgress(readSize);
						// fos.write(read);
						fos.write(data, 0, read);
					}
					fos.flush();
					fos.close();
					is.close();
				} else {
					Log.d(Constants.TAG, "file create fail.");
				}
			} catch (IOException e) {
				Log.e(Constants.TAG, e.getMessage());
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(Void unused) {
			Dialog.dismiss();

			// 안드로이드 패키지 매니저를 통해 다운 받은 apk 파일을 처리하도록 한다.
			if (filename.indexOf(".apk") >= 0) {
				File apkFile = new File(Environment.getExternalStorageDirectory() + "/" + filename);
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
				startActivity(intent);
			}
		}
	}

}
