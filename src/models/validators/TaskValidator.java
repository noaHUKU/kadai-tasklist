//バリデーション（入力チェック）のクラス
package models.validators;

import java.util.ArrayList;
import java.util.List;

import models.Task;

public class TaskValidator {
 // バリデーションを実行する
    public static List<String> validate(Task m) {
        List<String> errors = new ArrayList<String>();

        String content_error = validateContent(m.getContent());
        if(!content_error.equals("")) {//cotent_errorが空欄ではない時＝エラーメッセージが返されている時
            errors.add(content_error);//エラーリストに追加。
        }

        return errors;
    }

    // メッセージの必須入力チェック
    private static String validateContent(String content) {
        if(content == null || content.equals("")) {
            return "タスクを入力してください。";//contentがnullまたは空欄のときに返す
        }

        return "";//データがある時、空欄で返す。
    }

}
