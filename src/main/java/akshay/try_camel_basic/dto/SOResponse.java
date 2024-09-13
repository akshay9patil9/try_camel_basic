package akshay.try_camel_basic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SOResponse {
    List<SOQuestion> items;

    public List<SOQuestion> getItems() {
        return items;
    }

    public void setItems(List<SOQuestion> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "SOResponse{" +
                "items=" + items +
                '}';
    }
}
