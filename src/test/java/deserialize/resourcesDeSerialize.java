package deserialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"page", "per_page", "total", "total_pages", "data"})
@JsonIgnoreProperties({"support"})
public class resourcesDeSerialize {

    int page, per_page, total, total_pages;
    List<resoucesData> data;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<resoucesData> getData() {
        return data;
    }

    public void setData(List<resoucesData> data) {
        this.data = data;
    }
}
