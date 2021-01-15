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

        List<Task> tasks = em.createNamedQuery("getAllTasks", Task.class).getResultList();
        //メソッドの引数①JPQLの文につけた名前(@NamedQueryのname)②クラスを指定→JPQLの文(一覧表示するデータを取得する)が実行。getResultList()でリスト形式で取得、リストtasksに入れる

        em.close();

        request.setAttribute("tasks", tasks);//データベースから取得した一覧をリクエストスコープにセット

        // フラッシュメッセージの入れ替えと削除
        if(request.getSession().getAttribute("flush") != null) {//セッションスコープに"flush"（フラッシュメッセージ）がnullでない時
            request.setAttribute("flush", request.getSession().getAttribute("flush"));//リクエストスコープにセット
            request.getSession().removeAttribute("flush");//セッションスコープから"flush"削除
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/taskss/index.jsp");
        rd.forward(request, response);
    }

}
