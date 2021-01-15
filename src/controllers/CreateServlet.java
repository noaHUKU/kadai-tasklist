//create（挿入処理）の作成。登録フォーム（new.jsp、タスク新規作成ページ）から送信されたものがここで処理されindex ページへリダイレクト（遷移）する。
package controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import models.validators.TaskValidator;
import utils.DBUtil;

/**
 * Servlet implementation class CreateServlet
 */
@WebServlet("/create")
public class CreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {//CSRF対策のチェック
            EntityManager em = DBUtil.createEntityManager();

            Task m = new Task();

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());//現在日時の情報を持つ日付型のオブジェクトを取得
            m.setCreated_at(currentTime);//カラムにセット
            m.setUpdated_at(currentTime);

            String content = request.getParameter("content");
            m.setContent(content);

            // バリデーションを実行してエラーがあったら新規登録のフォームに戻る
            List<String> errors = TaskValidator.validate(m);
            if(errors.size() > 0) {
                em.close();

                // フォームに初期値を設定、さらにエラーメッセージを送る
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("task", m);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/taskss/new.jsp");
                rd.forward(request, response);
            } else {


            em.getTransaction().begin();//トランザクション処理の開始
            em.persist(m);//データベースに保存
            em.getTransaction().commit();//データの新規登録を確定（コミット）
            request.getSession().setAttribute("flush", "登録が完了しました。"); //フラッシュメッセージをセッションスコープに追加
            em.close();//閉じる(DBに接続できる数は限られている)

            response.sendRedirect(request.getContextPath() + "/index");//indexページへリダイレクト（遷移）
            }
        }
    }

}
