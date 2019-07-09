package models;

import java.util.Objects;

public class News {
    private String news;
    private int departmentId;
    private int id;

    public News(String news, int departmentId) {
        this.news = news;
        this.departmentId = departmentId;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news1 = (News) o;
        return departmentId == news1.departmentId &&
                id == news1.id &&
                Objects.equals(news, news1.news);
    }

    @Override
    public int hashCode() {
        return Objects.hash(news, departmentId, id);
    }
}
