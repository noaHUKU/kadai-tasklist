//新規登録用のフォームの新規登録処理（SQLでいうと、INSERT文）を行うコントローラ
package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;

/**
 * Servlet implementation class NewServlet
 */
@WebServlet("/new")
public class NewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       /* EntityManager em = DBUtil.createEntityManager();
        em.getTransaction().begin();

        // Taskのインスタンス、mを生成
        Task m = new Task();

        // mの各フィールドにデータを代入

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());     // 現在の日時を取得
        m.setCreated_at(currentTime);
        m.setUpdated_at(currentTime);

        String content = "hello";
        m.setContent(content);


        // データベースに保存
        em.persist(m);
        em.getTransaction().commit();//データの新規登録を確定

        // 自動採番されたIDの値を表示
        response.getWriter().append(Integer.valueOf(m.getId()).toString());

        em.close();
        */


     // CSRF対策
        request.setAttribute("_token", request.getSession().getId());
        //フォームから hidden 要素で送られた値とセッションに格納された値が同一であれば送信を受け付けるようにする

        // おまじないとしてのインスタンスを生成、リクエストスコープに格納、入力内容の初期値エラー回避
        request.setAttribute("tasks", new Task());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/taskss/new.jsp");//ビューの呼び出し
        rd.forward(request, response);
     }



}
