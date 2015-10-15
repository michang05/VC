
package mobi.victorchandler.parser;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
/**
 * Class that parses SecurityQuestionList service
 * @author riveram
 *
 */
public class SecurityQuestionListParser extends BaseVcParser {

    private HashMap<Integer, String> secQuestionList;
    private String result;

    public SecurityQuestionListParser(String result) {
        this.result = result;
    }
/**
 * Get QuestionList responses an parses it
 * @return
 */
    public HashMap<Integer, String> getQuestionsList() {

        secQuestionList = new HashMap<Integer, String>();

        try {
            JSONObject json = new JSONObject(result);
            JSONArray listQuestions = json.getJSONArray(SECURITY_QUESTIONS);
            for (int i = 0; i < listQuestions.length(); i++) {
                JSONObject question = listQuestions.getJSONObject(i);
                secQuestionList.put(question.getInt(ID), question.getString(DESCRIPTION));

            }
        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }

        return secQuestionList;
    }

    @Override
    public List<?> parseJsonResponse() {
        // TODO Auto-generated method stub
        return null;
    }

}
