//package mobi.victorchandler.task;
//
//import mobi.victorchandler.BetSlip;
//import mobi.victorchandler.model.Multiple;
//import mobi.victorchandler.model.Single;
//import mobi.victorchandler.util.DataHelper;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.params.HttpProtocolParams;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.os.AsyncTask;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//public class DepositTask extends AsyncTask<Void, Void, Void> {
//
//	BetSlip betslip;
//
//	public DepositTask(BetSlip betslip) {
//		this.betslip = betslip;
//	}
//
//	@Override
//	protected Void doInBackground(Void... arg0) {
//
//		try {
//			HttpPost post = new HttpPost("http://172.23.0.128:8080/"
//                + Locale.getDefault().toString() + "en/quickdeposit");
//			HttpProtocolParams.setUseExpectContinue(post.getParams(), false);
//			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//			// for (int i = 0; i < priceids.length; i++) {
//			// nameValuePairs.add(new BasicNameValuePair("singles[" + i +
//			// "][outcomeId]", outcomeids[i]));
//			// nameValuePairs.add(new BasicNameValuePair("singles[" + i +
//			// "][priceId]", priceids[i]));
//			// nameValuePairs.add(new BasicNameValuePair("singles[" + i +
//			// "][priceFormat]", "2"));
//			// }
//			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//			HttpResponse response = new DefaultHttpClient().execute(post);
//			HttpEntity entity = response.getEntity();
//			if (entity != null) {
//				InputStream instream = entity.getContent();
//				String result = DataHelper.convertStreamToString(instream);
//				instream.close();
//				JSONObject json = new JSONObject(result);
//				JSONArray jsonarray;
//
//				jsonarray = json.getJSONArray("singles");
//				ArrayList<Single> resultsingles = new ArrayList<Single>(jsonarray.length());
//				Single single;
//				
//				for (int i = 0; i < jsonarray.length(); i++) {
//					single = new Single();
//					JSONObject object = jsonarray.getJSONObject(i).getJSONObject("outcome");
//					single.setOutcomeid(object.getString("id"));
//					single.setPriceid(object.getJSONObject("price").getString("id"));
//					single.setDecimalprice(object.getJSONObject("price").getDouble("decimal"));
//					single.setFormattedprice(object.getJSONObject("price").getString("formatted"));
//					resultsingles.add(single);
//				}
//
//				jsonarray = json.getJSONArray("multiples");
//				ArrayList<Multiple> resultmultiples = new ArrayList<Multiple>(jsonarray.length());
//				Multiple multiple;
//				for (int i = 0; i < jsonarray.length(); i++) {
//					 multiple = new Multiple();
//					JSONObject object = jsonarray.getJSONObject(i);
//					multiple.setDescription(object.getString("description"));
//					multiple.setMultiplicity(object.getInt("multiplicity"));
//					JSONObject win = object.getJSONObject("win");
//					multiple.setWinfactor(win.getDouble("multiplicationFactor"));
//					multiple.setWinid(win.getInt("betTypeId"));
//					JSONObject ew = object.getJSONObject("ew");
//					multiple.setEwfactor(ew.getDouble("multiplicationFactor"));
//					multiple.setEachway(ew.getBoolean("enabled"));
//					multiple.setEwid(ew.getInt("betTypeId"));
//					resultmultiples.add(multiple);
//				}
//				// resultbets = new Object[] { resultsingles, resultmultiples };
//			}
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}
//
//	@Override
//	protected void onPreExecute() {
//		betslip.showDialog(BetSlip.DIALOG_PROCESSCREDIT);
//		super.onPreExecute();
//	}
//
//	@Override
//	protected void onPostExecute(Void result) {
//		betslip.dismissDialog(BetSlip.DIALOG_PROCESSCREDIT);
//		//TODO continue here
//		// if succesful {
//		// betslip.removeDialog(BetSlip.DIALOG_ADDCREDIT);
//		// new SubmitTask(betslip).execute();
//		// } else
//		betslip.showDialog(BetSlip.DIALOG_CREDITERROR);
//		super.onPostExecute(result);
//	}
//
//}
