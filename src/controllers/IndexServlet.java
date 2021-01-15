//データベースから複数のメッセージ情報を取得して一覧表示するサーブレット→ビューはindex.jsp
package controllers;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
        //DBを管理するオブジェクトの呼び出し

        //開くページ数を取得（デフォルトは１ページ目）
        int page =1;
        try{
            page = Integer.parseInt(request.getParameter("page"));//文字列から数値に変更
        }catch(NumberFormatException e){}//アプリケーションが文字列を数値型に変換しようとしたとき、文字列の形式が正しくない場合にスローされます。


        //最大件数と開始位置を指定してメッセージを取得
        //メソッドの引数にJPQLの文につけた名前(@NamedQueryのname)を指定→JPQLの文(一覧表示するデータを取得する)が実行
        List<Task> tasks = em.createNamedQuery("getAllTasks", Task.class)
                              .setFirstResult(15 * (page -1))//何件目からデータを取得するか（配列と同じ0番目から）
                              .setMaxResults(15)//「データの最大取得件数（今回は15件で固定）」を設定
                              .getResultList();//リスト形式で取得

        //全件数を取得
        long tasks_count = (long)em.createNamedQuery("getTasksCount", Long.class)
                .getSingleResult();//getSingleResult() という “1件だけ取得する” という命令を指定

        em.close();

        request.setAttribute("tasks", tasks);//データベースから取得した一覧をリクエストスコープにセット
        request.setAttribute("tasks_count", tasks_count); // 全件数をリクエストスコープへ
        request.setAttribute("page", page);               // ページ数をリクエストスコープへ

        // フラッシュメッセージの入れ替えと削除
        if(request.getSession().getAttribute("flush") != null) {//セッションスコープに"flush"（フラッシュメッセージ）がnullでない時
            request.setAttribute("flush", request.getSession().getAttribute("flush"));//リクエストスコープにセット
            request.getSession().removeAttribute("flush");//セッションスコープから"flush"削除
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/taskss/index.jsp");
        rd.forward(request, response);
    }

}
