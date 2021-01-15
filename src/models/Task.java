//データベースの各カラムに対応した変数をもつクラス DTOの作成
package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity

//JPQL(=少し特殊なSQL文（SELECT文）)を用意。
@NamedQueries({
  @NamedQuery(
          name = "getAllTasks",//queryのSELECT文の名前
          query = "SELECT m FROM Task AS m ORDER BY m.id DESC"//一覧表示するデータを取得する文
          ),//閉じ括弧の直後にカンマ、注意
  @NamedQuery(
          name = "getTasksCount",
          query = "SELECT COUNT(m) FROM Task AS m"//データベースのタスクデータの件数を取得する文
          )
})

@Table(name = "tasks")
public class Task {
   //ID
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //作成日時
    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    //更新日時
    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    //タスクの内容
    @Column(name = "content", length = 255, nullable = false)
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}