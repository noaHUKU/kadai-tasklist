//文字化け防止の「おまじない」
package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class EncodingFilter
 */
@WebFilter("/*")
//アプリケーションの すべての サーブレットで適用するフィルターという指定
public class EncodingFilter implements Filter {

    /**
     * Default constructor.
     */
    public EncodingFilter() {

    }

    /**
     * @see Filter#destroy()
     * 「（フィルタの処理が不要になったため）フィルタを破棄する」というときの処理
     */
    public void destroy() {

    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     * フィルタとしての実行内容
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");//
        response.setCharacterEncoding("UTF-8");

        chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     * フィルタの処理がはじめて実行されるときの処理
     */
    public void init(FilterConfig fConfig) throws ServletException {

    }

}