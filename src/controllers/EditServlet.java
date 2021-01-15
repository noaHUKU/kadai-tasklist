//修正する機能の作成。edit（編集画面）のサーブレット
package controllers;

import java.io.IOException;

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
 * Servlet implementation class EditServlet
 */
@WebServlet("/edit")
public class EditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        // 該当のIDのタスク1件のみをデータベースから取得
        Task m = em.find(Task.class, Integer.parseInt(request.getParameter("id")));

        em.close();

        // リクエストスコープに登録
        request.setAttribute("task", m);//データベースから取得した該当のIDのタスク1件を登録
        request.setAttribute("_token", request.getSession().getId());//セッションID

        // データが存在しているときのみ（NullPointerExceptionで処理を止めない為の処理）
        // タスクIDをセッションスコープに登録（/update にもデータを送信する（画面移動が一回ではない）のでセッションスコープ）
        if(m != null) {
            request.getSession().setAttribute("task_id", m.getId());
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/taskss/edit.jsp");
        rd.forward(request, response);
    }
}